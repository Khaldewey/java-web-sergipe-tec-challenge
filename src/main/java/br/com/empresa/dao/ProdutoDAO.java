package br.com.empresa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.empresa.config.ConnectionFactory;
import br.com.empresa.models.Produto;
import br.com.empresa.models.metadados.ProdutoColuna;

public class ProdutoDAO {
    
    public void salvar(Produto produto) throws Exception {

        String sql = "INSERT INTO " + Produto.NM_TABELA +
                " (" + ProdutoColuna.DESCRICAO + ", " +
                    ProdutoColuna.VALOR + ", " +
                    ProdutoColuna.ESTOQUE + ", " +
                    ProdutoColuna.DATA_CADASTRO + ") " +
                "VALUES (?, ?, ?, ?)";

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

        String sql = "SELECT " +
                ProdutoColuna.ID + ", " +
                ProdutoColuna.DESCRICAO + ", " +
                ProdutoColuna.VALOR + ", " +
                ProdutoColuna.ESTOQUE + ", " +
                ProdutoColuna.DATA_CADASTRO +
                " FROM " + Produto.NM_TABELA;

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }

        return lista;
    }

    public List<Produto> buscarPorDescricao(String descricao) throws Exception {

        List<Produto> lista = new ArrayList<>();

        String sql = "SELECT " +
                ProdutoColuna.ID + ", " +
                ProdutoColuna.DESCRICAO + ", " +
                ProdutoColuna.VALOR + ", " +
                ProdutoColuna.ESTOQUE + ", " +
                ProdutoColuna.DATA_CADASTRO +
                " FROM " + Produto.NM_TABELA +
                " WHERE LOWER(" + ProdutoColuna.DESCRICAO + ") LIKE LOWER(?)";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + descricao + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }

        return lista;
    }

    public Produto buscarPorId(Long id) throws Exception {

        String sql = "SELECT " +
                ProdutoColuna.ID + ", " +
                ProdutoColuna.DESCRICAO + ", " +
                ProdutoColuna.VALOR + ", " +
                ProdutoColuna.ESTOQUE + ", " +
                ProdutoColuna.DATA_CADASTRO +
                " FROM " + Produto.NM_TABELA +
                " WHERE " + ProdutoColuna.ID + " = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }

        return null;
    }

    private Produto mapear(ResultSet rs) throws Exception {

        Produto p = new Produto(
                rs.getString(ProdutoColuna.DESCRICAO.getNome()),
                rs.getBigDecimal(ProdutoColuna.VALOR.getNome()),
                rs.getInt(ProdutoColuna.ESTOQUE.getNome()),
                rs.getDate(ProdutoColuna.DATA_CADASTRO.getNome())
        );

        p.setId(rs.getLong(ProdutoColuna.ID.getNome()));

        return p;
    }

}
