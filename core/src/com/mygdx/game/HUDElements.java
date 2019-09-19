package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HUDElements extends SpriteBatch {

    MyGame game;
    PlayerCharacter player;
    Texture hpBarTexture = new Texture("HP.png");


    public void addHudElements () {
        this.begin();
        for (int i = 0; i < player.hitPoints; i++) {
            this.draw(hpBarTexture, (7 * i), 75, 7, 12);
        }
        this.end();
    }
}
