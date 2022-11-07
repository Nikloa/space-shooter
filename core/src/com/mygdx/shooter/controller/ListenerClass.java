package com.mygdx.shooter.controller;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.shooter.model.LaserEntity;
import com.mygdx.shooter.model.MeteorEntity;
import com.mygdx.shooter.model.ShipEntity;

public class ListenerClass implements ContactListener {

    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if (a.getUserData() != null && b.getUserData() != null) {
            Class<?> aClass = a.getUserData().getClass();
            Class<?> bClass = b.getUserData().getClass();

            if (aClass == ShipEntity.class && bClass == MeteorEntity.class) {
                contact.setEnabled(false);
                if (((MeteorEntity) b.getUserData()).isAlive())
                    ((ShipEntity) a.getUserData()).damage();
                ((MeteorEntity) b.getUserData()).damage();
            } else if (aClass == MeteorEntity.class && bClass == ShipEntity.class) {
                contact.setEnabled(false);
                if (((MeteorEntity) b.getUserData()).isAlive())
                    ((ShipEntity) b.getUserData()).damage();
                ((MeteorEntity) a.getUserData()).damage();
            }

            if (aClass == LaserEntity.class && bClass == MeteorEntity.class) {
                contact.setEnabled(false);
                ((LaserEntity) a.getUserData()).damage();
                ((MeteorEntity) b.getUserData()).damage();
            } else if (aClass == MeteorEntity.class && bClass == LaserEntity.class) {
                contact.setEnabled(false);
                ((MeteorEntity) a.getUserData()).damage();
                ((LaserEntity) b.getUserData()).damage();
            }

            if (aClass == ShipEntity.class && bClass == LaserEntity.class) {
                contact.setEnabled(false);
            } else if (aClass == LaserEntity.class && bClass == ShipEntity.class) {
                contact.setEnabled(false);
            }

            if (aClass == LaserEntity.class && bClass == LaserEntity.class) {
                contact.setEnabled(false);
            }
        }

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
