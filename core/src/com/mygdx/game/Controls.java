package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class Controls {

    private MyGame game;
    PlayerCharacter playerCharacter;


    Controls(MyGame game) {
        this.game = game;
        playerCharacter = game.playerCharacter;
    }

    void manageCameraControls() {
        float cameraSpeedX = game.levelWidth / 2f + 60;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            game.camera.translate(0, game.levelHeight / 2f * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            game.camera.translate(0, -game.levelHeight / 2f * Gdx.graphics.getDeltaTime());
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            game.camera.translate(-cameraSpeedX * Gdx.graphics.getDeltaTime(), 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            game.camera.translate(cameraSpeedX * Gdx.graphics.getDeltaTime(), 0);
        }
        if (Gdx.input.isKeyPressed((Input.Keys.Q))) {
            game.camera.zoom -= Gdx.graphics.getDeltaTime();
        } else if (Gdx.input.isKeyPressed((Input.Keys.E))) {
            game.camera.zoom += Gdx.graphics.getDeltaTime();
        }
    }

    public void resetTheXVelocities() {
        game.mainCharacterBody.setLinearVelocity(0, game.mainCharacterBody.getLinearVelocity().y);
    }

    void addTheKeyInput() {


        Vector2 playerVelocity = game.mainCharacterBody.getLinearVelocity();
        Vector2 playerPosition = game.mainCharacterBody.getPosition();

        int maxVelocity = 75;
        if (Gdx.input.isKeyPressed(Input.Keys.A) || (Gdx.input.isKeyPressed(Input.Keys.LEFT))) {
            game.mainCharacterBody.setLinearVelocity(-maxVelocity, playerVelocity.y);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || (Gdx.input.isKeyPressed(Input.Keys.RIGHT))) {
            game.mainCharacterBody.setLinearVelocity(maxVelocity, playerVelocity.y);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.W) || (Gdx.input.isKeyPressed(Input.Keys.UP))) {

            game.mainCharacterBody.setLinearVelocity(playerVelocity.x, maxVelocity);
        }
    }
}
