import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class ControladorCalculadora {

    private final VistaCalculadora vista;
    private final LogicaCalculadora logica = new LogicaCalculadora();

    private String operando1 = "";
    private String operador = "";
    private String operando2Anterior = null;
    private boolean nuevoNumero = true;

    public ControladorCalculadora(VistaCalculadora vista) {
        this.vista = vista;
        inicializar();
    }

    private void inicializar() {
        for (String clave : vista.botones.keySet()) {
            JButton boton = vista.botones.get(clave);
            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    manejarEvento(clave);
                }
            });
        }
    }

    private void manejarEvento(String comando) {
        String textoActual = vista.display.getText();

        switch (comando) {
            case "AC":
                vista.display.setText("");
                operando1 = "";
                operador = "";
                operando2Anterior = null;
                nuevoNumero = true;
                vista.operacionLabel.setText("");
                break;

            case "C":
                if (!textoActual.isEmpty()) {
                    vista.display.setText(textoActual.substring(0, textoActual.length() - 1));
                }
                break;

            case "+/-":
                if (!textoActual.isEmpty()) {
                    if (textoActual.startsWith("-")) {
                        vista.display.setText(textoActual.substring(1));
                    } else {
                        vista.display.setText("-" + textoActual);
                    }
                }
                break;

            case "=":
                if (!operador.isEmpty()) {
                    String operando2;
                    
                    if (nuevoNumero) {
                        // Si es nuevo número (justo después de operador), usa el operando2 anterior
                        operando2 = operando2Anterior != null ? operando2Anterior : operando1;
                    } else {
                        // Si no, usa el valor actual del display
                        operando2 = textoActual.isEmpty() ? operando1 : textoActual;
                        operando2Anterior = operando2;
                    }
                    
                    String resultado = logica.operar(operando1, operador, operando2);
                    vista.display.setText(resultado);
                    
                    // Guardar en historial
                    String operacionCompleta = operando1 + " " + operador + " " + operando2 + " = " + resultado;
                    vista.historialCalculadora.agregar(operacionCompleta);
                    
                    operando1 = resultado;
                    nuevoNumero = true;
                    vista.operacionLabel.setText("");
                }
                break;

            case "HISTORIAL":
                // Mostrar historial (implementación depende de tu clase VistaCalculadora)
                break;

            case "BORRAR HIST.":
                vista.historialCalculadora.borrarHistorial();
                break;

            case "+": case "-": case "*": case "/":
                if (!textoActual.isEmpty()) {
                    operando1 = textoActual;
                    operador = comando;
                    vista.operacionLabel.setText(operando1 + " " + operador);
                    nuevoNumero = true;
                    operando2Anterior = null;
                }
                break;

            case ".":
                if (nuevoNumero) {
                    vista.display.setText("0.");
                    nuevoNumero = false;
                } else if (!textoActual.contains(".")) {
                    vista.display.setText(textoActual + ".");
                }
                break;

            default: // Números (0-9)
                if (nuevoNumero) {
                    vista.display.setText(comando);
                    nuevoNumero = false;
                } else {
                    vista.display.setText(textoActual + comando);
                }
        }
    }
}