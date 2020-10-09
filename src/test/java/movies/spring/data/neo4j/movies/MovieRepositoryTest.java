package movies.spring.data.neo4j.movies;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.neo4j.harness.Neo4j;
import org.neo4j.harness.Neo4jBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Daniel Tyreus
 * @author Mark Angrish
 * @author Jasper Blues
 * @author Frantisek Hartman
 * @author Jennifer Reif
 * @author Michael J. Simons
 */
@DataNeo4jTest
@Transactional
public class MovieRepositoryTest {

	private static Neo4j embeddedDatabaseServer;

	@BeforeAll
	public static void initializeNeo4j() {

		embeddedDatabaseServer = Neo4jBuilders.newInProcessBuilder()
			.withDisabledServer() // Don't need Neos HTTP server
			.withFixture(""
				+ "CREATE (TheMatrix:Movie {title:'The Matrix', released:1999, tagline:'Welcome to the Real World'})\n"
				+ "CREATE (TheMatrixReloaded:Movie {title:'The Matrix Reloaded', released:2003, tagline:'Free your mind'})\n"
				+ "CREATE (TheMatrixRevolutions:Movie {title:'The Matrix Revolutions', released:2003, tagline:'Everything that has a beginning has an end'})\n"
				+ "CREATE (p:Person {name: 'Keanu Reeves', born: 1964}) -[:ACTED_IN {roles: ['Neo']}]-> (TheMatrix)\n"
			)
			.build();
	}

	@DynamicPropertySource
	static void neo4jProperties(DynamicPropertyRegistry registry) {

		registry.add("spring.data.neo4j.uri", embeddedDatabaseServer::boltURI);
		registry.add("spring.data.neo4j.username", () -> "neo4j");
		registry.add("spring.data.neo4j.password", () -> null);
	}


	/**
	 * Test of findByTitle method, of class MovieRepository.
	 */
	@Test
	public void testFindByTitle(@Autowired MovieRepository movieRepository) {

		String title = "The Matrix";
		assertThat(movieRepository.findOneByTitle(title)).hasValueSatisfying(m -> {
			assertThat(m.getReleased()).isEqualTo(1999);
			assertThat(m.getActors()).hasSize(1);
			var actor = m.getActors().get(0);
			assertThat(actor.getRoles()).containsExactly("Neo");
			assertThat(actor.getPerson()).extracting(Person::getName).isEqualTo("Keanu Reeves");
		});
	}

	/**
	 * Test of findByTitleContaining method, of class MovieRepository.
	 */
	@Test
	public void testFindByTitleContaining(@Autowired MovieRepository movieRepository) {
		String title = "Matrix Re";
		assertThat(movieRepository.findAllByTitleLikeIgnoreCase(title))
			.hasSize(2)
			.extracting(Movie::getTitle).containsExactlyInAnyOrder("The Matrix Reloaded", "The Matrix Revolutions");
	}
}