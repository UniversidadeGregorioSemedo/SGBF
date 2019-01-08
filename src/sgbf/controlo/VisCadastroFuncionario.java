/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.controlo;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sgbf.modelo.ModFuncionario;
import sgbf.modelo.ModVisitante;
import sgbf.util.UtilControloExcessao;

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
    @FXML
    private AnchorPane AnchorPaneFuncionario;
    
    private String operacao = null;
    private final ModVisitante utenteMod = new ModVisitante();
    private final ConUtente utenteCon = new ConUtente();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    @FXML
    private void pesquisarUtente(){
        operacao = "Pesquisar Utente";
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        if(this.texteFiedPesquisarUtente.getText().isEmpty()){
           throw new UtilControloExcessao(operacao, "Introduza o código ou nome do Utente", Alert.AlertType.INFORMATION);
        }else{
            todosRegistosEncontrados = this.utenteCon.pesquisar(this.pegarDadosDaPesquisa(), operacao);
            if(todosRegistosEncontrados.isEmpty()){
                //this.bloquearItensDaJanela();
                //this.limparItensDaJanela();
               throw new UtilControloExcessao(operacao, "Utente não encontradao", Alert.AlertType.INFORMATION);
            }else{
                this.carregarResultadosNaTablea(todosRegistosEncontrados);
               // this.bloquearItensDaJanela();
            }
        }
    }
    
    private ModVisitante pegarDadosDaPesquisa(){
        if(this.texteFiedPesquisarUtente.getText().contains("123456789")){
           utenteMod.setIdUtente(Integer.valueOf(this.texteFiedPesquisarUtente.getText()), operacao);
           utenteMod.setPrimeiro_nome(this.texteFiedPesquisarUtente.getText(), operacao);
           return utenteMod;
        }else{
           utenteMod.setPrimeiro_nome(this.texteFiedPesquisarUtente.getText(), operacao);
           return utenteMod;
        }
    }
    
    private void carregarResultadosNaTablea(List<Object> todosRegistosEncontrados){
        tableColumNomeUtente.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumIdTipoIdentificacaoUtente.setCellValueFactory(new PropertyValueFactory<>("tipo_identificacao"));
        tableColumNmeroIdentificacaoUtente.setCellValueFactory(new PropertyValueFactory<>("numero"));
        tableColumContactoUtente.setCellValueFactory(new PropertyValueFactory<>("contacto"));
        tableViewVisitane.setItems(this.todosRegistosParaCarregar(todosRegistosEncontrados));
    }
    
    private ObservableList<ModVisitante> todosRegistosParaCarregar(List<Object> todosRegistosEncontrados){
        List<ModVisitante> listaDosRegistosWncontrados = new ArrayList<>();
        for(Object utenteRegistado: todosRegistosEncontrados){
            ModVisitante visitanteMod = (ModVisitante)utenteRegistado;
            if(visitanteMod.getCategoria().equalsIgnoreCase("Administrador")){
                listaDosRegistosWncontrados.add(visitanteMod);
            }else{
                if(visitanteMod.getCategoria().equalsIgnoreCase("Funcionário")){
                    listaDosRegistosWncontrados.add(visitanteMod);
                }
            }
        }
        if(listaDosRegistosWncontrados.isEmpty()){
           throw new UtilControloExcessao(operacao, "Utente não encontradao", Alert.AlertType.INFORMATION);
        }else{
            return FXCollections.observableArrayList(listaDosRegistosWncontrados);
        }
    }
    
    
}
