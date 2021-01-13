package com.ktg.usuarioSpring.payload.response;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private long id;
    private String correo;
    private String nombre;
    private String apellido;

    public JwtResponse(String accessToken, long id, String correo, String nombre, String apellido) {
        this.token = accessToken;
        this.id = id;
        this.correo = correo;
        this.nombre = nombre;
        this.apellido = apellido;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
