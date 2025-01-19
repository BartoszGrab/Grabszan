/**
 * Moduł odpowiedzialny za aplikację Grab Szan.
 */
module grab.szan {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.fxml;

    exports grab.szan.main;
    opens grab.szan.controller to javafx.fxml;
}
