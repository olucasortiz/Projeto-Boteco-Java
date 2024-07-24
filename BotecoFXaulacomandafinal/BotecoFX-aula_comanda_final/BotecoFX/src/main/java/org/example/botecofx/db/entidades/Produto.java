package org.example.botecofx.db.entidades;

public class Produto {
    private int id;
    private String nome;
    private String descricao;
    private double preco;
    Categoria categoria;
    private Unidade unidade;



    public Produto(int id, String nome, String descricao, double preco, Categoria categoria, Unidade unidade) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
        this.unidade = unidade;
    }

    public Produto(int id, String text, String tfPrecoText, Unidade uni, Categoria cat) {
        this(0,"","",0,null,null);
    }

    public Produto(String nome, String descricao, double preco, Categoria categoria, Unidade unidade) {
        this(0,nome,descricao,preco,categoria,unidade);
    }

    public Produto() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    @Override
    public String toString() {
        return  nome;
    }
}
