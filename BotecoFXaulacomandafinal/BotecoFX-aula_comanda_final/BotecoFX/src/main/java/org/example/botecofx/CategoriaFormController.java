package org.example.botecofx;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.botecofx.db.dals.DALCategoria;
import org.example.botecofx.db.entidades.Categoria;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoriaFormController implements Initializable {
    public TextField tfId;
    public TextField tdNome;

    public void onCancelar(ActionEvent actionEvent) {
        tfId.getScene().getWindow().hide();
    }

    public void onConfirmar(ActionEvent actionEvent) {
        Categoria cat = new Categoria();
        cat.setNome(tdNome.getText());
        DALCategoria dal = new DALCategoria();

        if (tfId.getText().isEmpty()) {
            if (!dal.gravar(cat)) {
                mostrarAlertaErro("Erro ao cadastrar a categoria " + cat.getNome());
            }
        } else {
            cat.setId(Integer.parseInt(tfId.getText()));
            if (!dal.alterar(cat)) {
                mostrarAlertaErro("Erro ao alterar a categoria " + cat.getNome());
            }
        }
        tfId.getScene().getWindow().hide();
    }

    private void mostrarAlertaErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (CategoriaTableController.categoria != null) {
            Categoria cat = CategoriaTableController.categoria;
            tfId.setText(String.valueOf(cat.getId()));
            tdNome.setText(cat.getNome());
        }
    }
}
