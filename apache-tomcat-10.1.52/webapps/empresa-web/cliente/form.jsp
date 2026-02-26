<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Cliente</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f9;
            margin: 0;
            padding: 40px;
            display: flex;
            justify-content: center;
        }

        .container {
            background: white;
            padding: 30px;
            width: 400px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
        }

        h2 {
            margin-bottom: 20px;
            text-align: center;
        }

        .form-group {
            margin-bottom: 18px;
        }

        label {
            font-weight: bold;
        }

        input {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }

        input.error {
            border-color: red;
        }

        .error-message {
            color: red;
            font-size: 13px;
            margin-top: 5px;
        }

        button {
            width: 100%;
            padding: 10px;
            border: none;
            background-color: #007bff;
            color: white;
            border-radius: 5px;
            cursor: pointer;
            font-size: 15px;
        }

        button:hover {
            background-color: #0056b3;
        }

        .voltar {
            display: block;
            text-align: center;
            margin-top: 15px;
            text-decoration: none;
            color: #6c757d;
        }

        .voltar:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="container">

    <h2>Cadastro de Cliente</h2>

    <c:if test="${not empty erro}">
    <div style="color: red; font-weight: bold;">
        ${erro}
    </div>
    </c:if>

    <form id="clienteForm"
          action="${pageContext.request.contextPath}/clientes"
          method="post">

        <div class="form-group">
            <label>Nome</label>
            <input type="text" id="nome" name="nome">
            <div class="error-message" id="nomeErro"></div>
        </div>

        <div class="form-group">
            <label>Email</label>
            <input type="email" id="email" name="email">
            <div class="error-message" id="emailErro"></div>
        </div>

        <div class="form-group">
            <label>Data Cadastro</label>
            <input type="date" id="dataCadastro" name="dataCadastro">
            <div class="error-message" id="dataErro"></div>
        </div>

        <button type="submit">Salvar</button>
    </form>

    <a href="${pageContext.request.contextPath}/clientes" class="voltar">
        ← Voltar
    </a>

</div>

<script>
    const form = document.getElementById("clienteForm");

    form.addEventListener("submit", function(event) {

        let valido = true;

        limparErros();

        const nome = document.getElementById("nome");
        const email = document.getElementById("email");
        const data = document.getElementById("dataCadastro");

        if (nome.value.trim().length < 3) {
            mostrarErro(nome, "nomeErro", "Nome deve ter pelo menos 3 caracteres.");
            valido = false;
        }

        const regexEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!regexEmail.test(email.value)) {
            mostrarErro(email, "emailErro", "Email inválido.");
            valido = false;
        }

        if (!data.value) {
            mostrarErro(data, "dataErro", "Informe a data de cadastro.");
            valido = false;
        }

        if (!valido) {
            event.preventDefault();
        }
    });

    function mostrarErro(input, idErro, mensagem) {
        input.classList.add("error");
        document.getElementById(idErro).innerText = mensagem;
    }

    function limparErros() {
        const inputs = document.querySelectorAll("input");
        inputs.forEach(i => i.classList.remove("error"));

        const mensagens = document.querySelectorAll(".error-message");
        mensagens.forEach(m => m.innerText = "");
    }
</script>

</body>
</html>