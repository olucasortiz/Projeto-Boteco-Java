package org.example.botecofx;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.botecofx.db.dals.DALGarcon;
import org.example.botecofx.db.dals.DALProduto;
import org.example.botecofx.db.entidades.Categoria;
import org.example.botecofx.db.entidades.Garcon;
import org.example.botecofx.db.entidades.Produto;
import org.example.botecofx.db.entidades.Unidade;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProdutoTableController implements Initializable {
    public static Produto produto=null;
    @FXML
    private TableColumn<Produto, Categoria> colCategoria;

    @FXML
    private TableColumn<Produto, String> colCodigo;

    @FXML
    private TableColumn<Produto, String> colNome;

    @FXML
    private TableColumn<Produto, String> colPreco;

    @FXML
    private TableColumn<Produto, Unidade> colUnidade;

    @FXML
    private TableView<Produto> tabela;

    @FXML
    private TextField tfFiltro;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colUnidade.setCellValueFactory(new PropertyValueFactory<>("unidade"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        carregaTabela("");
        tfFiltro.setOnKeyReleased(keyEvent -> {
            carregaTabela("upper(prod_nome) LIKE '%"+tfFiltro.getText().toUpperCase()+"%'");
        });
    }

    private void carregaTabela(String filtro)
    {
        DALProduto dal=new DALProduto();
        List<Produto> produtos= dal.get(filtro);


        tabela.setItems(FXCollections.observableArrayList(produtos));
    }
    @FXML
    void onAlterar(ActionEvent event) throws IOException {
        produto=tabela.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("produto-form-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("ALTERAÇÃO DE PRODUTO");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        carregaTabela("");
        produto=null;

    }

    @FXML
    void onApagar(ActionEvent event) {
        Produto produto=tabela.getSelectionModel().getSelectedItem();
        new DALProduto().apagar(produto);
        carregaTabela("");
    }

    @FXML
    void onCadastrar(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("produto-form-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("CADASTRO DE PRODUTO");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        carregaTabela("");
    }

    @FXML
    void onPesquisar(ActionEvent event) {
        carregaTabela("upper(prod_nome) LIKE '%"+tfFiltro.getText().toUpperCase()+"%'");
    }
}
