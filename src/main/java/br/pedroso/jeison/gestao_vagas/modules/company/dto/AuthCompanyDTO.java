package br.pedroso.jeison.gestao_vagas.modules.company.dto;

public class AuthCompanyDTO {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthCompanyDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
