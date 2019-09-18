package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

public class MenuScreen extends ScreenAdapter {

    MyGame game;

    public MenuScreen(MyGame game) {
        this.game = game;
    }

    @Override
    public void show(){

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) {
                    game.setScreen(new GameScreen(game));
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, .25f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.screensBatch.begin();
        game.font.draw(game.screensBatch, "Welcome to the Game!", 150, 250);
        game.font.draw(game.screensBatch, "Press space to play.", 150, 200);
        game.screensBatch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}

