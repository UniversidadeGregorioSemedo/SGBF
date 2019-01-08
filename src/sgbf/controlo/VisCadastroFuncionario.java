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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sgbf.modelo.ModFuncionario;
import sgbf.modelo.ModVisitante;

/**
 * FXML Controller class
 *
 * @author Marron
 */
public class VisCadastroFuncionario implements Initializable {

    @FXML
    private Button botaoCadastrar, botaoAlterar, botaoRemover, botaoNovo, botaoCancelar, botaoSair;
    @FXML
    private TextField texteFiedPesquisarUtente,texteFiedPesquisarFuncionario,
            texteFiedcodigoFuncionario, texteFiedcodigoUtente;
    @FXML
    private JFXButton botaoPesquisarUtente, botaoPesquisarFuncionario;
    @FXML
    private ComboBox<String> comboBoxCargo;
    @FXML
    private TableView<ModVisitante> tableViewVisitane; 
    @FXML
    private TableView<ModFuncionario> tableViewFuncionario; 
    @FXML
    private TableColumn<ModVisitante, String> tableColumNomeUtente,tableColumIdTipoIdentificacaoUtente,
            tableColumNmeroIdentificacaoUtente,tableColumContactoUtente;
    @FXML
    private TableColumn<ModFuncionario, String> tableColumNomeFuncionario,
            tableColumCategoriaFuncionario,tableColumContactoFuncionario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
