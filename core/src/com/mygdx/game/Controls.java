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

    private final float startingJumpSpeed = 10;
    private float playerCharacterJumpingSpeed = startingJumpSpeed;
    private boolean youWin;
    private float marioSpeed = 5f;
    private final int baseGroundLevel = 70;
    private final int maxVelocity = 75;

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
        float marioXPos = playerCharacter.getCoordinates().getxPosition();
        float marioYPos = playerCharacter.getCoordinates().getyPosition();

        Vector2 playerVelocity = game.mainCharacterBody.getLinearVelocity();
        Vector2 playerPosition = game.mainCharacterBody.getPosition();

        /*        if (playerCharacter.isJump()) {
         *//*            float marioAcceleration = 0.25f;
            playerCharacterJumpingSpeed -= marioAcceleration;
            marioYPos += playerCharacterJumpingSpeed;
            playerCharacter.getCoordinates().setyPosition(marioYPos);*//*
        }*/

        if (marioXPos < baseGroundLevel) {
            marioXPos = baseGroundLevel;
            playerCharacter.getCoordinates().setxPosition(marioXPos);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A) || (Gdx.input.isKeyPressed(Input.Keys.LEFT))) {
            game.mainCharacterBody.setLinearVelocity(-maxVelocity, playerVelocity.y);
/*            marioXPos -= marioSpeed;
            playerCharacter.getCoordinates().setxPosition(marioXPos);*/
        }
        if (marioXPos > game.levelWidth * 4) {
            //youWin = true;
            marioXPos = game.levelWidth;
            playerCharacter.getCoordinates().setxPosition(marioXPos);
            game.setScreen(new YouWinScreen(game));
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) || (Gdx.input.isKeyPressed(Input.Keys.RIGHT))) {
            game.mainCharacterBody.setLinearVelocity(maxVelocity, playerVelocity.y);
/*            marioXPos += marioSpeed;
            playerCharacter.getCoordinates().setxPosition(marioXPos);*/
        }
        if (playerCharacter.getCoordinates().getyPosition() < baseGroundLevel) {
            playerCharacterJumpingSpeed = startingJumpSpeed;
            playerCharacter.setJump(false);
            marioYPos = baseGroundLevel;
            playerCharacter.getCoordinates().setyPosition(marioYPos);
        }
        /*if (marioYPos > game.levelHeight) {
            marioYPos = game.levelHeight;
            playerCharacter.getCoordinates().setyPosition(marioYPos);
        } else*/
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.W) || (Gdx.input.isKeyPressed(Input.Keys.UP))) {
            //playerCharacter.setJump(true);
            /*game.mainCharacterBody.applyLinearImpulse(0, 500,
                    game.mainCharacterBody.getPosition().x,
                    game.mainCharacterBody.getPosition().y,
                    true);*/
            game.mainCharacterBody.setLinearVelocity(playerVelocity.x,  maxVelocity);
        }
    }
}
