package movies.spring.data.neo4j.domain;


import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * @author Mark Angrish
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@RelationshipEntity(type = "ACTED_IN")
public class Role {

	@GraphId
	private Long id;

	private Collection<String> roles = new ArrayList<>();

	@StartNode
	private Person person;

	@EndNode
	private Movie movie;

	public Role() {
	}

	public Role(Movie movie, Person actor) {
		this.movie = movie;
		this.person = actor;
	}

	public Long getId() {
		return id;
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

	public void addRoleName(String name) {
		this.roles.add(name);
	}
}
