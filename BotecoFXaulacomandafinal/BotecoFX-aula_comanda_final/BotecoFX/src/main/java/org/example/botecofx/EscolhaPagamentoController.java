package org.example.botecofx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import org.example.botecofx.db.dals.DALComanda;
import org.example.botecofx.db.entidades.Comanda;

import java.net.URL;
import java.util.ResourceBundle;

public class EscolhaPagamentoController implements Initializable {
    @FXML
    private RadioButton rbPix;

    @FXML
    private RadioButton rbDinheiro;

    @FXML
    private Label lbValor;

    @FXML
    private TextField tfValor;

    @FXML
    private Label lbChavePix;

    private ToggleGroup toggleGroup;
    private Comanda comanda;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toggleGroup = new ToggleGroup();
        rbPix.setToggleGroup(toggleGroup);
        rbDinheiro.setToggleGroup(toggleGroup);

        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == rbPix) {
                lbChavePix.setVisible(true);
            } else {
                lbChavePix.setVisible(false);
            }
        });
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
        tfValor.setText(String.format("%.2f", comanda.getValor()));
    }

    @FXML
    void onConfirmar() {
        if (toggleGroup.getSelectedToggle() == null) {
            mostrarAlertaErro("Selecione uma forma de pagamento.");
            return;
        }

        String mensagemPagamento;
        if (rbPix.isSelected()) {
            mensagemPagamento = "Pagamento confirmado!\nValor: " + comanda.getValor() + "\nChave PIX: 1899999-9999";
        } else {
            mensagemPagamento = "Pagamento confirmado!\nValor: " + comanda.getValor();
        }

        comanda.setStatus('P'); // Define o status como 'Paga'
        DALComanda dal = new DALComanda();
        if (dal.alterar(comanda)) {
            mostrarAlertaInformacao(mensagemPagamento);
            Stage stage = (Stage) tfValor.getScene().getWindow();
            stage.close(); // Fecha a janela de pagamento
        } else {
            mostrarAlertaErro("Erro ao processar o pagamento.");
        }
    }


    @FXML
    void onCancelar() {
        Stage stage = (Stage) tfValor.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlertaErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void mostrarAlertaInformacao(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
