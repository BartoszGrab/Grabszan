package grab.szan.controller;

import grab.szan.Client;
import grab.szan.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Controller responsible for handling the "Connect to Server" view.
 * Allows the user to input host and port information.
 */
public class ConnectingServerController implements Controller {

    @FXML
    private TextField hostField;

    @FXML
    private TextField portField;

    /**
     * Called when the user clicks the connect button.
     * Attempts to establish a client connection to the server using the provided host and port.
     */
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

    /**
     * Attempts to connect the client to the specified host and port.
     *
     * @param host the server's host
     * @param port the server's port
     * @return true if connection is successful, false otherwise
     */
    private boolean connectToServer(String host, int port) {
        Client.createClient(host, port);
        return Client.getInstance().start();
    }
}
