package movies.spring.data.neo4j.domain;



import org.neo4j.ogm.annotation.*;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

@JsonIdentityInfo(generator=JSOGGenerator.class)
@RelationshipEntity(type = "ACTED_IN")
public class Role {
    @GraphId
    Long id;
    Collection<String> roles;
    @StartNode Person person;
    @EndNode   Movie movie;

    public Collection<String> getRoles() {
        return roles;
    }

    public Person getPerson() {
        return person;
    }

    public Movie getMovie() {
        return movie;
    }
}
