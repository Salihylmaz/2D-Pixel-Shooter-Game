import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Player extends Entity {
    int hareketMiktarı = 4;
    public Set<Integer> pressedKeys; // Basılı tuşları tutar
    public int lastDx = 0; // En son hareketin X yönü
    public int lastDy = 0; // En son hareketin Y yönü

    public BufferedImage normalSprite;
    private BufferedImage flippedSprite;
    private boolean facingRight = true; // Başlangıçta sağa bakıyor

    public Player(int x, int y, int width, int height) {
        super(x, y, width, height, null);
        this.pressedKeys = new HashSet<>();
        try {
            normalSprite = ImageIO.read(new File("res/characters/player.png"));
            flippedSprite = flipImageHorizontally(normalSprite);
            this.sprite = normalSprite; // Başlangıçta sağa bakıyor
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage loadSprite() {
        try {
            return ImageIO.read(new File("res/characters/player.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Çarpışma kontrolü
    public boolean intersects(Enemy enemy) {
        return this.getBounds().intersects(enemy.getBounds());
    }

    private static BufferedImage flipImageHorizontally(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage flippedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = flippedImage.createGraphics();

        g2d.drawImage(image, 0, 0, width, height, width, 0, 0, height, null);
        g2d.dispose();

        return flippedImage;
    }

    public void update() {
        int dx = 0;
        int dy = 0;

        if (pressedKeys.contains(KeyEvent.VK_W)) dy -= 1;
        if (pressedKeys.contains(KeyEvent.VK_S)) dy += 1;
        if (pressedKeys.contains(KeyEvent.VK_A)) dx -= 1;
        if (pressedKeys.contains(KeyEvent.VK_D)) dx += 1;

        double movementFactor = (dx != 0 && dy != 0) ? 1.0 / Math.sqrt(2) : 1.0;

        this.setX(getX() + (int) (dx * hareketMiktarı * movementFactor));
        this.setY(getY() + (int) (dy * hareketMiktarı * movementFactor));

        if (dx < 0 && facingRight) {
            facingRight = false;
            this.sprite = flippedSprite;
        } else if (dx > 0 && !facingRight) {
            facingRight = true;
            this.sprite = normalSprite;
        }

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
        System.out.println(" Player_x: " + this.getX()+ " Player_y: "+ this.getY());
    }

    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }

    public void draw(Graphics g){
        g.drawImage(this.getSprite(), this.getX(), this.getY(),30,30, null);
    }

}
