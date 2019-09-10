package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;

public class ContactSensor implements ContactListener {

    Body playerBody;
    Body groundBody;
    MyGame game;
    boolean stopCoinDetection = false;


    public boolean playerOnGround = false;

    public ContactSensor(MyGame game, Body playerBody, Body groundBody) {
        this.playerBody = playerBody;
        this.groundBody = groundBody;
        this.game = game;
    }

    @Override
    public void beginContact(Contact contact) {

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if ((fixtureA.getBody() == groundBody &&
                fixtureB.getBody() == playerBody)
                ||
                (fixtureA.getBody() == playerBody &&
                        fixtureB.getBody() == groundBody)) {
            playerOnGround = true;
        }
        System.out.println(contact.getFixtureA().getUserData() + " --- " + contact.getFixtureB().getUserData());
/*        if (contact.getFixtureA().getUserData() != null && contact.getFixtureB().getUserData() != null) {
            if ((contact.getFixtureB().getBody() == playerBody &&
                    contact.getFixtureB().getUserData().equals("coin"))
                    ||
                    (contact.getFixtureA().getUserData().equals("coin") &&
                            contact.getFixtureB().getBody() == playerBody)) {
                System.out.println("COLLISION DETECTED!!");
            }
        }*/
        if (fixtureA.getUserData() != null) {
            if (fixtureB.getBody() == playerBody && fixtureA.getUserData().equals("coin")) {
                System.out.println("COLLISION DETECTED!!");
                fixtureA.getBody().setUserData("delete");
            }
        }
        if (fixtureB.getUserData() != null) {
            if (fixtureA.getBody() == playerBody && fixtureB.getUserData().equals("coin")) {
                System.out.println("COLLISION DETECTED!!");
                fixtureB.getBody().setUserData("delete");
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
