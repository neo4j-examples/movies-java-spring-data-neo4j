package movies.spring.data.neo4j.movies;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.stereotype.Service;

/**
 * @author Michael Hunger
 * @author Mark Angrish
 * @author Jennifer Reif
 * @author Michael J. Simons
 */
@Service
public class MovieService {

	private final MovieRepository movieRepository;

	private final Driver driver;

	MovieService(MovieRepository movieRepository, Driver driver) {
		this.movieRepository = movieRepository;
		this.driver = driver;
	}

	public List<Movie> getDirectedBy(String name) {
		return movieRepository.findAllByDirectorsName(name);
	}

	public List<Movie> getActedInBy(String name) {
		return movieRepository.findAllByActorsPersonName(name);
	}

	/**
	 * This is an example of when you might want to use the pure driver in case you have no need for mapping at all, neither in the
	 * form of the way the {@link org.springframework.data.neo4j.core.Neo4jClient} allows and not in form of entities.
	 *
	 * @return A representation D3.js can handle
	 */
	public Map<String, List<Object>> f() {

		var nodes = new ArrayList<>();
		var links = new ArrayList<>();

		try (Session session = driver.session()) {
			var records = session.readTransaction(tx -> tx.run(""
				+ " MATCH (m:Movie) <- [r:ACTED_IN] - (p:Person)"
				+ " WITH m, p ORDER BY m.title, p.name"
				+ " RETURN m.title AS movie, collect(p.name) AS actors"
			).list());
			records.forEach(record -> {
				var movie = Map.of("label", "movie", "title", record.get("movie").asString());

				var targetIndex = nodes.size();
				nodes.add(movie);

				record.get("actors").asList(v -> v.asString()).forEach(name -> {
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
}
