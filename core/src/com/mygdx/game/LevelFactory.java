package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevelFactory {

    private MyGame game;
    Level level = new Level();

    List<Collectible> coinsList = new ArrayList<>();

    public LevelFactory(MyGame game) {
        this.game = game;
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            float xPos = random.nextInt(150);
            float yPos = random.nextInt(10);
            Collectible coin = new Collectible(game, xPos, yPos, "Coin.png");
            coinsList.add(coin);
        }
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

    public Body createFloor(BodyDef.BodyType type, float x1, float y1, float x2, float y2, float density) {
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

}
