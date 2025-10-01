import java.awt.*;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    private DrawField drawField;

    public MainFrame()
    {
        super("Paint");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(400, 100, 640, 480);
        setMinimumSize(new Dimension(640, 480));

        drawField = new DrawField();
        add(drawField);

        this.setVisible(true);
    }

}
