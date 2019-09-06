package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Controls {

    private MyGame game;

    Controls(MyGame game) {
        this.game = game;
    }

    MarioCharacter mario = new MarioCharacter();
    private final float startingJumpSpeed = 10;
    private float marioJumpSpeed = startingJumpSpeed;
    private boolean youWin;
    private float marioSpeed = 5f;
    private final int baseGroundLevel = 70;

    void manageCameraControls() {
        float cameraSpeedX = game.levelWidth/2f + 60;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            game.camera.translate(0, game.levelHeight/2f * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            game.camera.translate(0, -game.levelHeight/2f * Gdx.graphics.getDeltaTime());
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

    void addTheKeyInput() {
        float marioXPos = mario.getCoordinates().getxPosition();
        float marioYPos = mario.getCoordinates().getyPosition();

        if (mario.isJump()) {
            float marioAcceleration = 0.25f;
            marioJumpSpeed -= marioAcceleration;
            marioYPos += marioJumpSpeed;
            mario.getCoordinates().setyPosition(marioYPos);
        }

        if (marioXPos < baseGroundLevel) {
            marioXPos = baseGroundLevel;
            mario.getCoordinates().setxPosition(marioXPos);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A) || (Gdx.input.isKeyPressed(Input.Keys.LEFT))) {
            marioXPos -= marioSpeed;
            mario.getCoordinates().setxPosition(marioXPos);
        }
        if (marioXPos > game.levelWidth*2) {
            //youWin = true;
            marioXPos = game.levelWidth;
            mario.getCoordinates().setxPosition(marioXPos);
            game.setScreen(new YouWinScreen(game));
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) || (Gdx.input.isKeyPressed(Input.Keys.RIGHT))) {
            marioXPos += marioSpeed;
            mario.getCoordinates().setxPosition(marioXPos);
        }
        if (mario.getCoordinates().getyPosition() < baseGroundLevel) {
            marioJumpSpeed = startingJumpSpeed;
            mario.setJump(false);
            marioYPos = baseGroundLevel;
            mario.getCoordinates().setyPosition(marioYPos);
        }
        if (marioYPos > game.levelHeight) {
            marioYPos = game.levelHeight;
            mario.getCoordinates().setyPosition(marioYPos);
        } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.W) || (Gdx.input.isKeyPressed(Input.Keys.UP))) {
            mario.setJump(true);
        }
    }
}
