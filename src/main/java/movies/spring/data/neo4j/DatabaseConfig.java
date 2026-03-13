/*
 * Copyright (c) "Neo4j"
 * Neo4j Sweden AB [https://neo4j.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package movies.spring.data.neo4j;

import org.neo4j.cypherdsl.core.renderer.Dialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.DatabaseSelection;
import org.springframework.data.neo4j.core.DatabaseSelectionProvider;

@Configuration
public class DatabaseConfig {

	/**
	 * Neo4j version-aware database selector.
	 * <p>
	 * This is only needed for applications running with both Neo4j versions 3 (where multitenancy is not available) and 4.
	 * <p>
	 * Ideally, one would run instead (where driver is an instance of org.neo4j.driver.Driver):
	 * <code>
	 * String neo4jVersion = driver.session().run("RETURN 1").consume().server().version();
	 * </code>
	 * ... but this requires permissions that the user configured by default does not have.
	 *
	 * @param database the configured database name
	 * @return DatabaseSelection the corresponding database name for Neo4j 4+ or undefined otherwise
	 */
	@Bean
	DatabaseSelectionProvider databaseSelectionProvider(@Value("${spring.data.neo4j.database}") String database) {
		return () -> DatabaseSelection.byName(database);
	}

	@Bean
	org.neo4j.cypherdsl.core.renderer.Configuration cypherDslConfiguration() {

		return org.neo4j.cypherdsl.core.renderer.Configuration.newConfig()
			.withDialect(Dialect.NEO4J_5).build();
	}
}
