package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        System.out.println("GDX Version: " + com.badlogic.gdx.Version.VERSION);
        //new LwjglApplication(new test(), config);
/*        System.out.println(config.width);
        System.out.println(config.height);
        config.width = Math.round(config.width);
        config.height = Math.round(config.height);*/
        config.width = 768;
        config.height = 1366;
        config.fullscreen = true;
        new LwjglApplication(new MyGame(), config);
    }
}
