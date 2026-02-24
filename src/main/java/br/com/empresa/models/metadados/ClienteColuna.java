package br.com.empresa.models.metadados;

public enum ClienteColuna {

    ID("id"),
    NOME("nome"),
    EMAIL("email"),
    DATA_CADASTRO("data_cadastro");

    private final String nomeColuna;

    ClienteColuna(String nomeColuna) {
        this.nomeColuna = nomeColuna;
    }

    public String getNome() {
        return nomeColuna;
    }

    @Override
    public String toString() {
        return nomeColuna;
    }
}
