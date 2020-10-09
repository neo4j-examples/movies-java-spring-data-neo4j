package movies.spring.data.neo4j.movies;

import java.util.List;

import org.springframework.data.repository.Repository;

/**
 * @author Michael J. Simons
 */
public interface GenreRepository extends Repository<Genre, Long> {

	List<Genre> findAll();
}
