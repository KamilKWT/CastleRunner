package com.samsung.project.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.samsung.project.Objects.InteractiveObjects.InteractiveObjects;
import com.samsung.project.Sprites.Player;


public class WorldContactListener implements ContactListener{

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() == "Player" || fixtureB.getUserData() == "Player") {
            Fixture player = fixtureA.getUserData() == "Player" ? fixtureA : fixtureB;
            Fixture object = player == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() instanceof InteractiveObjects) {
                ((InteractiveObjects) object.getUserData()).onHit();
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