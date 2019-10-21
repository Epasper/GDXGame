package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public class Contacts implements ContactListener {

    Body playerBody;
    MyGame game;
    PlayerCharacter playerCharacter;

    Array<Body> bodiesToBeRemoved = new Array<>();
    Array<Body> projectileBodiesToBeRemoved = new Array<>();

    public boolean playerOnGround = false;

    public Contacts(MyGame game, Body groundBody) {
        this.game = game;
        this.playerCharacter = game.playerCharacter;
        this.playerBody = playerCharacter.body;

    }

    @Override
    public void beginContact(Contact contact) {

        //System.out.println("Player on ground: " + playerOnGround);

        final Fixture fixtureA = contact.getFixtureA();
        final Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() != null && fixtureB.getUserData() != null) {
            System.out.println("FixA: " + fixtureA.getUserData().toString() +
                    "fixB: " + fixtureB.getUserData().toString());
            playerMonsterCollisionCheck(fixtureA, fixtureB);

            playerGroundCollisionCheck(fixtureA, fixtureB);

            playerCoinCollisionCheck(fixtureA, fixtureB);

            projectileMonsterCollision(fixtureA, fixtureB);

            projectileDestruction(fixtureA, fixtureB);
        }
    }

    private void projectileDestruction(Fixture fixtureA, Fixture fixtureB) {
        if (fixtureB.getUserData().toString().contains("projectile")) {
            System.out.println("Projectile destruction");
            projectileBodiesToBeRemoved.add(fixtureB.getBody());
            System.out.println("List Length: " + projectileBodiesToBeRemoved.size);
        }

        if (fixtureA.getUserData().toString().contains("projectile")) {
            System.out.println("Projectile destruction");
            projectileBodiesToBeRemoved.add(fixtureA.getBody());
            System.out.println("List Length: " + projectileBodiesToBeRemoved.size);
        }
    }

    private void projectileMonsterCollision(Fixture fixtureA, Fixture fixtureB) {
        if (fixtureB.getUserData().toString().contains("projectile") &&
                fixtureA.getUserData().toString().contains("monster")) {
            System.out.println("Monster hit!");
            bodiesToBeRemoved.add(fixtureA.getBody());
        }

        if (fixtureA.getUserData().toString().contains("projectile") &&
                fixtureB.getUserData().toString().contains("monster")) {
            System.out.println("Monster hit!");
            bodiesToBeRemoved.add(fixtureB.getBody());
        }
    }

    private void playerCoinCollisionCheck(Fixture fixtureA, Fixture fixtureB) {
        if (fixtureA.getUserData() != null) {
            if (fixtureB.getBody() == playerBody && fixtureA.getUserData().equals("coin")) {
                System.out.println("COLLISION DETECTED!!");
                //System.out.println(fixtureA.isSensor());
                //System.out.println(fixtureB.isSensor());
                bodiesToBeRemoved.add(fixtureA.getBody());
            }

        }
        if (fixtureB.getUserData() != null) {
            if (fixtureA.getBody() == playerBody && fixtureB.getUserData().equals("coin")) {
                System.out.println("COLLISION DETECTED!!");
                //System.out.println(fixtureA.isSensor());
                //System.out.println(fixtureB.isSensor());
                bodiesToBeRemoved.add(fixtureB.getBody());
            }
        }
    }

    private void playerGroundCollisionCheck(Fixture fixtureA, Fixture fixtureB) {
        if ((fixtureA.getUserData().toString().contains("ground") &&
                fixtureB.getBody() == playerBody)
                ||
                (fixtureA.getBody() == playerBody &&
                        fixtureB.getUserData().toString().contains("ground"))) {
            playerOnGround = true;
        }
        /*System.out.println(contact.getFixtureA().getUserData()
                + " --- " + contact.getFixtureA().getBody().toString()
                + " --- " + contact.getFixtureB().getUserData()
                + " --- " + contact.getFixtureA().getFilterData().maskBits
                + " --- " + contact.getFixtureB().getFilterData().maskBits
                + " --- " + contact.getFixtureB().getBody().toString());*/
    }

    private void playerMonsterCollisionCheck(Fixture fixtureA, Fixture fixtureB) {
        if ((fixtureA.getUserData().toString().contains("monster") &&
                fixtureB.getBody() == playerBody)
                ||
                (fixtureA.getBody() == playerBody &&
                        fixtureB.getUserData().toString().contains("monster"))) {
/*            System.out.println("USER DATA FROM MONSTER:"+fixtureB.getBody().getUserData().toString().substring(10));
            System.out.println("USER DATA FROM MONSTER:"+fixtureA.getBody().getUserData().toString().substring(10));*/
            int monsterID = 0;
            monsterID = Integer.parseInt(fixtureB.getUserData().toString().substring(10));
            if (monsterID < 10) {
                monsterID = Integer.parseInt(fixtureA.getUserData().toString().substring(10));
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
