<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
    </head>
<body>
  <h3>Buscar Cliente</h3>

  <form method="get" action="${pageContext.request.contextPath}/clientes">
      <input type="text" name="nome" placeholder="Buscar por nome">
      <input type="number" name="id" placeholder="Buscar por ID">
      <button type="submit">Buscar</button>
  </form>

  <br>

  <h3>Lista de Clientes</h3>

  <table border="1">
      <tr>
          <th>ID</th>
          <th>Nome</th>
          <th>Email</th>
          <th>Data Cadastro</th>
      </tr>

      <c:forEach var="c" items="${clientes}">
          <tr>
              <td>${c.id}</td>
              <td>${c.nome}</td>
              <td>${c.email}</td>
              <td>${c.dataCadastro}</td>
          </tr>
      </c:forEach>

  </table>

  <br>

  <a href="${pageContext.request.contextPath}/cliente/form.jsp">Novo Cliente</a>
  <a href="${pageContext.request.contextPath}/pagina-principal">Voltar</a>

</body>
</html>