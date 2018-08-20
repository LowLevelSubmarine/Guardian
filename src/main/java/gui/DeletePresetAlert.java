package gui;

import javafx.scene.control.ButtonType;
import presets.Preset;

public class DeletePresetAlert extends GuardianAlert {

    private boolean pressedContinue;

    public DeletePresetAlert(Preset preset) {
        super(AlertType.WARNING);
        this.setGraphic(null);
        this.setHeaderText(null);
        this.setTitle("Delete preset \"" + preset.toString() + "\"");
        this.setContentText("Do you really want to delete preset \"" + preset.toString() + "\"? " +
                "Password and infos can't be restored!");
        ButtonType keepButton = new ButtonType("Keep");
        ButtonType continueButton = new ButtonType("Delete");
        this.getButtonTypes().setAll(keepButton, continueButton);
        this.pressedContinue = this.showAndWait().get().equals(continueButton);
    }

    public boolean pressedContinue() {
        return this.pressedContinue;
    }
}
