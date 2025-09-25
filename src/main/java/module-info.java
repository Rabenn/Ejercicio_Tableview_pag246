module es.ruben {
    requires javafx.controls;
    requires javafx.fxml;

    opens es.ruben to javafx.fxml;
    exports es.ruben;
    exports es.ruben.modelos;
    opens es.ruben.modelos to javafx.fxml;
}