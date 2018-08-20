package core;

import gui.Menu;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import statics.VersionInfo;

public class Main extends Application {

    private Stage stage;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        setMenuScene();
    }

    private void setMenuScene() {
        Menu menu = new Menu();
        Runtime.getRuntime().addShutdownHook(new ShutdownHook(menu));
        this.stage.setScene(menu.getScene());
        this.stage.setTitle(VersionInfo.PROJECT_TITLE);
        this.stage.getIcons().add(new Image(VersionInfo.PROJECT_ICON));
        this.stage.setResizable(false);
        this.stage.show();
    }
}
