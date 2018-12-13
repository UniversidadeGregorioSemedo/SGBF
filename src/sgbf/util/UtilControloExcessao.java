

package sgbf.util;

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
}
