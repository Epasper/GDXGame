package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class GameScreen extends ScreenAdapter {

    private MyGame game;
    private Controls controls;

    private float circleX = 300;
    private float circleY = 150;
    private float circleRadius = 50;

    private float accumulator = 0;

    public GameScreen(MyGame game) {
        this.game = game;
        controls = new Controls(game);
    }

    //todo support for different screen resolutions

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

        game.debugRenderer.render(game.world, game.camera.combined);
        game.world.step(1 / 60f, 6, 2);

        controls.resetTheXVelocities();
        controls.checkTheJumpingAllowance();

    }

    private void drawAFrame() {
        drawABackground();

        drawTheFloor();

        final float spriteSize = 4f;
        game.playerCharacter.playerSprite.setPosition(game.playerCharacter.body.getPosition().x, game.playerCharacter.body.getPosition().y);
        game.gameBatch.draw(game.playerCharacter.playerSprite,
                game.playerCharacter.playerSprite.getX() - spriteSize / 2f,
                game.playerCharacter.playerSprite.getY() - spriteSize / 2f, spriteSize, spriteSize);

        game.gameBatch.end();

    }

    private void drawTheFloor() {
        for (int i = 0; i < 10; i++) {
            game.gameBatch.draw(game.groundTileTexture, -4f +
                            (i * 23f),
                    0f,
                    23f,
                    7.5f);
        }
    }

    private void drawABackground() {
        Sprite background = new Sprite(game.backgroundTexture);
        game.gameBatch.begin();
        game.gameBatch.draw(background, 0, 0,
                356.8f,
                106.7f);
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