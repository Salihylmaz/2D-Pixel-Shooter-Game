import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private MyFrame frame;
    private Controller controller;

    public GamePanel(MyFrame frame, Controller controller) {
        this.frame = frame;
        this.controller = controller;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (frame.player.isActive)
            frame.player.draw(g);

        for (Enemy enemy : frame.enemies) {
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
