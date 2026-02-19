package br.com.empresa.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;

import br.com.empresa.dao.ProdutoDAO;
import br.com.empresa.models.Produto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/produtos")
public class ProdutoServlet extends HttpServlet {

    ProdutoDAO dao = new ProdutoDAO();

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException {

        try {
            request.setAttribute("mensagem", "Teste de Comunicação");                       
            request.getRequestDispatcher("index.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException, IOException {

        String descricao = request.getParameter("descricao");
        BigDecimal valor = new BigDecimal(request.getParameter("valor"));
        String quantidadeEmEstoque = request.getParameter("qtd");
        String dataDeCadastro = request.getParameter("dataCadastro");

        try {
            Produto produto = new Produto(descricao, valor, Integer.valueOf(quantidadeEmEstoque), Date.valueOf(dataDeCadastro));
            dao.salvar(produto);
            response.sendRedirect("produtos");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
