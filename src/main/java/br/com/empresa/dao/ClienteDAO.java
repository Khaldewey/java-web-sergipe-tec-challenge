package br.com.empresa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.empresa.config.ConnectionFactory;
import br.com.empresa.models.Cliente;

public class ClienteDAO {

    public void salvar(Cliente cliente) throws Exception {

        String sql = "INSERT INTO clientes (nome, email, data_cadastro) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setDate(3, cliente.getDataCadastro());

            stmt.executeUpdate();
        }
    }

    public List<Cliente> listar() throws Exception {

        List<Cliente> lista = new ArrayList<>();

        String sql = "SELECT id, nome, email, data_cadastro FROM clientes";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Cliente c = new Cliente(
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getDate("data_cadastro")
                );

                c.setId(rs.getLong("id"));
                lista.add(c);
            }
        }

        return lista;
    }

    public List<Cliente> buscarPorNome(String nome) throws Exception {

        List<Cliente> lista = new ArrayList<>();

        String sql = "SELECT * FROM clientes WHERE LOWER(nome) LIKE LOWER(?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Cliente c = new Cliente(
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getDate("data_cadastro")
                );

                c.setId(rs.getLong("id"));
                lista.add(c);
            }
        }

        return lista;
    }

    public Cliente buscarPorId(Long id) throws Exception {

        String sql = "SELECT * FROM clientes WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Cliente c = new Cliente(
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getDate("data_cadastro")
                );

                c.setId(rs.getLong("id"));
                return c;
            }
        }

        return null;
    }
}