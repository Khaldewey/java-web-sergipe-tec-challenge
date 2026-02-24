package br.com.empresa.models.metadados;

public enum PedidoColuna {

    ID("id"),
    CLIENTE_ID("cliente_id"),
    DATA_PEDIDO("data_pedido");

    private final String nome;

    PedidoColuna(String nome) {
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