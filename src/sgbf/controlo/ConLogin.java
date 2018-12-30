/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sgbf.modelo.ModFuncionario;

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
        if(accao.getSource() == loginEntrar){
           final String operacao = "Iniciar sessão";
           this.abrirTelaPrincipal(operacao,accao);
        }else{
            if(accao.getSource() == loginCancelar){
                System.exit(0);
            }
        }
    }
    
    private void abrirTelaPrincipal(String operacao,MouseEvent accao){
        try{
            if(this.autenticar(operacao)){
                Node node = (Node)accao.getSource();
                Stage stage =  (Stage) node.getScene().getWindow();
                stage.setMaximized(true);
                stage.close();

                Parent root = FXMLLoader.load(this.getClass().getResource("..\\visao\\VisTelaPrincipal.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Sistema de Gestão de Biblioteca");
                stage.setResizable(false);
                stage.show();
            }else{
                loginMensagem.setText("Usuário ou senha incorreta");
            }
        }catch(IOException erro){
           
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
