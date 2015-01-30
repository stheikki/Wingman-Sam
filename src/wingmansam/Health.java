package wingmansam;

import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Observable;
import static wingmansam.WingManSam.g2;
import static wingmansam.WingManSam.m;
import static wingmansam.WingManSam.m2;

/**
 *
 * @author Sonja
 */
public class Health {

    Image img, img1, img2, img3, spareLife;
    int  sizeX, sizeY, speed, x, y, health, spareLives, space;
    boolean dead;
    
    Health(Image img, Image img1, Image img2, Image img3, Image img4, int X, int Y) {

        this.img = img;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.spareLife = img4;
        dead = false;
        health = 4;
        spareLives = 3;
        x = X;
        y = Y;
  
        System.out.println("w:" + sizeX + " y:" + sizeY);
    }

    public void update() {
        health--;
        if (health == 0) {
            spareLives--;
            if (spareLives > 0){
            health = 4;
            } 
            else { 
                dead = true; 
            }
        }
    }

    public void draw(ImageObserver obs) {

        //draw the remaining sparelives
        for (int i=0; i<spareLives; i++){
            WingManSam.g2.drawImage(spareLife, x + spareLife.getWidth(null)*i, 400, obs);
        }
        
        //draw the remaining amount of HP left
        if (health == 4) {
            WingManSam.g2.drawImage(img, x + spareLife.getWidth(null)*3, y, obs);
        } else if (health == 3) {
            WingManSam.g2.drawImage(img1, x + spareLife.getWidth(null)*3, y, obs);
        } else if (health == 2) {
            WingManSam.g2.drawImage(img2, x + spareLife.getWidth(null)*3, y, obs);
        } else if (health == 1) {
            WingManSam.g2.drawImage(img3, x + spareLife.getWidth(null)*3, y, obs);
        }
    }
}
