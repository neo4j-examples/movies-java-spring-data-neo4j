package movies.spring.data.neo4j.controller;

import java.util.Collection;
import java.util.Map;

import movies.spring.data.neo4j.domain.Movie;
import movies.spring.data.neo4j.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mark Angrish
 */
@RestController
@RequestMapping("/")
public class MovieController {

	final MovieService movieService;

	@Autowired
	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}

    @GetMapping("/movies/search/findByTitle")
    public Movie findByTitle(@RequestParam String title) {
        return movieService.findByTitle(title);
    }

    @GetMapping("/movies/search/findByTitleLike")
    public Collection<Movie> findByTitleLike(@RequestParam String title) {
        return movieService.findByTitleLike(title);
    }

    //@RequestMapping("/graph")
    @GetMapping("/graph")
	public Map<String, Object> graph(@RequestParam(value = "limit",required = false) Integer limit) {
		return movieService.graph(limit == null ? 100 : limit);
	}
}
