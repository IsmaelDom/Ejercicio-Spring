package com.ktg.usuarioSpring.payload.response;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private long id;
    private String correo;

    public JwtResponse(String accessToken, long id, String correo) {
        this.token = accessToken;
        this.id = id;
        this.correo = correo;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
