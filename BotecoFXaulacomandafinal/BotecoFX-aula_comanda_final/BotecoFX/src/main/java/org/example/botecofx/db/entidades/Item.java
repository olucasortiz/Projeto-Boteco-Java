package org.example.botecofx.db.entidades;

public class Item {
    private Comanda com;
    private Produto prod;
    private int qtd;

    public Item() {
    }

    public Item(Comanda com, Produto prod, int id) {
        this.com = com;
        this.prod = prod;
        this.qtd = id;
    }

    public Comanda getCom() {
        return com;
    }

    public void setCom(Comanda com) {
        this.com = com;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public Produto getProd() {
        return prod;
    }

    public void setProd(Produto prod) {
        this.prod = prod;
    }

}
