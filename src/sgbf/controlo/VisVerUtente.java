/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.controlo;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sgbf.modelo.ModVisitante;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilValidarDados;

/**
 * FXML Controller class
 *
 * @author Marron
 */
public class VisVerUtente implements Initializable {

    @FXML
    private JFXButton botaoPesquisar;
    @FXML
    private JFXButton botaoSair, botaoCancelar;
    @FXML
    private TextField texteFiedPesquisar;
    @FXML
    private JFXToggleButton toggleButtonMaisInformacoes;
    @FXML
    private TableView<ModVisitante> tableViewVisitane;
    @FXML
    private TableColumn<ModVisitante, String> tableColumId, tableColumNome, tableColumIdTipoIdentificacao,
            tableColumNmeroIdentificacao, tableColumContacto, tableColumCategoria,tableColumEndereco;
    @FXML
    private Label labelUsuario, labelEmail, labelEnderecoImagem, labelInstituicao, labelDataRegisto, labelModificacao;
    @FXML
    private AnchorPane AnchorPaneVisitante;
    
      
    private String operacao = null;
    private final ModVisitante utenteMod = new ModVisitante();
    private final ConUtente utenteCon = new ConUtente();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.tableViewVisitane.setPlaceholder(new Label("Utentes não listados"));
        this.texteFiedPesquisar.setTooltip(new Tooltip("Introduza o código, nome do utente ou use *( _ ) para listar todos registos "));
        this.tableViewVisitane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> this.carregarResultados(newValue));
    }

    @FXML
    private void pesquisarUtente(){
        operacao = "Pesquisar Utente";
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        if(this.texteFiedPesquisar.getText().isEmpty()){
           throw new UtilControloExcessao(operacao, "Introduza o código ou nome do Utente", Alert.AlertType.INFORMATION);
        }else{
            todosRegistosEncontrados = this.utenteCon.pesquisar(this.pegarDadosDaPesquisaUtente(), operacao);
            if(todosRegistosEncontrados.isEmpty()){
                this.limparItensDaJanela();
               throw new UtilControloExcessao(operacao, "Utente não encontradao", Alert.AlertType.INFORMATION);
            }else{
                this.carregarResultadosNaTableaUtente(todosRegistosEncontrados);
            }
        }
    }
    
    private ModVisitante pegarDadosDaPesquisaUtente(){
        if(UtilValidarDados.eNumero(this.texteFiedPesquisar.getText())){
           utenteMod.setIdUtente(Integer.valueOf(this.texteFiedPesquisar.getText()), operacao);
           utenteMod.setPrimeiro_nome(this.texteFiedPesquisar.getText(), operacao);
           return utenteMod;
        }else{
           utenteMod.setPrimeiro_nome(this.texteFiedPesquisar.getText(), operacao);
           return utenteMod;
        }
    }
    
    
    private void carregarResultadosNaTableaUtente(List<Object> todosRegistosEncontrados){
        tableColumId.setCellValueFactory(new PropertyValueFactory<>("idUtente"));
        tableColumNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumIdTipoIdentificacao.setCellValueFactory(new PropertyValueFactory<>("tipo_identificacao"));
        tableColumNmeroIdentificacao.setCellValueFactory(new PropertyValueFactory<>("numero"));
        tableColumContacto.setCellValueFactory(new PropertyValueFactory<>("contacto"));
        tableColumEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        tableColumCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        tableViewVisitane.setItems(this.todosUtentesParaCarregar(todosRegistosEncontrados));
    }
    
    private ObservableList<ModVisitante> todosUtentesParaCarregar(List<Object> todosRegistosEncontrados){
        List<ModVisitante> listaDosRegistosWncontrados = new ArrayList<>();
        for(Object utenteRegistado: todosRegistosEncontrados){
            ModVisitante visitanteMod = (ModVisitante)utenteRegistado;
            listaDosRegistosWncontrados.add(visitanteMod);
        }
        return FXCollections.observableArrayList(listaDosRegistosWncontrados);
    }
    
    private void carregarResultados(ModVisitante visitanteMod) {
        if(visitanteMod != null){
            labelUsuario.setText(visitanteMod.getUsuario()); 
            labelEmail.setText(visitanteMod.getEmail()); 
            labelEnderecoImagem.setText(visitanteMod.getEndereco_imagem());
            labelInstituicao.setText("Nenhuma informação"); 
            labelDataRegisto.setText(visitanteMod.getUtilControloDaData().getData_registo());
            labelModificacao.setText(visitanteMod.getUtilControloDaData().getData_modificacao());
        }
    }
    
    @FXML
    private void limparItensDaJanela(){
        texteFiedPesquisar.setText(null);
        labelUsuario.setText("Nenhuma informação");
        labelEmail.setText("Nenhuma informação");
        labelEnderecoImagem.setText("Nenhuma informação");
        labelInstituicao.setText("Nenhuma informação");
        labelDataRegisto.setText("Nenhuma informação");
        labelModificacao.setText("Nenhuma informação");
        tableViewVisitane.getItems().clear();
    }
    
    @FXML
    private void sair(ActionEvent event) {
        AnchorPaneVisitante.setVisible(false);
    }

}
