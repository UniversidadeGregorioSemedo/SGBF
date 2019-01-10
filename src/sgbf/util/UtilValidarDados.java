/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.util;

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
    
}
