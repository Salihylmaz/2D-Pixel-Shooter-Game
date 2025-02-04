import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private MyFrame frame;
    private Controller controller;
    Image img;
    public GamePanel(MyFrame frame, Controller controller) {
        this.frame = frame;
        this.controller = controller;
        img = Toolkit.getDefaultToolkit().createImage("res\\images\\res.jpg");

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);

        frame.player.draw(g);

        for (Enemy enemy : Enemy.enemies) {
            enemy.draw(g);
        }
        for (Bullet bullet : frame.bullets) {
            bullet.draw(g);
        }
        controller.drawBullet(g);

        if (frame.gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Times", Font.PLAIN, 45));
            if (frame.win) {
                g.drawString("You Win!", 150, 150);
            } else {
                g.drawString("Game Over", 120, 150);
            }
        }
    }

}
