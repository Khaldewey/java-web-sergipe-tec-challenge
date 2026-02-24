<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Produtos</title>

    <style>
        body {
            font-family: Arial, Helvetica, sans-serif;
            background: linear-gradient(135deg, #1e3c72, #2a5298);
            margin: 0;
            padding: 40px;
            color: #333;
        }

        .container {
            max-width: 1100px;
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

        .form-group {
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
        }

        input {
            padding: 10px;
            border-radius: 6px;
            border: 1px solid #ccc;
            flex: 1;
            min-width: 200px;
        }

        button {
            padding: 10px 18px;
            border-radius: 6px;
            border: none;
            background-color: #2a5298;
            color: white;
            cursor: pointer;
            font-weight: bold;
            transition: 0.3s;
        }

        button:hover {
            background-color: #1e3c72;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        th {
            background-color: #2a5298;
            color: white;
            padding: 12px;
            text-align: left;
        }

        td {
            padding: 10px;
            border-bottom: 1px solid #ddd;
        }

        tr:hover {
            background-color: #f2f2f2;
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

    <h2>📦 Gestão de Produtos</h2>

    <div class="card">
        <h3>Buscar Produto</h3>

        <form method="get" action="${pageContext.request.contextPath}/produtos">
            <div class="form-group">
                <input type="text" name="descricao" placeholder="Buscar por descrição">
                <input type="number" name="id" placeholder="Buscar por ID">
                <button type="submit">Buscar</button>
            </div>
        </form>
    </div>

    <div class="card">
        <h3>Lista de Produtos</h3>

        <table>
            <tr>
                <th>ID</th>
                <th>Descrição</th>
                <th>Valor</th>
                <th>Estoque</th>
                <th>Data Cadastro</th>
            </tr>

            <c:choose>
                <c:when test="${not empty produtos}">
                    <c:forEach var="p" items="${produtos}">
                        <tr>
                            <td>${p.id}</td>
                            <td>${p.descricao}</td>
                            <td>R$ ${p.valor}</td>
                            <td>${p.quantidadeEmEstoque}</td>
                            <td>${p.dataDeCadastro}</td>
                        </tr>
                    </c:forEach>
                </c:when>

                <c:otherwise>
                    <tr>
                        <td colspan="5" class="empty">
                            Nenhum produto encontrado.
                        </td>
                    </tr>
                </c:otherwise>
            </c:choose>

        </table>

        <div class="actions">
            <a href="${pageContext.request.contextPath}/produto/form.jsp">
                <button>Novo Produto</button>
            </a>

            <a href="${pageContext.request.contextPath}/pagina-principal">
                <button class="btn-secondary">Voltar</button>
            </a>
        </div>

    </div>

</div>

</body>
</html>