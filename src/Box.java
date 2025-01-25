import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class Box extends Rectangle {
    int hareketMiktarı = 4;
    Color color;
    boolean isActive;
    public Set<Integer> pressedKeys; // Basılı tuşları tutar
    public int lastDx = 0; // En son hareketin X yönü
    public int lastDy = 0; // En son hareketin Y yönü

    Box(int x,int y,int width, int height,Color color){
        this.x=x;
        this.y=y;
        this.width = width;
        this.height = height;
        this.color= color;
        this.isActive = true;
        this.pressedKeys = new HashSet<>();

    }

    public void update() {
        int dx = 0;
        int dy = 0;

        if (pressedKeys.contains(KeyEvent.VK_W)) dy -= 1;
        if (pressedKeys.contains(KeyEvent.VK_S)) dy += 1;
        if (pressedKeys.contains(KeyEvent.VK_A)) dx -= 1;
        if (pressedKeys.contains(KeyEvent.VK_D)) dx += 1;

        double movementFactor = (dx != 0 && dy != 0) ? 1.0 / Math.sqrt(2) : 1.0;

        this.x += dx * hareketMiktarı * movementFactor;
        this.y += dy * hareketMiktarı * movementFactor;

        // Eğer hareket varsa, yönü güncelle
        if (dx != 0 || dy != 0) {
            lastDx = dx;
            lastDy = dy;
        }
        if (lastDx == 0 && lastDy == 0) {
            lastDx = 1; // Varsayılan olarak sağa gitsin
        }

    }

    public void keyPressed(KeyEvent e){
        pressedKeys.add(e.getKeyCode());
        System.out.println(" Player_x: " + this.x+ " Player_y: "+ this.y);
    }

    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }

    public void draw(Graphics g){
        g.setColor(this.color);
        g.fillRect(this.x, this.y, this.width, this.height);

    }


}
