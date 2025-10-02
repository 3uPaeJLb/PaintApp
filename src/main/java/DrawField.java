import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class DrawField extends JPanel implements MouseListener, MouseMotionListener {

    private int fieldWidth = 640;
    private int fieldHeight = 480;
    private int prevX, prevY;

    private BufferedImage image;


    public DrawField() {
        image = new BufferedImage(fieldWidth, fieldHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, fieldWidth, fieldHeight);
        g2d.dispose();

        this.addMouseMotionListener(this);
        this.addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 0, 0, this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.fillOval(e.getX() - 2, e.getY() - 2, 4, 4);
        g2d.dispose();

        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

        prevX = e.getX();
        prevY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        int x = e.getX();
        int y = e.getY();

        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.drawLine(prevX, prevY, x, y); // Рисуем линию от предыдущей точки к текущей
        g2d.dispose();

        prevX = x;
        prevY = y;

        repaint(); // Перерисовать панель
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
