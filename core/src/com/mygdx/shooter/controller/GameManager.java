package com.mygdx.shooter.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.shooter.model.LaserEntity;
import com.mygdx.shooter.model.MainEntity;
import com.mygdx.shooter.model.MeteorEntity;
import com.mygdx.shooter.model.ShipEntity;

import java.util.concurrent.CopyOnWriteArrayList;

public class GameManager {

    SoundManager sound;
    World world;
    ShipEntity ship;
    CopyOnWriteArrayList<MeteorEntity> meteors;
    CopyOnWriteArrayList<LaserEntity> lasers;
    final int maxMeteors = 7;
    BodyEditorLoader loader;
    int score;
    Array<Body> bodies;
    final float PTM = 100f;
    boolean debug = false;

    public GameManager(World world) {

        this.world = world;
        this.sound = new SoundManager();

        bodies = new Array<>();

        loader = new BodyEditorLoader(Gdx.files.internal("assets.json"));

        ship = new ShipEntity(loader, world, PTM, sound);
        meteors = new CopyOnWriteArrayList<>();
        for (int i = 0; i < maxMeteors; i++) {
            meteors.add(new MeteorEntity(loader, world, PTM));
        }
        lasers = new CopyOnWriteArrayList<>();
    }

    public void calculate() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            ship.applyForceCenter();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            ship.applyForceLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            ship.applyForceRight();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            lasers.add(ship.shoot(world));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            debug = !debug;
        }

        world.getBodies(bodies);
        for (Body body : bodies) {
            if (!((MainEntity) body.getUserData()).isAlive()) {
                world.destroyBody(body);
            }
        }

        for (MeteorEntity meteor : meteors) {
            if (!meteor.isAlive()) {
                meteors.remove(meteor);
                score++;
            }
        }

        if (meteors.size() < maxMeteors) {
            meteors.add(new MeteorEntity(loader, world, PTM));
        }

        for (LaserEntity laser : lasers) {
            if (!laser.isAlive()) {
                lasers.remove(laser);
            }
        }
    }

    public void draw(SpriteBatch batch) {
        if (ship.isAlive()) {
            ship.render(batch);
        }
        for (MeteorEntity meteor : meteors) {
            if (meteor.isAlive()) {
                meteor.render(batch);
            }
        }
        for (LaserEntity laser : lasers) {
            if (laser.isAlive()) {
                laser.render(batch);
            }
        }
    }

    public void dispose() {
        ship.dispose();
        for (MeteorEntity meteor : meteors) {
            meteor.dispose();
        }
        for (LaserEntity laser : lasers) {
            laser.dispose();
        }
    }

    public boolean isRun() {
        return ship.isRun();
    }

    public boolean isDebug() {
        return debug;
    }

    public float getPTM() {
        return PTM;
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return ship.getLives();
    }
}
