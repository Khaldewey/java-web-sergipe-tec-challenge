<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
      
      <% 
        String teste = (String) request.getAttribute("mensagem"); 
      %>
    </head>
<body>
    <h2><%=teste%></h2>
</body>
</html>