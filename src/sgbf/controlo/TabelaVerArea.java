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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sgbf.modelo.ModArea;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilValidarDados;

/**
 * FXML Controller class
 *
 * @author Marron
 */
public class TabelaVerArea implements Initializable {

    
    @FXML
    private JFXButton botaoPesquisar;
    @FXML
    private Button  botaoCadastrar, botaoAlterar, botaoRemover, botaoNovo, botaoCancelar, botaoSair;
    @FXML
    private TextField texteFiedPesquisar,texteFiedSector;
    @FXML
    private TableView<ModArea> tableViewArea; 
    @FXML
    private TableColumn<ModArea, String> tableColumSector,tableColumIDataRegisto,tableColumNmeroDataModificacao;
    @FXML
    private TableColumn<ModArea, Integer> tableColumId;
    @FXML
    private AnchorPane AnchorPaneUtente;
    
    private String operacao = null;
    private final ModArea areaMod = new ModArea();
    private final ConArea areaCon = new ConArea();
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       this.bloquearItensDaJanela();
       this.tableViewArea.setPlaceholder(new Label("Areas não listadas"));
       tableViewArea.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> this.exibirDadosNosCampos(newValue));
    }
    
    
     
    @FXML
    private void cadastrarArea(){
        operacao = "Registar Area";
        areaMod.setSector(texteFiedSector.getText(), operacao);
        if(areaCon.registar(areaMod, operacao)){
           this.bloquearItensDaJanela();
           this.limparItensDaJanela();
           throw new UtilControloExcessao(operacao, "Area Cadastrada com sucesso", Alert.AlertType.CONFIRMATION);
        }
    }
    
    
    @FXML
    private void alterarArea(){
        operacao = "Editar Area";
        areaMod.setIdArea(this.tableViewArea.getSelectionModel().getSelectedItem().getIdArea(), operacao);
        areaMod.setSector(texteFiedSector.getText(), operacao);
        if(areaCon.alterar(areaMod, operacao)){
           this.bloquearItensDaJanela();
           this.limparItensDaJanela();
           throw new UtilControloExcessao(operacao, "Area Alterada com sucesso", Alert.AlertType.CONFIRMATION);
        }
    }
    
    @FXML
    private void removerArea(){
        operacao = "Remover Area";
        ModArea areaARemover = this.tableViewArea.getSelectionModel().getSelectedItem();
        if(areaCon.remover(areaARemover, operacao)){
           this.tableViewArea.getItems().remove(areaARemover);
           this.bloquearItensDaJanela();
           throw new UtilControloExcessao(operacao, "Area removida com sucesso", Alert.AlertType.CONFIRMATION);
        }
    }
    
    @FXML
    private void pesquisarArea(){
        operacao = "Pesquisar Area";
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        if(this.texteFiedPesquisar.getText().isEmpty()){
           throw new UtilControloExcessao(operacao, "Introduza o código ou Sector do Area", Alert.AlertType.INFORMATION);
        }else{
            todosRegistosEncontrados = this.areaCon.pesquisar(this.pegarDadosDaPesquisa(), operacao);
            if(todosRegistosEncontrados.isEmpty()){
                this.bloquearItensDaJanela();
                this.limparItensDaJanela();
               throw new UtilControloExcessao(operacao, "Area não encontrada", Alert.AlertType.INFORMATION);
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
        AnchorPaneUtente.setVisible(false);
    }
   
    @FXML
    private void desbloquearItensDaJanela(){
        this.texteFiedSector.setDisable(false);
        this.botaoNovo.setDisable(true);
        this.botaoCadastrar.setDisable(false);
    }
    
    private void bloquearItensDaJanela(){
        this.texteFiedSector.setDisable(true);
        this.botaoNovo.setDisable(false);
        this.botaoCadastrar.setDisable(true);
        this.botaoAlterar.setDisable(true);
        this.botaoRemover.setDisable(true);
    }
    
    private void limparItensDaJanela(){
        this.texteFiedSector.setText(null);
        this.texteFiedPesquisar.setText(null);
        this.tableViewArea.getItems().clear();
    }
   
    private void exibirDadosNosCampos(ModArea areaMod){
        if(tableViewArea.getSelectionModel().getSelectedCells().size() == 1){
            texteFiedSector.setText(areaMod.getSector());
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
    
    private ModArea pegarDadosDaPesquisa(){
        if(UtilValidarDados.eNumero(this.texteFiedPesquisar.getText())){
           areaMod.setIdArea(Integer.valueOf(this.texteFiedPesquisar.getText()), operacao);
           areaMod.setSector(this.texteFiedPesquisar.getText(), operacao);
           return areaMod;
        }else{
           areaMod.setSector(this.texteFiedPesquisar.getText(), operacao);
           return areaMod;
        }
    }
    
    private void carregarResultadosNaTablea(List<Object> todosRegistosEncontrados){
        tableColumId.setCellValueFactory(new PropertyValueFactory<>("idArea"));
        tableColumSector.setCellValueFactory(new PropertyValueFactory<>("sector"));
        tableColumIDataRegisto.setCellValueFactory(new PropertyValueFactory<>("data_registo"));
        tableColumNmeroDataModificacao.setCellValueFactory(new PropertyValueFactory<>("data_modificacao"));
        tableViewArea.setItems(this.todosRegistosParaCarregar(todosRegistosEncontrados));
    }
    
    private ObservableList<ModArea> todosRegistosParaCarregar(List<Object> todosRegistosEncontrados){
        List<ModArea> listaDosRegistosWncontrados = new ArrayList<>();
        for(Object utenteRegistado: todosRegistosEncontrados){
            ModArea areaMod = (ModArea)utenteRegistado;
            listaDosRegistosWncontrados.add(areaMod);
        } 
        return FXCollections.observableArrayList(listaDosRegistosWncontrados);
    }
    
}
