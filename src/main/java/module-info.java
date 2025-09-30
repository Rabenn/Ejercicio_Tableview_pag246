module es.ruben {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires java.sql;
    requires java.management;

    // Abre los paquetes a FXML (para controladores)
    opens es.ruben to javafx.fxml;
    opens es.ruben.controladores to javafx.fxml;

    // Exporta los paquetes que quieras usar fuera del módulo
    exports es.ruben;
    exports es.ruben.controladores;
}