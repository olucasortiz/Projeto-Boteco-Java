package org.example.botecofx.db.dals;

import org.example.botecofx.db.SingletonDB;
import org.example.botecofx.db.entidades.Comanda;
import org.example.botecofx.db.entidades.Garcon;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DALComanda implements IDAL<Comanda>{
    @Override
    public boolean gravar(Comanda entidade) {
        String sql= """
                INSERT INTO public.comanda(
                	gar_id, com_numero, com_nome, com_data, com_desc, com_valor, com_status)
                	VALUES (#1, #2, '#3', '#4', '#5', #6, '#7');
                """;
        sql=sql.replace("#1",""+entidade.getGarcon().getId());
        sql=sql.replace("#2",""+entidade.getNumero());
        sql=sql.replace("#3",entidade.getNome());
        sql=sql.replace("#4",entidade.getData().toString());
        sql=sql.replace("#5",entidade.getDescricao());
        sql=sql.replace("#6",""+entidade.getValor());
        sql=sql.replace("#7",""+entidade.getStatus());
        if(SingletonDB.getConexao().manipular(sql)) {
            int id = SingletonDB.getConexao().getMaxPK("comanda","com_id");
            for (Comanda.Item item : entidade.getItens()) {
                sql="INSERT INTO item values (" + id + "," + item.produto().getId()+ "," + item.quant() + ")";
                SingletonDB.getConexao().manipular(sql);
            }
        }
        return true;
    }

    @Override
    public boolean alterar(Comanda entidade) {
        String sql= """
                UPDATE public.comanda
                SET gar_id=#1, com_numero=#2, com_nome='#3', com_data='#4', com_desc='#5',
                com_valor=#6, com_status='#7'
                WHERE com_id=#8
                """;
        sql=sql.replace("#1",""+entidade.getGarcon().getId());
        sql=sql.replace("#2",""+entidade.getNumero());
        sql=sql.replace("#3",entidade.getNome());
        sql=sql.replace("#4",entidade.getData().toString());
        sql=sql.replace("#5",entidade.getDescricao());
        sql=sql.replace("#6",""+entidade.getValor());
        sql=sql.replace("#7",""+entidade.getStatus());
        sql=sql.replace("#8",""+entidade.getId());
        if(SingletonDB.getConexao().manipular(sql)) {
           SingletonDB.getConexao().manipular("DELETE FROM item WHERE com_id="+entidade.getId());
           for (Comanda.Item item : entidade.getItens()) {
               sql="INSERT INTO item values (" + entidade.getId() + "," + item.produto().getId()+ "," + item.quant() + ")";
               SingletonDB.getConexao().manipular(sql);
            }
        }
        return true;
    }

    @Override
    public boolean apagar(Comanda entidade) {
        //deletar itens da comanda
        return SingletonDB.getConexao().
            manipular("DELETE FROM comanda WHERE com_id="+entidade.getId());
    }

    @Override
    public Comanda get(int id) {
        Comanda comanda = null;
        String sql = "SELECT * FROM comanda WHERE com_id=" + id;
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            if (rs.next()) {
                comanda = new Comanda(rs.getInt("com_id"), new DALGarcon().get(rs.getInt("gar_id")),
                        rs.getInt("com_numero"), rs.getString("com_nome"),
                        rs.getDate("com_data").toLocalDate(), rs.getString("com_desc"),
                        rs.getDouble("com_valor"), rs.getString("com_status").charAt(0));

                // Carregar os itens da comanda
                sql = "SELECT * FROM item WHERE com_id=" + id;
                ResultSet rs2 = SingletonDB.getConexao().consultar(sql);
                while (rs2.next()) {
                    comanda.addItem(rs2.getInt("it_quantidade"),
                            new DALProduto().get(rs2.getInt("prod_id")), comanda.getGarcon());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comanda;
    }

    @Override
    public List<Comanda> get(String filtro) {
        List<Comanda> comandas = new ArrayList<>();
        String sqlComanda = "SELECT * FROM comanda";
        if (!filtro.isEmpty()) {
            sqlComanda += " WHERE " + filtro;
        }
        ResultSet rs = SingletonDB.getConexao().consultar(sqlComanda);
        try {
            while (rs.next()) {
                Comanda comanda = new Comanda(rs.getInt("com_id"), new DALGarcon().get(rs.getInt("gar_id")),
                        rs.getInt("com_numero"), rs.getString("com_nome"),
                        rs.getDate("com_data").toLocalDate(), rs.getString("com_desc"),
                        rs.getDouble("com_valor"), rs.getString("com_status").charAt(0));
                String sqlItem = "SELECT * from item WHERE com_id=" + comanda.getId();
                ResultSet rs2 = SingletonDB.getConexao().consultar(sqlItem);
                while (rs2.next()) {
                    comanda.addItem(rs2.getInt("it_quantidade"),
                            new DALProduto().get(rs2.getInt("prod_id")), comanda.getGarcon());
                }
                comandas.add(comanda);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comandas;
    }

}
