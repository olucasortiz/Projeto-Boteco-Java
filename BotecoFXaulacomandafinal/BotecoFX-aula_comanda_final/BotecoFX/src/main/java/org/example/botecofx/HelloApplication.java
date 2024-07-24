package org.example.botecofx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.botecofx.db.SingletonDB;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menu-adm-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("BotecoFX");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        if(SingletonDB.conectar()==false)
        {
            //JOptionPane.showMessageDialog(null,"Erro ao conectar com o banco");
            System.out.println("Erro ao conectar com o banco");
            Platform.exit();;
        }
        launch();
    }
}