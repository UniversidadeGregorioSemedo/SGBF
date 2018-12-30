

package sgbf.util;

import javafx.scene.control.Alert;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author look
 */

public class UtilControloExcessao extends RuntimeException {

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
}
