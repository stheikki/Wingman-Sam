package wingmansam;

import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import javax.imageio.ImageIO;

public class Enemy {

    Image img, exp1, exp2, exp3, exp4, exp5, exp6;
    int x, y, sizeX, sizeY, speed, counter, counter2, deathX, deathY, eNum, xSpeed;
    int health;
    Random gen;
    boolean show, create, boom, bossScreen;

    Enemy(Image img, int speed, Random gen, int enemyNum, int health) {
        this.img = img;
        sizeX = img.getWidth(null);
        sizeY = img.getHeight(null);

        eNum = enemyNum;
        bossScreen = false;
        if (eNum == 1) {
            this.x = Math.abs(gen.nextInt() % (600 - 30));
            this.y = -20;
            xSpeed = 0;
        }
        if (eNum == 2) {
            this.x = 0 - this.sizeX;
            this.y = 100;
            xSpeed = 1;
        }

        this.deathX = 0;
        this.deathY = 0;
        this.speed = speed;
        this.gen = gen;
        this.show = false;
        this.create = false;
        this.health = health;

        counter = 0;
        counter2 = 0;
        
        System.out.println("w:" + sizeX + " y:" + sizeY);
    }

    public void shoot() {
        for (int i = 0; i < 40; i++) {
            if (WingManSam.enemyBullets.get(i).show == false) {
                WingManSam.enemyBullets.get(i).show = true;
                WingManSam.enemyBullets.get(i).x = this.x + 1;
                WingManSam.enemyBullets.get(i).y = this.y + 1;
                break;
            }
        }
    }

    public void update() {

        if (show && create && eNum == 1) {
            y += speed;
        }
        //reset enemy planes when out of bounds
        if (this.y > 490) {
            this.reset();
        }

        if (eNum == 2 && this.show) {
            if (this.x == 20) {
                bossScreen = true;
            }
            if (bossScreen == true && (x > WingManSam.w - sizeX || x == 0)) {
                xSpeed *= -1;
            }
            x += xSpeed;

            //update the boss img for draw()
            this.img = WingManSam.bossFlight.get(counter);
            counter++;
            if (counter == WingManSam.bossFlight.size()) {
                counter = 0;
            }
        } else if (eNum == 1) {
            //update the enemy img for draw()
            this.img = WingManSam.enemyFlight.get(counter);
            counter++;
            if (counter == WingManSam.enemyFlight.size()) {
                counter = 0;
            }
        }

        //check collision with player1
        if (WingManSam.m.collision(x, y, sizeX, sizeY) && this.show) {
            health--;

            if (health == 0) {
                show = false;
                deathX = x;
                deathY = y;
                show = false;
                boom = true;
                this.reset();
                show = true;
                if (eNum == 2) {
                    WingManSam.victory = true;
                }
            }
            WingManSam.gameEvents.setValue("Explosion");
            WingManSam.gameEvents.setValue("");

            //reduce player health
            WingManSam.HP1.update();
            if (WingManSam.HP1.dead) {
                WingManSam.m.boom = true;
            }

        } //check collision with player2
        else if (WingManSam.m2.collision(x, y, sizeX, sizeY) && this.show) {
            health--;

            if (health == 0) {
                show = false;
                boom = true;
                deathX = x;
                deathY = y;
                this.reset();
                show = true;
                if (eNum == 2) {
                    WingManSam.victory = true;
                }
            }
            WingManSam.gameEvents.setValue("Explosion");
            //    WingManSam.gameEvents.setValue("");
            //reduce player health
            WingManSam.HP2.update();
            if (WingManSam.HP2.dead) {
                WingManSam.m2.boom = true;
            }

        }

        //check collisions with player bullets
        for (int i = 0; i < WingManSam.bullets.size(); i++) {
            if (WingManSam.bullets.get(i).collision(x, y, sizeX, sizeY) && this.show) {
                health--;

                if (health == 0) {
                    show = false;
                    boom = true;
                    deathX = x;
                    deathY = y;
                    this.reset();
                    show = true;
                    if (eNum == 2) {
                        WingManSam.victory = true;
                    }
                }
                WingManSam.gameEvents.setValue("Explosion");
                //         WingManSam.gameEvents.setValue("");
                WingManSam.bullets.get(i).reset();

            } else {
                WingManSam.gameEvents.setValue("");
            }
        }
        
    }

    public void reset() {
        this.x = Math.abs(WingManSam.generator.nextInt() % (600 - 30));
        this.y = -10;
        this.health = 1;
        this.show = false;
        this.create = false;
    }

    public void draw(ImageObserver obs) {
        //draw the animation of an explosion at the spot of death
        if (boom) {
            this.img = WingManSam.EXPLOSION.get(counter2);
            WingManSam.g2.drawImage(this.img, deathX, deathY, obs);
            counter2++;
            //reset animation to start
            if (counter2 == WingManSam.EXPLOSION.size()) {
                counter2 = 0;
                boom = false;
            }
        }
        if (show && create) {
            WingManSam.g2.drawImage(img, x, y, obs);
        }

    }
}
