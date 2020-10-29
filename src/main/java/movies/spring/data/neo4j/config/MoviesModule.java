package movies.spring.data.neo4j.config;

import movies.spring.data.neo4j.movies.Actor;
import movies.spring.data.neo4j.movies.Movie;
import movies.spring.data.neo4j.movies.Person;
import movies.spring.data.neo4j.users.User;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * A Jackson module so that we don't have to add Jackson annotations directly to our domain.
 *
 * @author Michael J. Simons
 */
final class MoviesModule extends SimpleModule {

	MoviesModule() {
		setMixInAnnotation(Actor.class, ActorMixIn.class);
		setMixInAnnotation(Movie.class, MovieMixIn.class);
		setMixInAnnotation(User.class, UserMixIn.class);
	}

	abstract static class ActorMixIn {

		@JsonCreator ActorMixIn(Person person) {
		}

		@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
		abstract Person getPerson();
	}

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	abstract static class MovieMixIn {
	}

	abstract static class UserMixIn {

		@JsonCreator UserMixIn(@JsonProperty("name") String name) {
		}

		@JsonIgnore
		abstract String getHashedPassword();

		@JsonIgnore
		abstract String getApiKey();

		@JsonProperty(access = JsonProperty.Access.READ_ONLY)
		abstract LocalDateTime getCreatedAt();

		@JsonProperty(access = JsonProperty.Access.READ_ONLY)
		abstract String getCreatedBy();

		@JsonProperty(access = JsonProperty.Access.READ_ONLY)
		abstract LocalDateTime getModifiedAt();

		@JsonProperty(access = JsonProperty.Access.READ_ONLY)
		abstract String getModifiedBy();
	}

}
