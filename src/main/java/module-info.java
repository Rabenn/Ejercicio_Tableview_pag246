module es.ruben {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;

    opens es.ruben to javafx.fxml;
    exports es.ruben;
}