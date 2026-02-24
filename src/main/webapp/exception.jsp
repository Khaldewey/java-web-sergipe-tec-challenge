<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Erro</title>
    <style>
        body {
            font-family: Arial;
            background-color: #f8d7da;
            padding: 40px;
        }
        .box {
            background: white;
            padding: 30px;
            border-radius: 8px;
        }
        h1 {
            color: #721c24;
        }
        pre {
            background: #f1f1f1;
            padding: 15px;
            overflow-x: auto;
        }
        a {
            display: inline-block;
            margin-top: 20px;
        }
    </style>
</head>
<body>

<div class="box">
    <h1>⚠ Ocorreu um erro inesperado</h1>
    <p>Desculpe pelo inconveniente. Tente novamente.</p>

    <h3>Detalhes técnicos:</h3>
    <pre>
${exception}
    </pre>

    <a href="${pageContext.request.contextPath}/">Voltar para o início</a>
</div>

</body>
</html>