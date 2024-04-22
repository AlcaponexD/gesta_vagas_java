package br.pedroso.jeison.gestao_vagas.modules.candidate.services;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.BadCredentialsException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.pedroso.jeison.gestao_vagas.modules.candidate.CandidateEntity;
import br.pedroso.jeison.gestao_vagas.modules.candidate.CandidateRepository;
import br.pedroso.jeison.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.pedroso.jeison.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;

@Service
public class AuthCandidateService {
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${security.token.secret.candidate}")
    private String secretKey;

    private String message = "Username/password mismatch";

    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO)
            throws BadCredentialsException {
        CandidateEntity candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username())
                .orElseThrow(() -> {
                    throw new BadCredentialsException(message);
                });

        Boolean passwordMatches = this.passwordEncoder
                .matches(authCandidateRequestDTO.password(), candidate.getPassword());

        if (!passwordMatches) {
            throw new BadCredentialsException(message);
        }

        Instant expires_in = Instant.now().plus(Duration.ofHours(2));
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String token = JWT.create()
                .withIssuer("javagas")
                .withSubject(candidate.getId().toString())
                .withClaim("roles", Arrays.asList("candidate"))
                .withExpiresAt(expires_in)
                .sign(algorithm);

        AuthCandidateResponseDTO authCandidateResponse = AuthCandidateResponseDTO.builder()
                .access_token(token)
                .expires_in(expires_in.toEpochMilli())
                .build();

        return authCandidateResponse;
    }
}
