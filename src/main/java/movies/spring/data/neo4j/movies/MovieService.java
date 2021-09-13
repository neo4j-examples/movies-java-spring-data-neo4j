package movies.spring.data.neo4j.movies;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.Value;
import org.neo4j.driver.types.TypeSystem;
import org.springframework.data.neo4j.core.DatabaseSelectionProvider;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Michael Hunger
 * @author Mark Angrish
 * @author Jennifer Reif
 * @author Michael J. Simons
 */
@Service
public class MovieService {

	private final MovieRepository movieRepository;

	private final Neo4jClient neo4jClient;

	private final Driver driver;

	private final DatabaseSelectionProvider databaseSelectionProvider;

	MovieService(MovieRepository movieRepository,
				 Neo4jClient neo4jClient,
				 Driver driver,
				 DatabaseSelectionProvider databaseSelectionProvider) {

		this.movieRepository = movieRepository;
		this.neo4jClient = neo4jClient;
		this.driver = driver;
		this.databaseSelectionProvider = databaseSelectionProvider;
	}

	public MovieDetailsDto fetchDetailsByTitle(String title) {
		return this.neo4jClient
				.query("" +
						"MATCH (movie:Movie {title: $title}) " +
						"OPTIONAL MATCH (person:Person)-[r]->(movie) " +
						"WITH movie, COLLECT({ name: person.name, job: REPLACE(TOLOWER(TYPE(r)), '_in', ''), role: HEAD(r.roles) }) as cast " +
						"RETURN movie { .title, cast: cast }"
				)
				.in(database())
				.bindAll(Map.of("title", title))
				.fetchAs(MovieDetailsDto.class)
				.mappedBy(this::toMovieDetails)
				.one()
				.orElse(null);
	}

	public int voteInMovieByTitle(String title) {
		return this.neo4jClient
				.query( "MATCH (m:Movie {title: $title}) " +
						"WITH m, coalesce(m.votes, 0) AS currentVotes " +
						"SET m.votes = currentVotes + 1;" )
				.in( database() )
				.bindAll(Map.of("title", title))
				.run()
				.counters()
				.propertiesSet();
	}

	public List<MovieResultDto> searchMoviesByTitle(String title) {
		return this.movieRepository.findSearchResults(title)
				.stream()
				.map(MovieResultDto::new)
				.collect(Collectors.toList());
	}

	/**
	 * This is an example of when you might want to use the pure driver in case you have no need for mapping at all, neither in the
	 * form of the way the {@link org.springframework.data.neo4j.core.Neo4jClient} allows and not in form of entities.
	 *
	 * @return A representation D3.js can handle
	 */
	public Map<String, List<Object>> fetchMovieGraph() {

		var nodes = new ArrayList<>();
		var links = new ArrayList<>();

		try (Session session = sessionFor(database())) {
			var records = session.readTransaction(tx -> tx.run(""
				+ " MATCH (m:Movie) <- [r:ACTED_IN] - (p:Person)"
				+ " WITH m, p ORDER BY m.title, p.name"
				+ " RETURN m.title AS movie, collect(p.name) AS actors"
			).list());
			records.forEach(record -> {
				var movie = Map.of("label", "movie", "title", record.get("movie").asString());

				var targetIndex = nodes.size();
				nodes.add(movie);

				record.get("actors").asList(Value::asString).forEach(name -> {
					var actor = Map.of("label", "actor", "title", name);

					int sourceIndex;
					if (nodes.contains(actor)) {
						sourceIndex = nodes.indexOf(actor);
					} else {
						nodes.add(actor);
						sourceIndex = nodes.size() - 1;
					}
					links.add(Map.of("source", sourceIndex, "target", targetIndex));
				});
			});
		}
		return Map.of("nodes", nodes, "links", links);
	}

	private Session sessionFor(String database) {
		if (database == null) {
			return driver.session();
		}
		return driver.session(SessionConfig.forDatabase(database));
	}

	private String database() {
		return databaseSelectionProvider.getDatabaseSelection().getValue();
	}

	private MovieDetailsDto toMovieDetails(TypeSystem ignored, org.neo4j.driver.Record record) {
		var movie = record.get("movie");
		return new MovieDetailsDto(
				movie.get("title").asString(),
				movie.get("cast").asList((member) -> {
					var result = new CastMemberDto(
							member.get("name").asString(),
							member.get("job").asString()
					);
					var role = member.get("role");
					if (role.isNull()) {
						return result;
					}
					return result.withRole(role.asString());
				})
		);
	}
}
