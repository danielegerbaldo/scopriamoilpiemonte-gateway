package TAASS.scopriamoilpiemontegateway.dto;


public enum Role {
    ROLE_ADMIN, ROLE_CLIENT, ROLE_MAYOR, ROLE_PUBLISHER;

    public String getAuthority() {
        return name();
    }

}