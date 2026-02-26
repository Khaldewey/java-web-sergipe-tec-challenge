<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Clientes</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f9;
            margin: 0;
            padding: 40px;
        }

        h2 {
            margin-bottom: 20px;
        }

        .container {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
        }

        .search-box {
            margin-bottom: 25px;
        }

        input {
            padding: 8px;
            margin-right: 10px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }

        button {
            padding: 8px 15px;
            border: none;
            background-color: #007bff;
            color: white;
            border-radius: 5px;
            cursor: pointer;
        }

        button:hover {
            background-color: #0056b3;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th {
            background-color: #007bff;
            color: white;
            padding: 10px;
        }

        td {
            padding: 10px;
            border-bottom: 1px solid #ddd;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        .actions {
            margin-top: 20px;
        }

        .actions a {
            text-decoration: none;
            margin-right: 15px;
            padding: 8px 15px;
            border-radius: 5px;
            background-color: #28a745;
            color: white;
        }

        .actions a.voltar {
            background-color: #6c757d;
        }

        .actions a:hover {
            opacity: 0.9;
        }
    </style>
</head>
<body>

<div class="container">

    <h2>Gestão de Clientes</h2>

    <div class="search-box">
        <form method="get" action="${pageContext.request.contextPath}/clientes">
            <input type="text" name="nome" placeholder="Buscar por nome">
            <input type="number" name="id" placeholder="Buscar por ID">
            <button type="submit">Buscar</button>
        </form>
    </div>

    <table>
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

    <div class="actions">
        <a href="${pageContext.request.contextPath}/cliente/form.jsp">
            + Novo Cliente
        </a>

        <a href="${pageContext.request.contextPath}/pagina-principal" class="voltar">
            ← Voltar
        </a>
    </div>

</div>

</body>
</html>