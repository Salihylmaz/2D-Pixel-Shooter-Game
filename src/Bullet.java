import java.awt.*;

public class Bullet extends Rectangle {

    int x,y;
    int mov_amount = 10;

    Bullet(int x, int y, int width, int height){
        this.x = x;
        this.y= y;
        this.width = width;
        this.height = height;
    }

    public void move(){
        x += mov_amount;
    }

  public void draw(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(x,y,this.width,this.height);
  }

}
