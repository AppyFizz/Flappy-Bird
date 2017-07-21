package com.cool.shreyan;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class FlappyBird extends ApplicationAdapter {

    private final float GRAVITY = 1.5f;
    private final float TOUCH_VELOCITY = -25f;

    private enum GameState {
        NOT_BEGUN, PLAYING
    }


    private SpriteBatch batch;
    private Texture background;

    private TextureAtlas atlas;
    private Animation<TextureRegion> birdAnimation;
    private TextureRegion bird;

    private float elapsedTime;
    private float birdY;
    private float velocity;

    private GameState state;


    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("bg.png");

        atlas = new TextureAtlas("bird.atlas");
        birdAnimation = new Animation<TextureRegion>(1 / 7f, atlas.getRegions(), Animation.PlayMode.LOOP);

        elapsedTime = 0;
        bird = birdAnimation.getKeyFrame(elapsedTime);
        birdY = (Gdx.graphics.getHeight() - bird.getRegionHeight()) / 2;

        state = GameState.NOT_BEGUN;
    }


    @Override
    public void render() {
        if (state == GameState.PLAYING) {
            velocity += GRAVITY;
            birdY -= velocity;
            if (Gdx.input.justTouched()) {
                velocity = TOUCH_VELOCITY;
            }
        } else if (Gdx.input.justTouched()) {
            state = GameState.PLAYING;
        }

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        elapsedTime += Gdx.graphics.getDeltaTime();
        bird = birdAnimation.getKeyFrame(elapsedTime);
        batch.draw(bird, (Gdx.graphics.getWidth() - bird.getRegionWidth()) / 2, birdY, bird.getRegionWidth(), bird.getRegionHeight());
        batch.end();
    }


    @Override
    public void dispose() {
        batch.dispose();
        atlas.dispose();
    }
}
