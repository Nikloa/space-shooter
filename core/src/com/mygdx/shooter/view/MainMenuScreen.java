package com.mygdx.shooter.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Align;
import com.mygdx.shooter.SpaceShooter;

public class MainMenuScreen implements Screen {

    final SpaceShooter game;
    OrthographicCamera camera;
    GlyphLayout layoutBig;
    GlyphLayout layoutSmall;
    TiledMap background;
    TiledMapRenderer tiledMapRenderer;
    final float width;
    final float height;

    final SpriteBatch batch;
    BitmapFont font;

    public MainMenuScreen(final SpaceShooter shooter) {
        game = shooter;
        batch = game.getBatch();
        font = game.getFont();
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);
        camera.update();

        background = new TmxMapLoader().load("background.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(background, Math.max(width, height) / 1024);

        layoutBig = new GlyphLayout();
        layoutSmall = new GlyphLayout();
        layoutBig.setText(font, "Welcome to SpaceShooter!!!", Color.WHITE, width, Align.center,true);
        layoutSmall.setText(font, "Tap anywhere to begin!", Color.WHITE, width, Align.center,true);

      }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        font.draw(batch, layoutBig, 0, height * 0.6f);
        font.draw(batch, layoutSmall, 0, height * 0.4f);
        batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }


    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
