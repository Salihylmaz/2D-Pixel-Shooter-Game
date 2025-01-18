import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class MyFrame extends JFrame {

    Image image;
    Graphics graphics;
    Box player;
    Box enemy;
    Box enemy2;
    ArrayList<Bullet> bullets;
    ArrayList<Box> enemies;
    Bullet bullet;
    boolean gameOver;
    private Controller controller;

    MyFrame(){
        player = new Box(50,50,30,30,Color.BLUE);
        enemies = new ArrayList<>();
        enemy = new Box(300,250,30,30,Color.RED);
        enemy2 = new Box(30,250,30,30,Color.RED);
        bullets = new ArrayList<>();
        //controller = new Controller();
        gameOver = false;

        enemies.add(new Box(300,250,30,30,Color.RED));
        enemies.add(new Box(300,150,30,30,Color.RED));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
        this.setVisible(true);
        this.addKeyListener(new AL());

        // Oyun döngüsü
        Timer timer = new Timer(30, e -> {
            if (!gameOver) {
                // Mermileri hareket ettir
                for (int i = 0; i < bullets.size(); i++) {
                    bullet = bullets.get(i);
                    bullet.move(); // Mermiyi hareket ettir

                    // Ekran dışına çıkan mermileri sil
                    if (bullet.y < 0 || bullet.y > getHeight()) {
                        bullets.remove(i);
                        i--;
                    }
                }

                // Çarpışmaları kontrol et
                checkCollisions();

                // Yeniden çiz
                repaint();
            }
        });
        timer.start();

    }

    public void paint(Graphics g){
        image = createImage(this.getWidth(), this.getHeight());
        graphics = image.getGraphics();
        g.drawImage(image,0,0, this);
        if(player.isActive)
            player.draw(g);
        if(enemy.isActive)
            enemy.draw(g);
        if(enemy2.isActive)
            enemy2.draw(g);
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }

        if(gameOver == true){
            g.setColor(Color.RED);
            g.setFont(new Font("Times",Font.PLAIN,45));
            g.drawString("Game OVER",120,150);
        }

    }

    public boolean isColliding(Box enemy, Bullet bullet) {
        int enemyCenterX = enemy.x + enemy.width / 2;
        int enemyCenterY = enemy.y + enemy.height / 2;

        int bulletCenterX = bullet.x + bullet.width / 2;
        int bulletCenterY = bullet.y + bullet.height / 2;

        int distanceX = Math.abs(enemyCenterX - bulletCenterX);
        int distanceY = Math.abs(enemyCenterY - bulletCenterY);

        return distanceX < (enemy.width / 2 + bullet.width / 2) &&
                distanceY < (enemy.height / 2 + bullet.height / 2);
    }

    public void checkCollisions(){
        if(enemy != null && player.intersects(enemy)){
            gameOver = true;
            System.out.println("Game over");
        }

        for (int i = 0; i < bullets.size(); i++) {
            bullet = bullets.get(i);
            if (enemy != null && isColliding(enemy,bullet)) {
                bullets.remove(i);
                i--;
                enemy.isActive=false;      // Düşmanı sahneden kaldır
                gameOver = true;
                System.out.println("Enemy has taken");
            }
        }

    }

    public class AL extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            if(!gameOver){
                player.keyPressed(e);

                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    bullets.add(new Bullet(player.x + 12, player.y+12,5,5));
                    //controller.addBullet(new Bullet(player.x,player.y,5,5));
                }
                checkCollisions();
                repaint();

            }
            else{

            }

        }
    }

}

