package movies.spring.data.neo4j.repositories;

import static org.junit.Assert.*;

import java.util.Collection;

import movies.spring.data.neo4j.domain.Movie;
import movies.spring.data.neo4j.domain.Person;
import movies.spring.data.neo4j.domain.Role;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.harness.ServerControls;
import org.neo4j.harness.TestServerBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Daniel Tyreus
 * @author Mark Angrish
 * @author Jasper Blues
 * @author Frantisek Hartman
 * @author Jennifer Reif
 * @author Michael J. Simons
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class MovieRepositoryTest {

	private static ServerControls embeddedDatabaseServer;

	@BeforeClass
	public static void initializeNeo4j() {

		embeddedDatabaseServer = TestServerBuilders.newInProcessBuilder().newServer();
	}

	@DynamicPropertySource
	static void neo4jProperties(DynamicPropertyRegistry registry) {

		registry.add("spring.data.neo4j.uri", embeddedDatabaseServer::boltURI);
		registry.add("spring.data.neo4j.username", () -> "neo4j");
		registry.add("spring.data.neo4j.password", () -> null);
	}

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private PersonRepository personRepository;

	@Before
	public void setUp() {
		Movie matrix = new Movie("The Matrix", 1999, "Welcome to the Real World");

		movieRepository.save(matrix);

		Person keanu = new Person("Keanu Reeves", 1964);

		personRepository.save(keanu);

		Role neo = new Role(matrix, keanu);
		neo.addRoleName("Neo");

		matrix.addRole(neo);

		movieRepository.save(matrix);
	}

	/**
	 * Test of findByTitle method, of class MovieRepository.
	 */
	@Test
	public void testFindByTitle() {

		String title = "The Matrix";
		Movie result = movieRepository.findByTitle(title);
		assertNotNull(result);
		assertEquals(1999, result.getReleased());
	}

	/**
	 * Test of findByTitleContaining method, of class MovieRepository.
	 */
	@Test
	public void testFindByTitleContaining() {
		String title = "*Matrix*";
		Collection<Movie> result = movieRepository.findByTitleLike(title);
		assertNotNull(result);
		assertEquals(1, result.size());
	}

	/**
	 * Test of graph method, of class MovieRepository.
	 */
	@Test
	public void testGraph() {
		Collection<Movie> graph = movieRepository.findAllLimitBy(5);

		assertEquals(1, graph.size());

		Movie movie = graph.iterator().next();

		assertEquals(1, movie.getRoles().size());

		assertEquals("The Matrix", movie.getTitle());
		assertEquals("Keanu Reeves", movie.getRoles().iterator().next().getPerson().getName());
	}
}