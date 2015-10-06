package movies.spring.data.neo4j.domain;

import org.neo4j.graphdb.Direction;
import org.neo4j.ogm.annotation.*;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@JsonIdentityInfo(generator=JSOGGenerator.class)
// tag::movie[]
@NodeEntity
public class Movie {
    @GraphId Long id;

    String title;

    int released;
    String tagline;

    @Relationship(type="ACTED_IN", direction = Relationship.INCOMING) Collection<Role> roles;

// end::movie[]

    public Movie() { }

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
}
