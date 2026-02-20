package br.com.empresa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.empresa.config.ConnectionFactory;
import br.com.empresa.models.Produto;

public class ProdutoDAO {
    
    public void salvar(Produto produto) throws Exception {
        String sql = "INSERT INTO produtos (descricao, valor, estoque, data_cadastro) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getDescricao());
            stmt.setBigDecimal(2, produto.getValor());
            stmt.setInt(3, produto.getQuantidadeEmEstoque());
            stmt.setDate(4, produto.getDataDeCadastro());
            stmt.executeUpdate();
        }
    }

    public List<Produto> listar() throws Exception {
        List<Produto> lista = new ArrayList<>();

        String sql = "SELECT id, descricao, valor, estoque, data_cadastro FROM produtos";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto p = new Produto(
                    rs.getString("descricao"),
                    rs.getBigDecimal("valor"),
                    rs.getInt("estoque"),
                    rs.getDate("data_cadastro")
                );
                p.setId(rs.getLong("id"));
                lista.add(p);
            }
        }

        return lista;
    }

    public List<Produto> buscarPorDescricao(String descricao) throws Exception {
        List<Produto> lista = new ArrayList<>();

        String sql = "SELECT * FROM produtos WHERE LOWER(descricao) LIKE LOWER(?)";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + descricao + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produto p = new Produto(
                    rs.getString("descricao"),
                    rs.getBigDecimal("valor"),
                    rs.getInt("estoque"),
                    rs.getDate("data_cadastro")
                );
                p.setId(rs.getLong("id"));
                lista.add(p);
            }
        }

        return lista;
    }

    public Produto buscarPorId(Long id) throws Exception {
        String sql = "SELECT * FROM produtos WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Produto p = new Produto(
                    rs.getString("descricao"),
                    rs.getBigDecimal("valor"),
                    rs.getInt("estoque"),
                    rs.getDate("data_cadastro")
                );
                p.setId(rs.getLong("id"));
                return p;
            }
        }

        return null;
    }

}
