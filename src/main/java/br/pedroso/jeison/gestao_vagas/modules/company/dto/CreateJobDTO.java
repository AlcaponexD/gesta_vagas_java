package br.pedroso.jeison.gestao_vagas.modules.company.dto;

public class CreateJobDTO {
    private String description;
    private String benefits;
    private String level;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

}
