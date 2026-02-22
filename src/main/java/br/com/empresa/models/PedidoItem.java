package br.com.empresa.models;

import java.math.BigDecimal;

public class PedidoItem {

    private Long id;
    private Long pedidoId;
    private Long produtoId;
    private BigDecimal valorUnitarioProduto;
    private Integer quantidade;
    private BigDecimal desconto;
    private String produtoNome;

    public PedidoItem() {}

    public PedidoItem(Long produtoId,
                      BigDecimal valorUnitarioProduto,
                      Integer quantidade,
                      BigDecimal desconto) {
        this.produtoId = produtoId;
        this.valorUnitarioProduto = valorUnitarioProduto;
        this.quantidade = quantidade;
        this.desconto = desconto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public BigDecimal getValorUnitarioProduto() {
        return valorUnitarioProduto;
    }

    public void setValorUnitarioProduto(BigDecimal valorUnitarioProduto) {
        this.valorUnitarioProduto = valorUnitarioProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    
    public String getProdutoNome() {
        return produtoNome;
    }

    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((pedidoId == null) ? 0 : pedidoId.hashCode());
        result = prime * result + ((produtoId == null) ? 0 : produtoId.hashCode());
        result = prime * result + ((valorUnitarioProduto == null) ? 0 : valorUnitarioProduto.hashCode());
        result = prime * result + ((quantidade == null) ? 0 : quantidade.hashCode());
        result = prime * result + ((desconto == null) ? 0 : desconto.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PedidoItem other = (PedidoItem) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (pedidoId == null) {
            if (other.pedidoId != null)
                return false;
        } else if (!pedidoId.equals(other.pedidoId))
            return false;
        if (produtoId == null) {
            if (other.produtoId != null)
                return false;
        } else if (!produtoId.equals(other.produtoId))
            return false;
        if (valorUnitarioProduto == null) {
            if (other.valorUnitarioProduto != null)
                return false;
        } else if (!valorUnitarioProduto.equals(other.valorUnitarioProduto))
            return false;
        if (quantidade == null) {
            if (other.quantidade != null)
                return false;
        } else if (!quantidade.equals(other.quantidade))
            return false;
        if (desconto == null) {
            if (other.desconto != null)
                return false;
        } else if (!desconto.equals(other.desconto))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PedidoItem [id=" + id + ", pedidoId=" + pedidoId + ", produtoId=" + produtoId + ", valorUnitarioProduto=" + valorUnitarioProduto
                + ", quantidade=" + quantidade + ", desconto=" + desconto + "]";
    }

    
}
