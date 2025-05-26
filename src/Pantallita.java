import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

class Pantallita extends JTextField {

    public Pantallita() {
        setOpaque(false);
        setForeground(new Color(20, 60, 20));
        setFont(new Font("Segoe UI", Font.BOLD, 32));
        setHorizontalAlignment(JTextField.RIGHT);
        setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        setBackground(new Color(0, 0, 0, 0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        int w = getWidth();
        int h = getHeight();

        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color verdeInicio = new Color(180, 255, 180, 160);
        Color verdeFin = new Color(100, 200, 100, 120);
        GradientPaint fondoVerde = new GradientPaint(0, 0, verdeInicio, 0, h, verdeFin);
        g2.setPaint(fondoVerde);
        g2.fillRoundRect(0, 0, w, h, 30, 30);

        GradientPaint reflejo = new GradientPaint(0, 0, new Color(255, 255, 255, 100),
                0, h / 2, new Color(255, 255, 255, 10));
        g2.setPaint(reflejo);
        g2.fillRoundRect(0, 0, w, h / 2, 30, 30);

        g2.setColor(new Color(255, 255, 255, 160));
        g2.setStroke(new BasicStroke(2f));
        g2.drawRoundRect(1, 1, w - 2, h - 2, 30, 30);

        g2.setColor(new Color(0, 0, 0, 40));
        g2.setStroke(new BasicStroke(3f));
        g2.drawRoundRect(3, 3, w - 6, h - 6, 28, 28);

        g2.dispose();
        BufferedImage blurred = blurImage(image);
        g.drawImage(blurred, 0, 0, null);
        super.paintComponent(g);
    }

    private BufferedImage blurImage(BufferedImage img) {
        float[] kernel = {
                1f / 16f, 2f / 16f, 1f / 16f,
                2f / 16f, 4f / 16f, 2f / 16f,
                1f / 16f, 2f / 16f, 1f / 16f
        };
        Kernel gaussianKernel = new Kernel(3, 3, kernel);
        ConvolveOp op = new ConvolveOp(gaussianKernel, ConvolveOp.EDGE_NO_OP, null);
        return op.filter(img, null);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }
}