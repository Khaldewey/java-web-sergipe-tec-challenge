package br.com.empresa.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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

            String descricao = request.getParameter("descricao");
            String id = request.getParameter("id");

            List<Produto> lista;

            if (descricao != null && !descricao.isEmpty()) {
                lista = dao.buscarPorDescricao(descricao);
            } else if (id != null && !id.isEmpty()) {
                Produto p = dao.buscarPorId(Long.valueOf(id));
                lista = new ArrayList<>();
                if (p != null) lista.add(p);
            } else {
                lista = dao.listar();
            }

            request.setAttribute("produtos", lista);                      
            request.getRequestDispatcher("/produto/index.jsp")
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
            response.sendRedirect(request.getContextPath() + "/produtos");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
