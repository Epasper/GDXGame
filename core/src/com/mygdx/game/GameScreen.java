package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;

public class GameScreen extends ScreenAdapter {

    private final float timeStep = 1 / 60f;
    private final int velocityIterations = 6;
    private final int positionIterations = 2;

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

        resetTheXVelocities();

        //doPhysicsStep(delta);

    }

    private void resetTheXVelocities() {
        game.mainCharacterBody.setLinearVelocity(0,game.mainCharacterBody.getLinearVelocity().y);
    }

/*    private void doPhysicsStep(float deltaTime) {

        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= timeStep) {
            game.world.step(timeStep, velocityIterations, positionIterations);
            accumulator -= timeStep;
        }
    }*/

    private void drawAFrame() {
        game.gameBatch.begin();
        game.gameBatch.draw(game.backgroundTexture, 0, 0);
        for (int i = 0; i < 10; i++) {
            game.gameBatch.draw(game.groundTileTexture, -40 + (i * 230), 0);
        }
        game.gameBatch.end();

/*        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(0, 1, 0, 1);
        game.shapeRenderer.circle(controls.playerCharacter.getCoordinates().getxPosition(), controls.playerCharacter.getCoordinates().getyPosition(), 20);
        game.shapeRenderer.end();*/
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