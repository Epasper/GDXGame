package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;

public class ShapeFactory {

    private MyGame game;
    CircleShape circle = new CircleShape();

    public ShapeFactory(MyGame game) {
        this.game = game;
    }

    public Body createBox(BodyDef.BodyType type, float x, float y, float width, float height, float density) {
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(width, height);

        BodyDef def = new BodyDef();
        def.type = type;
        Body body = game.world.createBody(def);
        body.createFixture(poly, density);
        body.setTransform(x, y, 0);
        poly.dispose();

        return body;
    }

    public Body createCircle(BodyDef.BodyType type, float x, float y, float radius, float density) {
        circle.setRadius(radius);

        BodyDef def = new BodyDef();
        def.type = type;
        Body body = game.world.createBody(def);
        body.createFixture(circle, density);
        body.setTransform(x, y, 0);
        //circle.dispose();

        return body;
    }
}
