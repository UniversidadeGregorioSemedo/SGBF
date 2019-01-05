/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.controlo;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Marron
 */
public class VisUtente implements Initializable {

    @FXML
    private Button botaoCadastrar, botaoAlterar, botaoRemover, botaoNovo, botaoCancelar;
    @FXML
    private TextField texteFiedId, texteFiedPrimeiroNome, texteFiedSegundoNome;
    @FXML
    private TextField texteFiedNumIden, texteFiedContacto, texteFiedEmail;
    @FXML
    private TextField texteFiedUsuario, texteFiedSenha, texteFiedEndereco, texteFiedEnderecoImagem;
    @FXML
    private ComboBox comboBoxGenero, comboBoxTipoIndentificacao, comboBoxCategoria;     

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       this.bloquearItensDaJanela();
    } 
    
    @FXML
    public void bloquearItensDaJanela(){
        this.texteFiedId.setDisable(true);
        this.texteFiedPrimeiroNome.setDisable(true);
        this.texteFiedSegundoNome.setDisable(true);
        this.texteFiedNumIden.setDisable(true);
        this.texteFiedContacto.setDisable(true);
        this.texteFiedEmail.setDisable(true);
        this.texteFiedUsuario.setDisable(true);
        this.texteFiedSenha.setDisable(true);
        this.texteFiedEndereco.setDisable(true);
        this.texteFiedEnderecoImagem.setDisable(true);
        this.comboBoxGenero.setDisable(true);
        this.comboBoxTipoIndentificacao.setDisable(true);
        this.comboBoxCategoria.setDisable(true);
        this.botaoCadastrar.setDisable(true);
        this.botaoAlterar.setDisable(true);
        this.botaoRemover.setDisable(true);
    }
    
    @FXML
    public void desbloquearItensDaJanela(){
        this.texteFiedId.setDisable(false);
        this.texteFiedPrimeiroNome.setDisable(false);
        this.texteFiedSegundoNome.setDisable(false);
        this.texteFiedNumIden.setDisable(false);
        this.texteFiedContacto.setDisable(false);
        this.texteFiedEmail.setDisable(false);
        this.texteFiedUsuario.setDisable(false);
        this.texteFiedSenha.setDisable(false);
        this.texteFiedEndereco.setDisable(false);
        this.texteFiedEnderecoImagem.setDisable(false);
        this.comboBoxGenero.setDisable(false);
        this.comboBoxTipoIndentificacao.setDisable(false);
        this.comboBoxCategoria.setDisable(false);
        this.botaoCadastrar.setDisable(false);
        this.botaoAlterar.setDisable(false);
        this.botaoRemover.setDisable(false);
    }
    
    
   
    
   
    
}
