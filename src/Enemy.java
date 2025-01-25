import java.awt.*;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Random;


public class Enemy extends Rectangle {
    Color color;
    boolean isActive;
    MyFrame myFrame;
    ArrayList<Enemy> enemies;
    Random rand = new Random();

    Enemy(int x,int y,int width, int height,Color color){
        this.x=x;
        this.y=y;
        this.width = width;
        this.height = height;
        this.color= color;
        this.isActive = true;

    }

    public void spawn(){
        int rand_x = rand.nextInt(450);
        int rand_x_negative = rand.nextInt(0);//negatif x olustur ikinci bir add kısmına koy
        int rand_y = rand.nextInt(450);
        if (rand_x < 0 || rand_x > myFrame.getWidth() || rand_y < 0 || rand_y > myFrame.getHeight()) {

            enemies.add(new Enemy(rand_x,rand_y,30,30,Color.RED));

        }
    }

    public void draw(Graphics g){
        g.setColor(this.color);
        g.fillRect(this.x, this.y, this.width, this.height);
    }

    public void moveToPlayer(Box player){
        int distanceX = Math.abs(this.x - player.x);
        int distanceY = Math.abs(this.y - player.y);
        
        if (distanceX > distanceY) {
            if (this.x < player.x) {
                this.x++;
            } else if (this.x > player.x) {
                this.x--;
            }
        } else {
            if (this.y < player.y) {
                this.y++; // Aşağı doğru hareket
            } else if (this.y > player.y) {
                this.y--; // Yukarı doğru hareket
            }
        }

    }

}
