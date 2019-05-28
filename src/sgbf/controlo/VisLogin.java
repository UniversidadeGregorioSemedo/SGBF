package sgbf.controlo;

import sgbf.dao.DaoUsuario;
import sgbf.dao.DaoEmprestimo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import sgbf.util.ProriedadesDaJanela;

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
        this.loginNomeUsuario.setTooltip(new Tooltip("Introduza o nome do usuário"));
        this.loginSenha.setTooltip(new Tooltip("Introduza a senha do usuário"));
        this.loginMensagem.setTooltip(null);
    }

    @FXML
    private void clicarBotoes(MouseEvent accao) {
        try {
            Node node = (Node) accao.getSource();
            Stage propreidadeDaJanela = (Stage) node.getScene().getWindow();
            if (accao.getSource() == loginEntrar) {
                this.abrirDeAmbienteDeTrabalho(operacao, propreidadeDaJanela);
            } else if (accao.getSource() == loginCancelar) {
                Main.sairdoSistema(operacao, propreidadeDaJanela);
            }
        } catch (IOException erro) {
            throw new UtilControloExcessao(operacao, "Operação indisponível no momento", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void recuperarSenha() {
        throw new UtilControloExcessao(operacao, "Operação indisponível no momento", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void contacteNos() {
        throw new UtilControloExcessao(operacao, "Operação indisponível no momento", Alert.AlertType.INFORMATION);
    }

    private void abrirDeAmbienteDeTrabalho(String operacao, Stage propreidadeDaJanela) throws IOException {
        try {
            if (this.validarCredenciasDoUsuario(operacao)) {
                propreidadeDaJanela.close();
                Parent root = FXMLLoader.load(this.getClass().getResource("..\\visao\\VisTelaPrincipal.fxml"));
                Scene scene = new Scene(root);
                propreidadeDaJanela.setScene(scene);
                ProriedadesDaJanela.exibirUsuarioLogado(propreidadeDaJanela);
                propreidadeDaJanela.setMaximized(true);
                propreidadeDaJanela.setResizable(true);
                propreidadeDaJanela.show();
                DaoEmprestimo emprestimoCon = new DaoEmprestimo();
                this.loginMensagem.setTooltip(null);
            } else {
                loginMensagem.setText("Usuário ou senha incorreta");
                this.loginMensagem.setTooltip(new Tooltip("Usuário ou senha incorreta"));
            }
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao se conectar com a Base de Dados !\nErro: "+erro, Alert.AlertType.ERROR);
        }
    }

    private boolean validarCredenciasDoUsuario(String operacao) throws SQLException {
        ModFuncionario funcionaMod = new ModFuncionario();
        DaoUsuario usuarioCon = new DaoUsuario();
        funcionaMod.setUsuario(this.loginNomeUsuario.getText(), operacao);
        funcionaMod.setSenha(this.loginSenha.getText(), operacao);
        UtilUsuarioLogado.setUsuarioLogado(usuarioCon.identificarUsuario(funcionaMod, operacao));
        return UtilUsuarioLogado.getUsuarioLogado() != null;
    }

}
