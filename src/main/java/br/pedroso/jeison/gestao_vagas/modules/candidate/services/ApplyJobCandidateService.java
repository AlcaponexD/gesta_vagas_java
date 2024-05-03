package br.pedroso.jeison.gestao_vagas.modules.candidate.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import br.pedroso.jeison.gestao_vagas.exceptions.JobNotFoundException;
import br.pedroso.jeison.gestao_vagas.exceptions.UserNotFoundException;
import br.pedroso.jeison.gestao_vagas.modules.candidate.CandidateRepository;
import br.pedroso.jeison.gestao_vagas.modules.company.repositories.JobRepository;

public class ApplyJobCandidateService {
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    public void execute(UUID idCandidate, UUID idJob) {
        // Validate exists candidate
        this.candidateRepository.findById(idCandidate)
                .orElseThrow(() -> {
                    throw new UserNotFoundException();
                });

        // Validate exists job
        this.jobRepository.findById(idJob)
                .orElseThrow(() -> {
                    throw new JobNotFoundException();
                });

        // Apply job to candidate
    }
}
