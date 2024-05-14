package br.pedroso.jeison.gestao_vagas.modules.company.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.pedroso.jeison.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.pedroso.jeison.gestao_vagas.modules.company.entities.JobEntity;
import br.pedroso.jeison.gestao_vagas.modules.company.services.CreateJobService;
import io.micrometer.core.ipc.http.HttpSender.Response;
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

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/company/job")

public class JobController<jobEntity> {
        @Autowired
        private CreateJobService createJobService;

        @PostMapping("/")
        @PreAuthorize("hasRole('COMPANY')")
        @Tag(name = "Vagas", description = "Informações das vagas")
        @Operation(summary = "Essa função é responsavel por cadastrar as vagas", description = "Cadastro de vagas")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Sucesso", content = {
                                        @Content(schema = @Schema(implementation = JobEntity.class))
                        })
        })
        @SecurityRequirement(name = "jwt_auth")
        public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO createJobDTO,
                        HttpServletRequest request) {

                // Necessário pois foi injetado company_id direto do filter
                try {
                        Object companyId = request.getAttribute("company_id");

                        JobEntity jobEntity = JobEntity.builder()
                                        .benefits(createJobDTO.getBenefits())
                                        .companyId(UUID.fromString(companyId.toString()))
                                        .description(createJobDTO.getDescription())
                                        .level(createJobDTO.getLevel())
                                        .build();

                        var result = this.createJobService.execute(jobEntity);
                        return ResponseEntity.ok().body(result);
                } catch (Exception e) {
                        return ResponseEntity.badRequest().body(e);
                }
        }

}
