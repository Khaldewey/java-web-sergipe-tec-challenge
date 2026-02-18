<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulário Produto</title>
</head>
<body>

    <h2>Cadastro de Produto</h2>

    <form action="produtos" method="post">

        <div>
            <label>Descrição:</label><br>
            <input type="text" name="descricao" required>
        </div>

        <br>

        <div>
            <label>Valor:</label><br>
            <input type="number" name="valor" step="0.01" required>
        </div>

        <br>

        <div>
            <label>Quantidade:</label><br>
            <input type="number" name="qtd" required>
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
    <a href="index.jsp">Voltar</a>

</body>
</html>
