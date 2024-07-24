package org.example.botecofx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.botecofx.db.dals.DALCategoria;
import org.example.botecofx.db.entidades.Categoria;
import org.example.botecofx.db.entidades.Comanda;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class CategoriaTableController implements Initializable {
    public TextField tfFiltro;
    public TableColumn<Categoria, Integer> colCodigo;
    public TableView<Categoria> tabela;
    public TableColumn<Categoria, String> colNome;
    @FXML
    private Label welcomeText;
    public static Categoria categoria = null;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void onPesquisar(ActionEvent actionEvent) {
        carregaTabela("upper(cat_nome) LIKE '%" + tfFiltro.getText().toUpperCase() + "%'");
    }

    public void onCadastrar(ActionEvent actionEvent) throws IOException {
        categoria = null; // Resetar a categoria ao cadastrar uma nova
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("categoria-form-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("CADASTRO DE CATEGORIA");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        carregaTabela(""); // Atualizar a tabela após o cadastro
    }

    private void carregaTabela(String filtro) {
        DALCategoria dal = new DALCategoria();
        List<Categoria> categorias = dal.get(filtro);
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tabela.setItems(FXCollections.observableArrayList(categorias));
    }

    public void onAlterar(ActionEvent actionEvent) throws IOException {
        categoria = tabela.getSelectionModel().getSelectedItem();
        if (categoria != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("categoria-form-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("ALTERAÇÃO DE CATEGORIA");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            carregaTabela(""); // Atualizar a tabela após a alteração
        } else {
            mostrarAlertaErro("Nenhuma categoria selecionada para alterar.");
        }
    }

    public void onApagar(ActionEvent actionEvent) {
        Categoria categoria = tabela.getSelectionModel().getSelectedItem();
        if (categoria != null) {
            new DALCategoria().apagar(categoria);
            carregaTabela(""); // Atualizar a tabela após a exclusão
        } else {
            mostrarAlertaErro("Nenhuma categoria selecionada para apagar.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carregaTabela(""); // Carregar todas as categorias ao iniciar
    }

    private void mostrarAlertaErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
