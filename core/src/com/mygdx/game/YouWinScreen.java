package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

public class YouWinScreen extends ScreenAdapter {

    MyGame game;

    public YouWinScreen(MyGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean keyDown(int keyCode) {

                if (keyCode == Input.Keys.ENTER) {
                    game.setScreen(new MenuScreen(game));
                    game.camera.position.set(game.levelWidth/2f, game.levelHeight/2f, 0);
                    game.camera.update();                }

                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.25f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.screensBatch.begin();
        game.font.draw(game.screensBatch, "You win!", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .75f);
        game.font.draw(game.screensBatch, "Press enter to restart.", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .25f);
        game.screensBatch.end();

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}