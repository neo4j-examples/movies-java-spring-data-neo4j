package movies.spring.data.neo4j.domain;

import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@NodeEntity
public class Movie {

	@GraphId private Long id;

	private String title;

	private int released;

	private String tagline;

	@Relationship(type = "ACTED_IN", direction = Relationship.INCOMING)
	private List<Role> roles;


	public Movie() {
	}

	public Long getId() {
		return id;
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

	public void setTitle(String title) {
		this.title = title;
	}

	public void setReleased(int released) {
		this.released = released;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
