package movies.spring.data.neo4j.movies;

import java.util.Objects;

public class CastMemberDto {

    private final String name;

    private final String job;

    private final String role;

    public CastMemberDto(String name, String job) {
        this(name, job, null);
    }

    // visible for testing
    CastMemberDto(String name, String job, String role) {
        this.name = name;
        this.job = job;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public String getRole() {
        return role;
    }

    public CastMemberDto withRole(String role) {
        return new CastMemberDto(this.name, this.job, role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CastMemberDto that = (CastMemberDto) o;
        return Objects.equals(name, that.name) && Objects.equals(job, that.job) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, job, role);
    }
}
