package br.com.empresa.models;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class Pedido {

    private Long id;
    private Long clienteId;
    private Date dataPedido;
    private List<PedidoItem> itens;
    private String clienteNome;

    public Pedido() {}

    public Pedido(Long clienteId, Date dataPedido) {
        this.clienteId = clienteId;
        this.dataPedido = dataPedido;
    }

    public Pedido(Long id, Long clienteId, Date dataPedido) {
        this.id = id;
        this.clienteId = clienteId;
        this.dataPedido = dataPedido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

    public List<PedidoItem> getItens() {
        return itens;
    }

    public void setItens(List<PedidoItem> itens) {
        this.itens = itens;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public BigDecimal getTotal() {

        BigDecimal total = BigDecimal.ZERO;

        for (PedidoItem item : itens) {
            BigDecimal totalItem = item.getValorUnitarioProduto()
                    .multiply(BigDecimal.valueOf(item.getQuantidade()))
                    .subtract(item.getDesconto());

            total = total.add(totalItem);
        }

        return total;
    }

    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((clienteId == null) ? 0 : clienteId.hashCode());
        result = prime * result + ((dataPedido == null) ? 0 : dataPedido.hashCode());
        result = prime * result + ((itens == null) ? 0 : itens.hashCode());
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
        Pedido other = (Pedido) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (clienteId == null) {
            if (other.clienteId != null)
                return false;
        } else if (!clienteId.equals(other.clienteId))
            return false;
        if (dataPedido == null) {
            if (other.dataPedido != null)
                return false;
        } else if (!dataPedido.equals(other.dataPedido))
            return false;
        if (itens == null) {
            if (other.itens != null)
                return false;
        } else if (!itens.equals(other.itens))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Pedido [id=" + id + ", clienteId=" + clienteId + ", dataPedido=" + dataPedido + ", itens=" + itens
                + "]";
    }
    
}
