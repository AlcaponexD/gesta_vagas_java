package br.pedroso.jeison.gestao_vagas.modules.company.services;

import java.time.Instant;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.pedroso.jeison.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.pedroso.jeison.gestao_vagas.modules.company.entities.CompanyEntity;
import br.pedroso.jeison.gestao_vagas.modules.company.repositories.CompanyRepository;
import java.time.Duration;

@Service
public class AuthCompanyService {

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passordEncoder;

    private String messageException = "Username/password mismatch";

    public String execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        CompanyEntity company = this.companyRepository.findByUsername(authCompanyDTO.getUsername());

        if (company == null) {
            throw new AuthenticationException(messageException);
        }

        // Verificar a senha são iguais
        Boolean passwordMatches = this.passordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());
        // Se nao for igual -> erro
        if (!passwordMatches) {
            throw new AuthenticationException(messageException);
        }
        // se for igual -> gerar o token
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String token = JWT.create().withIssuer("javagas")
                .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                .withSubject(company.getId().toString()).sign(algorithm);

        return token;
    }
}
