
import javax.swing.SwingUtilities;

public class Calculadora{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VistaCalculadora vista = new VistaCalculadora();
            new ControladorCalculadora(vista);
        });
    }
}
