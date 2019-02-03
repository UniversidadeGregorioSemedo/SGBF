package sgbf.util;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author look
 */
public class UtilControloExcessao extends RuntimeException {

    public UtilControloExcessao() {
    }

    public UtilControloExcessao(String titulo, String mensagem, Alert.AlertType icone) {
        super(mensagem);
        Alert alert = new Alert(icone);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public boolean temCerteza(String operacao, String mensagem) {
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
