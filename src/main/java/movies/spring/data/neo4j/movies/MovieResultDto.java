package movies.spring.data.neo4j.movies;

import java.util.Objects;

public class MovieResultDto {

    private final Movie movie;

    public MovieResultDto(Movie movie) {
        this.movie = movie;
    }

    public Movie getMovie() {
        return movie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieResultDto that = (MovieResultDto) o;
        return Objects.equals(movie, that.movie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie);
    }
}
