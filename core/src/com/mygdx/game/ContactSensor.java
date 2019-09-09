package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;

public class ContactSensor implements ContactListener {

    Body playerBody;
    Body groundBody;

    public boolean playerOnGround = false;

    public ContactSensor(Body playerBody, Body groundBody) {
        this.playerBody = playerBody;
        this.groundBody = groundBody;
    }

    @Override
    public void beginContact(Contact contact) {

        if ((contact.getFixtureA().getBody() == groundBody &&
                contact.getFixtureB().getBody() == playerBody)
                ||
                (contact.getFixtureA().getBody() == playerBody &&
                        contact.getFixtureB().getBody() == groundBody)) {
            playerOnGround = true;
        }

    }

    @Override
    public void endContact(Contact contact) {

        if ((contact.getFixtureA().getBody() == groundBody &&
                contact.getFixtureB().getBody() != playerBody)
                ||
                (contact.getFixtureA().getBody() == playerBody &&
                        contact.getFixtureB().getBody() != groundBody)) {
            playerOnGround = false;
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
