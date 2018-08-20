package gui;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import statics.VersionInfo;

public class GuardianAlert extends Alert {

    public GuardianAlert(AlertType alertType) {
        super(alertType);
        setImage();
    }

    private void setImage() {
        Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(VersionInfo.PROJECT_ICON));
    }

}
