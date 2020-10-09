package movies.spring.data.neo4j.movies;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * This repository is partially exported via Spring Data REST (the {@link #findOneByTitle(String)} and {@link #findAllByTitleLikeIgnoreCase(String)} methods).
 * It is indirectly used in the {@code movies.spring.data.neo4j.api.MovieController} via a dedicated movie service.
 * It is not a public interface to indicate that access is either through the rest resources or through the service.
 *
 * @author Michael Hunger
 * @author Mark Angrish
 * @author Michael J. Simons
 */
@RepositoryRestResource(path = "movies")
@Tag(name = "movies")
interface MovieRepository extends Repository<Movie, String> {

	List<Movie> findAll();

	Optional<Movie> findById(String title);

	Optional<Movie> findOneByTitle(String title);

	List<Movie> findAllByTitleLikeIgnoreCase(String title);

	@RestResource(exported = false)
	List<Movie> findAllByDirectorsName(String name);

	@RestResource(exported = false)
	List<Movie> findAllByActorsPersonName(String name);
}
