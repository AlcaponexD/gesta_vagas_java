package br.pedroso.jeison.gestao_vagas.modules.company.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.pedroso.jeison.gestao_vagas.exceptions.UserFoundException;
import br.pedroso.jeison.gestao_vagas.modules.company.entities.CompanyEntity;
import br.pedroso.jeison.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class CreateCompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    PasswordEncoder passordEncoder;

    public CompanyEntity execute(CompanyEntity companyEntity) {
        this.companyRepository.findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });

        // Encrypt pass
        String password = passordEncoder.encode(companyEntity.getPassword());
        companyEntity.setPassword(password);

        return this.companyRepository.save(companyEntity);
    }
}
