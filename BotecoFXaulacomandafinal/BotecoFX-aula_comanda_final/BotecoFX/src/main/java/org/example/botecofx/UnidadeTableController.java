package org.example.botecofx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.botecofx.db.dals.DALUnidade;
import org.example.botecofx.db.entidades.Unidade;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class UnidadeTableController implements Initializable {
    public TextField tfFiltro;
    public TableColumn<Unidade, Integer> colCodigo;
    public TableView<Unidade> tabela;
    public TableColumn<Unidade, String> colNome;
    @FXML
    private Label welcomeText;
    public static Unidade unidade = null;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void onPesquisar(ActionEvent actionEvent) {
        carregaTabela("upper(uni_nome) LIKE '%" + tfFiltro.getText().toUpperCase() + "%'");
    }

    public void onCadastrar(ActionEvent actionEvent) throws IOException {
        unidade = null; // Resetar a unidade ao cadastrar uma nova
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("unidade-form-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("CADASTRO DE UNIDADE");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        carregaTabela(""); // Atualizar a tabela após o cadastro
    }

    private void carregaTabela(String filtro) {
        DALUnidade dal = new DALUnidade();
        List<Unidade> unidades = dal.get(filtro);
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tabela.setItems(FXCollections.observableArrayList(unidades));
    }

    public void onAlterar(ActionEvent actionEvent) throws IOException {
        unidade = tabela.getSelectionModel().getSelectedItem();
        if (unidade != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("unidade-form-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("ALTERAÇÃO DE UNIDADE");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            carregaTabela(""); // Atualizar a tabela após a alteração
        } else {
            mostrarAlertaErro("Nenhuma unidade selecionada para alterar.");
        }
    }

    public void onApagar(ActionEvent actionEvent) {
        Unidade unidade = tabela.getSelectionModel().getSelectedItem();
        if (unidade != null) {
            new DALUnidade().apagar(unidade);
            carregaTabela(""); // Atualizar a tabela após a exclusão
        } else {
            mostrarAlertaErro("Nenhuma unidade selecionada para apagar.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carregaTabela(""); // Carregar todas as unidades ao iniciar
    }

    private void mostrarAlertaErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
