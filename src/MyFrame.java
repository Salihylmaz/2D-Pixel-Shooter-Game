import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Random;

public class MyFrame extends JFrame {

    Image image;
    Graphics graphics;
    Box player;
    ArrayList<Bullet> bullets;
    ArrayList<Enemy> enemies;
    boolean gameOver;
    boolean win;
    private Controller controller;
    Enemy enemy_deneme;
    Random rand = new Random();


    MyFrame(){
        player = new Box(100,100,30,30,Color.BLUE);
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        controller = new Controller(this);
        gameOver = false;
        win = false;

        enemies.add(new Enemy(500,250,30,30,Color.RED));
        //enemies.add(new Enemy(300,150,30,30,Color.RED));
        //enemies.add(new Enemy(200,150,30,30,Color.RED));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
        this.setVisible(true);
        this.addKeyListener(new AL());

        // Oyun döngüsü
        Timer timer = new Timer(30, e -> {
            if (!gameOver) {
                player.update();
                for (Enemy enemy : enemies) {
                    enemy.moveToPlayer(player);
                }
               // enemy_deneme.spawn();
                controller.bulletMove();
                checkCollisions();
                repaint();
            }
        });
        timer.start();
        Timer timer_enemy = new Timer(500, e -> {
            if(!gameOver)
                spawn();
        });
        timer_enemy.start();
    }

    public void spawn(){
        int rand_x = rand.nextInt(750) - 100; // X ekseninde -100 ile 650 arasında değer
        int rand_y = rand.nextInt(750) - 100;

        if ((rand_x < 0 || rand_x > 600 || rand_y < 0 || rand_y > 600) && enemies.size() < 10) {
            enemies.add(new Enemy(rand_x, rand_y, 30, 30, Color.RED));
            System.out.println("Total enemies: " + enemies.size());
        }

        for(Enemy enemy : enemies){
            System.out.println("Total enemies: " + enemies.size());
            System.out.println("Enemy spawned at: (" + rand_x + ", " + rand_y + ")");
        }

    }

    public void paint(Graphics g){
        image = createImage(this.getWidth(), this.getHeight());
        graphics = image.getGraphics();
        g.drawImage(image,0,0, this);

        if(player.isActive)
            player.draw(g);

        for(Enemy enemy : enemies){ //Birden fazla düşman ekleyebiliyorum bu sayede
            enemy.draw(g);
        }
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
        controller.drawBullet(g);
        if(gameOver == true){
            g.setColor(Color.RED);
            g.setFont(new Font("Times",Font.PLAIN,45));
            if (win) {
                g.drawString("You Win!", 150, 150);
            } else {
                g.drawString("Game Over", 120, 150);
            }
        }

    }

    public boolean isColliding(Enemy enemy, Bullet bullet) {
        return bullet.x < enemy.x + enemy.width &&  // Merminin sol kenarı düşmanın sağ kenarını geçmiyor
                bullet.x + bullet.width > enemy.x && // Merminin sağ kenarı düşmanın sol kenarını geçmiyor
                bullet.y < enemy.y + enemy.height && // Merminin üst kenarı düşmanın alt kenarını geçmiyor
                bullet.y + bullet.height > enemy.y;  // Merminin alt kenarı düşmanın üst kenarını geçmiyor
    }

    public void checkCollisions(){

        for (int i = 0; i < controller.bulletSize(); i++) {
            Bullet bullet = controller.bulletGet(i);

            for (int j = 0; j < enemies.size(); j++) {
                Enemy enemy = enemies.get(j);

                // Çarpışmayı kontrol et
                if (isColliding(enemy,bullet)) {
                    controller.removeBullet(i);
                    i--;
                    enemies.remove(j);
                    j--;
                    System.out.println("Enemy hit!");
                    break;
                }
            }
        }

        for (Enemy enemy : enemies) {
            enemy.moveToPlayer(player);
        }

        for (Enemy enemy : enemies) {
            if (player.intersects(enemy)) {
                gameOver = true;
                win = false;
                break;
            }
        }
        // Tüm düşmanlar vurulursa
        if (enemies.isEmpty()) {
            gameOver = true;
            win = true;
        }
    }

    public void restartGame() {
        player = new Box(100, 100, 30, 30, Color.BLUE);
        enemies.clear();
        bullets.clear();
        enemies.add(new Enemy(500, 250, 30, 30, Color.RED));
        gameOver = false;
        win = false;
        repaint();
    }

    public void quitGame() {
        System.exit(0);
    }

    public class AL extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            if(!gameOver){
                player.keyPressed(e);

                // Mermi oluşturma
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    int dx = 0, dy = 0;

                    // Oyuncunun basılı tuttuğu yön tuşlarına göre merminin hareket yönünü belirleyin
                    if (player.pressedKeys.contains(KeyEvent.VK_W)) dy = -1; // Yukarı
                    if (player.pressedKeys.contains(KeyEvent.VK_S)) dy = 1;  // Aşağı
                    if (player.pressedKeys.contains(KeyEvent.VK_A)) dx = -1; // Sol
                    if (player.pressedKeys.contains(KeyEvent.VK_D)) dx = 1;  // Sağ

                    if (dx == 0 && dy == 0) {
                        dx = 1;  // X ekseninde sağa doğru fırlat
                    }

                    controller.addBullet(new Bullet(player.x + player.width / 2, player.y + player.height / 2, 5, 5, player.lastDx, player.lastDy));

                }

                checkCollisions();
                repaint();

            }
            else{
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    restartGame(); // R tuşu ile yeniden başlat
                } else if (e.getKeyCode() == KeyEvent.VK_Q) {
                    quitGame(); // Q tuşu ile çıkış yap
                }
            }

            }
        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }

    }

}
