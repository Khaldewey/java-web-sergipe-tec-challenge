<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Pedido</title>

    <style>
        body {
            font-family: Arial, Helvetica, sans-serif;
            background: linear-gradient(135deg, #1e3c72, #2a5298);
            margin: 0;
            padding: 40px;
        }

        .container {
            max-width: 600px;
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

        input, select {
            width: 100%;
            padding: 10px;
            border-radius: 6px;
            border: 1px solid #ccc;
            margin-top: 5px;
        }

        input:focus, select:focus {
            outline: none;
            border-color: #2a5298;
        }

        .readonly {
            background-color: #f3f3f3;
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

<h2>🧾 Cadastro de Pedido</h2>

<form action="${pageContext.request.contextPath}/pedidos" method="post" id="pedidoForm">

   <div class="form-group">
      <label>Cliente:</label>
      <select name="clienteId" required>
          <option value="">Selecione</option>
          <c:forEach var="c" items="${clientes}">
              <option value="${c.id}">
                  ${c.nome}
              </option>
          </c:forEach>
      </select>
   </div>

  <div class="form-group">
    <label>Produto:</label>
    <select name="produtoId" id="produtoSelect" required>
      <option value="">Selecione</option>
      <c:forEach var="p" items="${produtos}">
          <option 
              value="${p.id}"
              data-valor-unitario="${p.valor}"
              data-estoque="${p.quantidadeEmEstoque}">
              ${p.descricao} - R$ ${p.valor} (Estoque: ${p.quantidadeEmEstoque})
          </option>
      </c:forEach>
    </select>
  </div>

  <div class="form-group">
      <label>Valor Unitário:</label>
      <input type="text" name="valor-unitario" id="valor-unitario" readonly class="readonly">
  </div>

  <div class="form-group">
      <label>Quantidade:</label>
      <input type="number" name="quantidade" id="quantidade" min="1" required>
  </div>

  <div class="form-group">
        <label>Total:</label>
        <input type="text" id="valor-total" readonly class="readonly">
   </div>

  <div class="form-group">
      <label>Desconto:</label>
      <input type="text" name="desconto" id="desconto" value="0.00">
  </div>

  <div class="form-group">
      <label>Data Pedido:</label>
      <input type="date" name="dataPedido" required>
  </div>

  <button type="submit">Salvar</button>

  <a href="${pageContext.request.contextPath}/pedidos">
      <button type="button" class="btn-secondary">Voltar</button>
  </a>

</form>

</div>
</div>

<script>
document.addEventListener("DOMContentLoaded", function () {

    const produtoSelect = document.getElementById("produtoSelect");
    const valorUnitarioInput = document.getElementById("valor-unitario");
    const quantidadeInput = document.getElementById("quantidade");
    const totalInput = document.getElementById("valor-total");
    const descontoInput = document.getElementById("desconto");

    let estoqueAtual = 0;

    descontoInput.addEventListener("input", function (e) {
        let v = e.target.value.replace(/\D/g, "");

        if (v.length === 0) {
            e.target.value = "0.00";
            return;
        }

        let numero = parseInt(v);
        e.target.value = (numero / 100).toFixed(2);

        calcularTotal();
    });

    produtoSelect.addEventListener("change", function () {

        const selectedOption = this.options[this.selectedIndex];

        const valor = selectedOption.getAttribute("data-valor-unitario");
        estoqueAtual = parseInt(selectedOption.getAttribute("data-estoque"));

        valorUnitarioInput.value = valor ? parseFloat(valor).toFixed(2) : "";
        quantidadeInput.max = estoqueAtual;

        calcularTotal();
    });

    quantidadeInput.addEventListener("input", function () {

        if (parseInt(this.value) > estoqueAtual) {
            alert("Quantidade maior que o estoque disponível!");
            this.value = estoqueAtual;
        }

        calcularTotal();
    });

    function calcularTotal() {

        const valor = parseFloat(valorUnitarioInput.value) || 0;
        const quantidade = parseInt(quantidadeInput.value) || 0;
        const desconto = parseFloat(descontoInput.value) || 0;

        let total = (valor * quantidade) - desconto;

        if (total < 0) total = 0;

        totalInput.value = total.toFixed(2);
    }

});
</script>

</body>
</html>