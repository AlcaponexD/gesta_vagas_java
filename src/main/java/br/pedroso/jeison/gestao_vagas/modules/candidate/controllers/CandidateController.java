package br.pedroso.jeison.gestao_vagas.modules.candidate.controllers;

import org.springframework.web.bind.annotation.RestController;

import br.pedroso.jeison.gestao_vagas.exceptions.UserFoundException;
import br.pedroso.jeison.gestao_vagas.modules.candidate.CandidateEntity;
import br.pedroso.jeison.gestao_vagas.modules.candidate.CandidateRepository;
import br.pedroso.jeison.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.pedroso.jeison.gestao_vagas.modules.candidate.entity.ApplyJobEntity;
import br.pedroso.jeison.gestao_vagas.modules.candidate.services.ApplyJobCandidateService;
import br.pedroso.jeison.gestao_vagas.modules.candidate.services.CreateCandidateService;
import br.pedroso.jeison.gestao_vagas.modules.candidate.services.ListAllJobsByFilterService;
import br.pedroso.jeison.gestao_vagas.modules.candidate.services.ProfileCandidateService;
import br.pedroso.jeison.gestao_vagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Informações de candidato")

public class CandidateController {

    @Autowired
    private ApplyJobCandidateService applyJobCandidateService;

    @Autowired
    private CreateCandidateService createCandidateService;

    @Autowired
    private ProfileCandidateService profileCandidateService;

    @Autowired
    private ListAllJobsByFilterService listAllJobsByFilterService;

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

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    // Cria a documentação da rota no swagger
    @Operation(summary = "Listagem de vagas disponivel para o candidato", description = "Lista de vagas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> findJobByFilter(@RequestParam String filter) {
        return this.listAllJobsByFilterService.execute(filter);
    }

    @PreAuthorize("hasRole('CANDIDATE')")
    @SecurityRequirement(name = "jwt_auth")
    @Operation(summary = "Inscrição do candidato para uma vaga", description = "Aplicar em vaga")
    @PostMapping("/job/apply")
    public ResponseEntity<Object> applyJob(HttpServletRequest httpServletRequest, @RequestBody UUID idJob) {
        Object idCandidate = httpServletRequest.getAttribute("candidate_id");

        try {
            ApplyJobEntity result = this.applyJobCandidateService.execute(UUID.fromString(idCandidate.toString()),
                    idJob);
            return ResponseEntity.ok().body(result);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}