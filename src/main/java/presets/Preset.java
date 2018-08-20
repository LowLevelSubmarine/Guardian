
package presets;

import encryption.Enigmo;
import gui.*;
import gui.Menu;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class Preset {

    private Menu menu;
    private PresetData data;

    private LimitedTextField nameField = new LimitedTextField(256);
    private LimitedTextField keyField = new LimitedTextField(Enigmo.SECRET_SIZE);
    private LimitedTextField passwordField = new LimitedTextField(Enigmo.SECRET_SIZE);
    private Button linkButton = new Button();
    Button generateButton = new Button("Generate");
    private TextArea textArea = new TextArea();
    private Pane presetPane;

    public Preset(PresetData data, Menu menu) {
        this.data = data;
        this.menu = menu;
        buildInfoPane();
    }

    public Preset(String name, Menu menu) {
        this.data = new PresetData();
        this.data.setName(name);
        this.menu = menu;
        buildInfoPane();
    }

    public PresetData getData() {
        return this.data;
    }

    public Pane getPresetPane() {
        return this.presetPane;
    }

    public String getCurrentKey() {
        return this.keyField.getText();
    }

    public void setCurrentKey(String key) {
        this.keyField.setText(key);
    }

    @Override
    public String toString() {
        String name = this.data.getName();
        if (name != null && !name.isEmpty()) {
            return name;
        } else {
            return "no name assigned";
        }
    }

    private void buildInfoPane() {
        VBox root = new VBox();
        root.setPadding(new Insets(20, 20, 20, 20));
        root.setSpacing(20);
        this.nameField.setText(this.data.getName());
        this.nameField.setFont(new Font(20));
        this.nameField.setPrefWidth(894);
        this.nameField.textProperty().addListener(this::onNameChanged);
        VBox keyBox = new VBox();
        keyBox.setPrefWidth(317);
        Label keyLabel = new Label("Key");
        keyLabel.setFont(new Font(15));
        this.keyField.setFont(new Font(15));
        this.keyField.textProperty().addListener(this::updatePassword);
        this.keyField.focusedProperty().addListener(this::updatePassword);
        keyBox.getChildren().add(keyLabel);
        keyBox.getChildren().add(this.keyField);
        VBox passwordBox = new VBox();
        passwordBox.setPrefWidth(317);
        Label passwordLabel = new Label("Password");
        passwordLabel.setFont(new Font(15));
        this.passwordField.setFont(new Font(15));
        passwordBox.getChildren().add(passwordLabel);
        passwordBox.getChildren().add(this.passwordField);
        HBox secureBox = new HBox();
        this.linkButton.setText("Link");
        this.linkButton.setOnAction(this::onLink);
        this.linkButton.setFont(new Font(15));
        this.linkButton.setPrefWidth(100);
        this.generateButton.setOnAction(this::onGenerate);
        this.generateButton.setFont(new Font(15));
        this.generateButton.setPrefWidth(100);
        secureBox.setSpacing(20);
        secureBox.setAlignment(Pos.BOTTOM_CENTER);
        secureBox.getChildren().addAll(keyBox, passwordBox, this.linkButton, this.generateButton);
        this.textArea.setPrefWidth(894);
        this.textArea.setPrefHeight(435);
        this.textArea.setText(this.data.getText());
        this.textArea.textProperty().addListener(this::textChanged);
        root.getChildren().add(this.nameField);
        root.getChildren().add(secureBox);
        root.getChildren().add(this.textArea);
        this.presetPane = root;

        updatePassword(null);
    }

    private void onNameChanged(Observable observable) {
        String name = this.nameField.getText();
        this.data.setName(name);
        this.menu.updateListItem(this);
    }

    private void updatePassword(Observable observable) {
        String key = this.keyField.getText();
        byte[] secret = this.data.getSecret();
        if (key != null && !key.isEmpty()) {
            if (secret != null) {
                String password = this.menu.getEnigmo().decodeString(key, secret);
                this.passwordField.setText(password);
                this.linkButton.setDisable(true);
                this.generateButton.setDisable(true);
            }
            this.linkButton.setDisable(false);
            this.passwordField.setDisable(false);
            this.generateButton.setDisable(false);
        } else {
            this.passwordField.setText("");
            this.passwordField.setDisable(true);
            this.linkButton.setDisable(true);
            this.generateButton.setDisable(true);
        }
    }

    private void onLink(ActionEvent event) {
        //#password# will be encoded with #key#.
        String key = this.keyField.getText();
        String password = this.passwordField.getText();
        //#encoded# is the encoded version of #password#.
        byte[] encoded = this.menu.getEnigmo().encodeString(key, password);
        //#decoded# is #encoded# decoded with the same key.
        String decoded = this.menu.getEnigmo().decodeString(key, encoded);
        if (!password.equals(decoded)) {
            if (!new IncompatibleCharAlert(password, decoded).pressedContinue()) {
                return;
            }
        }
        if (this.data.getSecret() != null) {
            if (!new ChangePasswordAlert().pressedLink()) {
                return;
            }
        }
        this.data.setSecret(encoded);
    }

    private void onGenerate(ActionEvent event) {
        String password = new PasswordGenerator().getPassword();
        if (password != null && !password.isEmpty()) {
            this.passwordField.setText(password);
        }
    }

    private void textChanged(Observable observable) {
        String text = this.textArea.getText();
        this.data.setText(text);
    }

}
