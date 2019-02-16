package sgbf.controlo;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sgbf.modelo.ModFuncionario;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilUsuarioLogado;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class VisLogin implements Initializable {

    @FXML
    private JFXTextField loginNomeUsuario;

    @FXML
    private JFXPasswordField loginSenha;

    @FXML
    private Label loginMensagem;

    @FXML
    private JFXButton loginEntrar;

    @FXML
    private JFXButton loginCancelar;

    @FXML
    private Hyperlink loginEsqueceuSenha;

    @FXML
    private Hyperlink loginContacte;
    final String operacao = "Iniciar sessão";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loginNomeUsuario.setTooltip(new Tooltip("Introduza o nome do usuário (em letras minúsculas)"));
        this.loginSenha.setTooltip(new Tooltip("Introduza a senha do usuário"));
    }

    @FXML
    private void clicarBotoes(MouseEvent accao) {
        Node node = (Node) accao.getSource();
        Stage propreidadeDaJanela = (Stage) node.getScene().getWindow();

        if (accao.getSource() == loginEntrar) {
            this.abrirTelaPrincipal(operacao, propreidadeDaJanela);
        } else {
            if (accao.getSource() == loginCancelar) {
                ConPrincipal.sairdoSistema(operacao, propreidadeDaJanela);
            }
        }
    }
    
    @FXML
    private void recuperarSenha(){
        throw new UtilControloExcessao(operacao, "Operação indisponível no momento", Alert.AlertType.INFORMATION);
    }
    
    @FXML
    private void contacteNos(){
        throw new UtilControloExcessao(operacao, "Operação indisponível no momento", Alert.AlertType.INFORMATION);
    }

    private void abrirTelaPrincipal(String operacao, Stage propreidadeDaJanela) {
        try {
            UtilUsuarioLogado.setUsuarioLogado(this.autenticar(operacao));
            if (UtilUsuarioLogado.getUsuarioLogado() != null) {
                propreidadeDaJanela.close();
                Parent root = FXMLLoader.load(this.getClass().getResource("..\\visao\\VisTelaPrincipal.fxml"));
                Scene scene = new Scene(root);
                propreidadeDaJanela.setScene(scene);
                propreidadeDaJanela.setTitle("Sistema de Gestão de Biblioteca( " + UtilUsuarioLogado.getUsuarioLogado().getNome() + " )");
                propreidadeDaJanela.setMaximized(true);
                propreidadeDaJanela.setResizable(true);
                propreidadeDaJanela.show();
                ConEmprestimo emprestimoCon = new ConEmprestimo();
            } else {
                loginMensagem.setText("Usuário ou senha incorreta");
            }
        } catch (IOException erro) {
            throw new UtilControloExcessao(operacao, "Erro so iniciar o sistema !\nErro: " + erro, Alert.AlertType.ERROR);
        }
    }

    private ModFuncionario autenticar(String operacao) {
        ModFuncionario funcionaMod = new ModFuncionario();
        ConUsuario usuarioCon = new ConUsuario();

        funcionaMod.setUsuario(this.loginNomeUsuario.getText(), operacao);
        funcionaMod.setSenha(this.loginSenha.getText(), operacao);
        return usuarioCon.autenticar(funcionaMod, operacao);
    }

}
