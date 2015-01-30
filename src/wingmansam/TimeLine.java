package wingmansam;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author Sonja
 */
public class TimeLine implements ActionListener{
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        WingManSam.time += 1;
        int timer = 0;

        if (WingManSam.gameStart){
        for (int i = 0; i < WingManSam.enemies.size(); i++) {
            if (WingManSam.enemies.get(i).create == false) {
                WingManSam.enemies.get(i).create = true;
                WingManSam.enemies.get(i).show = true;
                break;
            }
        }
        for (int i = 0; i < WingManSam.enemies.size(); i++) {
                WingManSam.enemies.get(i).shoot();
        }
        
        if (WingManSam.time == 30){
            WingManSam.finalBattle = true;
            WingManSam.boss.show = true;
            WingManSam.boss.create = true;
        }
        
          //show a powerup every 10 seconds
            if (((WingManSam.time == 10) || WingManSam.time % 10 == 0)) {
                WingManSam.PowerUp.show = true;
            }
            //after 7s of activation, powerup time is up
            if (WingManSam.m.activePU || WingManSam.m2.activePU) {
                timer++;
                if (timer == 7) {
                    WingManSam.m.activePU = false;
                    WingManSam.m2.activePU = false;
                    timer = 0;
                }
            }
        }
    }
}
