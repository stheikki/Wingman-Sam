/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wingmansam;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.Random;
import static wingmansam.WingManSam.m;


/**
 *
 * @author Sonja
 */
public class Weapon {
    
        Image img;
        int x, y, speed,sizeX, sizeY, width, height;
        boolean show = true;
        Rectangle bbox;

        Weapon(Image img, int speed) {
            width = img.getWidth(null);
            height = img.getHeight(null);
            this.img = img;
            this.x = m.x + (width/2);
            this.y = m.y + (height/2);
            this.speed = speed; 
            this.show = false;
        }
        
        public void update() {     
           if (show == true){
                y += speed;
           }
           //reset bullet when it exists screen
           if ((this.y < 0) || (this.y > 480)){
               this.reset();
           }
            else 
                WingManSam.gameEvents.setValue("");
        }
        
        public boolean collision(int x, int y, int w, int h) {
            bbox = new Rectangle(this.x, this.y, this.width, this.height);
            Rectangle otherBBox = new Rectangle (x,y,w,h);
            if(this.bbox.intersects(otherBBox)) { 
                return true;
            }
            return false;
        }
       
        public void reset() {                           
            this.show = false;
            this.x = 0;
            this.y = 0;
        }

        public void draw(ImageObserver obs) {
            if (show) {
                WingManSam.g2.drawImage(img, x, y, obs);
            }
        }

    
}
    

