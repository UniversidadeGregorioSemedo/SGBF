/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.controlo;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sgbf.modelo.ModAutor;

/**
 * FXML Controller class
 *
 * @author Marron
 */
public class VisVerAutor implements Initializable {

    @FXML
    private TextField texteFiedPesquisar;
    @FXML
    private JFXButton botaoPesquisar;
    @FXML
    private Button botaoSair;
    @FXML
    private TableView<ModAutor> tableViewAutor;
    @FXML
    private TableColumn<ModAutor, Integer> tableColumId;
    @FXML
    private TableColumn<ModAutor, String> tableColumNome;
    @FXML
    private TableColumn<ModAutor, String> tableColumContacto;
    @FXML
    private TableColumn<ModAutor, String> tableColumEmail;

    @FXML
    private AnchorPane anchorPaneVisAutor;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    } 
    
    @FXML
    private void sair(){
        this.anchorPaneVisAutor.setVisible(false);
    }
}
