package org.example.botecofx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.botecofx.db.SingletonDB;

import java.io.IOException;
import java.sql.ResultSet;

public class MenuAdmController {
    public void onCadProduto(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("produto-table-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1024,600);
        Stage stage = new Stage();
        stage.setTitle("CONSULTA DE Produto");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void onCadItem(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("item-table-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1024,600);
        Stage stage = new Stage();
        stage.setTitle("CONSULTA DE Itens");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void onCadGarcon(ActionEvent actionEvent) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("garcom-table-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1024,600);
        Stage stage = new Stage();
        stage.setTitle("CONSULTA DE GARÇOM");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void onCadCategoria(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("categoria-table-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1024,600);
        Stage stage = new Stage();
        stage.setTitle("CONSULTA DE CATEGORIA");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void onCadUnidade(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("unidade-table-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1024,600);
        Stage stage = new Stage();
        stage.setTitle("CONSULTA DE UNIDADE");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void onSair(ActionEvent actionEvent) {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Deseja fechar o aplicativo");
        if(alert.showAndWait().get()== ButtonType.OK)
            Platform.exit();
    }

    public void onGerenciarComanda(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("painel-comandas-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1024,600);
        Stage stage = new Stage();
        stage.setTitle("CONSULTA DE COMANDA");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    /*private void clkRel(ActionEvent event){
        gerarRelatorio("select from * select * from comanda, garcon where comanda.com_id = garcon.gar_id order by com_nome", "rel_comanda.jasper");
    }*/


}
