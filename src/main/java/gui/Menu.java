package gui;

import com.toddway.shelf.Shelf;
import com.toddway.shelf.ShelfItem;
import encryption.Enigmo;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import presets.Preset;
import presets.PresetComperator;
import presets.PresetData;

import java.io.File;
import java.util.LinkedList;

public class Menu {

    private static final String STORAGE_PATH = "resources";
    private static final String STORAGE_ITEM_NAME = "Presets";
    private static final String DEFAULTPRESETNAME = "Preset ";

    private Shelf shelf;
    private Enigmo enigmo;
    private Scene scene;
    private HBox root = new HBox();
    private ListView<Preset> list = new ListView<>();
    private Pane infoPlaceholder = new Pane();

    public Menu() {
        this.shelf = new Shelf(new File(STORAGE_PATH));
        this.enigmo = new Enigmo();
        loadPresets();
        buildScene();
    }

    public Scene getScene() {
        return this.scene;
    }

    public Enigmo getEnigmo() {
        return this.enigmo;
    }

    private void buildScene() {
        this.list.setPrefWidth(300);
        this.list.setPrefHeight(560);
        this.list.getSelectionModel().selectedItemProperty().addListener(this::onSelect);
        HBox presetEditBox = new HBox();
        presetEditBox.setPadding(new Insets(10, 10, 10, 10));
        presetEditBox.setSpacing(10);
        Button addButton = new Button();
        addButton.setFont(new Font(15));
        addButton.setPrefWidth(100);
        addButton.setText("Add");
        addButton.setOnAction(this::onAddButtonPress);
        Button removeButton = new Button();
        removeButton.setFont(new Font(15));
        removeButton.setPrefWidth(100);
        removeButton.setText("Remove");
        removeButton.setOnAction(this::onRemoveButtonPress);
        Button infoButton = new Button();
        infoButton.setFont(new Font(15));
        infoButton.setPrefWidth(100);
        infoButton.setText("Info");
        infoButton.setOnAction(this::onInfoButtonPress);
        presetEditBox.getChildren().add(addButton);
        presetEditBox.getChildren().add(removeButton);
        presetEditBox.getChildren().add(infoButton);
        VBox presetBox = new VBox();
        presetBox.getChildren().add(this.list);
        presetBox.getChildren().add(presetEditBox);
        this.root.getChildren().add(presetBox);
        this.root.getChildren().add(this.infoPlaceholder);
        this.scene = new Scene(this.root, 1260, 600);
    }

    private void onSelect(Observable observable, Preset oldPreset, Preset preset) {
        if (preset != null) {
            if (oldPreset != null) {
                preset.setCurrentKey(oldPreset.getCurrentKey());
            }
            this.infoPlaceholder.getChildren().setAll(preset.getPresetPane());
        } else {
            this.infoPlaceholder.getChildren().clear();
        }
    }

    private void onInfoButtonPress(ActionEvent event) {
        new InfoAlert().showAndWait();
    }

    private void onAddButtonPress(ActionEvent event) {
        Preset preset = new Preset(uniquePresetName(), this);
        this.list.getItems().add(preset);
        this.list.getSelectionModel().select(preset);
    }

    private void onRemoveButtonPress(ActionEvent event) {
        Preset preset = this.list.getSelectionModel().getSelectedItem();
        PresetData data = preset.getData();
        boolean isEdited = (data.getText() != null && !data.getText().isEmpty()) || data.getSecret() != null;
        if (!isEdited || new DeletePresetAlert(preset).pressedContinue()) {
            this.list.getItems().remove(preset);
        }
    }

    public void savePresets() {
        ShelfItem item = this.shelf.item(STORAGE_ITEM_NAME);
        LinkedList<PresetData> presetDataList = new LinkedList<>();
        for (Preset preset : this.list.getItems()) {
            presetDataList.add(preset.getData());
        }
        item.put(presetDataList);
    }

    public void loadPresets() {
        ShelfItem item = this.shelf.item(STORAGE_ITEM_NAME);
        if (item.exists()) {
            PresetData[] presetDataList = item.get(PresetData[].class);
            for (PresetData presetData : presetDataList) {
                this.list.getItems().add(new Preset(presetData, this));
            }
            this.list.getItems().sort(new PresetComperator());
        } else {
            onAddButtonPress(null);
            this.list.getSelectionModel().clearSelection();
        }
    }

    public void updateListItem(Preset preset) {
        int index = this.list.getItems().indexOf(preset);
        this.list.getItems().set(index, preset);
        this.list.getSelectionModel().select(preset);
    }

    private String uniquePresetName() {
        for (int index = 1;; index++) {
            boolean uniqueName = true;
            String potentionalName = DEFAULTPRESETNAME + index;
            for (Preset preset : this.list.getItems()) {
                if (preset.toString().equals(potentionalName)) {
                    uniqueName = false;
                    break;
                }
            }
            if (uniqueName) {
                return potentionalName;
            }
        }
    }

}
