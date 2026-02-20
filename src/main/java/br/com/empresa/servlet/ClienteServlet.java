package br.com.empresa.servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import br.com.empresa.dao.ClienteDAO;
import br.com.empresa.models.Cliente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/clientes")
public class ClienteServlet extends HttpServlet {

    ClienteDAO dao = new ClienteDAO();

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException {

        try {

            String nome = request.getParameter("nome");
            String id = request.getParameter("id");

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
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String dataCadastro = request.getParameter("dataCadastro");

        try {
            Cliente cliente = new Cliente( nome, email, Date.valueOf(dataCadastro));
            dao.salvar(cliente);
            response.sendRedirect(request.getContextPath() + "/clientes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}