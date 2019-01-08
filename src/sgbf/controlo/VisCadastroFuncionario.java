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
import javafx.event.ActionEvent;
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
    private final ModFuncionario funcionarioMod = new ModFuncionario();
    private final ConUtente utenteCon = new ConUtente();
    private final ConFuncionario funcionarioCon = new ConFuncionario();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.bloquearItensDaJanela();
        this.carregarValorNasComboxs();
        this.tableViewVisitane.getSelectionModel().selectedItemProperty().addListener(
              (observable, oldValue, newValue) -> this.exibirDadosDoUtenteNosCampos(newValue));
        this.tableViewFuncionario.getSelectionModel().selectedItemProperty().addListener(
               (observable, oldValue, newValue) -> this.exibirDadosDoFuncionarioNosCampos(newValue));
    }  
    
    @FXML
    private void cadastrarFuncionario(){
        operacao = "Registar Funcionário";
        if(tableViewVisitane.getSelectionModel().getSelectedCells().size() == 1){
            funcionarioMod.setCodigoFuncionario(texteFiedcodigoFuncionario.getText(), operacao);
            funcionarioMod.setCargo(comboBoxCargo.getSelectionModel().getSelectedItem(), operacao);
            funcionarioMod.setIdUtente(Integer.valueOf(texteFiedcodigoUtente.getText()), operacao);
            if(funcionarioCon.registar(funcionarioMod, operacao)){
               this.bloquearItensDaJanela();
               this.limparItensDaJanela();
               throw new UtilControloExcessao(operacao, "Funcionário Cadastrado com sucesso", Alert.AlertType.CONFIRMATION);
            }
        }else{
           throw new UtilControloExcessao(operacao, "Seleccione o Utente a Cadastrar", Alert.AlertType.WARNING);
        }
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
                this.bloquearItensDaJanela();
                this.limparItensDaJanela();
               throw new UtilControloExcessao(operacao, "Utente não encontradao", Alert.AlertType.INFORMATION);
            }else{
                this.carregarResultadosNaTablea(todosRegistosEncontrados);
               this.bloquearItensDaJanela();
            }
        }
    }
    
    @FXML
    private void novo(){
        this.desbloquearItensDaJanela();
        this.limparItensDaJanela();
    }
    
    @FXML
    private void cancelar(){
        this.bloquearItensDaJanela();
        this.limparItensDaJanela();
    }
    
    @FXML
    private void sair(ActionEvent event) {
        AnchorPaneFuncionario.setVisible(false);
    }
    
    @FXML
    private void desbloquearItensDaJanela(){
        this.texteFiedcodigoFuncionario.setDisable(false);
        this.comboBoxCargo.setDisable(false);
        this.botaoNovo.setDisable(true);
        this.botaoCadastrar.setDisable(false);
    }
    
    private void bloquearItensDaJanela(){
        this.texteFiedcodigoFuncionario.setDisable(true);
        this.texteFiedcodigoUtente.setDisable(true);
        this.comboBoxCargo.setDisable(true);
        this.botaoNovo.setDisable(false);
        this.botaoCadastrar.setDisable(true);
        this.botaoAlterar.setDisable(true);
        this.botaoRemover.setDisable(true);
    }
    
    private void carregarValorNasComboxs(){
        this.comboBoxCargo.getItems().addAll("Administrador","Bibliotecario","Supervisor");
    }
    
    private void exibirDadosDoUtenteNosCampos(ModVisitante visitanteMod){
        if(tableViewVisitane.getSelectionModel().getSelectedCells().size() == 1){
            texteFiedcodigoUtente.setText(String.valueOf(visitanteMod.getIdUtente()));
        }else{
            texteFiedcodigoUtente.setText(null);
        }
    }
    
    private void exibirDadosDoFuncionarioNosCampos(ModFuncionario funcionario){
        if(tableViewFuncionario.getSelectionModel().getSelectedCells().size() == 1){
            texteFiedcodigoFuncionario.setText(funcionario.getCodigoFuncionario());
            comboBoxCargo.getSelectionModel().select(funcionario.getCargo());
            botaoAlterar.setDisable(false);
            botaoRemover.setDisable(false);
            this.desbloquearItensDaJanela();
            botaoNovo.setDisable(true);
            botaoCadastrar.setDisable(true);
        }else{
            botaoAlterar.setDisable(true);
            botaoRemover.setDisable(true);
            botaoNovo.setDisable(false);
            this.limparItensDaJanela();
        }
    }
    
    private void limparItensDaJanela(){
        this.texteFiedPesquisarUtente.setText(null);
        this.texteFiedPesquisarFuncionario.setText(null);
        this.texteFiedcodigoFuncionario.setText(null);
        this.texteFiedcodigoUtente.setText(null);
       // this.tableViewVisitane.getItems().clear();
        this.tableViewFuncionario.getItems().clear();
        this.comboBoxCargo.setPromptText("Carga");
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
