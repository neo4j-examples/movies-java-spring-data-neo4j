package movies.spring.data.neo4j.movies;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.neo4j.harness.Neo4j;
import org.neo4j.harness.Neo4jBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

/**
 * Good test candidates for repositories are often times custom queries
 *
 * @author Michael J. Simons
 */
@DataNeo4jTest
public class PersonRepositoryTest {

	private static Neo4j embeddedDatabaseServer;

	@DynamicPropertySource
	static void neo4jProperties(DynamicPropertyRegistry registry) {

		registry.add("spring.neo4j.uri", embeddedDatabaseServer::boltURI);
		registry.add("spring.neo4j.authentication.username", () -> "neo4j");
		registry.add("spring.neo4j.authentication.password", () -> null);
	}

	@Test
	public void findAllThatActedInMovieShouldWork(@Autowired PeopleRepository peopleRepository) {

		assertThat(peopleRepository.findAllThatActedInMovie("The Matrix"))
			.hasSize(5);
	}

	@Test
	public void findBaconPeopleBetweenShouldWork(@Autowired PeopleRepository peopleRepository) {
		assertThat(peopleRepository.findBaconPeopleBetween("Kevin Bacon", "Emil Eifrem"))
			.hasSize(3);
	}

	@Test
	public void getDetailsByNameShouldWork(@Autowired PeopleRepository peopleRepository) {
		var personDetails = peopleRepository.getDetailsByName("Keanu Reeves");
		assertThat(personDetails).isNotNull();
		assertThat(personDetails.getActedIn()).hasSize(3);
		assertThat(personDetails.getRelated()).hasSize(5);
	}

	@BeforeAll
	static void initializeNeo4j() {

		embeddedDatabaseServer = Neo4jBuilders.newInProcessBuilder()
			.withDisabledServer() // Don't need Neos HTTP server
			.withFixture(
				"CREATE (TheMatrix:Movie {title:'The Matrix', released:1999, tagline:'Welcome to the Real World'})\n"
					+ "CREATE (Keanu:Person {name:'Keanu Reeves', born:1964})\n"
					+ "CREATE (Carrie:Person {name:'Carrie-Anne Moss', born:1967})\n"
					+ "CREATE (Laurence:Person {name:'Laurence Fishburne', born:1961})\n"
					+ "CREATE (Hugo:Person {name:'Hugo Weaving', born:1960})\n"
					+ "CREATE (LillyW:Person {name:'Lilly Wachowski', born:1967})\n"
					+ "CREATE (LanaW:Person {name:'Lana Wachowski', born:1965})\n"
					+ "CREATE (JoelS:Person {name:'Joel Silver', born:1952})\n"
					+ "CREATE (KevinB:Person {name:'Kevin Bacon', born:1958})\n"
					+ "CREATE\n"
					+ "(Keanu)-[:ACTED_IN {roles:['Neo']}]->(TheMatrix),\n"
					+ "(Carrie)-[:ACTED_IN {roles:['Trinity']}]->(TheMatrix),\n"
					+ "(Laurence)-[:ACTED_IN {roles:['Morpheus']}]->(TheMatrix),\n"
					+ "(Hugo)-[:ACTED_IN {roles:['Agent Smith']}]->(TheMatrix),\n"
					+ "(LillyW)-[:DIRECTED]->(TheMatrix),\n"
					+ "(LanaW)-[:DIRECTED]->(TheMatrix),\n"
					+ "(JoelS)-[:PRODUCED]->(TheMatrix)\n"
					+ "\n"
					+ "CREATE (Emil:Person {name:\"Emil Eifrem\", born:1978})\n"
					+ "CREATE (Emil)-[:ACTED_IN {roles:[\"Emil\"]}]->(TheMatrix)\n"
					+ "\n"
					+ "CREATE (TheMatrixReloaded:Movie {title:'The Matrix Reloaded', released:2003, tagline:'Free your mind'})\n"
					+ "CREATE\n"
					+ "(Keanu)-[:ACTED_IN {roles:['Neo']}]->(TheMatrixReloaded),\n"
					+ "(Carrie)-[:ACTED_IN {roles:['Trinity']}]->(TheMatrixReloaded),\n"
					+ "(Laurence)-[:ACTED_IN {roles:['Morpheus']}]->(TheMatrixReloaded),\n"
					+ "(Hugo)-[:ACTED_IN {roles:['Agent Smith']}]->(TheMatrixReloaded),\n"
					+ "(LillyW)-[:DIRECTED]->(TheMatrixReloaded),\n"
					+ "(LanaW)-[:DIRECTED]->(TheMatrixReloaded),\n"
					+ "(JoelS)-[:PRODUCED]->(TheMatrixReloaded)\n"
					+ "\n"
					+ "CREATE (TheMatrixRevolutions:Movie {title:'The Matrix Revolutions', released:2003, tagline:'Everything that has a beginning has an end'})\n"
					+ "CREATE\n"
					+ "(Keanu)-[:ACTED_IN {roles:['Neo']}]->(TheMatrixRevolutions),\n"
					+ "(Carrie)-[:ACTED_IN {roles:['Trinity']}]->(TheMatrixRevolutions),\n"
					+ "(Laurence)-[:ACTED_IN {roles:['Morpheus']}]->(TheMatrixRevolutions),\n"
					+ "(KevinB)-[:ACTED_IN {roles:['Unknown']}]->(TheMatrixRevolutions),\n"
					+ "(Hugo)-[:ACTED_IN {roles:['Agent Smith']}]->(TheMatrixRevolutions),\n"
					+ "(LillyW)-[:DIRECTED]->(TheMatrixRevolutions),\n"
					+ "(LanaW)-[:DIRECTED]->(TheMatrixRevolutions),\n"
					+ "(JoelS)-[:PRODUCED]->(TheMatrixRevolutions)\n"
			)
			.build();
	}

	@AfterAll
	static void stopNeo4j() {

		embeddedDatabaseServer.close();
	}
}