import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class MyFrame extends JFrame {

    Image image;
    Graphics graphics;
    Box player;
    ArrayList<Bullet> bullets;
    ArrayList<Box> enemies;
    boolean gameOver;
    private Controller controller;

    MyFrame(){
        player = new Box(50,50,30,30,Color.BLUE);
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        controller = new Controller(this);
        gameOver = false;

        enemies.add(new Box(300,250,30,30,Color.RED));
        enemies.add(new Box(300,150,30,30,Color.RED));
        enemies.add(new Box(200,150,30,30,Color.RED));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
        this.setVisible(true);
        this.addKeyListener(new AL());

        // Oyun döngüsü
        Timer timer = new Timer(30, e -> {
            if (!gameOver) {
                controller.bulletMove();
                checkCollisions();
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

        for(Box enemy : enemies){ //Birden fazla düşman ekleyebiliyorum bu sayede
            enemy.draw(g);
        }
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
        controller.drawBullet(g);
        if(gameOver == true){
            g.setColor(Color.RED);
            g.setFont(new Font("Times",Font.PLAIN,45));
            g.drawString("Game OVER",120,150);
        }

    }

    public boolean isColliding(Box enemy, Bullet bullet) {
        return bullet.x < enemy.x + enemy.width &&  // Merminin sol kenarı düşmanın sağ kenarını geçmiyor
                bullet.x + bullet.width > enemy.x && // Merminin sağ kenarı düşmanın sol kenarını geçmiyor
                bullet.y < enemy.y + enemy.height && // Merminin üst kenarı düşmanın alt kenarını geçmiyor
                bullet.y + bullet.height > enemy.y;  // Merminin alt kenarı düşmanın üst kenarını geçmiyor
    }


    public void checkCollisions(){

        for (int i = 0; i < controller.bulletSize(); i++) {
            Bullet bullet = controller.bulletGet(i);

            for (int j = 0; j < enemies.size(); j++) {
                Box enemy = enemies.get(j);

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

        for (Box enemy : enemies) {
            if (player.intersects(enemy)) {
                gameOver = true;
                System.out.println("Game Over");
                break;
            }
        }
        // Tüm düşmanlar vurulursa
        if (enemies.isEmpty()) {
            gameOver = true;
            System.out.println("You Win!");
        }
    }

    public class AL extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            if(!gameOver){
                player.keyPressed(e);

                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    controller.addBullet(new Bullet(player.x + 12,player.y + 12,5,5));
                }
                checkCollisions();
                repaint();

            }
            else{

            }

        }
    }

}

