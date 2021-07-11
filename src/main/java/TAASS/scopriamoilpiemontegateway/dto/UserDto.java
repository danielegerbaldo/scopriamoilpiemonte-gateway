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
    private long dipendenteDiComune;

    public long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public long getDipendenteDiComune() {
        return dipendenteDiComune;
    }
}