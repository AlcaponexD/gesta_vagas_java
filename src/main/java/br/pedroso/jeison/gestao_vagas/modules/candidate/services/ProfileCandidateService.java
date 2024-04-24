package br.pedroso.jeison.gestao_vagas.modules.candidate.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.pedroso.jeison.gestao_vagas.modules.candidate.CandidateRepository;
import br.pedroso.jeison.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.pedroso.jeison.gestao_vagas.modules.candidate.CandidateEntity;

@Service
public class ProfileCandidateService {
    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute(UUID idCandidate) {
        CandidateEntity candidate = candidateRepository.findById(idCandidate)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("User not found");
                });

        ProfileCandidateResponseDTO candidateDTO = ProfileCandidateResponseDTO.builder()
                .description(candidate.getDescription())
                .username(candidate.getUsername())
                .email(candidate.getEmail())
                .name(candidate.getName())
                .id(candidate.getId())
                .build();

        return candidateDTO;
    }
}
