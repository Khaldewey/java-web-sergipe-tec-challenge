package br.com.empresa.servlet;

import java.io.IOException;

import br.com.empresa.dao.ClienteDAO;
import br.com.empresa.dao.PedidoDAO;
import br.com.empresa.dao.ProdutoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/pagina-principal")
public class PaginaPrincipalServlet extends HttpServlet{

        PedidoDAO pedidoDAO = new PedidoDAO();    
        ClienteDAO clienteDAO = new ClienteDAO();
        ProdutoDAO produtoDAO = new ProdutoDAO();

     protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException {

        try {

            Long totalClientes = (long) clienteDAO.listar().size();
            Long totalProdutos = (long) produtoDAO.listar().size();
            Long totalPedidos = (long) pedidoDAO.contar();

            request.setAttribute("totalClientes", totalClientes);
            request.setAttribute("totalProdutos", totalProdutos);
            request.setAttribute("totalPedidos", totalPedidos);

            request.getRequestDispatcher("/pagina-principal/index.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            request.setAttribute("exception", e);
            request.getRequestDispatcher("/exception.jsp")
           .forward(request, response);
        }
    }

}
