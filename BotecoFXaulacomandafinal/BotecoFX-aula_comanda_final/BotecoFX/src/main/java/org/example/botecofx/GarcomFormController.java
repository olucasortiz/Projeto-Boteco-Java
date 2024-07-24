package org.example.botecofx;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.example.botecofx.db.dals.DALGarcon;
import org.example.botecofx.db.entidades.Garcon;
import org.example.botecofx.util.MaskFieldUtil;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

public class GarcomFormController implements Initializable {
    public TextField tfId;
    public TextField tfNome;
    public TextField tfCpf;
    public TextField tfCep;
    public TextField tfEndereco;
    public TextField tfCidade;
    public TextField tfUf;
    public TextField tfFone;
    public TextField tfNumero;

    public void onCancelar(ActionEvent actionEvent) {
        tfId.getScene().getWindow().hide();
    }

    public void onConfirmar(ActionEvent actionEvent) {
        Garcon garcon=new Garcon();
        garcon.setNome(tfNome.getText());
        garcon.setCpf(tfCpf.getText());
        garcon.setCep(tfCep.getText());
        garcon.setEndereco(tfEndereco.getText()+","+tfNumero.getText());
        garcon.setCidade(tfCidade.getText());
        garcon.setUf(tfUf.getText());
        garcon.setFone(tfFone.getText());
        DALGarcon dal=new DALGarcon();
        if(tfId.getText().isEmpty()) {
            if (dal.gravar(garcon) == false) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro ao cadastrar o garçon " + garcon.getNome());
                alert.showAndWait();
            }
        }
        else {
            garcon.setId(Integer.parseInt(tfId.getText()));
            if (dal.alterar(garcon) == false) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro ao alterar o garçon " + garcon.getNome());
                alert.showAndWait();
            }
        }
        tfId.getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MaskFieldUtil.cepField(tfCep);
        MaskFieldUtil.foneField(tfFone);
        MaskFieldUtil.cpfField(tfCpf);
        tfCep.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(tfCep.getText().length() == 9) {
                    new Thread(new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            String sjson = consultaCep(tfCep.getText(), "json");
                            JSONObject json = new JSONObject(sjson);
                            tfEndereco.setText(json.getString("logradouro"));
                            tfCidade.setText(json.getString("localidade"));
                            tfUf.setText(json.getString("uf"));
                            Platform.runLater(() -> {
                                tfNumero.requestFocus();
                            });
                            return null;
                        }
                    }).start();
                }
            }
        });

        // verificar se é alteração ou novo garçom
        if (GarcomTableController.garcon != null) {
            Garcon g = GarcomTableController.garcon;
            tfNome.setText(g.getNome());
            tfCpf.setText(g.getCpf());

            String endereco = g.getEndereco();
            String[] enderecoParts = endereco.split(",", 2);
            tfEndereco.setText(enderecoParts[0].trim());
            if (enderecoParts.length > 1) {
                tfNumero.setText(enderecoParts[1].trim());
            } else {
                tfNumero.setText("");
            }

            tfCidade.setText(g.getCidade());
            tfUf.setText(g.getUf());
            tfCep.setText(g.getCep());
            tfFone.setText(g.getFone());
            tfId.setText(String.valueOf(g.getId()));
        }
    }

    public static String consultaCep(String cep, String formato)
    {
        StringBuffer dados = new StringBuffer();
        try {
            URL url = new URL("https://viacep.com.br/ws/"+ cep + "/"+formato+"/");
            URLConnection con = url.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setAllowUserInteraction(false);
            InputStream in = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String s = "";
            while (null != (s = br.readLine()))
                dados.append(s);
            br.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return dados.toString();
    }
}
