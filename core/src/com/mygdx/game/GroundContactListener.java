package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public class GroundContactListener implements ContactListener {

    Body playerBody;
    Body groundBody;
    MyGame game;
    Array<Body> bodiesToBeRemoved = new Array<>();

    public boolean playerOnGround = false;

    public GroundContactListener(MyGame game, Body playerBody, Body groundBody) {
        this.playerBody = playerBody;
        this.groundBody = groundBody;
        this.game = game;
    }

    @Override
    public void beginContact(Contact contact) {


        final Fixture fixtureA = contact.getFixtureA();
        final Fixture fixtureB = contact.getFixtureB();

        if ((fixtureA.getBody() == groundBody &&
                fixtureB.getBody() == playerBody)
                ||
                (fixtureA.getBody() == playerBody &&
                        fixtureB.getBody() == groundBody)) {
            playerOnGround = true;
        }
        System.out.println(contact.getFixtureA().getUserData()
                + " --- " + contact.getFixtureA().getBody().toString()
                + " --- " + contact.getFixtureB().getUserData()
                + " --- " + contact.getFixtureB().getBody().toString());

        if (fixtureA.getUserData() != null) {
            if (fixtureB.getBody() == playerBody && fixtureA.getUserData().equals("coin")) {
                System.out.println("COLLISION DETECTED!!");
                bodiesToBeRemoved.add(fixtureA.getBody());
            }

        }
        if (fixtureB.getUserData() != null) {
            if (fixtureA.getBody() == playerBody && fixtureB.getUserData().equals("coin")) {
                System.out.println("COLLISION DETECTED!!");
                bodiesToBeRemoved.add(fixtureB.getBody());
            }
        }
    }


    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
