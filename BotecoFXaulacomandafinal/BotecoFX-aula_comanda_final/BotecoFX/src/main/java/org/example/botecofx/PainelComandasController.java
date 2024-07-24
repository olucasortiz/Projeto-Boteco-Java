package org.example.botecofx;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.botecofx.db.dals.DALComanda;
import org.example.botecofx.db.entidades.Comanda;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PainelComandasController implements Initializable {
    @FXML
    private Button btAlterar;

    @FXML
    private Button btNovo;

    @FXML
    private Button btPagar1;

    @FXML
    private Button btSair;

    @FXML
    private ListView<Comanda> listView;

    @FXML
    private TextArea taPreview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carregaComandas();
    }

    private void carregaComandas() {
        DALComanda dal = new DALComanda();
        List<Comanda> todasComandas = dal.get("");
        listView.setItems(FXCollections.observableList(todasComandas));
    }

    @FXML
    void onAlterarComanda(ActionEvent event) throws IOException {
        Comanda selectedComanda = listView.getSelectionModel().getSelectedItem();
        if (selectedComanda != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("comanda-form-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("ALTERAÇÃO DE COMANDA");
            ComandaFormController controller = fxmlLoader.getController();
            controller.setComanda(selectedComanda); // Configura a comanda selecionada para edição
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            carregaComandas(); // Atualiza a lista de comandas após a alteração
        } else {
            mostrarAlertaErro("Nenhuma comanda selecionada para alterar.");
        }
    }

    @FXML
    void onClickComanda(MouseEvent event) {
        if (listView.getSelectionModel().getSelectedIndex() >= 0) {
            Comanda aux = listView.getSelectionModel().getSelectedItem();
            taPreview.setText("Comanda número: " + aux.getNumero() +
                    "\nNome: " + aux.getNome() +
                    "\nDescrição: " + aux.getDescricao() +
                    "\nStatus: " + (aux.getStatus() == 'P' ? "Paga" : "Aberta"));
        }
    }

    @FXML
    void onNovaComanda(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("comanda-form-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Comanda");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    void onPagarComanda(ActionEvent event) throws IOException {
        Comanda selectedComanda = listView.getSelectionModel().getSelectedItem();
        if (selectedComanda != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("escolha-pagamento-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Escolha de Pagamento");
            EscolhaPagamentoController controller = fxmlLoader.getController();
            controller.setComanda(selectedComanda); // Passa a comanda selecionada para o controlador
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            carregaComandas(); // Atualiza a lista de comandas após o pagamento
        } else {
            mostrarAlertaErro("Nenhuma comanda selecionada para pagar.");
        }
    }

    @FXML
    void onSair(ActionEvent event) {
        btSair.getScene().getWindow().hide();
    }

    private void mostrarAlertaErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}