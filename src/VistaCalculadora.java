import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class VistaCalculadora extends JFrame {

    public Pantallita display;
    public JLabel operacionLabel;
    public HistorialCalculadora historialCalculadora;
    public HashMap<String, JButton> botones = new HashMap<>();

    public VistaCalculadora() {
        setTitle("MARINEITOR 3000");
        setSize(470, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Fondo personalizado
        FondoPanel contenido = new FondoPanel("leopardo.jpg");
        contenido.setLayout(new BorderLayout(10, 10));
        contenido.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel superior
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false); // transparente para ver el fondo

        operacionLabel = new JLabel("", SwingConstants.RIGHT);
        operacionLabel.setForeground(new Color(150, 150, 150));
        operacionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        operacionLabel.setPreferredSize(new Dimension(400, 40));
        operacionLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        operacionLabel.setOpaque(true);
        operacionLabel.setBackground(new Color(35, 35, 35));

        panelSuperior.add(operacionLabel, BorderLayout.NORTH);

        display = new Pantallita();
        display.setPreferredSize(new Dimension(400, 80));
        panelSuperior.add(display, BorderLayout.CENTER);

        historialCalculadora = new HistorialCalculadora();

        JPanel panelBotones = new JPanel(new GridBagLayout());
        panelBotones.setOpaque(false); // transparente
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3, 3, 3, 3);

        String[][] layout = {
                {"7", "8", "9", "/"},
                {"4", "5", "6", "*"},
                {"1", "2", "3", "-"},
                {".", "0", "+/-", "+"},
                {"C", "AC", "="},
                {"HISTORIAL", "BORRAR HIST.", "BORRAR HIST."}
        };

        for (int row = 0; row < layout.length; row++) {
            for (int col = 0; col < layout[row].length; col++) {
                String texto = layout[row][col];

                // Evitar duplicados
                if ((texto.equals("=") && col == 3) ||
                    (texto.equals("HISTORIAL") && col == 1) ||
                    (texto.equals("BORRAR HIST.") && col == 3)) {
                    continue;
                }

                JButton btn = crearBoton(texto);
                gbc.gridx = col;
                gbc.gridy = row;

                if (texto.equals("=") || texto.equals("HISTORIAL") || texto.equals("BORRAR HIST.")) {
                    gbc.gridwidth = 2;
                    btn.setPreferredSize(new Dimension(200, 100));
                    btn.setMinimumSize(new Dimension(200, 100));
                } else {
                    gbc.gridwidth = 1;
                    btn.setPreferredSize(new Dimension(100, 100));
                    btn.setMinimumSize(new Dimension(100, 100));
                }

                gbc.weightx = gbc.gridwidth;
                gbc.weighty = 1.0;

                panelBotones.add(btn, gbc);
                botones.put(texto, btn);

                if (gbc.gridwidth == 2) {
                    col++;
                }
            }
        }

        contenido.add(panelSuperior, BorderLayout.NORTH);
        contenido.add(panelBotones, BorderLayout.CENTER);
        contenido.add(historialCalculadora.getPanelConFondo(), BorderLayout.SOUTH);

        setContentPane(contenido);
        setVisible(true);
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 22));
        btn.setForeground(Color.WHITE);

        Color baseColor = new Color(50, 50, 50);
        Color hoverColor = new Color(80, 80, 80);

        if (texto.equals("=")) {
            baseColor = new Color(0, 153, 0);
            hoverColor = new Color(0, 180, 0);
        } else if (texto.equals("+") || texto.equals("-") || texto.equals("*") || texto.equals("/")) {
            baseColor = new Color(153, 0, 0);
            hoverColor = new Color(180, 0, 0);
        } else if (texto.equals("HISTORIAL") || texto.equals("BORRAR HIST.")) {
            baseColor = new Color(20, 20, 20);
            hoverColor = new Color(40, 40, 40);
        }

        btn.setBackground(baseColor);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        Color finalBaseColor = baseColor;
        Color finalHoverColor = hoverColor;

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(finalHoverColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(finalBaseColor);
            }
        });

        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VistaCalculadora());
    }
}
