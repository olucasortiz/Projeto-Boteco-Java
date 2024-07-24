package org.example.botecofx.db.dals;

import org.example.botecofx.db.SingletonDB;
import org.example.botecofx.db.entidades.Categoria;
import org.example.botecofx.db.entidades.Produto;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DALProduto implements IDAL<Produto>{

    @Override
    public boolean gravar(Produto entidade) {
        String sql= """
                INSERT INTO public.produto(
                cat_id, uni_id, prod_nome, prod_preco, prod_descr)
                VALUES (#1, #2, '#3', #4, '#5');
                """;
        sql=sql.replace("#1",""+entidade.getCategoria().getId());
        sql=sql.replace("#2",""+entidade.getUnidade().getId());
        sql=sql.replace("#3",entidade.getNome());
        sql=sql.replace("#4",""+entidade.getPreco());
        sql=sql.replace("#5",entidade.getDescricao());
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean alterar(Produto entidade) {
        String sql= """
                UPDATE produto
                    SET cat_id='#4', uni_id=#3 ,prod_nome='#1', prod_preco='#2', prod_descr='#6'
                    WHERE prod_id=#5;
                """;
        sql=sql.replace("#1",entidade.getNome());
        sql=sql.replace("#2",""+entidade.getPreco());
        sql=sql.replace("#3",""+entidade.getUnidade().getId());
        sql=sql.replace("#4",""+entidade.getCategoria().getId());
        sql=sql.replace("#5",""+entidade.getId());
        sql=sql.replace("#6",entidade.getDescricao());
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean apagar(Produto entidade) {
        return SingletonDB.getConexao().manipular("DELETE FROM public.produto WHERE prod_id="+entidade.getId());
    }

    @Override
    public Produto get(int id) {
        Produto produto=null;
        String sql="SELECT * FROM produto WHERE prod_id="+id;
        ResultSet rs= SingletonDB.getConexao().consultar(sql);
        try {
            if (rs.next()) {
                produto=new Produto(rs.getInt("prod_id"),rs.getString("prod_nome"),
                        rs.getString("prod_descr"),rs.getDouble("prod_preco"),
                        new DALCategoria().get(rs.getInt("cat_id")),
                        new DALUnidade().get(rs.getInt("uni_id")));
            }
        }
        catch (Exception e){  }
        return produto;
    }

    @Override
    public List<Produto> get(String filtro) {
        List <Produto> produtos=new ArrayList<>();
        String sql="SELECT * FROM produto";
        if(!filtro.isEmpty())
            sql+=" WHERE "+filtro;
        ResultSet rs= SingletonDB.getConexao().consultar(sql);
        try {
            while (rs.next()) {
                Produto produto=new Produto(rs.getInt("prod_id"),rs.getString("prod_nome"),
                   rs.getString("prod_descr"),rs.getDouble("prod_preco"),
                   new DALCategoria().get(rs.getInt("cat_id")),
                   new DALUnidade().get(rs.getInt("uni_id")));
                produtos.add(produto);
            }
        }
        catch (Exception e){  }
        return produtos;
    }
}
