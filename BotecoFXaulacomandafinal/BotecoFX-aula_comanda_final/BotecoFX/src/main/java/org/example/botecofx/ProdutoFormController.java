package org.example.botecofx;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.botecofx.db.SingletonDB;
import org.example.botecofx.db.dals.DALCategoria;
import org.example.botecofx.db.dals.DALProduto;
import org.example.botecofx.db.dals.DALUnidade;
import org.example.botecofx.db.entidades.Categoria;
import org.example.botecofx.db.entidades.Produto;
import org.example.botecofx.db.entidades.Unidade;
import org.example.botecofx.util.MaskFieldUtil;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProdutoFormController implements Initializable {
    @FXML
    private ComboBox<Categoria> cbCategoria;

    @FXML
    private ComboBox<Unidade> cbUnidade;

    @FXML
    private TextField tfDescricao;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfPreco;


    public void close() throws IOException {
        tfNome.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("produto-table-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1000,680);
        Stage stage = new Stage();
        stage.setTitle("CONSULTA DE PRODUTO");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MaskFieldUtil.monetaryField(tfPreco);
        carregaCombos();
        if(ProdutoTableController.produto!=null)
        {
            Produto p=ProdutoTableController.produto;
            tfNome.setText(p.getNome());
            tfDescricao.setText(p.getDescricao());
            System.out.println(String.format("%.2f",p.getPreco()));
            tfPreco.setText(String.format("%.2f",p.getPreco()));
            tfId.setText(""+p.getId());
            cbUnidade.getSelectionModel().select(p.getUnidade());
            cbCategoria.getSelectionModel().select(p.getCategoria());

        }
    }

    private void carregaCombos() {
        List<Categoria> categorias=new DALCategoria().get("");
        cbCategoria.setItems(FXCollections.observableArrayList(categorias));
        // carregando  direto:
        cbUnidade.setItems(FXCollections.observableArrayList(new DALUnidade().get("")));

    }

    @FXML
    void onCancelar(ActionEvent event) {
        tfId.getScene().getWindow().hide();
    }

    @FXML
    void onConfirmar(ActionEvent event) {
        Produto produto=new Produto();
        produto.setNome(tfNome.getText());
        produto.setDescricao(tfDescricao.getText());
        produto.setPreco(Double.parseDouble(tfPreco.getText().replace(",",".")));
        produto.setCategoria(cbCategoria.getSelectionModel().getSelectedItem());
        produto.setUnidade(cbUnidade.getSelectionModel().getSelectedItem());
        DALProduto dal=new DALProduto();
        if(tfId.getText().isEmpty()) {
            if (dal.gravar(produto) == false) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro ao cadastrar o produto " + produto.getNome());
                alert.showAndWait();
            }
        }
        else {
            produto.setId(Integer.parseInt(tfId.getText()));
            if (dal.alterar(produto) == false) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro ao alterar o produto " + produto.getNome()+"\n"+
                        SingletonDB.getConexao().getMensagemErro());
                alert.showAndWait();
            }
        }
        tfId.getScene().getWindow().hide();

    }
}
