package br.pedroso.jeison.gestao_vagas.modules.candidate.controllers;

import org.springframework.web.bind.annotation.RestController;

import br.pedroso.jeison.gestao_vagas.exceptions.UserFoundException;
import br.pedroso.jeison.gestao_vagas.modules.candidate.CandidateEntity;
import br.pedroso.jeison.gestao_vagas.modules.candidate.CandidateRepository;
import br.pedroso.jeison.gestao_vagas.modules.candidate.services.CreateCandidateService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/candidate")

public class CandidateController {

    @Autowired
    private CreateCandidateService createCandidateService;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
        try {
            CandidateEntity result = this.createCandidateService.execute(candidateEntity);
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}