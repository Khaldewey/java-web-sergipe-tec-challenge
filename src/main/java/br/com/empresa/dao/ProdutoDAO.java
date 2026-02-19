package br.com.empresa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import br.com.empresa.config.ConnectionFactory;
import br.com.empresa.models.Produto;

public class ProdutoDAO {
    
    public void salvar(Produto produto) throws Exception {
    String sql = "INSERT INTO produtos (descricao, valor, estoque, datacadastro) VALUES (?, ?, ?, ?)";

    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, produto.getDescricao());
        stmt.setBigDecimal(2, produto.getValor());
        stmt.setInt(3, produto.getQuantidadeEmEstoque());
        stmt.setDate(4, produto.getDataDeCadastro());
        stmt.executeUpdate();
    }
}

}
