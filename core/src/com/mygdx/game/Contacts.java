package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public class Contacts implements ContactListener {

    Body playerBody;
    Body groundBody;
    MyGame game;
    PlayerCharacter playerCharacter;
    Monster monster;
    Array<Body> bodiesToBeRemoved = new Array<>();

    public boolean playerOnGround = false;

    public Contacts(MyGame game, Body groundBody) {
        this.groundBody = groundBody;
        this.game = game;
        this.playerCharacter = game.playerCharacter;
        this.playerBody = playerCharacter.body;
        this.monster = game.monster;
    }

    @Override
    public void beginContact(Contact contact) {


        final Fixture fixtureA = contact.getFixtureA();
        final Fixture fixtureB = contact.getFixtureB();

        if ((fixtureA.getBody().getUserData().equals("monster") &&
                fixtureB.getBody() == playerBody)
                ||
                (fixtureA.getBody() == playerBody &&
                        fixtureB.getBody().getUserData().equals("monster"))) {
            playerCharacter.hitPoints--;
            monster.body.setLinearVelocity(0,30);
        }

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
                + " --- " + contact.getFixtureA().getFilterData().maskBits
                + " --- " + contact.getFixtureB().getFilterData().maskBits
                + " --- " + contact.getFixtureB().getBody().toString());

        if (fixtureA.getUserData() != null) {
            if (fixtureB.getBody() == playerBody && fixtureA.getUserData().equals("coin")) {
                System.out.println("COLLISION DETECTED!!");
                System.out.println(fixtureA.isSensor());
                System.out.println(fixtureB.isSensor());
                bodiesToBeRemoved.add(fixtureA.getBody());
            }

        }
        if (fixtureB.getUserData() != null) {
            if (fixtureA.getBody() == playerBody && fixtureB.getUserData().equals("coin")) {
                System.out.println("COLLISION DETECTED!!");
                System.out.println(fixtureA.isSensor());
                System.out.println(fixtureB.isSensor());
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
