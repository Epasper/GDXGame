package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class LevelFactory {

    private MyGame game;
    Level level = new Level();

    int monsterID = 10;

    public Array<Collectible> coinsList = new Array<>();
    public Array<Monster> monsterList = new Array<>();

    public int worldLength = 150;
    float[] groundVertices = new float[2 * worldLength];
    public int xPositionSpawningOffset = -100;


    public LevelFactory(MyGame game) {
        this.game = game;

    }

    public Body createLevel(BodyDef.BodyType type, float x1, float y1, float density) {

        Random random = new Random();
        float randX = xPositionSpawningOffset;
        float randY = -50;
        int randomizedDirection = 0;
        for (int i = 0; i < worldLength; i++) {


            int previousTile = randomizedDirection;

            randomizedDirection = calculateRandomizedDirection(random, randomizedDirection);
            boolean wasThePreviousTileHigher;
            boolean wasThePreviousTileLower;
            if (previousTile > randomizedDirection) {
                wasThePreviousTileHigher = true;
                wasThePreviousTileLower = false;
            } else if (previousTile < randomizedDirection) {
                wasThePreviousTileHigher = false;
                wasThePreviousTileLower = true;
            } else {
                wasThePreviousTileHigher = false;
                wasThePreviousTileLower = false;
            }

            randX += 10;
            randY += 10 * randomizedDirection;

            groundVertices[2 * i] = randX;
            groundVertices[2 * i + 1] = randY;

            rollForFloatingIslandSpawn(random, randX, randY, wasThePreviousTileHigher, wasThePreviousTileLower);

            rollForCoinSpawn(random, randX, randY);

            rollForMonsterSpawn(random, randX, randY);
        }

        ChainShape floorEdge = new ChainShape();
        floorEdge.createChain(groundVertices);

        BodyDef def = new BodyDef();
        def.type = type;
        Body body = game.world.createBody(def);
        body.createFixture(floorEdge, density);
        body.setTransform(x1, y1, 0);

        floorEdge.dispose();

        return body;
    }

    private void rollForFloatingIslandSpawn(Random random, float randX, float randY, boolean wasThePreviousTileHigher, boolean wasThePreviousTileLower) {
        final int chanceOfFloatingIslandSpawning = 40;
        int floatingIslandSpawnRoll = random.nextInt(100);
        int islandVerticalPositionMinimum;
        int islandLengthMaximum;
        int islandLengthMinimum = 9;
        int shiftTheIslandPosition;
        if (wasThePreviousTileHigher) {
            islandVerticalPositionMinimum = 25;
            islandLengthMaximum = 10;
            shiftTheIslandPosition = 10;
        } else if (wasThePreviousTileLower) {
            islandVerticalPositionMinimum = 25;
            islandLengthMaximum = 10;
            shiftTheIslandPosition = -10;
        } else {
            islandVerticalPositionMinimum = 25;
            islandLengthMaximum = 10;
            shiftTheIslandPosition = 10;
        }
        int islandRandomPosition = random.nextInt(5) + islandVerticalPositionMinimum;
        float islandY = randY + islandRandomPosition;
        int islandLength = (random.nextInt(islandLengthMaximum - islandLengthMinimum) +
                islandLengthMinimum);
        if (floatingIslandSpawnRoll < chanceOfFloatingIslandSpawning) {
            createFloatingIsland(randX + shiftTheIslandPosition, islandY, 3, islandLength);
        }
    }

    /*pseudocode:
     * if - previous Tile Was higher than this one
     * ---spawn an island higher and shorter
     * else if - previous tile was lower
     * ---spawn an island a bit higher*/

    public Body createFloatingIsland(float x1, float y1, float height, float length) {
        float[] islandApexes = new float[]{x1, y1, x1 - length, y1, x1 - length, y1 + height, x1, y1 + height};
        BodyDef.BodyType type = BodyDef.BodyType.StaticBody;
        PolygonShape poly = new PolygonShape();
        poly.set(islandApexes);

        BodyDef def = new BodyDef();
        def.type = type;
        Body body = game.world.createBody(def);
        body.createFixture(poly, 2);
        poly.dispose();
        body.getFixtureList().get(0).setUserData("ground");

        Filter filter = new Filter();
        filter.categoryBits = CollisionCategories.CATEGORY_SCENERY;
        filter.maskBits = CollisionCategories.MASK_SCENERY;
        body.getFixtureList().get(0).setFilterData(filter);

        return body;
    }

    private int calculateRandomizedDirection(Random random, int randomizedDirection) {
        if (randomizedDirection > 0) {
            randomizedDirection = random.nextInt(2);
        } else if (randomizedDirection < 0) {
            randomizedDirection = random.nextInt(2) - 1;
        } else {
            randomizedDirection = random.nextInt(3) - 1;
        }
        return randomizedDirection;
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
