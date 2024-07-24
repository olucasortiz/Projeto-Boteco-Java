package org.example.botecofx;

import org.example.botecofx.db.Conexao;
import org.example.botecofx.db.SingletonDB;
import org.example.botecofx.db.dals.DALComanda;
import org.example.botecofx.db.dals.DALGarcon;
import org.example.botecofx.db.dals.DALProduto;
import org.example.botecofx.db.entidades.Comanda;
import org.example.botecofx.db.entidades.Garcon;
import org.example.botecofx.db.entidades.Produto;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception{
        SingletonDB.conectar();
        List<Produto> produtos = new DALProduto().get("");

        DALComanda dalc=new DALComanda();
//        Comanda comanda=new Comanda(new DALGarcon().get(1),100,"Zinho", LocalDate.now(),"mesa azul",0,'A');//dalc.get(1);
//        //comanda.setNome("Asdrubal");
//        comanda.add(10,produtos.get(3));
//        comanda.add(3,produtos.get(4));
//        dalc.gravar(comanda);
        List<Comanda> comandas = dalc.get("");
        for(Comanda c : comandas) {
            System.out.println(c.getNome());
            for(Comanda.Item item : c.getItens())
                System.out.println("   "+item.produto().getNome());
        }



//        Garcon garcon;//=new Garcon("Gerson","121.555.888-99","19053100","Av Brasil, 200","Assis","SP","18 99988-8899");
//        DALGarcon dal=new DALGarcon();
//
//        List<Garcon> garcons = dal.get("upper(gar_cidade) like '%E%'");
//        for(Garcon g:garcons)
//            System.out.println(g.getNome());
//        garcons.forEach(g->{
//            System.out.println(g.getNome());
//        });
//        garcon=dal.get(3);
//        garcon.setNome("Gerson Glauco Filho");
//        System.out.println(garcon.getNome());
//        dal.alterar(garcon);
//        if(!dal.gravar(garcon))
//            System.out.println("Erro ao gravar o garcon "+SingletonDB.getConexao().getMensagemErro());
//        Conexao conexao=new Conexao();
//        if(conexao.conectar("jdbc:postgresql://localhost/","botecodb","postgres","postgres123"))
//        {
//            System.out.println("Conectado");
//            conexao.manipular("insert into garcon (gar_nome,gar_cpf,gar_cidade) values ('Gerson','5854821454','Teresina')");
//            ResultSet rs=conexao.consultar("select * from garcon");
//            while (rs.next())
//            {
//                System.out.println(rs.getString("gar_nome")+" "+rs.getString("gar_cidade"));
//            }
//            rs.close();
//        }
//        else
//        {
//            System.out.println("Falha ao conectar o banco");
//            System.out.println(conexao.getMensagemErro());
//        }

    }
}
