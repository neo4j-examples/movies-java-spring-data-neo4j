package movies.spring.data.neo4j.repositories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import movies.spring.data.neo4j.domain.Movie;
import movies.spring.data.neo4j.domain.Person;
import movies.spring.data.neo4j.domain.Role;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author pdtyreus
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository instance;
     @Autowired
    private PersonRepository personRepository;

    public MovieRepositoryTest() {
    }

    @Before
    public void initialize() {
       System.out.println("seeding embedded database");
       Movie matrix = new Movie();
       matrix.setTitle("The Matrix");
       matrix.setReleased(1999);

       instance.save(matrix);

       Person keanu = new Person();
       keanu.setName("Keanu Reeves");

       personRepository.save(keanu);

       Role neo = new Role();
       neo.setMovie(matrix);
       neo.setPerson(keanu);
       Collection<String> roleNames = new HashSet();
       roleNames.add("Neo");
       neo.setRoles(roleNames);

       List<Role> roles = new ArrayList();
       roles.add(neo);

       matrix.setRoles(roles);

       instance.save(matrix);
    }

    /**
     * Test of findByTitle method, of class MovieRepository.
     */
    @Test
    @DirtiesContext
    public void testFindByTitle() {

        System.out.println("findByTitle");
        String title = "The Matrix";
        Movie result = instance.findByTitle(title);
        assertNotNull(result);
        assertEquals(1999, result.getReleased());
    }

    /**
     * Test of findByTitleContaining method, of class MovieRepository.
     */
    @Test
    @DirtiesContext
    public void testFindByTitleContaining() {
        System.out.println("findByTitleContaining");
        String title = "Matrix";
        Collection<Movie> result = instance.findByTitleContaining(title);
        assertNotNull(result);
        assertEquals(1,result.size());
    }

    /**
     * Test of graph method, of class MovieRepository.
     */
    @Test
    @DirtiesContext
    public void testGraph() {
        System.out.println("graph");
//        List<Map<String,Object>> graph = instance.graph(5);

//        assertEquals(1,graph.size());
//
//        Map<String,Object> map = graph.get(0);

//        assertEquals(2,map.size());
//
//        String[] cast = (String[])map.get("cast");
//        String movie = (String)map.get("movie");
//
//        assertEquals("The Matrix",movie);
//        assertEquals("Keanu Reeves", cast[0]);
    }
}
