/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.visao;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Marron
 */
public class Tela_PrincipalController implements Initializable {
    @FXML
    private HBox Informação_Acervo;
    @FXML
    private HBox Informação_Utente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Carregar_acervos(ActionEvent event) {
    }

    @FXML
    private void Carregar_Usuario(ActionEvent event) {
    }

    @FXML
    private void PesquisarAcervo(ActionEvent event) {
    }

    @FXML
    private void PesquisarUtente(ActionEvent event) {
    }

    @FXML
    private void VerTodosAcervos(ActionEvent event) {
    }

    @FXML
    private void VerTodosUtentes(MouseEvent event) {
    }
    
}
