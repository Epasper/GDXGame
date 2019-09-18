package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class Controls {

    private MyGame game;
    PlayerCharacter playerCharacter;
    int maxVelocity = 50;
    int jumpVelocity = 75;

    GroundContactListener groundSensor;

    Controls(MyGame game) {
        this.game = game;
        playerCharacter = game.playerCharacter;
        groundSensor = new GroundContactListener(game, playerCharacter.body, game.groundBody);
        game.world.setContactListener(groundSensor);
    }

    //todo integrate auto-scrolling of background when character reaches ~20% of the map (both left and right edge)

    void manageCameraControls() {
        float cameraXPos = game.camera.position.x;
        float cameraYPos = game.camera.position.y;
        float playerXPos = playerCharacter.playerSprite.getX();
        float playerYPos = playerCharacter.playerSprite.getY();
        final float cameraSpeedX = maxVelocity;
        final float cameraBorderRight = 15f;
        final float cameraBorderLeft = -15f;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            game.camera.translate(0, game.levelHeight / 2f * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            game.camera.translate(0, -game.levelHeight / 2f * Gdx.graphics.getDeltaTime());
        }

        if (cameraXPos - playerXPos > cameraBorderRight) {
            game.camera.translate(-cameraSpeedX * Gdx.graphics.getDeltaTime(), 0);
        }
        if (cameraXPos - playerXPos < cameraBorderLeft) {
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

    public void checkTheJumpingAllowance() {
        if (game.mainCharacterBody.getLinearVelocity().y == 0) {
            //sensor.playerOnGround = true;
        }
    }

    void addTheKeyInput() {


        Vector2 playerVelocity = game.mainCharacterBody.getLinearVelocity();
        Vector2 playerPosition = game.mainCharacterBody.getPosition();

        if (Gdx.input.isKeyPressed(Input.Keys.A) || (Gdx.input.isKeyPressed(Input.Keys.LEFT))) {
            game.mainCharacterBody.setLinearVelocity(-maxVelocity, playerVelocity.y);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || (Gdx.input.isKeyPressed(Input.Keys.RIGHT))) {
            game.mainCharacterBody.setLinearVelocity(maxVelocity, playerVelocity.y);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.W) || (Gdx.input.isKeyPressed(Input.Keys.UP))) {
            if (groundSensor.playerOnGround) {
                game.mainCharacterBody.setLinearVelocity(playerVelocity.x, jumpVelocity);
                groundSensor.playerOnGround = false;
            }
        }
    }
}
