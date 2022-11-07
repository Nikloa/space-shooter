package com.mygdx.shooter.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

    Sound laser;
    Sound block;

    public SoundManager() {

        laser = Gdx.audio.newSound(Gdx.files.internal("sfx_laser2.ogg"));
        block = Gdx.audio.newSound(Gdx.files.internal("sfx_shieldDown.ogg"));

    }

    public void playSound(String sound) {

        switch (sound) {
            case "laser":
                laser.play(); break;
            case "block":
                block.play(); break;
        }
    }
}
