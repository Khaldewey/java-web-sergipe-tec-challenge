<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f9;
            margin: 0;
            padding: 40px;
        }

        h1 {
            text-align: center;
            margin-bottom: 40px;
        }

        .dashboard {
            display: flex;
            justify-content: center;
            gap: 30px;
        }

        .card {
            background: white;
            padding: 30px;
            border-radius: 10px;
            width: 220px;
            text-align: center;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            transition: 0.2s;
        }

        .card:hover {
            transform: scale(1.05);
        }

        .card h2 {
            margin: 10px 0;
        }

        .card a {
            text-decoration: none;
            display: inline-block;
            margin-top: 15px;
            padding: 8px 15px;
            background-color: #007bff;
            color: white;
            border-radius: 5px;
        }

        .card a:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<h1>Painel Administrativo</h1>

<div class="dashboard">

    <div class="card">
        <h2>Clientes</h2>
        <p>Total: ${totalClientes}</p>
        <a href="${pageContext.request.contextPath}/clientes">Acessar</a>
    </div>

    <div class="card">
        <h2>Produtos</h2>
        <p>Total: ${totalProdutos}</p>
        <a href="${pageContext.request.contextPath}/produtos">Acessar</a>
    </div>

    <div class="card">
        <h2>Pedidos</h2>
        <p>Total: ${totalPedidos}</p>
        <a href="${pageContext.request.contextPath}/pedidos">Acessar</a>
    </div>

</div>

</body>
</html>