package movies.spring.data.neo4j.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.neo4j.config.EnableNeo4jAuditing;

/**
 * @author Michael J. Simons
 */
@Configuration(proxyBeanMethods = false)
@EnableNeo4jAuditing
public class AdditionalSpringDataConfig {

	/**
	 * A real application could for example return the authenticated user here.
	 *
	 * @return
	 */
	@Bean
	public AuditorAware<String> auditorProvider() {
		return () -> Optional.ofNullable(System.getProperty("user.name"));
	}
}
