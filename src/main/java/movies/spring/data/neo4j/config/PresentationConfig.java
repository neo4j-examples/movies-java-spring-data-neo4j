package movies.spring.data.neo4j.config;

import movies.spring.data.neo4j.movies.Actor;
import movies.spring.data.neo4j.movies.Movie;
import movies.spring.data.neo4j.movies.Person;
import movies.spring.data.neo4j.users.User;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy.RepositoryDetectionStrategies;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

/**
 * Not directly related to Spring Data Neo4j: We want to reduce the API service by just selectively exporting our
 * repositories.
 * <p>We also massage the response structure into the same form which the original SDN-OGM demo had.
 *
 * @author Michael J. Simons
 */
@Configuration(proxyBeanMethods = false)
public class PresentationConfig {

	@Bean
	public RepositoryRestConfigurer repositoryRestConfigurer(MoviesModule moviesModuel) {

		return new RepositoryRestConfigurer() {

			@Override
			public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
				config.setRepositoryDetectionStrategy(RepositoryDetectionStrategies.ANNOTATED);
				config.exposeIdsFor(Person.class, Movie.class, User.class);
			}
		};
	}

	@Bean
	public RepresentationModelProcessor<EntityModel<Actor>> actorModelProcessor(EntityLinks entityLinks) {

		// Don't replace this with a lambda, the type information will get lost otherwise.
		return new RepresentationModelProcessor<EntityModel<Actor>>() {
			@Override public EntityModel<Actor> process(EntityModel<Actor> model) {

				var person = model.getContent().getPerson();
				return model.add(entityLinks.linkForItemResource(Person.class, person.getName()).withRel("person"));
			}
		};
	}

	@Bean
	public MoviesModule moviesModule() {
		return new MoviesModule();
	}
}
