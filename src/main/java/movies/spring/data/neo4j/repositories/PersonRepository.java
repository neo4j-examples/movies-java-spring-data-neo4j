package movies.spring.data.neo4j.repositories;

import movies.spring.data.neo4j.domain.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pdtyreus
 * @author Mark Angrish
 */
@Repository
public interface PersonRepository extends Neo4jRepository<Person, Long> {

    //@Query("MATCH (p:Person)WHERE p.name = {0} RETURN p")
    Person findByName(String name);

//    @Query("MATCH (p:Person)-[r:ACTED_IN]-(m:Movie) WHERE p.name = {0} RETURN p, collect(r), collect(m)")
//    Collection<Movie> findMovies(String name);
}