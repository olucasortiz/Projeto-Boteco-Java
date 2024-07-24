package org.example.botecofx.db.dals;

import org.example.botecofx.db.SingletonDB;
import org.example.botecofx.db.entidades.Categoria;
import org.example.botecofx.db.entidades.Unidade;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DALCategoria implements IDAL<Categoria> {

    @Override
    public boolean gravar(Categoria entidade) {
        String sql = """
                INSERT INTO public.categoria(
                cat_nome)
                VALUES ('#1');
                """;
        sql = sql.replace("#1", entidade.getNome());
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean alterar(Categoria entidade){
        String sql = """
                UPDATE public.categoria
                	SET cat_nome='#2'
                	WHERE cat_id=#1;
                """;
        sql = sql.replace("#1",""+entidade.getId());
        sql = sql.replace("#2", entidade.getNome());
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public boolean apagar(Categoria entidade) {
        String sql = "DELETE FROM categoria WHERE cat_id = " + entidade.getId();
        return SingletonDB.getConexao().manipular(sql);
    }

    @Override
    public Categoria get(int id) {
        Categoria categoria = null;
        String sql = "SELECT * FROM categoria WHERE cat_id = " + id;
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            if (rs.next()) {
                categoria = new Categoria(rs.getInt("cat_id"), rs.getString("cat_nome"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoria;
    }

    @Override
    public List<Categoria> get(String filtro) {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categoria";
        if (!filtro.isEmpty()) {
            sql += " WHERE " + filtro;
        }
        ResultSet rs = SingletonDB.getConexao().consultar(sql);
        try {
            while (rs.next()) {
                categorias.add(new Categoria(rs.getInt("cat_id"), rs.getString("cat_nome")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categorias;
    }

    public List<Categoria> get() {
        return get("");
    }
}
