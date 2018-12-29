/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.controlo;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;

/**
 * FXML Controller class
 *
 * @author Marron
 */
public class ViewTodosAcervosController implements Initializable {

    @FXML
    private Label CampoIdAcervo;
    @FXML
    private Label CampoTituloAcervo;
    @FXML
    private Label CampoSubtituloAcervo;
    @FXML
    private Label CampotipoAcervo;
    @FXML
    private Label CampoFormtoAcervo;
    @FXML
    private Label CampoEdicaoAcervo;
    @FXML
    private Label CampoVolumeAcervo;
    @FXML
    private Label CampoEditoraAcervo;
    @FXML
    private Label CampoIdiomaAcervo;
    @FXML
    private Label CampoCodBarraAcervo;
    @FXML
    private Label CampoIsbnAcervo;
    @FXML
    private Label CampoNumPagAcervo;
    @FXML
    private Label CampoCategoriaAcervo;
    @FXML
    private Label CampoEstoqueAcervo;
    @FXML
    private Label CampoAnoLancamentoAcervo;
    @FXML
    private Label CampoDataRegistroAcervo;
    @FXML
    private Label CampoDataModificacaoAcervo;
    @FXML
    private TableColumn<?, ?> colunatabelaIDAcervo;
    @FXML
    private TableColumn<?, ?> colunatabelaTituloAcervo;
    @FXML
    private TableColumn<?, ?> colunatabelaSubtituloTituloAcervo;
    @FXML
    private TableColumn<?, ?> colunatabelaCodBarraAcervo;
    @FXML
    private TableColumn<?, ?> colunatabelaIsbnAcervo;
    @FXML
    private JFXButton BotaoCadastrar;
    @FXML
    private JFXButton BotaoAlterar;
    @FXML
    private JFXButton BotaoRemover;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
