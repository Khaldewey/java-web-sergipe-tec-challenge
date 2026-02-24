package br.com.empresa.models.metadados;

public enum ProdutoColuna {

    ID("id"),
    DESCRICAO("descricao"),
    VALOR("valor"),
    ESTOQUE("estoque"),
    DATA_CADASTRO("data_cadastro");

    private final String nome;

    ProdutoColuna(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}