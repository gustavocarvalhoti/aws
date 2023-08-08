package service.lambda.request;

import java.util.List;

public class QuestionarioRequest {

    private String categoria;
    private List<ItemQuestionarioRequest> itens;

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setItens(List<ItemQuestionarioRequest> itens) {
        this.itens = itens;
    }

    public List<ItemQuestionarioRequest> getItens() {
        return itens;
    }
}
