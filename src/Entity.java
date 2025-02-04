import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected BufferedImage sprite;

    public Entity(int x, int y, int width, int height,BufferedImage sprite){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
    }

    // Çarpışma kontrolü için getBounds() metodunu ekliyoruz
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public abstract void update();

    public void update(Player player){

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

}
