package br.pedroso.jeison.gestao_vagas.exceptions;

public class UserFoundException extends RuntimeException {
    public UserFoundException() {
        super("Usuário já existente");
    }
}
