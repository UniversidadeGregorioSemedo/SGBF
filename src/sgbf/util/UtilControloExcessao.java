

package sgbf.util;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author look
 */

public class UtilControloExcessao extends RuntimeException {

    public UtilControloExcessao(){
    }
    
    public UtilControloExcessao(String messagem, String tituloDaJanela, String icone){
        super(messagem);
        ImageIcon imagem = new ImageIcon(icone);
        JOptionPane.showMessageDialog(null,super.getMessage(), tituloDaJanela, JOptionPane.INFORMATION_MESSAGE,imagem);
    }
    
    
    public UtilControloExcessao(String titulo, String mensagem, Alert.AlertType icone){
        super(mensagem);
        Alert alert = new Alert(icone);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
    
    public boolean temCerteza(String operacao, String mensagem){
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle(operacao);
        confirmacao.setHeaderText(null);
        confirmacao.setContentText(mensagem);
         
        ButtonType SIM = new ButtonType("Sim");
        ButtonType CANCELAR = new ButtonType("Cancelar");
        
        confirmacao.getButtonTypes().setAll(SIM, CANCELAR);
        
        Optional<ButtonType> opccao = confirmacao.showAndWait();
        return opccao.get() == SIM;
    }
   
}
