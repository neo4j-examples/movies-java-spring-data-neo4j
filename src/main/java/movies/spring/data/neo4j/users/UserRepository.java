package movies.spring.data.neo4j.users;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Probably not the best idea to expose a user repository as an unprotected REST resource in a real life application.
 * It is done so that we don't need to write controller code.
 *
 * @author Michael J. Simons
 */
@RepositoryRestResource(path = "users", excerptProjection = UserProjection.class)
@Tag(name = "users")
public interface UserRepository extends CrudRepository<User, String> {
}
