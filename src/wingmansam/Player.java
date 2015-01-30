package wingmansam;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Observable;
import java.util.Observer;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import static wingmansam.WingManSam.g2;

public class Player implements Observer {

    Image img;
    int x, y, speed, width, height, playerNum, counter, counter2;
    Rectangle bbox;
    
    
    boolean boom, activePU;

    static boolean shoot = false;

    Player(Image img, int x, int y, int speed, int playerNumber) {

        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = speed;
        counter = 0;
        counter2 = 0;
        width = img.getWidth(null);
        height = img.getHeight(null);
        boom = false;
        this.playerNum = playerNumber;
    }

    public void draw(ImageObserver obs) {
        //if player is dead, draw an explosion before game over
        if (boom) {
            this.img = WingManSam.bigEXPLOSION.get(counter2);
            WingManSam.g2.drawImage(this.img, x, y, obs);
            counter2++;
            //reset animation to start
            if (counter2 == WingManSam.bigEXPLOSION.size()) {
                counter2 = 0;
                boom = false;
                WingManSam.GameOver = true;
            }
        }     
        g2.drawImage(img, x, y, obs);
    }

    public boolean collision(int x, int y, int w, int h) {
        bbox = new Rectangle(this.x, this.y, this.width, this.height);
        Rectangle otherBBox = new Rectangle(x, y, w, h);
        if (this.bbox.intersects(otherBBox)) {
            return true;
        }
        return false;
    }

    public void shoot() {
        for (int i = 0; i < 40; i++) {
            if (WingManSam.bullets.get(i).show == false) {
                WingManSam.bullets.get(i).show = true;
                WingManSam.bullets.get(i).x = this.x + 16;
                WingManSam.bullets.get(i).y = this.y + 16;
                break;
            }           
        }
    }

    public void update(Observable obj, Object arg) {
        GameEvents ge = (GameEvents) arg;
        
        //update the playerImage for draw()
        this.img = WingManSam.playerImage.get(counter);
        counter++;
        if (counter == WingManSam.playerImage.size()) {
            counter = 0;
        }

        for (int i = 0; i < WingManSam.enemyBullets.size(); i++) {
            if (WingManSam.enemyBullets.get(i).collision(x, y, width, height)
                    && WingManSam.enemyBullets.get(i).show == true) {
                WingManSam.enemyBullets.get(i).reset();
                
                WingManSam.gameEvents.setValue("Explosion");              
                WingManSam.gameEvents.setValue("");
                if(this.playerNum == 1){
                    WingManSam.HP1.update();
                    if (WingManSam.HP1.dead){
                        boom = true;
                    }
                }
                else if(this.playerNum == 2){
                    WingManSam.HP2.update();
                    if (WingManSam.HP2.dead){
                        boom = true;
                    }                    
                }
            }
            
        }

        if (ge.type == 1) {

            if (playerNum == 1) {
                KeyEvent e = (KeyEvent) ge.event;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        x -= speed;
                        break;
                    case KeyEvent.VK_RIGHT:
                        x += speed;
                        break;
                    case KeyEvent.VK_UP:
                        y -= speed;
                        break;
                    case KeyEvent.VK_DOWN:
                        y += speed;
                        break;
                    default:
                        if (e.getKeyChar() == ' ') {
                            System.out.println("Fire");
                            shoot();
                        }
                }
            } else if (playerNum == 2) {
                KeyEvent e = (KeyEvent) ge.event;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A:
                        x -= speed;
                        break;
                    case KeyEvent.VK_D:
                        x += speed;
                        break;
                    case KeyEvent.VK_W:
                        y -= speed;
                        break;
                    case KeyEvent.VK_S:
                        y += speed;
                        break;
                    case KeyEvent.VK_ENTER:
                        System.out.println("Fire");
                        shoot();
                        break;
                    default:
                }
            }
        } else if (ge.type == 2) {
            String msg = (String) ge.event;
            if (msg.equals("Explosion")) {
                System.out.println("Explosion! Reduce Health");
                

                //explosion sound
                try {
                    //play music in the backgroud
                    InputStream boom = new FileInputStream(new File("Resources/snd_explosion1.wav"));
                    AudioStream in = new AudioStream(boom);
                    AudioPlayer.player.start(in);
                } catch (Exception e) {
                }

            }
        }

    }
}
