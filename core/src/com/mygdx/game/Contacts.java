package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public class Contacts implements ContactListener {

    Body playerBody;
    Body groundBody;
    MyGame game;
    PlayerCharacter playerCharacter;
    Array<Monster> allMonsters;
    Array<Body> bodiesToBeRemoved = new Array<>();

    public boolean playerOnGround = false;

    public Contacts(MyGame game, Body groundBody) {
        this.groundBody = groundBody;
        this.game = game;
        this.playerCharacter = game.playerCharacter;
        this.playerBody = playerCharacter.body;
        this.allMonsters = game.levelFactory.monsterList;
    }

    @Override
    public void beginContact(Contact contact) {


        final Fixture fixtureA = contact.getFixtureA();
        final Fixture fixtureB = contact.getFixtureB();

        if ((fixtureA.getBody().getUserData().toString().contains("monster") &&
                fixtureB.getBody() == playerBody)
                ||
                (fixtureA.getBody() == playerBody &&
                        fixtureB.getBody().getUserData().toString().contains("monster"))) {
/*            System.out.println("USER DATA FROM MONSTER:"+fixtureB.getBody().getUserData().toString().substring(10));
            System.out.println("USER DATA FROM MONSTER:"+fixtureA.getBody().getUserData().toString().substring(10));*/
            int monsterID = 0;
            monsterID = Integer.parseInt(fixtureB.getBody().getUserData().toString().substring(10));
            if (monsterID < 10) {
                monsterID = Integer.parseInt(fixtureA.getBody().getUserData().toString().substring(10));
            }
            Monster currentMonster = null;
            System.out.println("Monster id before checking: " + monsterID);
            for (Monster monster : game.levelFactory.monsterList) {
                System.out.println("Current monster ID: " + monster.monsterID);
                if (monster.monsterID == monsterID) {
                    currentMonster = monster;
                }
            }
            playerCharacter.hitPoints--;
            System.out.println("Check if the monster was found:" + currentMonster);
            try {
                currentMonster.body.applyForceToCenter(-10 * currentMonster.body.getLinearVelocity().x,
                        200f,
                        true);
                currentMonster.stopTheMovement = true;
            } catch (NullPointerException ignored) {
            }
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
