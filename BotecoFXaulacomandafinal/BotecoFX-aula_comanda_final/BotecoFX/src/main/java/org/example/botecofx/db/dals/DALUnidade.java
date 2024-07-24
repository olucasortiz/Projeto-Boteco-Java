package org.example.botecofx.db.dals;

import org.example.botecofx.db.SingletonDB;
import org.example.botecofx.db.entidades.Categoria;
import org.example.botecofx.db.entidades.Unidade;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DALUnidade implements IDAL<Unidade>{
    @Override
    public boolean gravar(Unidade entidade) {
        String sql = """
                INSERT INTO public.unidade(
                	uni_nome)
                	VALUES ('#1');
                """;
        sql = sql.replace("#1", entidade.getNome());
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean alterar(Unidade entidade){
        String sql = """
                UPDATE public.unidade
                	SET uni_nome='#2'
                	WHERE uni_id=#1;
                """;
        sql = sql.replace("#1",""+entidade.getId());
        sql = sql.replace("#2", entidade.getNome());
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean apagar(Unidade entidade){
        return SingletonDB.getConexao().manipular("DELETE FROM public.unidade WHERE uni_id="+entidade.getId());
    }

    @Override
    public Unidade get(int id) {
        Unidade unidade=null;
        String sql="SELECT * FROM unidade WHERE uni_id="+id;
        ResultSet rs= SingletonDB.getConexao().consultar(sql);
        try {
            if (rs.next()) {
                unidade=new Unidade(rs.getInt("uni_id"),rs.getString("uni_nome"));
            }
        }
        catch (Exception e){  }
        return unidade;
    }

    @Override
    public List<Unidade> get(String filtro) {
        List <Unidade> unidades=new ArrayList<>();
        String sql="SELECT * FROM unidade";
        if(!filtro.isEmpty())
            sql+=" WHERE "+filtro;
        ResultSet rs= SingletonDB.getConexao().consultar(sql);
        try {
            while (rs.next()) {
                Unidade unidade=new Unidade(rs.getInt("uni_id"),rs.getString("uni_nome"));
                unidades.add(unidade);
            }
        }
        catch (Exception e){  }
        return unidades;
    }
}
