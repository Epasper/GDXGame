package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class GameScreen extends ScreenAdapter {

    private MyGame game;
    private Controls controls;

    private float circleX = 300;
    private float circleY = 150;
    private float circleRadius = 50;

    public GameScreen(MyGame game) {
        this.game = game;
        controls = new Controls(game);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int x, int y, int pointer, int button) {
                int renderY = game.levelHeight - y;
                if (Vector2.dst(circleX, circleY, x, renderY) < circleRadius) {
                    game.setScreen(new YouWinScreen(game));
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {

        controls.addTheKeyInput();
        controls.manageCameraControls();

        clearScreen();

        setCamera();

        drawAFrame();
    }

    private void drawAFrame() {
        game.gameBatch.begin();
        game.gameBatch.draw(game.backgroundTexture, 0, 0);
        for (int i = 0; i < 10; i++) {
            game.gameBatch.draw(game.groundTileTexture, -40 + (i * 230), 0);
        }
        game.gameBatch.end();

        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(0, 1, 0, 1);
        game.shapeRenderer.circle(controls.mario.getCoordinates().getxPosition(), controls.mario.getCoordinates().getyPosition(), 20);
        game.shapeRenderer.end();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void setCamera() {
        game.camera.update();
        game.shapeRenderer.setProjectionMatrix(game.camera.combined);
        game.gameBatch.setProjectionMatrix(game.camera.combined);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}