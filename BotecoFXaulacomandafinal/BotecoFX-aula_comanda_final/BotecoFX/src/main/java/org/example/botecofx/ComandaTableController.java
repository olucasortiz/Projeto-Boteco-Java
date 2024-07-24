package org.example.botecofx;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.botecofx.db.dals.DALComanda;
import org.example.botecofx.db.entidades.Comanda;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ComandaTableController implements Initializable {
    public TextField tfFiltro;
    public TableColumn colCodigo;
    public TableView<Comanda> tabela;
    public TableColumn colStatus;
    public TableColumn colTotal;
    //membros estáticos
    public static Comanda comanda = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carregaTabela("");
        tfFiltro.setOnKeyReleased(keyEvent -> {
            carregaTabela("upper(status) LIKE '%" + tfFiltro.getText().toUpperCase() + "%'");
        });
    }

    private void carregaTabela(String filtro) {
        DALComanda dal = new DALComanda();
        List<Comanda> comandas = dal.get(filtro);
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        tabela.setItems(FXCollections.observableArrayList(comandas));
    }

    public void onPesquisar(ActionEvent actionEvent) {
        carregaTabela("upper(status) LIKE '%" + tfFiltro.getText().toUpperCase() + "%'");
    }

    public void onCadastrar(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("comanda-form-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("ABERTURA DE COMANDA");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        carregaTabela("");
    }

    public void onAlterar(ActionEvent actionEvent) throws IOException {
        comanda = tabela.getSelectionModel().getSelectedItem();
        if (comanda != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("comanda-form-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            ComandaFormController controller = fxmlLoader.getController();
            controller.setComanda(comanda);
            Stage stage = new Stage();
            stage.setTitle("ALTERAÇÃO DE COMANDA");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            carregaTabela("");
            comanda = null;
        }
    }

    public void onApagar(ActionEvent actionEvent) {
        comanda = tabela.getSelectionModel().getSelectedItem();
        if (comanda != null) {
            comanda.setStatus('P'); // Definindo como paga
            DALComanda dal = new DALComanda();
            dal.alterar(comanda);
            carregaTabela("");
        }
    }
}