package movies.spring.data.neo4j.movies;

import java.util.List;
import java.util.Objects;

public class MovieDetailsDto {

    private final String title;

    private final List<CastMemberDto> cast;

    public MovieDetailsDto(String title, List<CastMemberDto> cast) {
        this.title = title;
        this.cast = cast;
    }

    public String getTitle() {
        return title;
    }

    public List<CastMemberDto> getCast() {
        return cast;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieDetailsDto that = (MovieDetailsDto) o;
        return Objects.equals(title, that.title) && Objects.equals(cast, that.cast);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, cast);
    }
}
