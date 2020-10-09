package movies.spring.data.neo4j.movies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

/**
 * @author Mark Angrish
 * @author Michael J. Simons
 */
@Node
public class Movie {

	@Id
	private final String title;

	@Property("tagline")
	private final String description;

	@Relationship(type = "ACTED_IN", direction = Direction.INCOMING)
	private List<Actor> actors = new ArrayList<>();

	@Relationship(type = "DIRECTED", direction = Direction.INCOMING)
	private List<Person> directors = new ArrayList<>();

	private Integer released;

	public Movie(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public List<Actor> getActors() {
		return actors;
	}

	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}

	public String getDescription() {
		return description;
	}

	public List<Person> getDirectors() {
		return directors;
	}

	public Integer getReleased() {
		return released;
	}

	public void setReleased(Integer released) {
		this.released = released;
	}
}
