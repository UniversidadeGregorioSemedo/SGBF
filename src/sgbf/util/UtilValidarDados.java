/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.util;

import javafx.scene.control.Alert;

/**
 *
 * @author Dell
 */
public class UtilValidarDados {
    
    public static boolean eNumero(String texto){
        for(int i=0; i<texto.length(); i++){
            if(!Character.isDigit(texto.charAt(i))){
                return false;
            }
        }
        return true;
    }
    
    public static short validarQuantidade(String quantidade, String operacao){
        try{
            if(quantidade.isEmpty()){
                throw new UtilControloExcessao(operacao, "Introduza a quantidade", Alert.AlertType.ERROR);
            }else{
                return Short.valueOf(quantidade);
            }
        }catch(NumberFormatException erro){
            throw new UtilControloExcessao(operacao, "A quantidade introduzida não é válido\nErro: "+erro.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    public static Double subTotal(String subtotal, String operacao){
        try{
            if(subtotal.isEmpty()){
                return 0.0;
            }else{
                return Double.valueOf(subtotal);
            }
        }catch(NumberFormatException erro){
            throw new UtilControloExcessao(operacao, "O subtotal introduzido não é válido\nErro: "+erro.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
