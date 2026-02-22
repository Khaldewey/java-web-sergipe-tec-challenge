package br.com.empresa.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import br.com.empresa.dao.ClienteDAO;
import br.com.empresa.dao.PedidoDAO;
import br.com.empresa.dao.ProdutoDAO;
import br.com.empresa.models.Cliente;
import br.com.empresa.models.Pedido;
import br.com.empresa.models.PedidoItem;
import br.com.empresa.models.Produto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/pedidos")
public class PedidoServlet extends HttpServlet {

    PedidoDAO dao = new PedidoDAO();
    
    ClienteDAO clienteDAO = new ClienteDAO();

    ProdutoDAO produtoDAO = new ProdutoDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException {

        try {

            String form = request.getParameter("form");

            if ("true".equals(form)) {

                request.setAttribute("clientes", clienteDAO.listar());
                request.setAttribute("produtos", produtoDAO.listar());

                request.getRequestDispatcher("/pedido/form.jsp").forward(request, response);
                return;
            }

            String id = request.getParameter("id");
            String inicio = request.getParameter("inicio");
            String fim = request.getParameter("fim");
            String clienteId = request.getParameter("clienteId");
            String produtoId = request.getParameter("produtoId");

            List<Pedido> lista;
            if (clienteId != null && !clienteId.isEmpty()) {
                lista = dao.listarPorCliente(Long.valueOf(clienteId));
            } else if (produtoId != null && !produtoId.isEmpty()) {
                lista = dao.listarPorProduto(Long.valueOf(produtoId));
            } else if (id != null && !id.isEmpty()) {
                Pedido p = dao.buscarPorId(Long.valueOf(id));
                lista = new ArrayList<>();
                if (p != null) {
                    lista.add(p);
                }
            } else if (inicio != null && fim != null
                    && !inicio.isEmpty() && !fim.isEmpty()) {
                lista = dao.buscarPorPeriodo(
                        Date.valueOf(inicio),
                        Date.valueOf(fim)
                );

            } else {
                lista = dao.listarComCliente();
            }

            carregarItensECliente(lista);
            
            if (clienteId != null && !clienteId.isEmpty()) {
                BigDecimal total = dao.calcularTotalPorCliente(Long.valueOf(clienteId));
                request.setAttribute("totalCliente", total);
            }

            List<Cliente> clientes = clienteDAO.listar();
            List<Produto> produtos = produtoDAO.listar();

            request.setAttribute("clientes", clientes);
            request.setAttribute("produtos", produtos);

            request.setAttribute("pedidos", lista);
            request.getRequestDispatcher("/pedido/index.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                        HttpServletResponse response)
                        throws ServletException, IOException {

        try {

            Long clienteId = Long.valueOf(request.getParameter("clienteId"));
            Long produtoId = Long.valueOf(request.getParameter("produtoId"));
            Integer quantidade = Integer.valueOf(request.getParameter("quantidade"));
            BigDecimal desconto = new BigDecimal(request.getParameter("desconto"));
            Date dataPedido = Date.valueOf(request.getParameter("dataPedido"));

            ProdutoDAO produtoDAO = new ProdutoDAO();

            var produto = produtoDAO.buscarPorId(produtoId);

            if (produto == null) {
                throw new RuntimeException("Produto não encontrado");
            }

            BigDecimal valorUnitarioProduto = produto.getValor();

            Pedido pedido = new Pedido(clienteId, dataPedido);

            PedidoItem item = new PedidoItem(
                    produtoId,
                    valorUnitarioProduto, 
                    quantidade,
                    desconto
            );

            List<PedidoItem> itens = new ArrayList<>();
            itens.add(item);

            pedido.setItens(itens);

            dao.salvar(pedido);

            response.sendRedirect(request.getContextPath() + "/pedidos");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void carregarItensECliente(List<Pedido> lista) throws Exception {

        for (Pedido p : lista) {
            p.setItens(dao.buscarItensPorPedido(p.getId()));

            ClienteDAO clienteDAO = new ClienteDAO();
            p.setClienteNome(
                clienteDAO.buscarPorId(p.getClienteId()).getNome()
            );
        }
    }
}