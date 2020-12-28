package movies.spring.data.neo4j.movies;

import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * This repository is indirectly used in the {@code movies.spring.data.neo4j.api.MovieController} via a dedicated movie service.
 * It is not a public interface to indicate that access is either through the rest resources or through the service.
 *
 * @author Michael Hunger
 * @author Mark Angrish
 * @author Michael J. Simons
 */
interface MovieRepository extends Repository<Movie, String> {

	@Query("MATCH (movie:Movie) WHERE movie.title CONTAINS $title RETURN movie")
	List<Movie> findSearchResults(@Param("title") String title);
}
