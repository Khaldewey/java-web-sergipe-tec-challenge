<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulário Cliente</title>
</head>
<body>

    <h2>Cadastro de Cliente</h2>

    <form action="${pageContext.request.contextPath}/clientes" method="post">

        <div>
            <label>Nome:</label><br>
            <input type="text" name="nome" required>
        </div>

        <br>

        <div>
            <label>Email:</label><br>
            <input type="email" name="email" required>
        </div>

        <br>

        <div>
            <label>Data Cadastro:</label><br>
            <input type="date" name="dataCadastro" required>
        </div>

        <br>

        <button type="submit">Salvar</button>

    </form>

    <br>
    <a href="${pageContext.request.contextPath}/clientes">Voltar</a>

</body>
</html>