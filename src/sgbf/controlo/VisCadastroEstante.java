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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import sgbf.modelo.ModArea;
import sgbf.modelo.ModEstante;
import sgbf.modelo.ModVisitante;
import sgbf.util.UtilControloExcessao;

/**
 * FXML Controller class
 *
 * @author Marron
 */
public class VisCadastroEstante implements Initializable {
    
    @FXML
    private JFXButton botaoPesquisar;
    @FXML
    private Button   botaoCadastrar, botaoAlterar, botaoRemover, botaoNovo, botaoCancelar, botaoSair;
    @FXML
    private TextField texteFiedPesquisar,texteFiedDesigancao, texteFiedLinha;
    @FXML
    private TextField  texteFiedColuna, texteFiedDescricao;
    @FXML
    private ComboBox<ModArea> comboBoxArea;
    @FXML
    private TableView<ModEstante> tableViewEstante; 
    @FXML
    private TableColumn<ModEstante, String> tableColumDesignacao,tableColumDescricao,tableColumLinha,tableColumcoluna;
    @FXML
    private AnchorPane AnchorPaneEstante;
    
    private String operacao = null;
    private final ModEstante estanteMod = new ModEstante();
    private final ConEstante estanteCon = new ConEstante();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       this.bloquearItensDaJanela();
       this.carregarValorNasComboxs();
       this.tableViewEstante.setPlaceholder(new Label("Estantes não listadas"));
       tableViewEstante.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> this.exibirDadosNosCampos(newValue));
    }
    
    @FXML
    private void cadastrarEstante(){
        operacao = "Registar Estante";
        estanteMod.setDesignacao(texteFiedDesigancao.getText(), operacao);
        estanteMod.setLinha(Byte.valueOf(texteFiedLinha.getText()), operacao);
        estanteMod.setColuna(Byte.valueOf(texteFiedColuna.getText()), operacao);
        estanteMod.setDescricao(texteFiedDescricao.getText(), operacao);
        estanteMod.setAreaMod(comboBoxArea.getSelectionModel().getSelectedItem(), operacao);
        if(estanteCon.registar(estanteMod, operacao)){
           this.bloquearItensDaJanela();
           this.limparItensDaJanela();
           throw new UtilControloExcessao(operacao, "Estante Cadastrada com sucesso", Alert.AlertType.CONFIRMATION);
        }
    }
    
    
    @FXML
    private void alterarEstante(){
        operacao = "Editar Estante";
        estanteMod.setIdEstante(this.tableViewEstante.getSelectionModel().getSelectedItem().getIdEstante(), operacao);
        estanteMod.setDesignacao(texteFiedDesigancao.getText(), operacao);
        estanteMod.setLinha(Byte.valueOf(texteFiedLinha.getText()), operacao);
        estanteMod.setColuna(Byte.valueOf(texteFiedColuna.getText()), operacao);
        estanteMod.setDescricao(texteFiedDescricao.getText(), operacao);
        estanteMod.setAreaMod(comboBoxArea.getSelectionModel().getSelectedItem(), operacao);
        if(estanteCon.alterar(estanteMod, operacao)){
           this.bloquearItensDaJanela();
           this.limparItensDaJanela();
           throw new UtilControloExcessao(operacao, "Estante Editada com sucesso", Alert.AlertType.CONFIRMATION);
        }
    }
    
    @FXML
    private void removerEstante(){
        operacao = "Remover Estante";
        ModEstante estanteARemover = this.tableViewEstante.getSelectionModel().getSelectedItem();
        if(estanteCon.remover(estanteMod, operacao)){
           this.tableViewEstante.getItems().remove(estanteARemover);
           this.bloquearItensDaJanela();
           throw new UtilControloExcessao(operacao, "Estante removida com sucesso", Alert.AlertType.CONFIRMATION);
        }
    }
    
    @FXML
    private void pesquisarEstante(){
        operacao = "Pesquisar Estante";
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        if(this.texteFiedPesquisar.getText().isEmpty()){
           throw new UtilControloExcessao(operacao, "Introduza o código ou designação da estante", Alert.AlertType.INFORMATION);
        }else{
            todosRegistosEncontrados = this.estanteCon.pesquisar(this.pegarDadosDaPesquisa(), operacao);
            if(todosRegistosEncontrados.isEmpty()){
                this.bloquearItensDaJanela();
                this.limparItensDaJanela();
               throw new UtilControloExcessao(operacao, "Estante não encontrada", Alert.AlertType.INFORMATION);
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
        AnchorPaneEstante.setVisible(false);
    }
   
    @FXML
    private void desbloquearItensDaJanela(){
        this.texteFiedDesigancao.setDisable(false);
        this.texteFiedLinha.setDisable(false);
        this.texteFiedDescricao.setDisable(false);
        this.texteFiedColuna.setDisable(false);
        this.comboBoxArea.setDisable(false);
        this.botaoNovo.setDisable(true);
        this.botaoCadastrar.setDisable(false);
    }
    
    private void bloquearItensDaJanela(){
        this.texteFiedDesigancao.setDisable(true);
        this.texteFiedLinha.setDisable(true);
        this.texteFiedDescricao.setDisable(true);
        this.texteFiedColuna.setDisable(true);
        this.comboBoxArea.setDisable(true);
        this.botaoNovo.setDisable(false);
        this.botaoCadastrar.setDisable(true);
        this.botaoAlterar.setDisable(true);
        this.botaoRemover.setDisable(true);
    }
    
    private void limparItensDaJanela(){
        this.texteFiedDesigancao.setText(null);
        this.texteFiedLinha.setText(null);
        this.texteFiedDescricao.setText(null);
        this.texteFiedColuna.setText(null);
        this.texteFiedPesquisar.setText(null);
        this.tableViewEstante.getItems().clear();
    }
   
    private void carregarValorNasComboxs(){
        ConArea areaCon = new ConArea();
        List<ModArea> todasAreas = new ArrayList<>();
        ObservableList todasAreasParaCombox =null;
        for(Object todosRegistos: areaCon.listarTodos("Cadastramento de Estante")){
            ModArea areaRegistada = (ModArea)todosRegistos;
            todasAreas.add(areaRegistada);
        }
        todasAreasParaCombox = FXCollections.observableArrayList(todasAreas);
        this.comboBoxArea.setItems(todasAreasParaCombox);
    }

    
    private void exibirDadosNosCampos(ModEstante estanteMod){
        if(tableViewEstante.getSelectionModel().getSelectedCells().size() == 1){
            texteFiedDesigancao.setText(String.valueOf(estanteMod.getDesignacao()));
            texteFiedLinha.setText(String.valueOf(estanteMod.getLinha()));
            texteFiedDescricao.setText(estanteMod.getDescricao());
            texteFiedColuna.setText(String.valueOf(estanteMod.getColuna()));
            comboBoxArea.getSelectionModel().select(estanteMod.getAreaMod());
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
    
    private ModEstante pegarDadosDaPesquisa(){
        if(this.texteFiedPesquisar.getText().contains("123456789")){
           estanteMod.setIdEstante(Integer.valueOf(this.texteFiedPesquisar.getText()), operacao);
           estanteMod.setDesignacao(this.texteFiedPesquisar.getText(), operacao);
           return estanteMod;
        }else{
           estanteMod.setDesignacao(this.texteFiedPesquisar.getText(), operacao);
           return estanteMod;
        }
    }
    
    private void carregarResultadosNaTablea(List<Object> todosRegistosEncontrados){
        tableColumDesignacao.setCellValueFactory(new PropertyValueFactory<>("designacao"));
        tableColumDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tableColumLinha.setCellValueFactory(new PropertyValueFactory<>("linha"));
        tableColumcoluna.setCellValueFactory(new PropertyValueFactory<>("coluna"));
        tableViewEstante.setItems(this.todosRegistosParaCarregar(todosRegistosEncontrados));
    }
    
    private ObservableList<ModEstante> todosRegistosParaCarregar(List<Object> todosRegistosEncontrados){
        List<ModEstante> listaDosRegistosWncontrados = new ArrayList<>();
        for(Object estanteRegistado: todosRegistosEncontrados){
            ModEstante estanteMod = (ModEstante)estanteRegistado;
            listaDosRegistosWncontrados.add(estanteMod);
        } 
        return FXCollections.observableArrayList(listaDosRegistosWncontrados);
    }
    
    @FXML
    public void validarDadosNumericos(KeyEvent evt){
        String caracateresValidos = "1234567890";
        if(!caracateresValidos.contains(evt.getCharacter()+"")){
            evt.consume();
        }
    }

    
    
}
