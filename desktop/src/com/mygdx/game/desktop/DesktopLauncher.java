package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        //new LwjglApplication(new test(), config);
/*        System.out.println(config.width);
        System.out.println(config.height);
        config.width = Math.round(config.width);
        config.height = Math.round(config.height);*/
        new LwjglApplication(new MyGame(), config);
    }
}
