import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.*;



public class HistorialCalculadora {
    private List<String> historial = new ArrayList<>();
    private JTextArea historialArea;
    private JScrollPane scrollPane;
    private BufferedImage imagenFondo;
    private JPanel panelConFondo;

    public HistorialCalculadora() {
        try {
            cargarImagenFondo();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, 
                "No se pudo cargar la imagen de fondo.\nMotivo: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            imagenFondo = null;
        }

        // Configuración del área de texto
        historialArea = new JTextArea(8, 30);
        historialArea.setEditable(false);
        historialArea.setFont(new Font("Segoe UI", Font.BOLD, 16));
        historialArea.setForeground(Color.WHITE);
        historialArea.setBackground(new Color(0, 0, 0, 0));
        historialArea.setOpaque(false);

        // Configuración del scroll pane
        scrollPane = new JScrollPane(historialArea);
        scrollPane.setPreferredSize(new Dimension(450, 120));
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), "HISTORIAL", 0, 0,
            new Font("Segoe UI", Font.BOLD, 14), Color.WHITE));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);

        // Panel con fondo personalizado
        panelConFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Fondo sólido oscuro
                g.setColor(new Color(40, 40, 40));
                g.fillRect(0, 0, getWidth(), getHeight());

                // Dibuja la imagen de fondo con máxima transparencia (0.1f = 10% opacidad)
                if (imagenFondo != null) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
                    g2d.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                    g2d.dispose();
                }
            }
        };
        panelConFondo.setLayout(new BorderLayout());
        panelConFondo.add(scrollPane, BorderLayout.CENTER);
    }

    private void cargarImagenFondo() throws IOException {
        File imgFile = new File("fondo.png");
        
        if (imgFile.exists()) {
            imagenFondo = ImageIO.read(imgFile);
            return;
        }
        
        InputStream is = getClass().getResourceAsStream("/fondo.png");
        if (is != null) {
            imagenFondo = ImageIO.read(is);
            return;
        }
        
        throw new IOException("No se encontró fondo.png en el directorio del proyecto ni en recursos");
    }

    private void reproducirSonido(String nombreArchivo) {
    try (InputStream audioSrc = getClass().getResourceAsStream("/" + nombreArchivo);
         InputStream bufferedIn = new java.io.BufferedInputStream(audioSrc);
         AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn)) {

        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | NullPointerException e) {
        System.err.println("No se pudo reproducir el sonido: " + e.getMessage());
    }
}


    public void agregar(String operacion) {
    historial.add(operacion);

    // Verifica si el resultado final de la operación es 69 o 69.0
    if (operacion.matches(".*=\\s*69(\\.0*)?\\b.*")) {
        historial.add("ESE ES MI AREA");
        reproducirSonido("area.wav");
    }

    actualizarDisplay();
}


    public String obtenerHistorial() {
        return String.join("\n", historial);
    }

    public void borrarHistorial() {
        historial.clear();
        actualizarDisplay();
    }

    private void actualizarDisplay() {
        historialArea.setText(obtenerHistorial());
        historialArea.setCaretPosition(historialArea.getDocument().getLength());
    }

    public JPanel getPanelConFondo() {
        return panelConFondo;
    }
}