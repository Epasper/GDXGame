package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class PlayerCharacter {

    private Coordinates coordinates = new Coordinates();

    private boolean jump;

    Body body;

    MyGame game;

    Sprite playerSprite = new Sprite(new Texture("Mario.png"));

    PlayerCharacter(MyGame game) {
        coordinates.setxPosition(100);
        coordinates.setyPosition(200);
        this.game = game;
        //body = game.world.createBody(bodyDef);
        body = createBox(BodyDef.BodyType.DynamicBody, 0, 100, 2, 2, 5);
    }

    private Body createBox(BodyDef.BodyType type, float x, float y, float width, float height, float density) {
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

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public boolean isJump() {
        return jump;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }


}
