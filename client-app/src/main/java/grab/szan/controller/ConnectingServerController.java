package grab.szan.controller;

import grab.szan.Client;
import grab.szan.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ConnectingServerController {

    @FXML
    private TextField hostField;

    @FXML
    private TextField portField;

    @FXML
    private void onConnect() {
        String host = hostField.getText();
        String port = portField.getText();

        if (host.isEmpty() || port.isEmpty()) {
            Utils.showAlert("Error", "All fields must be filled!");
            return;
        }

        try {
            int portNumber = Integer.parseInt(port);

            boolean connectionSuccess = connectToServer(host, portNumber);

            if (connectionSuccess) {
                ViewManager.showMenuView();
                
            } else {
                Utils.showAlert("Error", "Failed to connect to the server!");
            }
        } catch (NumberFormatException e) {
            Utils.showAlert("Error", "Port must be a valid number!");
        } catch (Exception e) {
            Utils.showAlert("Error", "An unexpected error occurred: " + e.getMessage());
        }
    }

    private boolean connectToServer(String host, int port) {
        Client.createClient(host, port);
        return Client.getInstance().start();
    }

}
