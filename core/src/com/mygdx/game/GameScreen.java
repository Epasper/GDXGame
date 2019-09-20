package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Random;

// todo https://www.gamefromscratch.com/post/2013/11/27/LibGDX-Tutorial-9-Scene2D-Part-1.aspx <-- this class should probably extend an Application Listener and be a Scene instead of Screen

public class GameScreen extends ScreenAdapter {

    private MyGame game;
    private Controls controls;


    boolean spawnIsDone = false;

    Stage gameStage;

    private float circleX = 300;
    private float circleY = 150;
    private float circleRadius = 50;

    Array<Collectible> coinsList = new Array<>();


    public GameScreen(MyGame game) {
        this.game = game;
        controls = new Controls(game);
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            float xPos = random.nextInt(200);
            float yPos = random.nextInt(10);
            Collectible coin = new Collectible(game, xPos, yPos + 5, "Coin.png");
            coinsList.add(coin);
        }
    }

    //todo support for different screen resolutions

    @Override
    public void show() {
        gameStage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(gameStage);
        /*Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int x, int y, int pointer, int button) {
                int renderY = game.levelHeight - y;
                if (Vector2.dst(circleX, circleY, x, renderY) < circleRadius) {
                    game.setScreen(new YouWinScreen(game));
                }
                return true;
            }
        });*/
        System.out.println("End of Show");
    }

    @Override
    public void render(float delta) {

        //todo bodies should not be created in the render method.

        controls.addTheKeyInput();

        controls.manageCameraControls();

        clearScreen();

        setCamera();

        drawAFrame();

        game.monster.setMonsterInMotion();

        game.debugRenderer.render(game.world, game.camera.combined);
        game.world.step(1 / 60f, 6, 2);

        for (Body currentBody : controls.groundSensor.bodiesToBeRemoved) {
            for (int i = 0; i < coinsList.size; i++) {
                if (coinsList.get(i).collectibleBody == currentBody) {
                    coinsList.get(i).collectibleBody = null;
                    coinsList.removeIndex(i);
                }
            }
            game.world.destroyBody(currentBody);
            System.out.println("Body Destroyed: " + currentBody.toString());
        }
        controls.groundSensor.bodiesToBeRemoved.clear();

        controls.resetTheXVelocities();

    }

    private void drawAFrame() {
        game.gameBatch.begin();

        drawABackground();

        paintTheFloorImages();

        drawTheCoins();


        final float spriteSize = 4f;
        game.playerCharacter.playerSprite.setPosition(game.playerCharacter.body.getPosition().x, game.playerCharacter.body.getPosition().y);
        game.gameBatch.draw(game.playerCharacter.playerSprite,
                game.playerCharacter.playerSprite.getX() - spriteSize / 2f,
                game.playerCharacter.playerSprite.getY() - spriteSize / 2f,
                spriteSize,
                spriteSize);

        game.monster.monsterSprite.setPosition(game.monster.body.getPosition().x, game.monster.body.getPosition().y);
        game.gameBatch.draw(game.monster.monsterSprite,
                game.monster.monsterSprite.getX() - spriteSize / 2f,
                game.monster.monsterSprite.getY() - spriteSize / 2f,
                spriteSize,
                spriteSize);

        game.gameBatch.end();

        game.hud.addHudElements();

    }

    private void drawTheCoins() {
        for (Collectible currentCoin : coinsList) {
            final float spriteSize = 4f;
            game.gameBatch.draw(currentCoin,
                    currentCoin.getX() - spriteSize / 2f,
                    currentCoin.getY() - spriteSize / 2f,
                    spriteSize,
                    spriteSize);

        }
    }

    private void paintTheFloorImages() {
        int worldLength = game.levelFactory.worldLength;
        float[] vertices = game.levelFactory.groundVertices;
        for (int i = 0; i < worldLength - 1; i++) {
            if (vertices[2 * i + 3] < vertices[2 * i + 1]) {
                paintDownwardTile(vertices, i);
            } else if (vertices[2 * i + 3] > vertices[2 * i + 1]) {
                paintUpwardTile(vertices, i);
            } else {
                paintAStraightTile(vertices, i);
            }
        }
    }

    private void fillGroundWithTiles(float initialXPos, float initialYPos, boolean isDownward, boolean isUpward) {
        for (int i = 0; i > -12; i--) {
            if (!isUpward || i != 0) {
                game.gameBatch.draw(game.groundInnerTexture,
                        initialXPos,
                        initialYPos + (5 * i),
                        5f,
                        5f);
            }
            if (isDownward && i == 0) {
                continue;
            }
            game.gameBatch.draw(game.groundInnerTexture,
                    initialXPos + 5,
                    initialYPos + (5 * i),
                    5f,
                    5f);
        }
    }


    private void paintAStraightTile(float[] vertices, int i) {
        game.gameBatch.draw(game.groundTileTexture,
                vertices[2 * i] - 2,
                vertices[2 * i + 1],
                5f,
                5f);
        game.gameBatch.draw(game.groundTileTexture,
                vertices[2 * i] + 3,
                vertices[2 * i + 1],
                5f,
                5f);
        fillGroundWithTiles(vertices[2 * i] - 2, vertices[2 * i + 1] - 5, false, false);
    }

    private void paintUpwardTile(float[] vertices, int i) {
        game.gameBatch.draw(game.groundTileUpTexture,
                vertices[2 * i] - 2,
                vertices[2 * i + 1] + 5,
                5f,
                5f);
        game.gameBatch.draw(game.groundTileUpTexture,
                vertices[2 * i] + 3,
                vertices[2 * i + 1] + 10,
                5f,
                5f);
        fillGroundWithTiles(vertices[2 * i] - 2, vertices[2 * i + 1] + 5, false, true);

    }

    private void paintDownwardTile(float[] vertices, int i) {
        game.gameBatch.draw(game.groundTileDownTexture,
                vertices[2 * i] - 2,
                vertices[2 * i + 1],
                5f,
                5f);
        game.gameBatch.draw(game.groundTileDownTexture,
                vertices[2 * i] + 3,
                vertices[2 * i + 1] - 5,
                5f,
                5f);
        fillGroundWithTiles(vertices[2 * i] - 2, vertices[2 * i + 1] - 5, true, false);

    }

    private void drawABackground() {
        Sprite background = new Sprite(game.backgroundTexture);
        game.gameBatch.draw(background, 0, 0,
                356.8f,
                106.7f);
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void setCamera() {
        game.camera.position.set(game.mainCharacterBody.getPosition().x, game.mainCharacterBody.getPosition().y, 0);
        game.camera.update();
        game.gameBatch.setProjectionMatrix(game.camera.combined);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}