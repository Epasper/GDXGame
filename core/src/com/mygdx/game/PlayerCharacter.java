package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;

public class PlayerCharacter {

    private Coordinates coordinates = new Coordinates();

    private boolean jump;
    private int startingJumpHeight = 5;

    Body body;

    BodyDef bodyDef = new BodyDef();

    MyGame game;

    CircleShape circle = new CircleShape();


    PlayerCharacter(MyGame game) {
        coordinates.setxPosition(100);
        coordinates.setyPosition(200);
        this.game = game;
        body = game.world.createBody(bodyDef);
        initializePhysicalBody();
    }

    public void initializePhysicalBody() {
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(10, 30);

        circle.setRadius(6f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        Fixture fixture = body.createFixture(fixtureDef);
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
