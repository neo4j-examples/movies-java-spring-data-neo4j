package movies.spring.data.neo4j.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import movies.spring.data.neo4j.movies.Movie;
import movies.spring.data.neo4j.movies.MovieService;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Michael J. Simons
 */
@RestController
@RequestMapping("/api/v0/movies")
@Tag(name = "movies")
class MovieController {

	private final MovieService movieService;

	MovieController(MovieService movieService) {
		this.movieService = movieService;
	}

	@GetMapping("/directed_by/{name}")
	List<Movie> getDirectedBy(@PathVariable String name) {
		return movieService.getDirectedBy(name);
	}

	@GetMapping("/acted_in_by/{name}")
	List<Movie> getActedInBy(@PathVariable String name) {
		return movieService.getActedInBy(name);
	}

	@GetMapping("/graph")
	public Map<String, List<Object>> getGraph() {
		return movieService.f();
	}
}
