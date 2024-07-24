package org.example.botecofx.db.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Comanda {
    private int id;
    private Garcon garcon;
    private int numero;
    private String nome;
    private LocalDate data;
    private String descricao;
    private double valor;
    private char status;
    private List<Item> itens=new ArrayList<>();
    public static record Item (int quant, Produto produto, Garcon garconSelecionado){
        @Override
        public String toString() {
            return ""+ quant +" -  "+ produto +" " +garconSelecionado;
        }
    }

    public Comanda(int id, Garcon garcon, int numero, String nome, LocalDate data, String descricao, double valor, char status) {
        this.id = id;
        this.garcon = garcon;
        this.numero = numero;
        this.nome = nome;
        this.data = data;
        this.descricao = descricao;
        this.valor = valor;
        this.status = status;
    }

    public Comanda(Garcon garcon, int numero, String nome, LocalDate data, String descricao, double valor, char status) {
        this.garcon = garcon;
        this.numero = numero;
        this.nome = nome;
        this.data = data;
        this.descricao = descricao;
        this.valor = valor;
        this.status = status;
    }
    public void addItem(int quant, Produto produto, Garcon garconSelecionado){
        Item item =new Item(quant,produto, garconSelecionado);
        itens.add(item);
    }

    public Comanda() {
    }

    public List<Item> getItens() {
        return itens;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Garcon getGarcon() {
        return garcon;
    }

    public void setGarcon(Garcon garcon) {
        this.garcon = garcon;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return ""+numero;
    }
}
