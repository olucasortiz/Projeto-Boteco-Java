package org.example.botecofx;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.botecofx.db.dals.DALComanda;
import org.example.botecofx.db.dals.DALItem;
import org.example.botecofx.db.dals.DALProduto;
import org.example.botecofx.db.entidades.Item;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {
    public TextField tfComandaId;
    public TextField tfProdutoId;
    public TextField tfQtd;

    private Item item;

    public void onCancelar(ActionEvent actionEvent) {
        tfComandaId.getScene().getWindow().hide();
    }

    public void onConfirmar(ActionEvent actionEvent) {
        int comandaId = Integer.parseInt(tfComandaId.getText());
        int produtoId = Integer.parseInt(tfProdutoId.getText());
        int qtd = Integer.parseInt(tfQtd.getText());

        DALComanda dalComanda = new DALComanda();
        DALProduto dalProduto = new DALProduto();
        DALItem dalItem = new DALItem();

        if (item == null) {
            item = new Item(dalComanda.get(comandaId), dalProduto.get(produtoId), qtd);
            if (!dalItem.gravar(item)) {
                mostrarAlertaErro("Erro ao cadastrar o item.");
            }
        } else {
            item.setQtd(qtd);
            if (!dalItem.alterar(item)) {
                mostrarAlertaErro("Erro ao alterar o item.");
            }
        }
        tfComandaId.getScene().getWindow().hide();
    }

    private void mostrarAlertaErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (ItemTableController.item != null) {
            setItem(ItemTableController.item);
        }
    }

    public void setItem(Item item) {
        this.item = item;
        tfComandaId.setText(String.valueOf(item.getCom().getId()));
        tfProdutoId.setText(String.valueOf(item.getProd().getId()));
        tfQtd.setText(String.valueOf(item.getQtd()));
    }
}
