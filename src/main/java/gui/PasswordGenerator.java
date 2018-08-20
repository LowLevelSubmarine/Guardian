package gui;

import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import statics.VersionInfo;

import java.util.Random;

public class PasswordGenerator {

    private static final String SPECIAL_CHARS = "!\"#$%&'()*+,-./:;<=>?@[\\]^{}";
    private static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "1234567890";
    private static final int PASSWORD_MAX_LENGTH = 64;
    private static final int PASSWORD_MIN_LENGTH = 4;
    private static final int PASSWORD_LABEL_SPACE = 10;
    private static final int SEED_AMOUNT = 128;

    private String password;
    private Stage stage = new Stage();
    private Label passwordLabel = new Label();
    private Label lengthLabel = new Label();
    private Slider lengthSlider = new Slider();
    private Label seedLabel = new Label();
    private Slider seedSlider = new Slider();
    private CheckBox upperCaseCheckBox = new CheckBox("Upper case");
    private CheckBox lowerCaseCheckBox = new CheckBox("Lower case");
    private CheckBox specialCharsCheckBox = new CheckBox("Special chars");
    private CheckBox numbersCheckBox = new CheckBox("Numbers");
    private Random dice = new Random();

    public PasswordGenerator() {
        VBox root = new VBox();
        this.stage.setScene(new Scene(root, 600, 270));
        this.stage.getIcons().add(new Image(VersionInfo.PROJECT_ICON));
        this.stage.setTitle(VersionInfo.PROJECT_TITLE  + " - Password generator");

        root.setPadding(new Insets(20, 20, 20, 20));
        root.setSpacing(20);
        root.getChildren().add(this.passwordLabel);
        this.passwordLabel.setFont(new Font(15));
        this.passwordLabel.setTextAlignment(TextAlignment.CENTER);
        HBox sliderBox = new HBox();
        root.getChildren().add(sliderBox);
        sliderBox.setSpacing(20);
        sliderBox.getChildren().add(this.lengthLabel);
        this.lengthLabel.setPrefWidth(150);
        sliderBox.getChildren().add(this.lengthSlider);
        this.lengthSlider.setPrefWidth(560);
        this.lengthSlider.setMin(PASSWORD_MIN_LENGTH);
        this.lengthSlider.setMax(PASSWORD_MAX_LENGTH);
        this.lengthSlider.setValue(14);
        this.lengthSlider.setBlockIncrement(1);
        this.lengthSlider.setShowTickLabels(true);
        this.lengthSlider.setSnapToTicks(true);
        this.lengthSlider.setShowTickMarks(true);
        this.lengthSlider.setMajorTickUnit(PASSWORD_LABEL_SPACE);
        this.lengthSlider.setMinorTickCount(PASSWORD_LABEL_SPACE - 1);
        this.lengthSlider.valueProperty().addListener(this::onLengthSliderChanged);
        HBox seedBox = new HBox();
        root.getChildren().add(seedBox);
        seedBox.setSpacing(20);
        seedBox.getChildren().add(this.seedLabel);
        this.seedLabel.setPrefWidth(150);
        seedBox.getChildren().add(this.seedSlider);
        this.seedSlider.setPrefWidth(560);
        this.seedSlider.setMin(1);
        this.seedSlider.setMax(SEED_AMOUNT);
        this.seedSlider.setValue(1);
        this.lengthSlider.setBlockIncrement(1);
        this.seedSlider.setShowTickLabels(true);
        this.seedSlider.setSnapToTicks(true);
        this.seedSlider.setShowTickMarks(true);
        this.seedSlider.setMajorTickUnit(SEED_AMOUNT);
        this.seedSlider.setMinorTickCount(SEED_AMOUNT);
        this.seedSlider.valueProperty().addListener(this::onSeedSliderChanged);
        HBox charsetBox = new HBox();
        root.getChildren().add(charsetBox);
        charsetBox.setSpacing(20);
        charsetBox.getChildren().add(this.upperCaseCheckBox);
        this.upperCaseCheckBox.setPrefWidth(125);
        this.upperCaseCheckBox.setSelected(true);
        this.upperCaseCheckBox.selectedProperty().addListener(this::update);
        charsetBox.getChildren().add(this.lowerCaseCheckBox);
        this.lowerCaseCheckBox.setPrefWidth(125);
        this.lowerCaseCheckBox.setSelected(true);
        this.lowerCaseCheckBox.selectedProperty().addListener(this::update);
        charsetBox.getChildren().add(this.specialCharsCheckBox);
        this.specialCharsCheckBox.setPrefWidth(125);
        this.specialCharsCheckBox.setSelected(false);
        this.specialCharsCheckBox.selectedProperty().addListener(this::update);
        charsetBox.getChildren().add(this.numbersCheckBox);
        this.numbersCheckBox.setPrefWidth(125);
        this.numbersCheckBox.setSelected(false);
        this.numbersCheckBox.selectedProperty().addListener(this::update);
        HBox buttonsBox = new HBox();
        root.getChildren().add(buttonsBox);
        buttonsBox.setAlignment(Pos.CENTER_RIGHT);
        buttonsBox.setSpacing(20);
        Button generateButton = new Button("Generate");
        buttonsBox.getChildren().add(generateButton);
        generateButton.setOnAction(this::onExportPassword);
        Button discardButton = new Button("Discard");
        buttonsBox.getChildren().add(discardButton);
        discardButton.setOnAction(this::onDiscardChanges);
        update(null);
        this.stage.showAndWait();
    }

    private void onDiscardChanges(ActionEvent event) {
        this.password = null;
        this.stage.hide();
    }

    private void onExportPassword(ActionEvent event) {
        this.password = this.passwordLabel.getText();
        this.stage.hide();
    }

    public String getPassword() {
        return this.password;
    }

    private void onLengthSliderChanged(Observable observable, Number oldVal, Number newVal) {
        onSliderChange(this.lengthSlider, oldVal, newVal);
    }

    private void onSeedSliderChanged(Observable observable, Number oldVal, Number newVal) {
        onSliderChange(this.seedSlider, oldVal, newVal);
    }

    private void onSliderChange(Slider slider, Number oldVal, Number newVal) {
        int oldInt = oldVal.intValue();
        int newInt = newVal.intValue();
        slider.setValue(newInt);
        if (oldInt != newInt) {
            update(null);
        }
    }

    private void update(Observable observable) {
        //Update Slider labels
        this.lengthLabel.setText("Length: " + (int) this.lengthSlider.getValue());
        this.seedLabel.setText("Seed: " + (int) this.seedSlider.getValue());
        //Generate charset
        String charset = "";
        if (upperCaseCheckBox.isSelected()) {
            charset += UPPER_CASE;
        }
        if (lowerCaseCheckBox.isSelected()) {
            charset += LOWER_CASE;
        }
        if (specialCharsCheckBox.isSelected()) {
            charset += SPECIAL_CHARS;
        }
        if (numbersCheckBox.isSelected()) {
            charset += NUMBERS;
        }
        //Generate password
        dice.setSeed((long) (this.lengthSlider.getValue() * this.seedSlider.getValue()));
        if (!charset.isEmpty()) {
            String password = "";
            for (int i = 0; i < this.lengthSlider.getValue(); i++) {
                password += charset.charAt(dice.nextInt(charset.length()));
            }
            this.passwordLabel.setText(password);
        }
    }

}
