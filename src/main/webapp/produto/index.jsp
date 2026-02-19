<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
    </head>
<body>
  <h3>Buscar Produto</h3>

  <form method="get" action="${pageContext.request.contextPath}/produtos">
      <input type="text" name="descricao" placeholder="Buscar por descrição">
      <input type="number" name="id" placeholder="Buscar por ID">
      <button type="submit">Buscar</button>
  </form>

  <br>

  <h3>Lista de Produtos</h3>

  <table border="1">
      <tr>
          <th>ID</th>
          <th>Descrição</th>
          <th>Valor</th>
          <th>Estoque</th>
          <th>Data Cadastro</th>
      </tr>

      <c:forEach var="p" items="${produtos}">
          <tr>
              <td>${p.id}</td>
              <td>${p.descricao}</td>
              <td>${p.valor}</td>
              <td>${p.quantidadeEmEstoque}</td>
              <td>${p.dataDeCadastro}</td>
          </tr>
      </c:forEach>

  </table>

  <a href="${pageContext.request.contextPath}/produto/form.jsp">Novo Produto</a>
</body>
</html>