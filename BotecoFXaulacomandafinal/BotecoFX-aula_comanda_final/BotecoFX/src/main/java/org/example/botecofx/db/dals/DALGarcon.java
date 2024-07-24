package org.example.botecofx.db.dals;

import org.example.botecofx.db.SingletonDB;
import org.example.botecofx.db.entidades.Garcon;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DALGarcon implements IDAL <Garcon>{

    @Override
    public boolean gravar(Garcon entidade) {
        String sql= """
                INSERT INTO garcon(
                	gar_nome, gar_cpf, gar_cep, 
                	gar_endereco, gar_cidade, gar_uf, gar_fone)
                	VALUES ('#1', '#2', '#3', '#4', '#5', '#6', '#7');
                """;
               sql=sql.replace("#1",entidade.getNome());
               sql=sql.replace("#2",entidade.getCpf());
               sql=sql.replace("#3",entidade.getCep());
               sql=sql.replace("#4",entidade.getEndereco());
               sql=sql.replace("#5",entidade.getCidade());
               sql=sql.replace("#6",entidade.getUf());
               sql=sql.replace("#7",entidade.getFone());
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean alterar(Garcon entidade) {
        String sql= """
                UPDATE public.garcon
                	SET gar_nome='#1', gar_cpf='#2', gar_cep='#3',
                	gar_endereco='#4', gar_cidade='#5', gar_uf='#6', gar_fone='#7'
                	WHERE gar_id=#8
                	""";
        sql=sql.replace("#1",entidade.getNome());
        sql=sql.replace("#2",entidade.getCpf());
        sql=sql.replace("#3",entidade.getCep());
        sql=sql.replace("#4",entidade.getEndereco());
        sql=sql.replace("#5",entidade.getCidade());
        sql=sql.replace("#6",entidade.getUf());
        sql=sql.replace("#7",entidade.getFone());
        sql=sql.replace("#8",""+entidade.getId());

        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean apagar(Garcon entidade) {
        return SingletonDB.getConexao().
                manipular("DELETE FROM garcon WHERE gar_id="+entidade.getId());
    }

    @Override
    public Garcon get(int id) {
        Garcon garcon=null;
        String sql="SELECT * FROM garcon WHERE gar_id="+id;
        ResultSet rs=SingletonDB.getConexao().consultar(sql);
        try {
            if (rs.next()) {
               garcon=new Garcon(rs.getInt("gar_id"),rs.getString("gar_nome"),
                                 rs.getString("gar_cpf"),rs.getString("gar_cep"),
                       rs.getString("gar_endereco"),rs.getString("gar_cidade"),
                       rs.getString("gar_uf"),rs.getString("gar_fone"));
            }
        }
        catch (Exception e){  }
        return garcon;
    }

    @Override
    public List<Garcon> get(String filtro) {
        List <Garcon> garcons=new ArrayList<>();
        String sql="SELECT * FROM garcon";
        if(!filtro.isEmpty())
           sql+=" WHERE "+filtro;
        ResultSet rs=SingletonDB.getConexao().consultar(sql);
        try {
            while (rs.next()) {
                Garcon garcon=new Garcon(rs.getInt("gar_id"),rs.getString("gar_nome"),
                        rs.getString("gar_cpf"),rs.getString("gar_cep"),
                        rs.getString("gar_endereco"),rs.getString("gar_cidade"),
                        rs.getString("gar_uf"),rs.getString("gar_fone"));
                garcons.add(garcon);
            }
        }
        catch (Exception e){  }
        return garcons;
    }

}
