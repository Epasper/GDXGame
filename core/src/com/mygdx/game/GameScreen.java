package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

// todo https://www.gamefromscratch.com/post/2013/11/27/LibGDX-Tutorial-9-Scene2D-Part-1.aspx <-- this class should probably extend an Application Listener and be a Scene instead of Screen

public class GameScreen extends ScreenAdapter {

    private MyGame game;
    private Controls controls;

    private int currentAnimationFrame = 1;
    int animationFps = 1;

    Stage gameStage;

    public GameScreen(MyGame game) {
        this.game = game;
        controls = new Controls(game);
    }

    //todo support for different screen resolutions

    @Override
    public void show() {
        gameStage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(gameStage);
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

        for (Monster monster : game.levelFactory.monsterList) {
            monster.setMonsterInMotion();
        }

        game.debugRenderer.render(game.world, game.camera.combined);
        game.world.step(1 / 60f, 6, 2);

        //todo optimize the loops, so that the parameters are read and only one loop is being iterated (add user data to body on collision and get the user data here, then iterate through one list only)
        for (Body currentBody : controls.contacts.bodiesToBeRemoved) {
            for (int i = 0; i < game.levelFactory.coinsList.size; i++) {
                if (game.levelFactory.coinsList.get(i).collectibleBody == currentBody) {
                    game.levelFactory.coinsList.get(i).collectibleBody = null;
                    game.levelFactory.coinsList.removeIndex(i);
                }
            }
            for (int i = 0; i < game.levelFactory.monsterList.size; i++) {
                if (game.levelFactory.monsterList.get(i).body == currentBody) {
                    game.levelFactory.monsterList.get(i).body = null;
                    game.levelFactory.monsterList.removeIndex(i);
                }
            }
            game.world.destroyBody(currentBody);
            System.out.println("Body Destroyed: " + currentBody.toString());
        }
        for (Body currentBody : controls.contacts.projectileBodiesToBeRemoved) {
            //for (int i = 0; i < game.projectileArray.size; i++) {
            //if (game.projectileArray.get(i).getBody() == currentBody) {
            //game.projectileArray.get(i).setBody(null);
            //game.projectileArray.removeIndex(i);
            // }
            // }
            game.world.destroyBody(currentBody);
            game.projectileArray.clear();
            System.out.println("Body Destroyed: " + currentBody.toString());
        }

        controls.contacts.bodiesToBeRemoved.clear();
        controls.contacts.projectileBodiesToBeRemoved.clear();

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

        for (Monster monster : game.levelFactory.monsterList) {
            monster.monsterSprite.setPosition(monster.body.getPosition().x, monster.body.getPosition().y);
            game.gameBatch.draw(monster.monsterSprite,
                    monster.monsterSprite.getX() - spriteSize / 2f,
                    monster.monsterSprite.getY() - spriteSize / 2f,
                    spriteSize,
                    spriteSize);
        }

        game.gameBatch.end();

        game.hud.addHudElements();

    }

    private void drawTheCoins() {

        for (Collectible currentCoin : game.levelFactory.coinsList) {
            final float spriteSize = 4f;
            if (currentAnimationFrame > 5) currentAnimationFrame = 1;
            System.out.println(currentAnimationFrame);
            currentCoin.setTexture(currentCoin.collectibleAnimation.get(currentAnimationFrame));
            animationFps++;
            if (animationFps > 400) {
                currentAnimationFrame++;
                animationFps = 1;
            }
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