package br.com.empresa.models;

import java.math.BigDecimal;
import java.sql.Date;


public class Produto {
    private Long id;
    String descricao = "";
    BigDecimal valor = null;
    Integer quantidadeEmEstoque = null;
    Date dataDeCadastro = null;
     
    public Produto() {
    }

    public Produto(String descricao, BigDecimal valor, Integer quantidadeEmEstoque, Date dataDeCadastro) {
        this.descricao = descricao;
        this.valor = valor;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        this.dataDeCadastro = dataDeCadastro;
    }

    public Produto(Long id, String descricao, BigDecimal valor, Integer quantidadeEmEstoque, Date dataDeCadastro) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        this.dataDeCadastro = dataDeCadastro;
    }

   
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Integer getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    public void setQuantidadeEmEstoque(Integer quantidadeEmEstoque) {
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }

    public Date getDataDeCadastro() {
        return dataDeCadastro;
    }

    public void setDataDeCadastro(Date dataDeCadastro) {
        this.dataDeCadastro = dataDeCadastro;
    }  

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

     @Override
    public String toString() {
        return "Produto [descricao=" + descricao + ", valor=" + valor + ", quantidadeEmEstoque=" + quantidadeEmEstoque
                + ", dataDeCadastro=" + dataDeCadastro + "]";
    }


     @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
        result = prime * result + ((valor == null) ? 0 : valor.hashCode());
        result = prime * result + ((quantidadeEmEstoque == null) ? 0 : quantidadeEmEstoque.hashCode());
        result = prime * result + ((dataDeCadastro == null) ? 0 : dataDeCadastro.hashCode());
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
        Produto other = (Produto) obj;
        if (descricao == null) {
            if (other.descricao != null)
                return false;
        } else if (!descricao.equals(other.descricao))
            return false;
        if (valor == null) {
            if (other.valor != null)
                return false;
        } else if (!valor.equals(other.valor))
            return false;
        if (quantidadeEmEstoque == null) {
            if (other.quantidadeEmEstoque != null)
                return false;
        } else if (!quantidadeEmEstoque.equals(other.quantidadeEmEstoque))
            return false;
        if (dataDeCadastro == null) {
            if (other.dataDeCadastro != null)
                return false;
        } else if (!dataDeCadastro.equals(other.dataDeCadastro))
            return false;
        return true;
    }



}
