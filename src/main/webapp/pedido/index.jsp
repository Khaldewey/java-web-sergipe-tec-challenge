<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
</head>
<body>

<form method="get" action="${pageContext.request.contextPath}/pedidos">
    <h3>Consultar pedidos por identificador ou período</h3>
    <input type="number" name="id" placeholder="Buscar por ID">
    <input type="date" name="inicio">
    <input type="date" name="fim">
    <h3>Consultar pedidos de um cliente e valor total</h3>
    <select name="clienteId">
        <option value="">Todos</option>
            <c:forEach var="c" items="${clientes}">
                <option value="${c.id}"
                    <c:if test="${param.clienteId == c.id}">selected</c:if>>
                        ${c.nome}
                </option>
            </c:forEach>
    </select>
    <h3>Consultar pedidos por produto</h3>
    <select name="produtoId">
        <option value="">Todos</option>

        <c:forEach var="p" items="${produtos}">
            <option value="${p.id}"
                <c:if test="${param.produtoId == p.id}">selected</c:if>>
                ${p.descricao}
            </option>
        </c:forEach>
    </select>
    <button type="submit">Buscar</button>
</form>

<br>

<h3>Lista de Pedidos</h3>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Cliente</th>
        <th>Data</th>
        <th>Total</th>
        <th>Itens</th>
    </tr>

    <c:forEach var="p" items="${pedidos}">
        <tr>
            <td>${p.id}</td>
            <td>${p.clienteNome}</td>
            <td>${p.dataPedido}</td>
            <td>R$ ${p.total}</td>
            <td>
                <table border="1">
                    <tr>
                        <th>Produto</th>
                        <th>Qtd</th>
                        <th>Valor</th>
                    </tr>

                    <c:forEach var="i" items="${p.itens}">
                        <tr>
                            <td>${i.produtoNome}</td>
                            <td>${i.quantidade}</td>
                            <td>${i.valorUnitarioProduto}</td>
                        </tr>
                    </c:forEach>

                </table>
            </td>
        </tr>
    </c:forEach>
</table>
<c:if test="${not empty totalCliente}">
    <h4>Total do Cliente: R$ ${totalCliente}</h4>
</c:if>

<br>

<a href="${pageContext.request.contextPath}/pedidos?form=true">Novo Pedido</a>
<a href="${pageContext.request.contextPath}/pagina-principal">Voltar</a>

</body>
</html>