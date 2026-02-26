package br.com.empresa.servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.util.PSQLException;

import br.com.empresa.dao.ClienteDAO;
import br.com.empresa.models.Cliente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/clientes")
public class ClienteServlet extends HttpServlet {

    public static String parametroId = "id";
    public static String parametroNome = "nome";
    public static String parametroEmail = "email";
    public static String parametroDataCadastro = "dataCadastro";

    ClienteDAO dao = new ClienteDAO();

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException {

        try {

            String nome = request.getParameter(parametroNome);
            String id = request.getParameter(parametroId);

            List<Cliente> lista;

            if (nome != null && !nome.isEmpty()) {

                lista = dao.buscarPorNome(nome);

            } else if (id != null && !id.isEmpty()) {

                Cliente c = dao.buscarPorId(Long.valueOf(id));
                lista = new ArrayList<>();

                if (c != null) lista.add(c);

            } else {

                lista = dao.listar();
            }

            request.setAttribute("clientes", lista);

            request.getRequestDispatcher("/cliente/index.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            request.setAttribute("exception", e);
            request.getRequestDispatcher("/exception.jsp")
           .forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException, IOException {

        String nome = request.getParameter(parametroNome);
        String email = request.getParameter(parametroEmail);
        String dataCadastro = request.getParameter(parametroDataCadastro);
        Cliente cliente = null;

        try {
            cliente = new Cliente( nome, email, Date.valueOf(dataCadastro));
            dao.salvar(cliente);
            response.sendRedirect(request.getContextPath() + "/clientes");
        } catch (PSQLException e) {
            if ("23505".equals(e.getSQLState())) {

                request.setAttribute("erro",
                    "Já existe e-mail cadastrado na base de dados.");

                request.setAttribute("cliente", cliente);

                request.getRequestDispatcher("/cliente/form.jsp")
                    .forward(request, response);
            } else {
                throw new ServletException(e);
            }

        } catch (Exception e) {

            request.setAttribute("exception", e);
            request.getRequestDispatcher("/exception.jsp")
                .forward(request, response);
        }
    }
}