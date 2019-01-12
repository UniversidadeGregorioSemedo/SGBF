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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sgbf.modelo.ModProveniencia;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilValidarDados;

/**
 * FXML Controller class
 *
 * @author Marron
 */
public class VisCadastramentoProveniencia implements Initializable {

    @FXML
    private TextField TextFieldPesquisarProveniencia,textFieldCodigoProveniencia;
    @FXML
    private JFXButton botaoPesquisarProveniencia;
    @FXML
    private TableView<ModProveniencia> tabelaViewProveniencia;
    @FXML
    private ComboBox<String> combocadproveniencia;
    @FXML
    private Button botaoNovo,BotaoCadastrar,BotaoAlterar,BotaoRemover,BotaoSair,botaoCancelar;
    @FXML
    private AnchorPane anchoPaneProveniencia;
    @FXML
    private TableColumn<ModProveniencia, Integer> tabelaColunaId;
    @FXML
    private TableColumn<ModProveniencia, String> tabelaColunaTipo,tabelaColunaRegistro,tabelaColunaModificacao;
    
    
    private String operacao = null;
    private final ModProveniencia provenienciamod = new ModProveniencia();
    private final ConProveniencia provenienciacom = new ConProveniencia();
 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.bloquearItensDaJanela();
        this.carregarValorNasComboxs();
        this.tabelaViewProveniencia.setPlaceholder(new Label("Proveniencia não listadas"));
        tabelaViewProveniencia.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> this.exibirDadosNosCampos(newValue));
    } 
    private void bloquearItensDaJanela(){
        //this.TextFieldPesquisarProveniencia.setDisable(false);
        this.textFieldCodigoProveniencia.setDisable(true);
        this.combocadproveniencia.setDisable(true);
        this.botaoNovo.setDisable(false);
        this.BotaoCadastrar.setDisable(true);
        this.BotaoAlterar.setDisable(true);
        this.BotaoRemover.setDisable(true);
}
    private void desbloquearItensDaJanela(){
        this.textFieldCodigoProveniencia.setDisable(true);
        this.combocadproveniencia.setDisable(false);
        this.botaoNovo.setDisable(true);
        this.BotaoCadastrar.setDisable(false); 
    }
    private void limparItensDaJanela(){
        this.combocadproveniencia.setPromptText("Proveniencia");
        this.textFieldCodigoProveniencia.setText(null);
        this.tabelaViewProveniencia.getItems().clear();
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
        anchoPaneProveniencia.setVisible(false);
    }
    
    @FXML
    private void cadastrarProveniencia(){
        operacao = "Registar Proveniencia";
       provenienciamod.setTipo(combocadproveniencia.getSelectionModel().getSelectedItem(), operacao);
        if(provenienciacom.registar(provenienciamod, operacao)){
           this.bloquearItensDaJanela();
           this.limparItensDaJanela();
           throw new UtilControloExcessao(operacao, "Proveniencia Cadastrada com sucesso", Alert.AlertType.CONFIRMATION);
        }
    }
    
    @FXML
    private void alterarProveniencia(){
        operacao = "Alterar Proveniencia";
        provenienciamod.setIdProveniencia(this.tabelaViewProveniencia.getSelectionModel().getSelectedItem().getIdProveniencia(), operacao);
        provenienciamod.setTipo(combocadproveniencia.getSelectionModel().getSelectedItem(), operacao);
        if(provenienciacom.alterar(provenienciamod, operacao)){
        this.bloquearItensDaJanela();
        this.limparItensDaJanela();
        throw new UtilControloExcessao(operacao, "Proveniencia Alterada com sucesso", Alert.AlertType.CONFIRMATION);
        } 
    }
    
    
    @FXML
    private void removerProvenienica(){
    operacao = "Remover Area";
        ModProveniencia provenienciaRemover = this.tabelaViewProveniencia.getSelectionModel().getSelectedItem();
        if(provenienciacom.remover(provenienciaRemover, operacao)){
        this.tabelaViewProveniencia.getItems().remove(provenienciaRemover);
        this.bloquearItensDaJanela();
        throw new UtilControloExcessao(operacao, "Proveniencia removida com sucesso", Alert.AlertType.CONFIRMATION);
        }
    }    
    
    @FXML
    private void pesquisarProveniencia(){
    operacao = "Pesquisar Proveniencia";
    List<Object> todosRegistosEncontrados = new ArrayList<>();
    if(this.TextFieldPesquisarProveniencia.getText().isEmpty()){
       throw new UtilControloExcessao(operacao, "Introduza o código ou Tipo de Doação", Alert.AlertType.INFORMATION);
    }else{
        todosRegistosEncontrados = this.provenienciacom.pesquisar(this.pegarDadosDaPesquisa(), operacao);
        if(todosRegistosEncontrados.isEmpty()){
            this.bloquearItensDaJanela();
            this.limparItensDaJanela();
           throw new UtilControloExcessao(operacao, "Proveniencia não encontrada", Alert.AlertType.INFORMATION);
        }else{
            this.carregarResultadosNaTablea(todosRegistosEncontrados);
            this.bloquearItensDaJanela();
        }
    }
}
 
    private void exibirDadosNosCampos(ModProveniencia provenienciamod){
        if(tabelaViewProveniencia.getSelectionModel().getSelectedCells().size() == 1){
            textFieldCodigoProveniencia.setText(String.valueOf(provenienciamod.getIdProveniencia()));
            combocadproveniencia.getSelectionModel().select(provenienciamod.getTipo());
            BotaoAlterar.setDisable(false);
            BotaoRemover.setDisable(false);
            this.desbloquearItensDaJanela();
            botaoNovo.setDisable(true);
            BotaoCadastrar.setDisable(true);
        }else{
            BotaoAlterar.setDisable(true);
            BotaoRemover.setDisable(true);
            botaoNovo.setDisable(false);
            this.limparItensDaJanela();
        }
    }
    private ModProveniencia pegarDadosDaPesquisa(){
        if(UtilValidarDados.eNumero(this.TextFieldPesquisarProveniencia.getText())){
           provenienciamod.setIdProveniencia(Integer.valueOf(this.TextFieldPesquisarProveniencia.getText()),operacao);
           //Estou sem ideia como pesquisar a proveniencia pelo tipo!!
           return provenienciamod;
        }else{
            //Estou sem ideia como pesquisar a proveniencia pelo tipo!!
            return provenienciamod;
        }   
    }
     private void carregarResultadosNaTablea(List<Object> todosRegistosEncontrados){
         tabelaColunaId.setCellValueFactory(new PropertyValueFactory<>("idProveniencia"));
        tabelaColunaTipo.setCellValueFactory(new PropertyValueFactory<>("Tipo"));
        tabelaColunaRegistro.setCellValueFactory(new PropertyValueFactory<>("data_registo"));
        tabelaColunaModificacao.setCellValueFactory(new PropertyValueFactory<>("data_modificao"));
        tabelaViewProveniencia.setItems(this.todosRegistosParaCarregar(todosRegistosEncontrados));
     }
      private ObservableList<ModProveniencia> todosRegistosParaCarregar(List<Object> todosRegistosEncontrados){
        List<ModProveniencia> listaDosRegistosWncontrados = new ArrayList<>();
        for(Object utenteRegistado: todosRegistosEncontrados){
            ModProveniencia provenienciamod = (ModProveniencia)utenteRegistado;
            listaDosRegistosWncontrados.add(this.provenienciamod);
        } 
        return FXCollections.observableArrayList(listaDosRegistosWncontrados);
    }

     
    private void carregarValorNasComboxs(){
        this.combocadproveniencia.getItems().addAll("Doação","Compra","Outro");
    }
    
}
    
    



