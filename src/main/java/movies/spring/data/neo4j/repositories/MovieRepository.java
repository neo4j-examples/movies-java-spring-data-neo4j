package movies.spring.data.neo4j.repositories;

import java.util.Collection;

import movies.spring.data.neo4j.domain.Movie;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Michael Hunger
 * @author Mark Angrish
 */
@Repository
public interface MovieRepository extends Neo4jRepository<Movie, Long> {

    @Query("MATCH (m:Movie {title: {title}}) RETURN m LIMIT 1")
	Movie findByTitle(@Param("title") String title);

    @Query("MATCH (m:Movie) WHERE m.title =~ '(?i).*matrix*.' RETURN m")
	Collection<Movie> findByTitleLike(@Param("title") String title);

//    @Query("MATCH (m:Movie)-[r:ACTED_IN]-(p:Person) WHERE m.name = {0} RETURN m, collect(r), collect(p)")
//    Collection<Person> findPersons(String name);

	@Query("MATCH (m:Movie)<-[r:ACTED_IN]-(a:Person) RETURN m,r,a LIMIT {limit}")
	Collection<Movie> graph(@Param("limit") int limit);
}