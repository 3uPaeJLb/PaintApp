import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainFrame extends JFrame {

    private final DrawField drawField;
    private final JToolBar toolBar;

    private final JButton pencilBtn;
    private final JButton brushBtn;
    private final JButton eraserBtn;
    private final JButton colorBtn;
    private final JButton saveBtn;
    private final JButton fillBtn; // добавить поле


    public MainFrame() {
        super("Paint");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(400, 100, 800, 600);
        setMinimumSize(new Dimension(640, 480));

        drawField = new DrawField();
        add(drawField, BorderLayout.CENTER);

        toolBar = new JToolBar();
        toolBar.setFloatable(false);

        pencilBtn = createIconButton("/icons/pen.png", "Карандаш");
        brushBtn = createIconButton("icons/brush.png", "Кисть");
        eraserBtn = createIconButton("icons/eraser.png", "Ластик");
        colorBtn = createIconButton("/icons/palette.png", "Выбрать цвет");
        saveBtn = createIconButton("/icons/save.png", "Сохранить");
        fillBtn = createIconButton("/icons/fill.png", "Заливка");

        toolBar.add(pencilBtn);
        toolBar.add(brushBtn);
        toolBar.add(eraserBtn);
        toolBar.add(colorBtn);
        toolBar.add(saveBtn);
        toolBar.add(fillBtn);

        toolBar.addSeparator(new Dimension(15, 0));
        toolBar.add(new JLabel("Размер: "));

        JSlider sizeSlider = new JSlider(1, 50, 4);
        sizeSlider.setPreferredSize(new Dimension(100, 18));
        sizeSlider.setMinimumSize(new Dimension(100, 18));
        sizeSlider.setMaximumSize(new Dimension(100, 18));
        sizeSlider.setPaintTicks(false);
        sizeSlider.setPaintLabels(false);
        sizeSlider.addChangeListener(e -> drawField.setToolSize(sizeSlider.getValue()));

        toolBar.add(sizeSlider);

        add(toolBar, BorderLayout.NORTH);

        pencilBtn.addActionListener(e -> drawField.setCurrentTool(InstrumentsType.PENCIL));
        brushBtn.addActionListener(e -> drawField.setCurrentTool(InstrumentsType.BRUSH));
        eraserBtn.addActionListener(e -> drawField.setCurrentTool(InstrumentsType.ERASER));
        fillBtn.addActionListener(e -> drawField.setCurrentTool(InstrumentsType.FILL));

        colorBtn.addActionListener(e -> {
            Color selectedColor = JColorChooser.showDialog(this, "Выберите цвет", Color.BLACK);
            if (selectedColor != null) {
                drawField.setCurrentColor(selectedColor);
            }
        });

        saveBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Сохранить изображение");
            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                if (!fileToSave.getName().toLowerCase().endsWith(".png")) {
                    fileToSave = new File(fileToSave.getAbsolutePath() + ".png");
                }
                drawField.saveImage(fileToSave);
            }
        });

        setVisible(true);
    }

    private JButton createIconButton(String resourcePath, String tooltip) {
        java.net.URL imageUrl = getClass().getResource(resourcePath);
        if (imageUrl == null) {
            System.err.println("Icon not found: " + resourcePath);
            return new JButton(tooltip);
        }

        ImageIcon icon = new ImageIcon(imageUrl);
        Image scaledImage = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(scaledImage));
        button.setToolTipText(tooltip);
        button.setPreferredSize(new Dimension(32, 32));
        return button;
    }
}
