package movies.spring.data.neo4j.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * @author Mark Angrish
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@NodeEntity
public class Movie {

	@GraphId
	private Long id;

	private String title;

	private int released;

	private String tagline;

	@Relationship(type = "ACTED_IN", direction = Relationship.INCOMING)
	private List<Role> roles = new ArrayList<>();

	public Movie() {
	}

	public Movie(String title, int released) {

		this.title = title;
		this.released = released;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public int getReleased() {
		return released;
	}

	public String getTagline() {
		return tagline;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void addRole(Role role) {
		this.roles.add(role);
	}
}
