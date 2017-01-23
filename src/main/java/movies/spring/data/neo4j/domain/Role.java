package movies.spring.data.neo4j.domain;


import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@JsonIdentityInfo(generator=JSOGGenerator.class)
@RelationshipEntity(type = "ACTED_IN")
public class Role {

	@GraphId
	private Long id;

	private Collection<String> roles;

	@StartNode
	private Person person;

	@EndNode
	private Movie movie;

	public Role() {
	}

	public Collection<String> getRoles() {
		return roles;
	}

	public Person getPerson() {
		return person;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setRoles(Collection<String> roles) {
		this.roles = roles;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}
}
