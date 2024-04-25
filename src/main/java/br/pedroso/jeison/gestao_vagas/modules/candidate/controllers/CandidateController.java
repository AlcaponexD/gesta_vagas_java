package br.pedroso.jeison.gestao_vagas.modules.candidate.controllers;

import org.springframework.web.bind.annotation.RestController;

import br.pedroso.jeison.gestao_vagas.exceptions.UserFoundException;
import br.pedroso.jeison.gestao_vagas.modules.candidate.CandidateEntity;
import br.pedroso.jeison.gestao_vagas.modules.candidate.CandidateRepository;
import br.pedroso.jeison.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.pedroso.jeison.gestao_vagas.modules.candidate.services.CreateCandidateService;
import br.pedroso.jeison.gestao_vagas.modules.candidate.services.ProfileCandidateService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/candidate")

public class CandidateController {

    @Autowired
    private CreateCandidateService createCandidateService;

    @Autowired
    private ProfileCandidateService profileCandidateService;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
        try {
            CandidateEntity result = this.createCandidateService.execute(candidateEntity);
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    // Segurança da rota com base em roles trabalha em conjunto com a pasta security
    // e os arquivos filtercandidate + security cofig
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<Object> get(HttpServletRequest httpServletRequest) {
        try {
            Object idCandidate = httpServletRequest.getAttribute("candidate_id");
            ProfileCandidateResponseDTO profile = this.profileCandidateService
                    .execute(UUID.fromString(idCandidate.toString()));
            return ResponseEntity.ok().body(profile);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}