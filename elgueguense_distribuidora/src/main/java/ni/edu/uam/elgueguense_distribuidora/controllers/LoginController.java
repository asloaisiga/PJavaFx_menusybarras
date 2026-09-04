package ni.edu.uam.elgueguense_distribuidora.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnEnter;

    private static final String USUARIO_VALIDO = "admin_distribuidora";
    private static final String CLAVE_VALIDA = "admin123";

    @FXML
    private void loginButtonAction(ActionEvent event) throws IOException {
        String usuario = txtUser.getText();
        String clave = txtPassword.getText();

        if(usuario.equals(USUARIO_VALIDO) && clave.equals(CLAVE_VALIDA)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ni/edu/uam/elgueguense_distribuidora/gueguense-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Distribuidora El Güegüense");
            stage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Usuario o Clave incorrecto");
            alert.showAndWait();
        }

    }

}

