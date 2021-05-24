package TAASS.scopriamoilpiemontegateway.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto {

    private long id;
    private String login;
    private String token;

    public long getId() {
        return id;
    }
}