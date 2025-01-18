import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Box extends Rectangle {
    int hareketMiktarı =10;
    Color color;
    boolean isActive;

    Box(int x,int y,int width, int height,Color color){
        this.x=x;
        this.y=y;
        this.width = width;
        this.height = height;
        this.color= color;
        this.isActive = true;

    }

    public void keyPressed(KeyEvent e){
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                this.y -= hareketMiktarı;
                break;
            case KeyEvent.VK_S:
                this.y += hareketMiktarı;
                break;
            case KeyEvent.VK_D:
                this.x += hareketMiktarı;
                break;
            case KeyEvent.VK_A:
                this.x -= hareketMiktarı;
                break;
        }
        System.out.println(" Player_x: " + this.x+ " Player_y: "+ this.y);

    }

    public void draw(Graphics g){
        g.setColor(this.color);
        g.fillRect(this.x, this.y, this.width, this.height);

    }


}
