package org.example.botecofx;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.botecofx.db.dals.DALUnidade;
import org.example.botecofx.db.entidades.Unidade;

import java.net.URL;
import java.util.ResourceBundle;

public class UnidadeFormController implements Initializable {
    public TextField tfId;
    public TextField tdNome;

    public void onCancelar(ActionEvent actionEvent) {
        tfId.getScene().getWindow().hide();
    }

    public void onConfirmar(ActionEvent actionEvent) {
        Unidade unidade = new Unidade();
        unidade.setNome(tdNome.getText());
        DALUnidade dal = new DALUnidade();

        if (tfId.getText().isEmpty()) {
            if (!dal.gravar(unidade)) {
                mostrarAlertaErro("Erro ao cadastrar a unidade " + unidade.getNome());
            }
        } else {
            unidade.setId(Integer.parseInt(tfId.getText()));
            if (!dal.alterar(unidade)) {
                mostrarAlertaErro("Erro ao alterar a unidade " + unidade.getNome());
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
        if (UnidadeTableController.unidade != null) {
            Unidade unidade = UnidadeTableController.unidade;
            tdNome.setText(unidade.getNome());
            tfId.setText(String.valueOf(unidade.getId()));
        }
    }
}
