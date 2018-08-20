package gui;

import javafx.geometry.Insets;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import statics.VersionInfo;

public class InfoAlert extends GuardianAlert {
    public InfoAlert() {
        super(AlertType.INFORMATION);
        this.setTitle(VersionInfo.PROJECT_TITLE + " Information");

        VBox root = new VBox();
        root.setPadding(new Insets(30, 30, 30, 30));
        root.setSpacing(30);
        DialogPane dialogPane = this.getDialogPane();
        dialogPane.getChildren().setAll(root);
        dialogPane.setPrefWidth(560);
        dialogPane.setPrefHeight(510);
        ImageView logo = new ImageView(new Image(VersionInfo.PROJECT_LOGO));
        Text version = new Text("Version: " + VersionInfo.VERSION_NUMBER + " - " + VersionInfo.VERSION_TITLE);
        version.setWrappingWidth(500);
        version.setTextAlignment(TextAlignment.RIGHT);
        version.setFont(new Font(12));
        VBox logoBox = new VBox(logo, version);
        root.getChildren().add(logoBox);
        Text text = new Text("This tool is there to let you create and manage all your different passwords with one password (further reffered to as \"key\") alone. " +
                "All the passwords are stored encrypted on your hard drive. " +
                "The encryption process ensures that the only way to get stored passwords back is by decrypting it with the key it was encrypted with. " +
                "To ensure your passwords are stored safely, only link randomly generated passwords with your key. " +
                "DO NOT store sensitive information inside the text area, considering only the passwords are stored encrypted due to safety reasons.\n\n" +
                "Guardian is developed by Florian Weichert. The projects source code can be found on " + VersionInfo.URL_GITHUB + ".");
        text.setWrappingWidth(500);
        text.setTextAlignment(TextAlignment.JUSTIFY);
        text.setFont(new Font(17));
        root.getChildren().add(text);
    }
}
