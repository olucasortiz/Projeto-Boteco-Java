package org.example.botecofx;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.botecofx.db.dals.DALGarcon;
import org.example.botecofx.db.entidades.Garcon;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GarcomTableController implements Initializable {
    public TextField tfFiltro;
    public TableColumn colCodigo;
    public TableView <Garcon>tabela;
    public TableColumn  colNome;
    public TableColumn colFone;
    //membros estáticos
    public static Garcon garcon=null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        carregaTabela("");
        tfFiltro.setOnKeyReleased(keyEvent -> {
            carregaTabela("upper(gar_nome) LIKE '%"+tfFiltro.getText().toUpperCase()+"%'");
        });
    }
    private void carregaTabela(String filtro)
    {
        DALGarcon dal=new DALGarcon();
        List<Garcon> garcons= dal.get(filtro);
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colFone.setCellValueFactory(new PropertyValueFactory<>("fone"));
        tabela.setItems(FXCollections.observableArrayList(garcons));
    }

    public void onPesquisar(ActionEvent actionEvent) {
        carregaTabela("upper(gar_nome) LIKE '%"+tfFiltro.getText().toUpperCase()+"%'");
    }

    public void onCadastrar(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("garcom-form-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("CADASTRO DE GARÇOM");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.setMaximized(true);
        stage.showAndWait();
        carregaTabela("");
//        Alert alert= new Alert(Alert.AlertType.INFORMATION);
//        alert.setContentText("CRIAR UM NOVO GARÇOM");
//        alert.showAndWait();
    }

    public void onAlterar(ActionEvent actionEvent) throws IOException {
        garcon=tabela.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("garcom-form-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("ALTERAÇÃO DE GARÇOM");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        carregaTabela("");
        garcon=null;
    }

    public void onApagar(ActionEvent actionEvent) {

        Garcon garcon=tabela.getSelectionModel().getSelectedItem();
        new DALGarcon().apagar(garcon);
        carregaTabela("");
    }


}