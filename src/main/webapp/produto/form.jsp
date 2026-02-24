<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Produto</title>

    <style>
        body {
            font-family: Arial, Helvetica, sans-serif;
            background: linear-gradient(135deg, #1e3c72, #2a5298);
            margin: 0;
            padding: 40px;
        }

        .container {
            max-width: 500px;
            margin: auto;
        }

        .card {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 6px 18px rgba(0,0,0,0.15);
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
        }

        .form-group {
            margin-bottom: 18px;
        }

        label {
            font-weight: bold;
        }

        input {
            width: 100%;
            padding: 10px;
            border-radius: 6px;
            border: 1px solid #ccc;
            margin-top: 5px;
        }

        input:focus {
            outline: none;
            border-color: #2a5298;
        }

        .error {
            color: red;
            font-size: 13px;
            margin-top: 4px;
        }

        button {
            width: 100%;
            padding: 12px;
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

        .btn-secondary {
            margin-top: 10px;
            background-color: #555;
        }

        .btn-secondary:hover {
            background-color: #333;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="card">
        <h2>📦 Cadastro de Produto</h2>

        <form id="produtoForm" 
              action="${pageContext.request.contextPath}/produtos" 
              method="post">

            <div class="form-group">
                <label>Descrição:</label>
                <input type="text" name="descricao" id="descricao">
                <div class="error" id="erroDescricao"></div>
            </div>

            <div class="form-group">
                <label>Valor:</label>
                <input type="number" name="valor" step="0.01" min="0" id="valor" required>
                <div class="error" id="erroValor"></div>
            </div>

            <div class="form-group">
                <label>Quantidade:</label>
                <input type="number" name="qtd" id="qtd"  min="0" step="1">
                <div class="error" id="erroQtd"></div>
            </div>

            <div class="form-group">
                <label>Data Cadastro:</label>
                <input type="date" name="dataCadastro" id="dataCadastro">
                <div class="error" id="erroData"></div>
            </div>

            <button type="submit">Salvar</button>

            <a href="${pageContext.request.contextPath}/produtos">
                <button type="button" class="btn-secondary">Voltar</button>
            </a>

        </form>
    </div>
</div>

<script>
document.addEventListener("DOMContentLoaded", function () {

    const valorInput = document.getElementById("valor");
    const form = document.getElementById("produtoForm");

    valorInput.addEventListener("input", function (e) {
        let v = e.target.value;

        v = v.replace(/\D/g, "");

        if (v.length === 0) {
            e.target.value = "0.00";
            return;
        }

        let numero = parseInt(v);
        let formatado = (numero / 100).toFixed(2);

        e.target.value = formatado;
    });

    valorInput.addEventListener("focus", function (e) {
        if (e.target.value === "") {
            e.target.value = "0.00";
        }
    });

    form.addEventListener("submit", function(e) {

        let valido = true;

        document.querySelectorAll(".error").forEach(el => el.innerText = "");

        let descricao = document.getElementById("descricao").value.trim();
        let valor = parseFloat(valorInput.value);
        let qtd = parseInt(document.getElementById("qtd").value);
        let data = document.getElementById("dataCadastro").value;

        let hoje = new Date().toISOString().split("T")[0];

        if (descricao === "") {
            document.getElementById("erroDescricao").innerText = "Descrição é obrigatória.";
            valido = false;
        }

        if (isNaN(valor) || valor <= 0) {
            document.getElementById("erroValor").innerText = "Valor deve ser maior que zero.";
            valido = false;
        }

        if (isNaN(qtd) || qtd < 0) {
            document.getElementById("erroQtd").innerText = "Quantidade não pode ser negativa.";
            valido = false;
        }

        if (data === "") {
            document.getElementById("erroData").innerText = "Data é obrigatória.";
            valido = false;
        } else if (data > hoje) {
            document.getElementById("erroData").innerText = "Data não pode ser futura.";
            valido = false;
        }

        if (!valido) {
            e.preventDefault();
        }
    });

});
</script>

</body>
</html>