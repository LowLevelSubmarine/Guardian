package gui;

import javafx.scene.control.ButtonType;

public class IncompatibleCharAlert extends GuardianAlert {

    private boolean pressedContinue;

    public IncompatibleCharAlert(String original, String decoded) {
        super(AlertType.CONFIRMATION);
        this.setGraphic(null);
        this.setHeaderText(null);
        this.setTitle("Incompatible characters");
        this.setContentText("The entered text contains incompatible characters.\n" +
                "\"" + original + "\" will get decoded to \"" + decoded + "\".");
        ButtonType editButton = new ButtonType("Edit");
        ButtonType continueButton = new ButtonType("Continue");
        this.getButtonTypes().setAll(editButton, continueButton);
        this.pressedContinue = this.showAndWait().get().equals(continueButton);
    }

    public boolean pressedContinue() {
        return this.pressedContinue;
    }
}
