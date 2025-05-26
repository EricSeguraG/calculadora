public class LogicaCalculadora {

    public String operar(String op1, String operador, String op2) {
        double n1 = Double.parseDouble(op1);
        double n2 = Double.parseDouble(op2);
        double resultado = 0;

        switch (operador) {
            case "+":
                resultado = n1 + n2;
                break;
            case "-":
                resultado = n1 - n2;
                break;
            case "*":
                resultado = n1 * n2;
                break;
            case "/":
                if (n2 == 0) return "Error";
                resultado = n1 / n2;
                break;
            default:
                return "Error";
        }

        // Redondear para evitar decimales largos
        if (resultado == (long) resultado) {
            return String.valueOf((long) resultado);
        } else {
            return String.valueOf(resultado);
        }
    }
}