package movies.spring.data.neo4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @author Michael Hunger
 * @author Mark Angrish
 */
@SpringBootApplication
@EntityScan("movies.spring.data.neo4j.domain")
public class SampleMovieApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleMovieApplication.class, args);
	}
}
