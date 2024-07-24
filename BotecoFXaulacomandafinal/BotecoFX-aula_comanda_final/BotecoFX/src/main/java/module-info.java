module org.example.botecofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires org.json;
    requires java.sql;

    opens org.example.botecofx.db.entidades to javafx.fxml;
    opens org.example.botecofx to javafx.fxml;
    exports org.example.botecofx;
    exports org.example.botecofx.db.entidades;
}