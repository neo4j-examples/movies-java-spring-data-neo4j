package movies.spring.data.neo4j.repositories;

import movies.spring.data.neo4j.domain.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pdtyreus
 */
@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

}
