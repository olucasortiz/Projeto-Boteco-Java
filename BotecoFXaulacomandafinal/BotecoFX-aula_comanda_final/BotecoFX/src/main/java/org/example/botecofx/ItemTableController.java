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
import org.example.botecofx.db.dals.DALItem;
import org.example.botecofx.db.entidades.Item;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ItemTableController implements Initializable {
    public TextField tfFiltro;
    public TableColumn<Item, Integer> colComandaId;
    public TableColumn<Item, Integer> colProdutoId;
    public TableColumn<Item, Integer> colQtd;
    public TableView<Item> tabela;
    @FXML
    private Label welcomeText;
    public static Item item = null;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void onPesquisar(ActionEvent actionEvent) {
        carregaTabela("upper(com_id) LIKE '%" + tfFiltro.getText().toUpperCase() + "%'");
    }

    public void onCadastrar(ActionEvent actionEvent) throws IOException {
        item = null; // Resetar o item ao cadastrar um novo
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("item-form-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("CADASTRO DE ITEM");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        carregaTabela(""); // Atualizar a tabela após o cadastro
    }

    private void carregaTabela(String filtro) {
        DALItem dal = new DALItem();
        List<Item> itens = dal.get(filtro);
        colComandaId.setCellValueFactory(new PropertyValueFactory<>("comId"));
        colProdutoId.setCellValueFactory(new PropertyValueFactory<>("prodId"));
        colQtd.setCellValueFactory(new PropertyValueFactory<>("qtd"));
        tabela.setItems(FXCollections.observableArrayList(itens));
    }

    public void onAlterar(ActionEvent actionEvent) throws IOException {
        item = tabela.getSelectionModel().getSelectedItem();
        if (item != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("item-form-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            ItemFormController controller = fxmlLoader.getController();
            controller.setItem(item); // Adiciona este método ao ItemFormController
            Stage stage = new Stage();
            stage.setTitle("ALTERAÇÃO DE ITEM");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            carregaTabela(""); // Atualizar a tabela após a alteração
        } else {
            mostrarAlertaErro("Nenhum item selecionado para alterar.");
        }
    }

    public void onApagar(ActionEvent actionEvent) {
        item = tabela.getSelectionModel().getSelectedItem();
        if (item != null) {
            new DALItem().apagar(item);
            carregaTabela(""); // Atualizar a tabela após a exclusão
        } else {
            mostrarAlertaErro("Nenhum item selecionado para apagar.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carregaTabela(""); // Carregar todos os itens ao iniciar
    }

    private void mostrarAlertaErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
