package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class LevelFactory {

    private MyGame game;
    Level level = new Level();

    int monsterID = 10;

    public Array<Collectible> coinsList = new Array<>();
    public Array<Monster> monsterList = new Array<>();

    public int worldLength = 50;
    float[] groundVertices = new float[2 * worldLength];
    public int xPositionSpawningOffset = -20;


    public LevelFactory(MyGame game) {
        this.game = game;

    }

    public Body createFloatingIsland(BodyDef.BodyType type, float x1, float y1, float x2, float y2, float density) {
        EdgeShape poly = new EdgeShape();
        poly.set(new Vector2(0, 0), new Vector2(x2 - x1, y2 - y1));

        BodyDef def = new BodyDef();
        def.type = type;
        Body body = game.world.createBody(def);
        body.createFixture(poly, density);
        body.setTransform(x1, y1, 0);
        poly.dispose();

        return body;
    }

    public Body createFloor(BodyDef.BodyType type, float x1, float y1, float density) {

        Random random = new Random();
        float randX = xPositionSpawningOffset;
        float randY = -10;
        int randomizedDirection = 0;
        for (int i = 0; i < worldLength; i++) {
            if (randomizedDirection > 0) {
                randomizedDirection = random.nextInt(2);
            } else if (randomizedDirection < 0) {
                randomizedDirection = random.nextInt(2) - 1;
            } else {
                randomizedDirection = random.nextInt(3) - 1;
            }

            randX += 10;
            randY += 10 * randomizedDirection;
            groundVertices[2 * i] = randX;
            groundVertices[2 * i + 1] = randY;

            rollForCoinSpawn(random, randX, randY);

            rollForMonsterSpawn(random, randX, randY);
        }
        ChainShape floorEdge = new ChainShape();
        floorEdge.createChain(groundVertices);
        //EdgeShape poly = new EdgeShape();
        //poly.set(new Vector2(0, 0), new Vector2(x2 - x1, y2 - y1));

        BodyDef def = new BodyDef();
        def.type = type;
        Body body = game.world.createBody(def);
        body.createFixture(floorEdge, density);
        body.setTransform(x1, y1, 0);
        // poly.dispose();
        floorEdge.dispose();

        return body;
    }

    private void rollForMonsterSpawn(Random random, float randX, float randY) {
        final int randomChanceOfCoinSpawn = 10;
        int coinSpawnRoll;
        int heightRoll;
        coinSpawnRoll = random.nextInt(100);
        if (coinSpawnRoll < randomChanceOfCoinSpawn) {
            heightRoll = random.nextInt(30);
            Monster currentMonster = new Monster(game, randX, randY + 15 + heightRoll, monsterID);
            monsterID++;
            monsterList.add(currentMonster);
        }
    }

    private void rollForCoinSpawn(Random random, float randX, float randY) {
        final int randomChanceOfCoinSpawn = 60;
        int coinSpawnRoll;
        int heightRoll;
        coinSpawnRoll = random.nextInt(100);
        if (coinSpawnRoll < randomChanceOfCoinSpawn) {
            heightRoll = random.nextInt(12);
            Collectible currentCoin = new Collectible(game, randX, randY + 8 + heightRoll, "Coin.png");
            coinsList.add(currentCoin);
        }
    }

}
