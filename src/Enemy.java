import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Random;


public class Enemy extends Entity {
    boolean isActive;
    public static ArrayList<Enemy> enemies = new ArrayList<>();
    static Random rand = new Random();
    private static BufferedImage enemySprite; // Tek bir sprite, tüm düşmanlar için ortak olacak.

    public Enemy(int x, int y, int width, int height){
        super(x,y,width,height,enemySprite);
        this.isActive = true;
    }

    public Rectangle getBounds() {
        return new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        Image tmp = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return resizedImage;
    }

    public static void loadSprite() {
        try {
            BufferedImage original = ImageIO.read(new File("res/characters/enemy.png"));
            enemySprite = resizeImage(original, 30, 30); // 30x30 boyutunda küçült
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void update(Player player) {
        int distanceX = Math.abs(this.getX() - player.getX());
        int distanceY = Math.abs(this.getY() - player.getY());

        if (distanceX > distanceY) {
            if (this.getX() < player.getX()) {
                this.setX(this.getX() + 1);
            } else if (this.getX() > player.getX()) {
                this.setX(this.getX() - 1);
            }
        } else {
            if (this.getY() < player.getY()) {
                this.setY(this.getY() + 1);
            } else if (this.getY() > player.getY()) {
                this.setY(this.getY() - 1);
            }
        }
    }


    public static void spawn(){
        int rand_x = rand.nextInt(650) - 100; // X ekseninde -100 ile 550 arasında değer
        int rand_y = rand.nextInt(650) - 100;

        if ((rand_x < 0 || rand_x > 500 || rand_y < 0 || rand_y > 500) && enemies.size() < 10) {
            enemies.add(new Enemy(rand_x, rand_y, 30, 30));
            System.out.println("Total enemies: " + enemies.size());
        }

        for(Enemy enemy : enemies){
            System.out.println("Total enemies: " + enemies.size());
            System.out.println("Enemy spawned at: (" + rand_x + ", " + rand_y + ")");
        }
    }

    public void draw(Graphics g){
        g.drawImage(this.getSprite(), this.getX(), this.getY(), null);
    }

}
