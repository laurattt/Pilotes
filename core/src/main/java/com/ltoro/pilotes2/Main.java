package com.ltoro.pilotes2;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    public FitViewport viewport;
    Texture pilotaTexture;

    float posx, posy, velx, vely;

    @Override
    public void create() {
        batch = new SpriteBatch();
        viewport = new FitViewport(8, 5); // 8x5 unitats (amplada x alçada)

        // Creem la Texture de la pilota (format en pixels! , de moment)
        Pixmap pilotaPixmap = new Pixmap(100,100,Pixmap.Format.RGBA8888);
        pilotaPixmap.setColor(Color.RED);
        pilotaPixmap.fillCircle(50,50,50); // posx, posy, radi
        pilotaTexture = new Texture(pilotaPixmap);

        // Moviment
        posx = posy = 0f;
        velx = vely = 3f;
    }


    @Override
    public void render() {
        // pintem background de color
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        // PUNT CLAU: ajustem càmera per traduir resolucions quan pintem
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        // Calculem
        float width = viewport.getWorldWidth();
        float height = viewport.getWorldHeight();

        float delta = Gdx.graphics.getDeltaTime();
        posx = posx + velx * delta;
        posy = posy + vely * delta;

        // rebotes
        float size = 1f; //pelota medida rebota para que salga ok

        if (posx + size >= width || posx <= 0) {
            velx = velx * -1; // la velocidad maneja el rebote
        }

        if (posy + size >= height || posy <= 0) {
            vely = vely * -1;
        }

        // pintem!
        batch.begin();
        // ...instruccions de dibuix amb "coordenades virtuals"...
        //batch.draw(pilotaTexture,2f,2f,1f,1f);
        batch.draw(pilotaTexture,posx,posy,1f,1f);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
