import java.awt.*;

public class Bullet extends Rectangle {

    int dx, dy; // Hareket yönleri
    int speed = 5;

    Bullet(int x, int y, int width, int height,int dx, int dy){
        this.x = x;
        this.y= y;
        this.width = width;
        this.height = height;

        if (dx != 0 && dy != 0) {
            int length = (int) Math.sqrt(dx * dx + dy * dy);
            this.dx = dx / length; // Normalize edilmiş X yönü
            this.dy = dy / length; // Normalize edilmiş Y yönü
        } else {
            this.dx = dx;
            this.dy = dy;
        }

    }

    public void move(){
        this.x += dx * speed;
        this.y += dy * speed;

    }

    public void draw(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(this.x,this.y,this.width,this.height);
    }

}
