<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Pedidos</title>

    <style>
        body {
            font-family: Arial, Helvetica, sans-serif;
            background: linear-gradient(135deg, #1e3c72, #2a5298);
            margin: 0;
            padding: 40px;
        }

        .container {
            max-width: 1200px;
            margin: auto;
        }

        h2 {
            color: white;
            margin-bottom: 20px;
        }

        .card {
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 6px 18px rgba(0,0,0,0.15);
            margin-bottom: 30px;
        }

        h3 {
            margin-top: 0;
        }

        .form-group {
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
            margin-bottom: 15px;
        }

        input, select {
            padding: 8px;
            border-radius: 6px;
            border: 1px solid #ccc;
            min-width: 150px;
        }

        button {
            padding: 10px 18px;
            border-radius: 6px;
            border: none;
            background-color: #2a5298;
            color: white;
            font-weight: bold;
            cursor: pointer;
            transition: 0.3s;
        }

        button:hover {
            background-color: #1e3c72;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th {
            background-color: #2a5298;
            color: white;
            padding: 10px;
            text-align: left;
        }

        td {
            padding: 8px;
            border-bottom: 1px solid #ddd;
            vertical-align: top;
        }

        tr:hover {
            background-color: #f5f5f5;
        }

        .inner-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 5px;
        }

        .inner-table th {
            background-color: #e9eef7;
            color: #333;
            font-size: 13px;
        }

        .inner-table td {
            font-size: 13px;
            border-bottom: 1px solid #eee;
        }

        .total-box {
            margin-top: 15px;
            font-weight: bold;
            background: #e9eef7;
            padding: 10px;
            border-radius: 6px;
        }

        .actions {
            margin-top: 20px;
        }

        .btn-secondary {
            background-color: #555;
            margin-left: 10px;
        }

        .btn-secondary:hover {
            background-color: #333;
        }

        .empty {
            text-align: center;
            padding: 20px;
            color: #777;
        }
    </style>
</head>
<body>

<div class="container">

    <h2>🧾 Gestão de Pedidos</h2>

    <div class="card">
        <form method="get" action="${pageContext.request.contextPath}/pedidos">

            <h3>Consultar por ID ou Período</h3>
            <div class="form-group">
                <input type="number" name="id" placeholder="Buscar por ID">
                <input type="date" name="inicio">
                <input type="date" name="fim">
            </div>

            <h3>Consultar por Cliente / Exibir valor total</h3>
            <div class="form-group">
                <select name="clienteId">
                    <option value="">Todos</option>
                    <c:forEach var="c" items="${clientes}">
                        <option value="${c.id}"
                            <c:if test="${param.clienteId == c.id}">selected</c:if>>
                            ${c.nome}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <h3>Consultar por Produto</h3>
            <div class="form-group">
                <select name="produtoId">
                    <option value="">Todos</option>
                    <c:forEach var="p" items="${produtos}">
                        <option value="${p.id}"
                            <c:if test="${param.produtoId == p.id}">selected</c:if>>
                            ${p.descricao}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <button type="submit">Buscar</button>

        </form>
    </div>

    <div class="card">
        <h3>Lista de Pedidos</h3>

        <table>
            <tr>
                <th>ID</th>
                <th>Cliente</th>
                <th>Data</th>
                <th>Total</th>
                <th>Itens</th>
            </tr>

            <c:choose>
                <c:when test="${not empty pedidos}">
                    <c:forEach var="p" items="${pedidos}">
                        <tr>
                            <td>${p.id}</td>
                            <td>${p.clienteNome}</td>
                            <td>${p.dataPedido}</td>
                            <td><strong>R$ ${p.total}</strong></td>
                            <td>
                                <table class="inner-table">
                                    <tr>
                                        <th>Produto</th>
                                        <th>Qtd</th>
                                        <th>Valor</th>
                                        <th>Desc.</th>
                                    </tr>
                                    <c:forEach var="i" items="${p.itens}">
                                        <tr>
                                            <td>${i.produtoNome}</td>
                                            <td>${i.quantidade}</td>
                                            <td>R$ ${i.valorUnitarioProduto}</td>
                                            <td>${i.desconto}</td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>

                <c:otherwise>
                    <tr>
                        <td colspan="5" class="empty">
                            Nenhum pedido encontrado.
                        </td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </table>

        <c:if test="${not empty totalCliente}">
            <div class="total-box">
                Total do Cliente: R$ ${totalCliente}
            </div>
        </c:if>

        <div class="actions">
            <a href="${pageContext.request.contextPath}/pedidos?form=true">
                <button>Novo Pedido</button>
            </a>

            <a href="${pageContext.request.contextPath}/pagina-principal">
                <button class="btn-secondary">Voltar</button>
            </a>
        </div>

    </div>

</div>

</body>
</html>