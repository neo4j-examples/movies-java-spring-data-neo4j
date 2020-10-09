package movies.spring.data.neo4j.movies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

/**
 * @author Michael J. Simons
 */
@RelationshipProperties
public class Actor {

	@TargetNode
	private final Person person;

	private List<String> roles = new ArrayList<>();

	public Actor(Person person) {
		this.person = person;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public Person getPerson() {
		return person;
	}
}
