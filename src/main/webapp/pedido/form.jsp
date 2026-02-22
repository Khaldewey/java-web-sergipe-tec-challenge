<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulário Pedido</title>
</head>

<body>

<h2>Cadastro de Pedido</h2>

<form action="${pageContext.request.contextPath}/pedidos" method="post">

   <div>
      <label>Cliente:</label><br>
      <select name="clienteId" required>
          <option value="">Selecione</option>
          <c:forEach var="c" items="${clientes}">
              <option value="${c.id}">
                  ${c.nome}
              </option>
          </c:forEach>
      </select>
   </div>

  <br>
  
  <div>
    <label>Produto:</label><br>
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

  <br>

  <div>
      <label>Valor Unitário:</label><br>
      <input type="number" step="0.01" name="valor-unitario" id="valor-unitario" readonly>
  </div>

  <br>

  <div>
      <label>Quantidade:</label><br>
      <input type="number" name="quantidade" id="quantidade" min="1" required>
  </div>

  <br>

   <div>
        <label>Total:</label><br>
        <input type="number" step="0.01" id="valor-total" readonly>
   </div>

  <br>

  <div>
      <label>Desconto:</label><br>
      <input type="number" step="0.01" name="desconto" value="0">
  </div>

  <br>

  <div>
      <label>Data Pedido:</label><br>
      <input type="date" name="dataPedido" required>
  </div>

  <br>

  <button type="submit">Salvar</button>

</form>

<br>
<a href="${pageContext.request.contextPath}/pedidos">Voltar</a>

<script>
    const produtoSelect = document.getElementById("produtoSelect");
    const valorUnitarioInput = document.getElementById("valor-unitario");
    const quantidadeInput = document.getElementById("quantidade");
    const totalInput = document.getElementById("valor-total");

    let estoqueAtual = 0;

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

    totalInput.value = (valor * quantidade).toFixed(2);
    }
</script>

</body>
</html>