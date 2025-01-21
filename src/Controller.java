import java.awt.*;
import java.util.ArrayList;

public class Controller {

    private ArrayList<Bullet> bullet_list = new ArrayList<>();
    MyFrame myFrame;
    Bullet bullet;

    public Controller(MyFrame myFrame) {
        this.myFrame = myFrame;
    }

    public void bulletMove(){
            for(int i=0;i<bullet_list.size();i++){
                bullet = bullet_list.get(i);
                bullet.move();
                if (bullet.x < 0 || bullet.x > myFrame.getWidth() || bullet.y < 0 || bullet.y > myFrame.getHeight()) {
                    removeBullet(i);
                    i--;
                }

            }

        }

        public void drawBullet(Graphics g){
            for (Bullet bullet : bullet_list) {
                bullet.draw(g);
            }
        }

        public Bullet bulletGet(int i){
            return bullet_list.get(i);
        }

        public int bulletSize(){
            return bullet_list.size();
        }

        public void addBullet(Bullet bullet){
            bullet_list.add(bullet);
        }

        public void removeBullet(int block){
            bullet_list.remove(block);
        }

}
