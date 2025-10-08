import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DrawField extends JPanel implements MouseListener, MouseMotionListener {

    private int fieldWidth = 640;
    private int fieldHeight = 480;
    private int prevX, prevY;
    private Instruments.ToolType currentTool = Instruments.ToolType.PENCIL;
    private int toolSize = 4;
    private Color currentColor = Color.BLACK;

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
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(image, 0, 0, this);
    }

    public void setCurrentTool(Instruments.ToolType tool) {
        this.currentTool = tool;
    }

    public void setToolSize(int size) {
        this.toolSize = size;
    }

    public void setCurrentColor(Color color) {
        this.currentColor = color;
    }

    @Override
    public void mouseClicked(MouseEvent e){
        Graphics2D g2d = image.createGraphics();
        if (currentTool == Instruments.ToolType.PENCIL) {
            g2d.setColor(currentColor);
            g2d.fillOval(e.getX() - toolSize / 2, e.getY() - toolSize / 2, toolSize, toolSize);
            g2d.dispose();
        } else if (currentTool == Instruments.ToolType.BRUSH) {
            g2d.setColor(currentColor);
            g2d.fillOval(e.getX() - toolSize - 1, e.getY() - toolSize - 1, 2*toolSize, 2*toolSize);
            g2d.dispose();
        } else if (currentTool == Instruments.ToolType.ERASER) {
            g2d.setColor(currentColor);
            g2d.fillOval(e.getX() - toolSize / 2, e.getY() -toolSize / 2, toolSize, toolSize);
            g2d.dispose();
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e){

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
        g2d.setColor(currentColor);

        if (currentTool == Instruments.ToolType.PENCIL) {
            // Простой точечный овал
            g2d.fillOval(x - toolSize / 2, y - toolSize / 2, toolSize, toolSize);
        }
        else if (currentTool == Instruments.ToolType.BRUSH) {
            int dx = x - prevX;
            int dy = y - prevY;
            int steps = Math.max(Math.abs(dx), Math.abs(dy));

            if (steps == 0) {
                // Просто один мазок, если мышь почти не сдвинулась
                g2d.fillOval(x - toolSize, y - toolSize, 2 * toolSize, 2 * toolSize);
            } else {
                for (int i = 0; i <= steps; i++) {
                    int drawX = prevX + i * dx / steps;
                    int drawY = prevY + i * dy / steps;
                    g2d.fillOval(drawX - toolSize, drawY - toolSize, 2 * toolSize, 2 * toolSize);
                }
            }
        }
        else if (currentTool == Instruments.ToolType.ERASER) {
            g2d.setColor(Color.WHITE);
            g2d.fillOval(x - toolSize / 2, y - toolSize / 2, toolSize, toolSize);
        }

        g2d.dispose();

        prevX = x;
        prevY = y;

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    public void saveImage(File file) {
        try {
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ошибка при сохранении файла", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
