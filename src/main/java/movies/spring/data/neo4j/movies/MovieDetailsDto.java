package movies.spring.data.neo4j.movies;

import java.util.List;

public record MovieDetailsDto(String title, List<CastMemberDto> cast) {
}
