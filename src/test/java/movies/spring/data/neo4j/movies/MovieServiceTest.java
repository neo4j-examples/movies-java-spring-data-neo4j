package movies.spring.data.neo4j.movies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
class MovieServiceTest {

    private static final String PASSWORD = "foobar";

    @Container
    private static final Neo4jContainer<?> neo4jContainer = new Neo4jContainer<>("neo4j:" + env("NEO4J_VERSION", "4.2"))
            .withAdminPassword(PASSWORD);

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.neo4j.uri", neo4jContainer::getBoltUrl);
        registry.add("spring.neo4j.authentication.username", () -> "neo4j");
        registry.add("spring.neo4j.authentication.password", () -> PASSWORD);
        registry.add("spring.data.neo4j.database", () -> "neo4j");
    }

    @BeforeEach
    void setup(@Autowired Driver driver) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run("MATCH (n) DETACH DELETE n");
                tx.run(""
                        + "CREATE (TheMatrix:Movie {title:'The Matrix', released:1999, tagline:'Welcome to the Real World'})\n"
                        + "CREATE (TheMatrixReloaded:Movie {title:'The Matrix Reloaded', released:2003, tagline:'Free your mind'})\n"
                        + "CREATE (TheMatrixRevolutions:Movie {title:'The Matrix Revolutions', released:2003, tagline:'Everything that has a beginning has an end'})\n"
                        + "CREATE (p:Person {name: 'Keanu Reeves', born: 1964}) -[:ACTED_IN {roles: ['Neo']}]-> (TheMatrix)\n");
                return null;
            });
        }
    }

    @Test
    public void searches_movies_by_title(@Autowired MovieService service) {
        String title = "Matrix Re";
        assertThat(service.searchMoviesByTitle(title))
                .hasSize(2)
                .extracting(mr -> mr.getMovie().getTitle()).containsExactlyInAnyOrder("The Matrix Reloaded", "The Matrix Revolutions");
    }

    @Test
    public void fetches_movie_details(@Autowired MovieService service) {
        MovieDetailsDto details = service.fetchDetailsByTitle("The Matrix");

        assertThat(details.getTitle()).isEqualTo("The Matrix");
        assertThat(details.getCast()).containsExactly(new CastMemberDto("Keanu Reeves", "acted", "Neo"));
    }

    @Test
    public void fetches_d3_graph(@Autowired MovieService service) {
        Map<String, List<Object>> d3Graph = service.fetchMovieGraph();

        assertThat(d3Graph).isEqualTo(
                Map.of(
                        "links", List.of(
                                Map.of("source", 1, "target", 0)
                        ),
                        "nodes", List.of(
                                Map.of("label", "movie", "title", "The Matrix"),
                                Map.of("label", "actor", "title", "Keanu Reeves")
                        )
                ));
    }

    private static String env(String name, String defaultValue) {
        String value = System.getenv(name);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }
}