package com.samsung.project.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.samsung.project.Objects.Elevator;
import com.samsung.project.Objects.InteractiveObjects.I_Wall;
import com.samsung.project.Objects.InteractiveObjects.InteractiveObjects;
import com.samsung.project.Sprites.Enemy;
import com.samsung.project.Sprites.Player;

public class WorldContactListener implements ContactListener{

    private boolean blocked;

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() instanceof Player || fixtureB.getUserData() instanceof Player) {
            Fixture player = fixtureA.getUserData() instanceof Player ? fixtureA : fixtureB;
            Fixture object = player == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() instanceof InteractiveObjects) {
                ((InteractiveObjects) object.getUserData()).onHit();
            }

            if (object.getUserData() instanceof Elevator) {
                ((Elevator) object.getUserData()).onHit(blocked);
            }

            if (object.getUserData() == "Elevator") {
                ((Player) player.getUserData()).onElevator = true;
            }

            if (object.getUserData() instanceof Enemy) {
                ((Enemy) object.getUserData()).onHit();
            }

            if (object.getUserData() instanceof I_Wall) {
                ((Player) player.getUserData()).onI_Wall = true;
            }

            if (object.getUserData() == "Floor") {
                ((Player) player.getUserData()).onFloor = true;
            }

            if (object.getUserData() == "Wall") {
                blocked = true;
            } else {
                blocked = false;
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