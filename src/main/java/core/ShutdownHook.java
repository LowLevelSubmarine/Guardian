package core;

import gui.Menu;

public class ShutdownHook extends Thread {

    Menu menu;

    public ShutdownHook(Menu menu) {
        this.menu = menu;
    }

    @Override
    public void run() {
        this.menu.savePresets();
    }

}
