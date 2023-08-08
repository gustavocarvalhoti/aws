package service.lambda.request;

public class ItemQuestionarioRequest {

    private String refQuestao;
    private Boolean ativo;
    private Integer ordem;

    public String getRefQuestao() {
        return refQuestao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setRefQuestao(String refQuestao) {
        this.refQuestao = refQuestao;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }
}
