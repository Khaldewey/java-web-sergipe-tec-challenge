package br.com.empresa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.empresa.config.ConnectionFactory;
import br.com.empresa.models.Cliente;
import br.com.empresa.models.metadados.ClienteColuna;

public class ClienteDAO {

    public void salvar(Cliente cliente) throws Exception {

        String sql = "INSERT INTO " + Cliente.NM_TABELA +
        " (" + ClienteColuna.NOME + ", " +
               ClienteColuna.EMAIL + ", " +
               ClienteColuna.DATA_CADASTRO + ") " +
        "VALUES (?, ?, ?)";

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

        String sql = "SELECT " +
        ClienteColuna.ID + ", " +
        ClienteColuna.NOME + ", " +
        ClienteColuna.EMAIL + ", " +
        ClienteColuna.DATA_CADASTRO +
        " FROM " + Cliente.NM_TABELA;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }

        return lista;
    }

    public List<Cliente> buscarPorNome(String nome) throws Exception {

        List<Cliente> lista = new ArrayList<>();

        String sql = "SELECT " +
                ClienteColuna.ID + ", " +
                ClienteColuna.NOME + ", " +
                ClienteColuna.EMAIL + ", " +
                ClienteColuna.DATA_CADASTRO +
                " FROM " + Cliente.NM_TABELA +
                " WHERE LOWER(" + ClienteColuna.NOME + ") LIKE LOWER(?)";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }

        return lista;
    }

    public Cliente buscarPorId(Long id) throws Exception {

        String sql = "SELECT " +
                ClienteColuna.ID + ", " +
                ClienteColuna.NOME + ", " +
                ClienteColuna.EMAIL + ", " +
                ClienteColuna.DATA_CADASTRO +
                " FROM " + Cliente.NM_TABELA +
                " WHERE " + ClienteColuna.ID + " = ?";

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

    private Cliente mapear(ResultSet rs) throws Exception {
        Cliente c = new Cliente(
                rs.getString(ClienteColuna.NOME.getNome()),
                rs.getString(ClienteColuna.EMAIL.getNome()),
                rs.getDate(ClienteColuna.DATA_CADASTRO.getNome())
        );

        c.setId(rs.getLong(ClienteColuna.ID.getNome()));
        return c;
    }
}