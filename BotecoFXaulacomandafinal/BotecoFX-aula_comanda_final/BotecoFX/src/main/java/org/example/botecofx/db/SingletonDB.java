package org.example.botecofx.db;

public class SingletonDB {
    static private Conexao conexao=null;

    private SingletonDB() {
    }

    static public boolean conectar()
    {   conexao=new Conexao();
        return conexao.conectar("jdbc:postgresql://localhost/","postgres","postgres","postgres123");
    }
    static public Conexao getConexao()
    {
        return conexao;
    }
}
