package br.pedroso.jeison.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobDTO {
    @Schema(example = "Vaga para pessoa desenvolvedora junior", requiredMode = RequiredMode.REQUIRED)
    private String description;
    @Schema(example = "Gympass,plano de saúde", requiredMode = RequiredMode.REQUIRED)
    private String benefits;
    @Schema(example = "Junior,pleno,senior", requiredMode = RequiredMode.REQUIRED)
    private String level;
}
