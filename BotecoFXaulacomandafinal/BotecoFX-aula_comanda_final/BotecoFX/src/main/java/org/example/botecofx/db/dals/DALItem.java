package org.example.botecofx.db.dals;

import org.example.botecofx.db.SingletonDB;
import org.example.botecofx.db.entidades.*;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DALItem implements IDAL<Item>{
    @Override
    public boolean gravar(Item entidade) {
        String sql = """
                INSERT INTO public.item(
               // com_id, prod_id,
                it_quantidade)
                VALUES (#1, #2, #3);
                """;
        //sql = sql.replace("#1",""+ entidade.getProd());
        //sql = sql.replace("#2",""+entidade.getCom());
        sql = sql.replace("#3",""+entidade.getQtd());
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean alterar(Item entidade){
        String sql = """
                UPDATE public.item
                 it_quantidade=#3
                 WHERE com_id = #1 AND prod_id = #2;
                """;
        sql = sql.replace("#1",""+entidade.getCom().getId());
        sql = sql.replace("#2",""+entidade.getProd().getId());
        sql = sql.replace("#3",""+entidade.getQtd());
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean apagar(Item entidade){
        String sql = "DELETE FROM public.item WHERE com_id = " + entidade.getCom().getId() + " AND prod_id = " + entidade.getProd().getId();
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public Item get(int id) {
        Item item=null;
        String sql="SELECT * FROM unidade WHERE com_id="+id;
        ResultSet rs= SingletonDB.getConexao().consultar(sql);
        try {
            if (rs.next()) {
                item=new Item(new DALComanda().get(rs.getInt("com_id")),
                        new DALProduto().get(rs.getInt("prod_id")),
                        rs.getInt("it_quantidade"));
            }
        }
        catch (Exception e){  }
        return item;
    }

    @Override
    public List<Item> get(String filtro) {
        List<Item> itens = new ArrayList<>();
        String sql = "SELECT * FROM public.item";
        if (!filtro.isEmpty()) {
            sql += " WHERE " + filtro;
        }
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            while (rs.next()) {
                Item item = new Item(
                        new DALComanda().get(rs.getInt("com_id")),
                        new DALProduto().get(rs.getInt("prod_id")),
                        rs.getInt("it_quantidade")
                );
                itens.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itens;
    }
}