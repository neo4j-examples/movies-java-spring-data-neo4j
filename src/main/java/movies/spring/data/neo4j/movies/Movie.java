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

package movies.spring.data.neo4j.movies;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.List;

/**
 * @author Mark Angrish
 * @author Michael J. Simons
 */
@Node
public class Movie {

	@Id
	private final String title;

	private final String tagline;

	private Integer released;

	private Long votes;

	public Movie(String title, String tagline) {
		this.title = title;
		this.tagline = tagline;
	}

	public String getTitle() {
		return title;
	}

	public String getTagline() {
		return tagline;
	}

	public Integer getReleased() {
		return released;
	}

	public Long getVotes() {
		return votes;
	}

	public void setReleased(Integer released) {
		this.released = released;
	}

	public void setVotes(Long votes) {
		this.votes = votes;
	}
}
