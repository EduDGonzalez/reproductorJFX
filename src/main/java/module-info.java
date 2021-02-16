module org.apache.poi.reproductormusica {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.base;
    
    opens org.apache.poi.reproductormusica to javafx.fxml;
    exports org.apache.poi.reproductormusica;
}
