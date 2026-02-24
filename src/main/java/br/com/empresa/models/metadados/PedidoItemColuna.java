package br.com.empresa.models.metadados;

public enum PedidoItemColuna {

    PEDIDO_ID("pedido_id"),
    PRODUTO_ID("produto_id"),
    VALOR_UNITARIO("valor_unitario_produto"),
    QUANTIDADE("quantidade"),
    DESCONTO("desconto");

    private final String nome;

    PedidoItemColuna(String nome) {
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