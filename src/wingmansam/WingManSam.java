package wingmansam;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.net.URL;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Ilmi
 *
 *
 */
public class WingManSam extends JApplet implements Runnable {

    private Thread thread;
    Image sea;
    Image myPlane;
    private BufferedImage bimg;
    static Graphics2D g2;
    int speed = 1, move = 0;
    static Random generator = new Random(1234567);
    Island I1, I2, I3;
    static Player m, m2;
    static int w = 640, h = 480; // fixed size window game 
    static Enemy boss;
    static GameEvents gameEvents, gameEvents2;
    static Health HP1, HP2;
    static Weapon Bullet;
    static ArrayList<Weapon> bullets;
    static ArrayList<Weapon> enemyBullets;
    static ArrayList<Enemy> enemies;
    static ArrayList<Image> bossFlight;
    static ArrayList<Image> EXPLOSION;
    static ArrayList<Image> bigEXPLOSION;
    static ArrayList<Image> enemyFlight;
    static ArrayList<Image> playerImage;
    Timer clock;
    static int time;

    static boolean finalBattle = false;
    static boolean gameStart = false;
    static boolean GameOver = false;
    static boolean victory = false;
    Image loading, youLose, win, powerUpImg;;
    AudioStream in;
    static PowerUps PowerUp;

    public void init() {

        setBackground(Color.white);
        Image island1, island2, island3, enemyImg, finalBoss;

        try {
            //sea = getSprite("Resources/water.png");
            clock = new Timer(1000, new TimeLine());
            time = 0;

            //play music in the backgroud
            InputStream music = new FileInputStream(new File("Resources/background.mid"));
            in = new AudioStream(music);
            AudioPlayer.player.start(in);

            Image health, health1, health2, health3, life, bullet;
            health = ImageIO.read(new File("Resources/health.png"));
            health1 = ImageIO.read(new File("Resources/health1.png"));
            health2 = ImageIO.read(new File("Resources/health2.png"));
            health3 = ImageIO.read(new File("Resources/health3.png"));
            life = ImageIO.read(new File("Resources/life.png"));
            bullet = ImageIO.read(new File("Resources/enemybullet2.png"));
            finalBoss = ImageIO.read(new File("Resources/boss.png"));
            loading = ImageIO.read(new File("Resources/loading.gif"));
            youLose = ImageIO.read(new File("Resources/gameOver.png"));
            win = ImageIO.read(new File("Resources/youWin.png"));
            powerUpImg = ImageIO.read(new File("Resources/powerup.png"));

            bullets = new ArrayList<Weapon>();
            enemies = new ArrayList<Enemy>();
            enemyBullets = new ArrayList<Weapon>();
            EXPLOSION = new ArrayList<Image>();
            bigEXPLOSION = new ArrayList<Image>();
            enemyFlight = new ArrayList<Image>();
            playerImage = new ArrayList<Image>();
            bossFlight = new ArrayList<Image>();

            sea = ImageIO.read(new File("Resources/water.png"));
            island1 = ImageIO.read(new File("Resources/island1.png"));
            island2 = ImageIO.read(new File("Resources/island2.png"));
            island3 = ImageIO.read(new File("Resources/island3.png"));
            myPlane = ImageIO.read(new File("Resources/myplane_1.png"));
            enemyImg = ImageIO.read(new File("Resources/enemy1_1.png"));

            HP1 = new Health(health, health1, health2, health3, life, 410, 400);
            HP2 = new Health(health, health1, health2, health3, life, 5, 400);
            I1 = new Island(island1, 100, 100, speed, generator);
            I2 = new Island(island2, 200, 400, speed, generator);
            I3 = new Island(island3, 300, 200, speed, generator);
            m = new Player(myPlane, 405, 330, 5, 1);
            m2 = new Player(myPlane, 170, 330, 5, 2);
            boss = new Enemy(finalBoss, 1, generator, 2, 20);
            PowerUp = new PowerUps(powerUpImg, 2, generator);

            for (int i = 0; i < 6; i++) {
                enemies.add(new Enemy(enemyImg, 2, generator, 1, 1));
            }
            for (int i = 0; i < 40; i++) {
                bullets.add(new Weapon(bullet, -5));
            }
            for (int i = 0; i < 40; i++) {
                enemyBullets.add(new Weapon(bullet, 5));
            }

            //add pictures in array lists for animations
            playerImage.add(ImageIO.read(new File("Resources/myplane_1.png")));
            playerImage.add(ImageIO.read(new File("Resources/myplane_2.png")));
            playerImage.add(ImageIO.read(new File("Resources/myplane_3.png")));
            EXPLOSION.add(ImageIO.read(new File("Resources/explosion1_1.png")));
            EXPLOSION.add(ImageIO.read(new File("Resources/explosion1_2.png")));
            EXPLOSION.add(ImageIO.read(new File("Resources/explosion1_3.png")));
            EXPLOSION.add(ImageIO.read(new File("Resources/explosion1_4.png")));
            EXPLOSION.add(ImageIO.read(new File("Resources/explosion1_5.png")));
            EXPLOSION.add(ImageIO.read(new File("Resources/explosion1_6.png")));
            bigEXPLOSION.add(ImageIO.read(new File("Resources/explosion2_1.png")));
            bigEXPLOSION.add(ImageIO.read(new File("Resources/explosion2_2.png")));
            bigEXPLOSION.add(ImageIO.read(new File("Resources/explosion2_3.png")));
            bigEXPLOSION.add(ImageIO.read(new File("Resources/explosion2_4.png")));
            bigEXPLOSION.add(ImageIO.read(new File("Resources/explosion2_5.png")));
            bigEXPLOSION.add(ImageIO.read(new File("Resources/explosion2_6.png")));
            bigEXPLOSION.add(ImageIO.read(new File("Resources/explosion2_7.png")));
            enemyFlight.add(ImageIO.read(new File("Resources/enemy1_1.png")));
            enemyFlight.add(ImageIO.read(new File("Resources/enemy1_2.png")));
            enemyFlight.add(ImageIO.read(new File("Resources/enemy1_3.png")));
            bossFlight.add(ImageIO.read(new File("Resources/boss.png")));
            bossFlight.add(ImageIO.read(new File("Resources/boss.png")));

            gameEvents = new GameEvents();
            gameEvents.addObserver(m);
            gameEvents.addObserver(m2);

            //start counting time
            clock.start();

            KeyControl key = new KeyControl();
            addKeyListener(key);
        } catch (Exception e) {
            System.out.print("No resources are found");
        }
    }

    public class KeyControl extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            gameEvents.setValue(e);
        }
    }

    public void drawBackGroundWithTileImage() {
        int TileWidth = sea.getWidth(this);
        int TileHeight = sea.getHeight(this);

        int NumberX = (int) (w / TileWidth);
        int NumberY = (int) (h / TileHeight);

        for (int i = -1; i <= NumberY; i++) {
            for (int j = 0; j <= NumberX; j++) {
                g2.drawImage(sea, j * TileWidth,
                        i * TileHeight + (move % TileHeight), TileWidth,
                        TileHeight, this);
            }
        }
        move += speed;
    }

    public void drawDemo() {
        drawBackGroundWithTileImage();

        I1.update();
        I2.update();
        I3.update();

        I1.draw(this);
        I2.draw(this);
        I3.draw(this);

        if (!gameStart && !GameOver && !victory) {
            g2.drawImage(loading, w / 2 - youLose.getWidth(null) / 2,
                    h / 2 - loading.getHeight(null) / 2, null);
        }
        if (GameOver) {
            g2.drawImage(youLose, w / 2 - youLose.getWidth(null) / 2,
                    h / 2 - youLose.getHeight(null) / 2, null);
            gameStart = false;
            AudioPlayer.player.stop(in);
        }

        if (victory) {
            g2.drawImage(win, w / 2 - win.getWidth(null) / 2,
                    h / 2 - win.getHeight(null) / 2, null);
            gameStart = false;
            AudioPlayer.player.stop(in);
        }

        if ((!gameStart) && (gameEvents.type == 1)) {
            KeyEvent e = (KeyEvent) gameEvents.event;
            if (e.getKeyChar() == ' ') {
                gameStart = true;
                time = 0;
            }
        }

        if (gameStart && !GameOver) {

            for (int i = 0; i < bullets.size(); i++) {
                bullets.get(i).update();
            }

            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).update();
            }

            for (int i = 0; i < enemyBullets.size(); i++) {
                enemyBullets.get(i).update();
            }

            m.draw(this);
            m2.draw(this);
            
            PowerUp.update();
            PowerUp.draw(this);

            for (int i = 0; i < enemies.size(); i++) {

                enemies.get(i).draw(this);
            }

            for (int i = 0; i < bullets.size(); i++) {
                bullets.get(i).draw(this);
            }
            for (int i = 0; i < enemyBullets.size(); i++) {
                enemyBullets.get(i).draw(this);
            }

            if (finalBattle) {
                boss.update();
                boss.draw(this);
            }

            HP1.draw(this);
            HP2.draw(this);
        }

    }

    public void paint(Graphics g) {
        if (bimg == null) {
            Dimension windowSize = getSize();
            bimg = (BufferedImage) createImage(windowSize.width,
                    windowSize.height);
            g2 = bimg.createGraphics();
        }
        drawDemo();
        g.drawImage(bimg, 0, 0, this);
    }

    public void start() {
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        setFocusable(true);
        thread.start();
    }

    public void run() {

        Thread me = Thread.currentThread();
        while (thread == me) {
            repaint();
            try {
                thread.sleep(25);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public static void main(String argv[]) {
        final WingManSam demo = new WingManSam();
        demo.init();
        JFrame f = new JFrame("Wingman Sam");
        f.addWindowListener(new WindowAdapter() {
        });
        f.getContentPane().add("Center", demo);
        f.pack();
        f.setSize(new Dimension(640, 480));
        f.setVisible(true);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        demo.start();
    }
}
