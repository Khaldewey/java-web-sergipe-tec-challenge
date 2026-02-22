package br.com.empresa.dao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.com.empresa.models.Pedido;
import br.com.empresa.models.PedidoItem;
import br.com.empresa.config.ConnectionFactory;

public class PedidoDAO {

    public void salvar(Pedido pedido) throws Exception {

        String sqlPedido = 
            "INSERT INTO pedidos (cliente_id, data_pedido) VALUES (?, ?) RETURNING id";

        try (Connection conn = ConnectionFactory.getConnection()) {

            conn.setAutoCommit(false);

            PreparedStatement stmtPedido =
                conn.prepareStatement(sqlPedido);

            stmtPedido.setLong(1, pedido.getClienteId());
            stmtPedido.setDate(2, pedido.getDataPedido());

            ResultSet rs = stmtPedido.executeQuery();
            rs.next();
            Long pedidoId = rs.getLong("id");

            for (PedidoItem item : pedido.getItens()) {

                String sqlItem =
                    "INSERT INTO pedido_item " +
                    "(pedido_id, produto_id, valor_unitario_produto, quantidade, desconto) " +
                    "VALUES (?, ?, ?, ?, ?)";

                PreparedStatement stmtItem =
                    conn.prepareStatement(sqlItem);

                stmtItem.setLong(1, pedidoId);
                stmtItem.setLong(2, item.getProdutoId());
                stmtItem.setBigDecimal(3, item.getValorUnitarioProduto());
                stmtItem.setInt(4, item.getQuantidade());
                stmtItem.setBigDecimal(5, item.getDesconto());

                stmtItem.executeUpdate();

                PreparedStatement stmtEstoque =
                    conn.prepareStatement(
                        "UPDATE produtos SET estoque = estoque - ? WHERE id = ?"
                    );
                
                validarEstoque(conn, item.getProdutoId(), item.getQuantidade());
                stmtEstoque.setInt(1, item.getQuantidade());
                stmtEstoque.setLong(2, item.getProdutoId());
                stmtEstoque.executeUpdate();
            }

            conn.commit();
        }
    }

    public List<Pedido> buscarPorPeriodo(Date inicio, Date fim) throws Exception {

        List<Pedido> lista = new ArrayList<>();

        String sql =
            "SELECT * FROM pedidos WHERE data_pedido BETWEEN ? AND ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, inicio);
            stmt.setDate(2, fim);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pedido p = new Pedido(
                    rs.getLong("id"),
                    rs.getLong("cliente_id"),
                    rs.getDate("data_pedido")
                );
                lista.add(p);
            }
        }

        return lista;
    }

    public List<Pedido> listarPorCliente(Long clienteId) throws Exception {

        List<Pedido> lista = new ArrayList<>();

        String sql = "SELECT * FROM pedidos WHERE cliente_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, clienteId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pedido p = new Pedido(
                    rs.getLong("id"),
                    rs.getLong("cliente_id"),
                    rs.getDate("data_pedido")
                );
                lista.add(p);
            }
        }

        return lista;
    }

    public List<Pedido> listarComCliente() throws Exception {

        List<Pedido> lista = new ArrayList<>();

        String sql =
            "SELECT p.id, p.cliente_id, c.nome AS cliente_nome, p.data_pedido " +
            "FROM pedidos p " +
            "JOIN clientes c ON p.cliente_id = c.id";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Pedido p = new Pedido(
                    rs.getLong("id"),
                    rs.getLong("cliente_id"),
                    rs.getDate("data_pedido")
                );

                p.setClienteNome(rs.getString("cliente_nome"));

                lista.add(p);
            }
        }

        return lista;
    }

    public List<Pedido> listarPorProduto(Long produtoId) throws Exception {

        List<Pedido> lista = new ArrayList<>();

        String sql =
            "SELECT DISTINCT p.* " +
            "FROM pedidos p " +
            "JOIN pedido_item pi ON p.id = pi.pedido_id " +
            "WHERE pi.produto_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, produtoId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pedido p = new Pedido(
                    rs.getLong("id"),
                    rs.getLong("cliente_id"),
                    rs.getDate("data_pedido")
                );
                lista.add(p);
            }
        }

        return lista;
    }

    public List<PedidoItem> buscarItensPorPedido(Long pedidoId) throws Exception {

        List<PedidoItem> itens = new ArrayList<>();

        String sql =
            "SELECT pi.*, pr.descricao " +
            "FROM pedido_item pi " +
            "JOIN produtos pr ON pi.produto_id = pr.id " +
            "WHERE pi.pedido_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, pedidoId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                PedidoItem item = new PedidoItem(
                    rs.getLong("produto_id"),
                    rs.getBigDecimal("valor_unitario_produto"),
                    rs.getInt("quantidade"),
                    rs.getBigDecimal("desconto")
                );

                item.setProdutoNome(rs.getString("descricao"));

                itens.add(item);
            }
        }

        return itens;
    }


    public BigDecimal calcularTotalPorCliente(Long clienteId) throws Exception {

        String sql =
            "SELECT SUM((pi.valor_unitario_produto * pi.quantidade) - pi.desconto) AS total " +
            "FROM pedidos p " +
            "JOIN pedido_item pi ON p.id = pi.pedido_id " +
            "WHERE p.cliente_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, clienteId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                BigDecimal total = rs.getBigDecimal("total");
                return total != null ? total : BigDecimal.ZERO;
            }
        }

        return BigDecimal.ZERO;
    }

    public Pedido buscarPorId(Long id) throws Exception {

        String sql = "SELECT * FROM pedidos WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Pedido(
                    rs.getLong("id"),
                    rs.getLong("cliente_id"),
                    rs.getDate("data_pedido")
                );
            }
        }

        return null;
    }

    private void validarEstoque(Connection conn,
                            Long produtoId,
                            Integer quantidade) throws Exception {

        String sql = "SELECT estoque FROM produtos WHERE id = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setLong(1, produtoId);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {

            int estoqueAtual = rs.getInt("estoque");

            if (quantidade > estoqueAtual) {
                throw new RuntimeException(
                    "Estoque insuficiente para o produto ID: " + produtoId
                );
            }

        } else {
            throw new RuntimeException("Produto não encontrado");
        }
    }
}