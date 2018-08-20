package gui;

import javafx.scene.control.ButtonType;

public class ChangePasswordAlert extends GuardianAlert {

    private boolean pressedLink;

    public ChangePasswordAlert() {
        super(AlertType.WARNING);
        this.setHeaderText(null);
        this.setGraphic(null);
        this.setTitle("Override warning");
        this.setContentText("By linking a new password, the old password will be discarded.");
        ButtonType linkButton = new ButtonType("Link");
        ButtonType doNotLinkButton = new ButtonType("Don't link");
        this.getButtonTypes().setAll(linkButton, doNotLinkButton);
        this.pressedLink = this.showAndWait().get().equals(linkButton);
    }

    public boolean pressedLink() {
        return this.pressedLink;
    }

}
