package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class Controls {

    private MyGame game;
    PlayerCharacter playerCharacter;
    //todo move the velocity to PlayerChar Class
    int maxVelocity = 50;
    int jumpVelocity = 75;

    Contacts groundSensor;

    Controls(MyGame game) {
        this.game = game;
        playerCharacter = game.playerCharacter;
        groundSensor = new Contacts(game, game.groundBody);
        game.world.setContactListener(groundSensor);
    }

    //todo integrate auto-scrolling of background when character reaches ~20% of the map (both left and right edge)

    void manageCameraControls() {
        float cameraXPos = game.camera.position.x;
        float cameraYPos = game.camera.position.y;
        float playerXPos = playerCharacter.playerSprite.getX();
        float playerYPos = playerCharacter.playerSprite.getY();
        final float cameraSpeedX = maxVelocity;
        final float cameraBorderRight = 2f;
        final float cameraBorderLeft = -2f;
        final float cameraBorderUp = 5f;
        final float cameraBorderDown = -15f;
        float cameraX;
        float cameraY;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            game.camera.translate(0, game.levelHeight / 2f * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            game.camera.translate(0, -game.levelHeight / 2f * Gdx.graphics.getDeltaTime());
        }

        //todo debugging line for dealing damage to player
        if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            game.playerCharacter.hitPoints--;
        }

/*        //camera translation upon getting closer to top/bottom edge
        if (cameraYPos - playerYPos < cameraBorderUp) {
            //game.camera.translate(0, cameraSpeedX * Gdx.graphics.getDeltaTime());
        }
        if (cameraYPos - playerYPos > cameraBorderDown) {
            //game.camera.translate(0, -cameraSpeedX * Gdx.graphics.getDeltaTime());
        }

        //camera translation upon getting closer to left/right edge
        if (cameraXPos - playerXPos > cameraBorderRight) {
            //game.camera.translate(-cameraSpeedX * Gdx.graphics.getDeltaTime(), 0);
        }
        if (cameraXPos - playerXPos < cameraBorderLeft) {
            //game.camera.translate(cameraSpeedX * Gdx.graphics.getDeltaTime(), 0);
        }*/
        if (Gdx.input.isKeyPressed((Input.Keys.PLUS))) {
            game.camera.zoom -= Gdx.graphics.getDeltaTime();
        } else if (Gdx.input.isKeyPressed((Input.Keys.MINUS))) {
            game.camera.zoom += Gdx.graphics.getDeltaTime();
        }
    }

    public void resetTheXVelocities() {
        game.mainCharacterBody.setLinearVelocity(0, game.mainCharacterBody.getLinearVelocity().y);
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
