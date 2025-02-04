import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class MyFrame extends JFrame {

    Sound sound = new Sound();
    Player player;
    ArrayList<Bullet> bullets;
    boolean gameOver;
    boolean win;
    private Controller controller;
    Random rand = new Random();
    int score = 0;
    private JLabel scoreLabel;
    private GamePanel gamePanel;

    MyFrame(){
        player = new Player(100,100,30,30);

        //enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        controller = new Controller(this);
        gameOver = false;
        win = false;

        Enemy.enemies.clear();

        // Düşman sprite'ını sadece 1 kez yükle
        Enemy.loadSprite();

        // Başlangıçta bir düşman oluştur
        Enemy.spawn();

        Enemy.enemies.add(new Enemy(500,250,30,30));

        this.setLayout(new BorderLayout());

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
        this.setVisible(true);
        this.addKeyListener(new AL());

        // Score label
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(scoreLabel, BorderLayout.NORTH);

        gamePanel = new GamePanel(this,controller);
        this.add(gamePanel, BorderLayout.CENTER);

        // Oyun döngüsü
        Timer timer = new Timer(30, e -> {
            if (!gameOver) {
                player.update();
                for (Enemy enemy : Enemy.enemies) {
                    //enemy.moveToPlayer(player);
                    enemy.update(player);
                }
                controller.bulletMove();
                checkCollisions();
                gamePanel.revalidate(); // Force UI update
                gamePanel.repaint();
            }
        });
        timer.start();
        Timer timer_enemy = new Timer(500, e -> {
            if(!gameOver)
                Enemy.spawn();
        });
        timer_enemy.start();
    }

    public void increaseScore() {
        score++;
        scoreLabel.setText("Score: " + score);
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

            for (int j = 0; j < Enemy.enemies.size(); j++) {
                Enemy enemy = Enemy.enemies.get(j);

                // Çarpışmayı kontrol et
                if (isColliding(enemy,bullet)) {
                    controller.removeBullet(i);
                    i--;
                    Enemy.enemies.remove(j);
                    j--;
                    System.out.println("Enemy hit!");
                    increaseScore();
                    System.out.println("Score: "+ score);
                    break;
                }
            }
        }

        for (Enemy enemy : Enemy.enemies) {
            enemy.update(player);
        }

        for (Enemy enemy : Enemy.enemies) {
            if (player.intersects(enemy)) {
                sound.playGameOverSound();
                gameOver = true;
                win = false;
                break;
            }
        }
        // Tüm düşmanlar vurulursa
        if (Enemy.enemies.isEmpty()) {
            gameOver = true;
            win = true;
        }
    }

    public void restartGame() {
        player = new Player(100, 100, 30, 30);
        Enemy.enemies.clear();
        bullets.clear();
        Enemy.enemies.add(new Enemy(500, 250, 30, 30));
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
                    sound.playBulletSound();
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
