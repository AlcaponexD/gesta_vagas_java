package br.pedroso.jeison.gestao_vagas.modules.candidate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.pedroso.jeison.gestao_vagas.exceptions.UserFoundException;
import br.pedroso.jeison.gestao_vagas.modules.candidate.CandidateEntity;
import br.pedroso.jeison.gestao_vagas.modules.candidate.CandidateRepository;

@Service
public class CreateCandidateService {
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public CandidateEntity execute(CandidateEntity candidateEntity) {
        this.candidateRepository.findByUsernameOrEmail(candidateEntity.getUsername(),
                candidateEntity.getEmail()).ifPresent((user) -> {
                    throw new UserFoundException();
                });

        String password = passwordEncoder.encode(candidateEntity.getPassword());
        candidateEntity.setPassword(password);
        return this.candidateRepository.save(candidateEntity);
    }
}
