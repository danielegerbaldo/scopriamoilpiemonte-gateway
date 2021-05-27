package TAASS.scopriamoilpiemontegateway.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto {

    private long id;
    private String email;
    private String role;

    public long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }
}