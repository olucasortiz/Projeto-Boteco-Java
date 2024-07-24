package org.example.botecofx;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.botecofx.db.dals.DALComanda;
import org.example.botecofx.db.dals.DALGarcon;
import org.example.botecofx.db.dals.DALProduto;
import org.example.botecofx.db.entidades.Comanda;
import org.example.botecofx.db.entidades.Garcon;
import org.example.botecofx.db.entidades.Produto;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ComandaFormController implements Initializable {
    @FXML
    private TextField tfNumero;
    @FXML
    private ComboBox<Garcon> cbGarcon;
    @FXML
    private ComboBox<Produto> cbProdutos;
    @FXML
    private Spinner<Integer> spQuantidade;
    @FXML
    private ListView<Comanda.Item> listView;
    @FXML
    private Button btSalvar;
    @FXML
    private Button btCancelar;
    @FXML
    private TextArea taPreview;

    private Comanda comanda;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spQuantidade.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1));
        cbProdutos.setItems(FXCollections.observableList(new DALProduto().get("")));
        cbGarcon.setItems(FXCollections.observableList(new DALGarcon().get("")));

        cbGarcon.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                atualizarPreview();
            }
        });
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
        if (comanda != null) {
            tfNumero.setText(String.valueOf(comanda.getNumero()));
            cbGarcon.setValue(comanda.getGarcon());
            listView.setItems(FXCollections.observableArrayList(comanda.getItens()));
            atualizarPreview();
        } else {
            tfNumero.clear();
            cbGarcon.getSelectionModel().clearSelection();
            listView.getItems().clear();
            taPreview.clear();
        }
    }

    @FXML
    void onInserirItem(ActionEvent event) {
        Produto produtoSelecionado = cbProdutos.getSelectionModel().getSelectedItem();
        int quantidade = spQuantidade.getValue();
        Garcon garconSelecionado = cbGarcon.getSelectionModel().getSelectedItem();

        if (produtoSelecionado != null && quantidade > 0 && garconSelecionado != null) {
            Comanda.Item item = new Comanda.Item(quantidade, produtoSelecionado, garconSelecionado);
            listView.getItems().add(item);
            atualizarPreview();
        } else {
            mostrarAlertaErro("Selecione um produto, um garçom e uma quantidade válida.");
        }
    }

    @FXML
    private void onSalvar() {
        if (comanda == null) {
            comanda = new Comanda();
        }

        String numero = tfNumero.getText();
        Garcon garcon = cbGarcon.getSelectionModel().getSelectedItem();

        if (numero.isEmpty() || garcon == null) {
            mostrarAlertaErro("Por favor, preencha todos os campos obrigatórios.");
            return;
        }

        comanda.setNumero(Integer.parseInt(numero));
        comanda.setGarcon(garcon);
        comanda.setData(LocalDate.now());
        comanda.setDescricao(""); // Adicione a descrição conforme necessário
        comanda.setStatus('A'); // Defina o status conforme necessário

        DALComanda dalComanda = new DALComanda();
        if (comanda.getId() == 0) {
            if (dalComanda.gravar(comanda)) {
                mostrarAlertaSucesso("Comanda salva com sucesso!");
            } else {
                mostrarAlertaErro("Erro ao salvar a comanda no banco de dados.");
            }
        } else {
            if (dalComanda.alterar(comanda)) {
                mostrarAlertaSucesso("Comanda atualizada com sucesso!");
            } else {
                mostrarAlertaErro("Erro ao atualizar a comanda no banco de dados.");
            }
        }

        fecharJanela();
    }


    @FXML
    private void onCancelar() {
        fecharJanela();
    }

    private void fecharJanela() {
        Stage stage = (Stage) btCancelar.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlertaErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void mostrarAlertaSucesso(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void atualizarPreview() {
        StringBuilder preview = new StringBuilder();
        preview.append("Número: ").append(tfNumero.getText()).append("\n");
        Garcon garconSelecionado = cbGarcon.getSelectionModel().getSelectedItem();
        if (garconSelecionado != null) {
            preview.append("Garçom: ").append(garconSelecionado.getNome()).append("\n");
        }
        for (Comanda.Item item : listView.getItems()) {
            preview.append(item.toString()).append("\n");
        }
        taPreview.setText(preview.toString());

    }
}
