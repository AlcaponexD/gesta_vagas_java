package br.pedroso.jeison.gestao_vagas.modules.candidate.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.pedroso.jeison.gestao_vagas.exceptions.JobNotFoundException;
import br.pedroso.jeison.gestao_vagas.exceptions.UserNotFoundException;
import br.pedroso.jeison.gestao_vagas.modules.candidate.CandidateRepository;
import br.pedroso.jeison.gestao_vagas.modules.candidate.entity.ApplyJobEntity;
import br.pedroso.jeison.gestao_vagas.modules.candidate.repository.ApplyJobRepository;
import br.pedroso.jeison.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class ApplyJobCandidateService {
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;

    public ApplyJobEntity execute(UUID idCandidate, UUID idJob) {
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

        // Apply candidate to job

        ApplyJobEntity applyJob = ApplyJobEntity.builder()
                .candidateId(idCandidate)
                .jobId(idJob).build();
        applyJobRepository.save(applyJob);

        return applyJob;
    }
}
