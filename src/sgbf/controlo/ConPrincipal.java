package sgbf.controlo;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import sgbf.util.UtilControloExcessao;

/**
 *
 * @author Dell
 */
public class ConPrincipal extends Application {

    @Override
    public void start(Stage primaryStage) {
        final String operacao = "Fechar o programa";
        try {
            this.fecharPeloBotaoWindow(operacao, primaryStage);

            Parent root = FXMLLoader.load(this.getClass().getResource("..\\visao\\VisLogin.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Sistema de Gestão de Biblioteca");
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao inciar o sistema !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void fecharPeloBotaoWindow(String operacao, Stage propridadeDaJanela) {
        propridadeDaJanela.setOnCloseRequest((evento) -> {
            evento.consume();
            ConPrincipal.sairdoSistema(operacao, propridadeDaJanela);
        });
    }

    public static void sairdoSistema(String operacao, Stage propriedadeDaJanela) {
        UtilControloExcessao confirmar = new UtilControloExcessao();
        final String mensagem = "Tem a certeza que pretende fechar o programa ?";
        if (confirmar.temCerteza(operacao, mensagem)) {
            propriedadeDaJanela.close();
        }
    }
}
