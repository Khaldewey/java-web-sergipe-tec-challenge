package br.com.empresa.dao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.com.empresa.models.Cliente;
import br.com.empresa.models.Pedido;
import br.com.empresa.models.PedidoItem;
import br.com.empresa.models.Produto;
import br.com.empresa.models.metadados.ClienteColuna;
import br.com.empresa.models.metadados.PedidoColuna;
import br.com.empresa.models.metadados.PedidoItemColuna;
import br.com.empresa.models.metadados.ProdutoColuna;
import br.com.empresa.config.ConnectionFactory;

public class PedidoDAO {

    public void salvar(Pedido pedido) throws Exception {

        String sqlPedido =
            "INSERT INTO " + Pedido.NM_TABELA +
            " (" + PedidoColuna.CLIENTE_ID + ", " +
                    PedidoColuna.DATA_PEDIDO + ") " +
            "VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection()) {

            conn.setAutoCommit(false);

            try (
                PreparedStatement stmtPedido =
                    conn.prepareStatement(
                        sqlPedido,
                        PreparedStatement.RETURN_GENERATED_KEYS
                    )
            ) {

                stmtPedido.setLong(1, pedido.getClienteId());
                stmtPedido.setDate(2, pedido.getDataPedido());

                stmtPedido.executeUpdate();

                Long pedidoId = null;

                try (ResultSet rs = stmtPedido.getGeneratedKeys()) {
                    if (rs.next()) {
                        pedidoId = rs.getLong(1);
                    }
                }

                salvarItens(conn, pedidoId, pedido.getItens());

                conn.commit();

            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        }
    }

    private void salvarItens(Connection conn,
                         Long pedidoId,
                         List<PedidoItem> itens) throws Exception {

        String sqlItem =
            "INSERT INTO " + PedidoItem.NM_TABELA +
            " (" + PedidoItemColuna.PEDIDO_ID + ", " +
                PedidoItemColuna.PRODUTO_ID + ", " +
                PedidoItemColuna.VALOR_UNITARIO + ", " +
                PedidoItemColuna.QUANTIDADE + ", " +
                PedidoItemColuna.DESCONTO + ") " +
            "VALUES (?, ?, ?, ?, ?)";

        String sqlAtualizaEstoque =
            "UPDATE " + Produto.NM_TABELA +
            " SET " + ProdutoColuna.ESTOQUE + " = " +
                    ProdutoColuna.ESTOQUE + " - ? " +
            "WHERE " + ProdutoColuna.ID + " = ?";

        for (PedidoItem item : itens) {

            validarEstoque(conn, item.getProdutoId(), item.getQuantidade());

            try (
                PreparedStatement stmtItem =
                    conn.prepareStatement(sqlItem);

                PreparedStatement stmtEstoque =
                    conn.prepareStatement(sqlAtualizaEstoque)
            ) {

                stmtItem.setLong(1, pedidoId);
                stmtItem.setLong(2, item.getProdutoId());
                stmtItem.setBigDecimal(3, item.getValorUnitarioProduto());
                stmtItem.setInt(4, item.getQuantidade());
                stmtItem.setBigDecimal(5, item.getDesconto());
                stmtItem.executeUpdate();

                stmtEstoque.setInt(1, item.getQuantidade());
                stmtEstoque.setLong(2, item.getProdutoId());
                stmtEstoque.executeUpdate();
            }
        }
    }

    public Pedido buscarPorId(Long id) throws Exception {

        String sql = "SELECT " +
                PedidoColuna.ID + ", " +
                PedidoColuna.CLIENTE_ID + ", " +
                PedidoColuna.DATA_PEDIDO +
                " FROM " + Pedido.NM_TABELA +
                " WHERE " + PedidoColuna.ID + " = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearPedido(rs);
                }
            }
        }

        return null;
    }

    public List<Pedido> buscarPorPeriodo(Date inicio, Date fim) throws Exception {

        List<Pedido> lista = new ArrayList<>();

        String sql = "SELECT " +
            PedidoColuna.ID + ", " +
            PedidoColuna.CLIENTE_ID + ", " +
            PedidoColuna.DATA_PEDIDO +
            " FROM " + Pedido.NM_TABELA +
            " WHERE " + PedidoColuna.DATA_PEDIDO + " BETWEEN ? AND ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, inicio);
            stmt.setDate(2, fim);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(mapearPedido(rs));
            }
        }

        return lista;
    }

    public List<Pedido> listarPorCliente(Long clienteId) throws Exception {

        List<Pedido> lista = new ArrayList<>();

        String sql = "SELECT " +
                PedidoColuna.ID + ", " +
                PedidoColuna.CLIENTE_ID + ", " +
                PedidoColuna.DATA_PEDIDO +
                " FROM " + Pedido.NM_TABELA +
                " WHERE " + PedidoColuna.CLIENTE_ID + " = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, clienteId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearPedido(rs));
                }
            }
        }

        return lista;
    }

    public List<Pedido> listarComCliente() throws Exception {

        List<Pedido> lista = new ArrayList<>();

        String sql =
            "SELECT p." + PedidoColuna.ID + ", " +
            "p." + PedidoColuna.CLIENTE_ID + ", " +
            "p." + PedidoColuna.DATA_PEDIDO + ", " +
            "c." + ClienteColuna.NOME + " AS cliente_nome " +
            "FROM " + Pedido.NM_TABELA + " p " +
            "JOIN " + Cliente.NM_TABELA + " c ON p." +
            PedidoColuna.CLIENTE_ID + " = c." + ClienteColuna.ID;

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Pedido p = mapearPedido(rs);
                p.setClienteNome(rs.getString("cliente_nome"));

                lista.add(p);
            }
        }

        return lista;
    }

    public List<Pedido> listarPorProduto(Long produtoId) throws Exception {

        List<Pedido> lista = new ArrayList<>();

        String sql =
            "SELECT DISTINCT p." + PedidoColuna.ID + ", " +
            "p." + PedidoColuna.CLIENTE_ID + ", " +
            "p." + PedidoColuna.DATA_PEDIDO + " " +
            "FROM " + Pedido.NM_TABELA + " p " +
            "JOIN " + PedidoItem.NM_TABELA + " pi ON p." +
            PedidoColuna.ID + " = pi." + PedidoItemColuna.PEDIDO_ID + " " +
            "WHERE pi." + PedidoItemColuna.PRODUTO_ID + " = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, produtoId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearPedido(rs));
                }
            }
        }

        return lista;
    }

    public List<PedidoItem> buscarItensPorPedido(Long pedidoId) throws Exception {

        List<PedidoItem> itens = new ArrayList<>();

        String sql =
            "SELECT pi." + PedidoItemColuna.PRODUTO_ID + ", " +
            "pi." + PedidoItemColuna.VALOR_UNITARIO + ", " +
            "pi." + PedidoItemColuna.QUANTIDADE + ", " +
            "pi." + PedidoItemColuna.DESCONTO + ", " +
            "pr." + ProdutoColuna.DESCRICAO + " " +
            "FROM " + PedidoItem.NM_TABELA + " pi " +
            "JOIN " + Produto.NM_TABELA + " pr ON pi." +
            PedidoItemColuna.PRODUTO_ID + " = pr." + ProdutoColuna.ID + " " +
            "WHERE pi." + PedidoItemColuna.PEDIDO_ID + " = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, pedidoId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {

                    PedidoItem item = mapearPedidoItem(rs);
                    item.setProdutoNome(
                        rs.getString(ProdutoColuna.DESCRICAO.getNome())
                    );

                    itens.add(item);
                }
            }
        }

        return itens;
    }

    public List<Pedido> buscarComFiltros(
        String clienteId,
        String produtoId,
        String id,
        String inicio,
        String fim) throws Exception {

        List<Pedido> lista = new ArrayList<>();
        List<Object> parametros = new ArrayList<>();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT DISTINCT p.")
        .append(PedidoColuna.ID).append(", p.")
        .append(PedidoColuna.CLIENTE_ID).append(", p.")
        .append(PedidoColuna.DATA_PEDIDO)
        .append(" FROM ").append(Pedido.NM_TABELA).append(" p ");

        if (produtoId != null && !produtoId.isEmpty()) {
            sql.append(" JOIN ")
            .append(PedidoItem.NM_TABELA)
            .append(" pi ON p.")
            .append(PedidoColuna.ID)
            .append(" = pi.")
            .append(PedidoItemColuna.PEDIDO_ID);
        }

        sql.append(" WHERE 1=1 ");

        if (clienteId != null && !clienteId.isEmpty()) {
            sql.append(" AND p.")
            .append(PedidoColuna.CLIENTE_ID)
            .append(" = ?");
            parametros.add(Long.valueOf(clienteId));
        }

        if (id != null && !id.isEmpty()) {
            sql.append(" AND p.")
            .append(PedidoColuna.ID)
            .append(" = ?");
            parametros.add(Long.valueOf(id));
        }

        if (produtoId != null && !produtoId.isEmpty()) {
            sql.append(" AND pi.")
            .append(PedidoItemColuna.PRODUTO_ID)
            .append(" = ?");
            parametros.add(Long.valueOf(produtoId));
        }

        if (inicio != null && !inicio.isEmpty()
            && fim != null && !fim.isEmpty()) {

            sql.append(" AND p.")
            .append(PedidoColuna.DATA_PEDIDO)
            .append(" BETWEEN ? AND ?");

            parametros.add(Date.valueOf(inicio));
            parametros.add(Date.valueOf(fim));
        }

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt =
                conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < parametros.size(); i++) {
                stmt.setObject(i + 1, parametros.get(i));
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(mapearPedido(rs));
            }
        }

        return lista;
    }

    public Long contar() throws Exception {

        String sql = "SELECT COUNT(*) AS total FROM " + Pedido.NM_TABELA;

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getLong("total");
            }
        }

        return 0L;
    }

    private void validarEstoque(Connection conn,
                            Long produtoId,
                            Integer quantidade) throws Exception {

        String sql = "SELECT " + ProdutoColuna.ESTOQUE +
             " FROM " + Produto.NM_TABELA +
             " WHERE " + ProdutoColuna.ID + " = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setLong(1, produtoId);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {

            int estoqueAtual = rs.getInt(ProdutoColuna.ESTOQUE.getNome());

            if (quantidade > estoqueAtual) {
                throw new RuntimeException(
                    "Estoque insuficiente para o produto ID: " + produtoId
                );
            }

        } else {
            throw new RuntimeException("Produto não encontrado");
        }
    }

    public BigDecimal calcularTotalPorCliente(Long clienteId) throws Exception {

        String sql =
            "SELECT SUM(" +
            "(pi." + PedidoItemColuna.VALOR_UNITARIO + " * " +
            "pi." + PedidoItemColuna.QUANTIDADE + ") - " +
            "pi." + PedidoItemColuna.DESCONTO +
            ") AS total " +
            "FROM " + Pedido.NM_TABELA + " p " +
            "JOIN " + PedidoItem.NM_TABELA + " pi ON p." +
            PedidoColuna.ID + " = pi." + PedidoItemColuna.PEDIDO_ID + " " +
            "WHERE p." + PedidoColuna.CLIENTE_ID + " = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, clienteId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    BigDecimal total = rs.getBigDecimal("total");
                    return total != null ? total : BigDecimal.ZERO;
                }
            }
        }

        return BigDecimal.ZERO;
    }

    private Pedido mapearPedido(ResultSet rs) throws Exception {
        return new Pedido(
            rs.getLong(PedidoColuna.ID.getNome()),
            rs.getLong(PedidoColuna.CLIENTE_ID.getNome()),
            rs.getDate(PedidoColuna.DATA_PEDIDO.getNome())
        );
    }

    private PedidoItem mapearPedidoItem(ResultSet rs) throws Exception {

        PedidoItem item = new PedidoItem(
            rs.getLong(PedidoItemColuna.PRODUTO_ID.getNome()),
            rs.getBigDecimal(PedidoItemColuna.VALOR_UNITARIO.getNome()),
            rs.getInt(PedidoItemColuna.QUANTIDADE.getNome()),
            rs.getBigDecimal(PedidoItemColuna.DESCONTO.getNome())
        );

        return item;
    }
}