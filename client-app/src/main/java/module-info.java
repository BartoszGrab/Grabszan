module grab.szan {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.fxml;

    exports grab.szan.main; // Eksportuje klasę startową
    opens grab.szan.controller to javafx.fxml; // Umożliwia dostęp do kontrolerów FXML
}
