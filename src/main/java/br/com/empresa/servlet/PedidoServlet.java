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

    public static String parametroId = "id";
    public static String parametroDataInicio = "inicio";
    public static String parametroDataFim = "fim";
    public static String parametroProdutoId = "produtoId";
    public static String parametroClienteId = "clienteId";
    public static String parametroQuantidade = "quantidade";
    public static String parametroDesconto = "desconto";
    public static String parametroDataPedido = "dataPedido";

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

            String id = request.getParameter(parametroId);
            String inicio = request.getParameter(parametroDataInicio);
            String fim = request.getParameter(parametroDataFim);
            String clienteId = request.getParameter(parametroClienteId);
            String produtoId = request.getParameter(parametroProdutoId);

            List<Pedido> lista = dao.buscarComFiltros(
                    clienteId,
                    produtoId,
                    id,
                    inicio,
                    fim
            );

            carregarItensECliente(lista);
            
            if (clienteId != null && !clienteId.isEmpty() && !lista.isEmpty()) {
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

            Long clienteId = Long.valueOf(request.getParameter(parametroClienteId));
            Long produtoId = Long.valueOf(request.getParameter(parametroProdutoId));
            Integer quantidade = Integer.valueOf(request.getParameter(parametroQuantidade));
            BigDecimal desconto = new BigDecimal(request.getParameter(parametroDesconto));
            Date dataPedido = Date.valueOf(request.getParameter(parametroDataPedido));

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