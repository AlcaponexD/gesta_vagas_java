package br.pedroso.jeison.gestao_vagas.modules.candidate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.pedroso.jeison.gestao_vagas.modules.company.repositories.JobRepository;
import br.pedroso.jeison.gestao_vagas.modules.company.entities.JobEntity;

@Service
public class ListAllJobsByFilterService {
    @Autowired
    private JobRepository jobRepository;

    public List<JobEntity> execute(String filter) {
        return this.jobRepository.findAllByDescriptionContainingIgnoreCase(filter);
    }
}
