import java.awt.*;

public class Bullet extends Rectangle {

    int dx, dy; // Hareket yönleri
    int x,y;
    int mov_amount = 10;

    Bullet(int x, int y, int width, int height,int dx, int dy){
        this.x = x;
        this.y= y;
        this.width = width;
        this.height = height;
        this.dx = dx;
        this.dy = dy;

        // Hareket yönünü normalize et (çapraz hareketi doğru hızda yapmak için)
        double length = Math.sqrt(dx * dx + dy * dy);
        if (length != 0) {
            this.dx /= length;
            this.dy /= length;
        }

    }

    public void move(){
        x += dx * mov_amount;
        y += dy * mov_amount;
    }

  public void draw(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(x,y,this.width,this.height);
  }

}
