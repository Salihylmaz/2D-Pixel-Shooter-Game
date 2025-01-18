import java.awt.*;
import java.util.ArrayList;

public class Controller {

        private ArrayList<Bullet> b = new ArrayList<>();

        Bullet bullet;

        public void bulletMove(){
            for(int i=0;i<b.size();i++){
                bullet = b.get(i);
                bullet.move();
                if (bullet.x < 0 ) {
                    removeBullet(bullet);
                    i--;
                }

            }

        }

        public void render(Graphics g){
            for (Bullet bullet : b) {
                bullet.draw(g);
            }
        }



        public void addBullet(Bullet block){
            b.add(block);
        }

        public void removeBullet(Bullet block){
            b.remove(block);
        }

}
