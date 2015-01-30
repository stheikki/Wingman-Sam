/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wingmansam;

import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.Random;

/**
 *
 * @author Sonja
 */
public class PowerUps {

    Image img;
    int x, y, sizeX, sizeY, speed;
    boolean show;

    Random gen;

    PowerUps(Image img, int speed, Random gen) {
        this.img = img;
        this.x = Math.abs(gen.nextInt() % (600 - 30));
        this.y = -20;
        this.speed = speed;
        this.gen = gen;
        this.show = false;
        sizeX = img.getWidth(null);
        sizeY = img.getHeight(null);
    }

    public void update() {

        if (show) {
            y += speed;
        }
        //reset enemy planes when out of bounds
        if (this.y > 490) {
            this.reset();
        }

        //check collision with player1
        if (WingManSam.m.collision(x, y, sizeX, sizeY)) {
            show = false;
            WingManSam.gameEvents.setValue("PowerUp");
            WingManSam.gameEvents.setValue("");
            this.reset();
            WingManSam.m.activePU = true;

        } //check collision with player2
        else if (WingManSam.m2.collision(x, y, sizeX, sizeY)) {
            show = false;
            WingManSam.gameEvents.setValue("PowerUp");
            WingManSam.gameEvents.setValue("");
            this.reset();
            WingManSam.m2.activePU = true;
        }
    }

    public void reset() {
        this.x = Math.abs(WingManSam.generator.nextInt() % (600 - 30));
        this.y = -10;
        this.show = false;
    }

    public void draw(ImageObserver obs) {
        if (show) {
            WingManSam.g2.drawImage(img, x, y, obs);
        }
    }

}