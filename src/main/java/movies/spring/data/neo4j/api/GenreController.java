package movies.spring.data.neo4j.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import movies.spring.data.neo4j.movies.Genre;
import movies.spring.data.neo4j.movies.GenreRepository;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Michael J. Simons
 */
@RestController
@RequestMapping("/api/v0/genres")
@Tag(name = "genres")
class GenreController {

	private final GenreRepository genreRepository;

	GenreController(GenreRepository genreRepository) {
		this.genreRepository = genreRepository;
	}

	@GetMapping
	List<Genre> genres() {
		return genreRepository.findAll();
	}
}
