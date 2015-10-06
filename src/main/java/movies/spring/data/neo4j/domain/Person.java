package movies.spring.data.neo4j.domain;


import org.neo4j.ogm.annotation.*;

import java.util.Collection;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

@JsonIdentityInfo(generator=JSOGGenerator.class)
@NodeEntity
public class Person {
    @GraphId Long id;

    private String name;
    private int born;

    @Relationship(type = "ACTED_IN")
    Collection<Movie> movies;

    public Person() { }

    public String getName() {
        return name;
    }

    public int getBorn() {
        return born;
    }

    public Collection<Movie> getMovies() {
        return movies;
    }
}
