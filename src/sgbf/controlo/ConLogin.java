
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sgbf.modelo.ModFuncionario;
import sgbf.util.UtilControloExcessao;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class ConLogin implements Initializable {

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

    public void clicarBotoes(MouseEvent accao){
        Node node = (Node)accao.getSource();
        Stage propreidadeDaJanela =  (Stage) node.getScene().getWindow();
        final String operacao = "Iniciar sessão";
       
        if(accao.getSource() == loginEntrar){
           this.abrirTelaPrincipal(operacao,accao,propreidadeDaJanela);
        }else{
            if(accao.getSource() == loginCancelar){
                ConPrincipal.sairdoSistema(operacao, propreidadeDaJanela);
            }
        }
    }
    
    private void abrirTelaPrincipal(String operacao,MouseEvent accao, Stage propreidadeDaJanela){
        try{
            if(this.autenticar(operacao)){
                propreidadeDaJanela.close();

                Parent root = FXMLLoader.load(this.getClass().getResource("..\\visao\\VisTelaPrincipal.fxml"));
                Scene scene = new Scene(root);
                propreidadeDaJanela.setScene(scene);
                propreidadeDaJanela.setTitle("Sistema de Gestão de Biblioteca");
                propreidadeDaJanela.setMaximized(true);
                propreidadeDaJanela.setResizable(true);
                propreidadeDaJanela.show();
            }else{
                loginMensagem.setText("Usuário ou senha incorreta");
            }
        }catch(IOException erro){
            throw new UtilControloExcessao(operacao, "Erro so iniciar o sistema !\nErro: "+erro, Alert.AlertType.ERROR);
        }
    }
    
    
    private boolean autenticar(String operacao){
        
        ModFuncionario funcionaMod = new ModFuncionario();
        ConUsuario usuarioCon = new ConUsuario();
        
        funcionaMod.setUsuario(this.loginNomeUsuario.getText(),operacao);
        funcionaMod.setSenha(this.loginSenha.getText(),operacao);
        return usuarioCon.autenticar(funcionaMod, operacao); 
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
