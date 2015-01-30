package wingmansam;

import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.Random;

public class Island {

        Image img;
        int x, y, speed;
        Random gen;

        Island(Image img, int x, int y, int speed, Random gen) {
            this.img = img;
            this.x = x;
            this.y = y;
            this.speed = speed;
            this.gen = gen;
        }

        public void update() {
            y += speed;
            if (y >= WingManSam.h) {
                y = -100;
                x = Math.abs(gen.nextInt() % (WingManSam.w - 30));
            }
        }

        public void draw(ImageObserver obs) {
            WingManSam.g2.drawImage(img, x, y, obs);
        }
    }