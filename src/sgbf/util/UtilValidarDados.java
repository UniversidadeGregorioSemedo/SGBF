/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Alert;

/**
 *
 * @author Dell
 */
public class UtilValidarDados {

    public static boolean eNumero(String texto) {
        for (int i = 0; i < texto.length(); i++) {
            if (!Character.isDigit(texto.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String validarInteiro(String valorIntroduzido) {
        if (valorIntroduzido == null) {
            return "0";
        } else {
            if (valorIntroduzido.isEmpty()) {
                return "0";
            } else {
                return valorIntroduzido;
            }
        }
    }

    public static short validarQuantidade(String quantidade, String operacao) {
        try {
            if (quantidade.isEmpty()) {
                throw new UtilControloExcessao(operacao, "Introduza a quantidade", Alert.AlertType.ERROR);
            } else {
                return Short.valueOf(quantidade);
            }
        } catch (NumberFormatException erro) {
            throw new UtilControloExcessao(operacao, "A quantidade introduzida não é válido\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public static Double custoUnitario(String subtotal, String operacao) {
        if (subtotal == null) {
            return 0.0;
        } else {
            if (subtotal.isEmpty()) {
                return 0.0;
            } else {
                return Double.valueOf(subtotal);
            }
        }
    }

    public boolean emailValido(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
