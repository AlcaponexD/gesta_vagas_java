package br.pedroso.jeison.gestao_vagas.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JWTCandidateProvider {

    @Value("${security.token.secret.candidate}")
    private String secretKey;

    public DecodedJWT validateToken(String token) {
        token = token.replace("Bearer ", "");
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        try {
            var subject = JWT.require(algorithm)
                    .build()
                    .verify(token);
            // .getSubject();
            return subject;
        } catch (JWTVerificationException e) {
            throw new IllegalStateException(e.getMessage());
        }

    }
}
