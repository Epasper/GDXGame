package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public class ContactSensor implements ContactListener {

    Body playerBody;
    Body groundBody;
    MyGame game;
    Array<Body> bodiesToBeRemoved = new Array<>();

    public boolean playerOnGround = false;

    public ContactSensor(MyGame game, Body playerBody, Body groundBody) {
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
                //fixtureA.getBody().setUserData("delete");
                bodiesToBeRemoved.add(fixtureA.getBody());
            }

        }
        if (fixtureB.getUserData() != null) {
            if (fixtureA.getBody() == playerBody && fixtureB.getUserData().equals("coin")) {
                System.out.println("COLLISION DETECTED!!");
                //fixtureB.getBody().setUserData("delete");
                bodiesToBeRemoved.add(fixtureB.getBody());
                /*Array<Body> bodies = new Array<>(game.world.getBodyCount());
                game.world.getBodies(bodies);
                for (Body body : bodies) {
                    if (body.getUserData() != null && body.getUserData().equals("destroy")) {
                        game.world.destroyBody (body);
                    }
                }*/
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
