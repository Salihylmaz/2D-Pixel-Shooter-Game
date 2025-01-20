import java.awt.*;
import java.lang.Math;


public class Enemy extends Rectangle {
    Color color;
    boolean isActive;

    Enemy(int x,int y,int width, int height,Color color){
        this.x=x;
        this.y=y;
        this.width = width;
        this.height = height;
        this.color= color;
        this.isActive = true;

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
