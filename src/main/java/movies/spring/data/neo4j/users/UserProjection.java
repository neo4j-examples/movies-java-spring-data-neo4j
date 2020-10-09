package movies.spring.data.neo4j.users;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "peng", types = User.class)
public interface UserProjection {

	String getName();

	 String getPassword();
}
