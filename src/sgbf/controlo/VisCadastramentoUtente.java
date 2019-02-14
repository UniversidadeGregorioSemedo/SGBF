/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.controlo;

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
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import sgbf.modelo.ModVisitante;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilValidarDados;

/**
 * FXML Controller class
 *
 * @author Marron
 */
public class VisCadastramentoUtente implements Initializable {

    @FXML
    private Button botaoPesquisar,  botaoCadastrar, botaoAlterar, botaoRemover, botaoNovo, botaoCancelar, botaoFechar;
    @FXML
    private TextField texteFiedPesquisar,texteFiedId, texteFiedPrimeiroNome, texteFiedSegundoNome;
    @FXML
    private TextField texteFiedNumIden, texteFiedContacto, texteFiedEmail;
    @FXML
    private TextField texteFiedUsuario, texteFiedSenha, texteFiedEndereco, texteFiedEnderecoImagem;
    @FXML
    private ComboBox<String> comboBoxGenero, comboBoxTipoIndentificacao, comboBoxCategoria;
    @FXML
    private TableView<ModVisitante> tableViewUtente; 
    @FXML
    private TableColumn<ModVisitante, String> tableColumNome,tableColumIdTipoIdentificacao,tableColumNmeroIdentificacao,tableColumCategoria;
    @FXML
    private TableColumn<ModVisitante, Integer> tableColumId;
    @FXML
    private AnchorPane AnchorPaneUtente;
    
    private String operacao = null;
    private final ModVisitante utenteMod = new ModVisitante();
    private final ConUtente utenteCon = new ConUtente();
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       this.bloquearItensDaJanela();
       this.carregarValorNasComboxs();
       this.tableViewUtente.setPlaceholder(new Label("Utentes não listados"));
       this.texteFiedPesquisar.setTooltip(new Tooltip("Introduza o código, nome do utente ou use *( _ ) para listar todos registos "));
       tableViewUtente.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> this.exibirDadosNosCampos(newValue));
    }
    
    
    @FXML
    private void cadastrarUtente(){
        operacao = "Registar Utente";
        utenteMod.setPrimeiro_nome(texteFiedPrimeiroNome.getText(), operacao);
        utenteMod.setSegundo_nome(texteFiedSegundoNome.getText(), operacao);
        utenteMod.setGenero(comboBoxGenero.getSelectionModel().getSelectedItem(), operacao);
        utenteMod.setTipo_identificacao(comboBoxTipoIndentificacao.getSelectionModel().getSelectedItem(), operacao);
        utenteMod.setNumero(texteFiedNumIden.getText(), operacao);
        utenteMod.setContacto(texteFiedContacto.getText(),operacao);
        utenteMod.setEmail(texteFiedEmail.getText(),operacao);
        utenteMod.setEndereco(texteFiedEndereco.getText(), operacao);
        utenteMod.setCategoria(comboBoxCategoria.getSelectionModel().getSelectedItem(),operacao);
        utenteMod.setUsuario(texteFiedUsuario.getText(), operacao);
        utenteMod.setSenha(texteFiedSenha.getText(), operacao);
        if(utenteCon.registar(utenteMod, operacao)){
           this.bloquearItensDaJanela();
           this.limparItensDaJanela();
           throw new UtilControloExcessao(operacao, "Utente Cadastrado com sucesso", Alert.AlertType.CONFIRMATION);
        }
    }
    
    
    @FXML
    private void alterarUtente(){
        operacao = "Editar Utente";
        utenteMod.setIdUtente(Integer.valueOf(texteFiedId.getText()), operacao);
        utenteMod.setPrimeiro_nome(texteFiedPrimeiroNome.getText(), operacao);
        utenteMod.setSegundo_nome(texteFiedSegundoNome.getText(), operacao);
        utenteMod.setGenero(comboBoxGenero.getSelectionModel().getSelectedItem(), operacao);
        utenteMod.setTipo_identificacao(comboBoxTipoIndentificacao.getSelectionModel().getSelectedItem(), operacao);
        utenteMod.setNumero(texteFiedNumIden.getText(), operacao);
        utenteMod.setContacto(texteFiedContacto.getText(),operacao);
        utenteMod.setEmail(texteFiedEmail.getText(),operacao);
        utenteMod.setEndereco(texteFiedEndereco.getText(), operacao);
        utenteMod.setCategoria(comboBoxCategoria.getSelectionModel().getSelectedItem(),operacao);
        utenteMod.setUsuario(texteFiedUsuario.getText(), operacao);
        utenteMod.setSenha(texteFiedSenha.getText(), operacao);
        if(utenteCon.alterar(utenteMod, operacao)){
           this.bloquearItensDaJanela();
           this.limparItensDaJanela();
           throw new UtilControloExcessao(operacao, "Utente alterado com sucesso", Alert.AlertType.CONFIRMATION);
        }
    }
    
    @FXML
    private void removerUtente(){
        operacao = "Remover Utente";
        ModVisitante utenteARemover = this.tableViewUtente.getSelectionModel().getSelectedItem();
        if(utenteCon.remover(utenteARemover, operacao)){
           this.tableViewUtente.getItems().remove(utenteARemover);
           this.bloquearItensDaJanela();
           throw new UtilControloExcessao(operacao, "Utente removido com sucesso", Alert.AlertType.CONFIRMATION);
        }
    }
    
    @FXML
    private void pesquisarUtente(){
        operacao = "Pesquisar Utente";
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        if(this.texteFiedPesquisar.getText().isEmpty()){
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
        AnchorPaneUtente.setVisible(false);
    }
   
    @FXML
    public void eliminarEspacoEmBranco(KeyEvent evt) {
        String caracateresValidos = " ";
        if (caracateresValidos.contains(evt.getCharacter() + "")) {
            evt.consume();
        }
    }
   
    private void desbloquearItensDaJanela(){
        this.texteFiedPrimeiroNome.setDisable(false);
        this.texteFiedSegundoNome.setDisable(false);
        this.texteFiedNumIden.setDisable(false);
        this.texteFiedContacto.setDisable(false);
        this.texteFiedEmail.setDisable(false);
        this.texteFiedUsuario.setDisable(false);
        this.texteFiedSenha.setDisable(false);
        this.texteFiedEndereco.setDisable(false);
        this.texteFiedEnderecoImagem.setDisable(true);
        this.comboBoxGenero.setDisable(false);
        this.comboBoxTipoIndentificacao.setDisable(false);
        this.comboBoxCategoria.setDisable(false);
        this.botaoNovo.setDisable(true);
        this.botaoCadastrar.setDisable(false);
    }
    
    private void bloquearItensDaJanela(){
        this.texteFiedId.setDisable(true);
        this.texteFiedPrimeiroNome.setDisable(true);
        this.texteFiedSegundoNome.setDisable(true);
        this.texteFiedNumIden.setDisable(true);
        this.texteFiedContacto.setDisable(true);
        this.texteFiedEmail.setDisable(true);
        this.texteFiedUsuario.setDisable(true);
        this.texteFiedSenha.setDisable(true);
        this.texteFiedEndereco.setDisable(true);
        this.texteFiedEnderecoImagem.setDisable(true);
        this.comboBoxGenero.setDisable(true);
        this.comboBoxTipoIndentificacao.setDisable(true);
        this.comboBoxCategoria.setDisable(true);
        this.botaoNovo.setDisable(false);
        this.botaoCadastrar.setDisable(true);
        this.botaoAlterar.setDisable(true);
        this.botaoRemover.setDisable(true);
    }
    
    private void limparItensDaJanela(){
        this.texteFiedId.setText(null);
        this.texteFiedPrimeiroNome.setText(null);
        this.texteFiedSegundoNome.setText(null);
        this.texteFiedNumIden.setText(null);
        this.texteFiedContacto.setText(null);
        this.texteFiedEmail.setText(null);
        this.texteFiedUsuario.setText(null);
        this.texteFiedSenha.setText(null);
        this.texteFiedEndereco.setText(null);
        this.texteFiedEnderecoImagem.setText(null);
        this.texteFiedPesquisar.setText(null);
        this.tableViewUtente.getItems().clear();
        this.comboBoxGenero.setPromptText("Gênero");
        this.comboBoxTipoIndentificacao.setPromptText("Identificação");
        this.comboBoxCategoria.setPromptText("Categoria");
    }
   
    private void carregarValorNasComboxs(){
        this.comboBoxGenero.getItems().addAll("Masculino","Femenino","Outro");
        this.comboBoxTipoIndentificacao.getItems().addAll("BI","Carta de condução","Passport","Cartão Escolar");
        this.comboBoxCategoria.getItems().addAll("Funcionário", "Professor", "Estudante");
    }

    
    private void exibirDadosNosCampos(ModVisitante visitanteMod){
        if(tableViewUtente.getSelectionModel().getSelectedCells().size() == 1){
            texteFiedId.setText(String.valueOf(visitanteMod.getIdUtente()));
            texteFiedPrimeiroNome.setText(visitanteMod.getPrimeiro_nome());
            texteFiedSegundoNome.setText(visitanteMod.getSegundo_nome());
            texteFiedNumIden.setText(visitanteMod.getNumero());
            texteFiedContacto.setText(visitanteMod.getContacto());
            texteFiedEmail.setText(visitanteMod.getEmail());
            texteFiedEndereco.setText(visitanteMod.getEndereco());
            texteFiedUsuario.setText(visitanteMod.getUsuario());
            texteFiedSenha.setText(visitanteMod.getSenha());
            comboBoxGenero.getSelectionModel().select(visitanteMod.getGenero());
            comboBoxTipoIndentificacao.getSelectionModel().select(visitanteMod.getTipo_identificacao());
            comboBoxCategoria.getSelectionModel().select(visitanteMod.getCategoria());
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
    
    private ModVisitante pegarDadosDaPesquisa(){
        if(UtilValidarDados.eNumero(this.texteFiedPesquisar.getText())){
           utenteMod.setIdUtente(Integer.valueOf(this.texteFiedPesquisar.getText()), operacao);
           utenteMod.setPrimeiro_nome(this.texteFiedPesquisar.getText(), operacao);
           return utenteMod;
        }else{
           utenteMod.setPrimeiro_nome(this.texteFiedPesquisar.getText(), operacao);
           return utenteMod;
        }
    }
    
    private void carregarResultadosNaTablea(List<Object> todosRegistosEncontrados){
        tableColumId.setCellValueFactory(new PropertyValueFactory<>("idUtente"));
        tableColumNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumIdTipoIdentificacao.setCellValueFactory(new PropertyValueFactory<>("tipo_identificacao"));
        tableColumNmeroIdentificacao.setCellValueFactory(new PropertyValueFactory<>("numero"));
        tableColumCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        tableViewUtente.setItems(this.todosRegistosParaCarregar(todosRegistosEncontrados));
    }
    
    private ObservableList<ModVisitante> todosRegistosParaCarregar(List<Object> todosRegistosEncontrados){
        List<ModVisitante> listaDosRegistosWncontrados = new ArrayList<>();
        for(Object utenteRegistado: todosRegistosEncontrados){
            ModVisitante visitanteMod = (ModVisitante)utenteRegistado;
            listaDosRegistosWncontrados.add(visitanteMod);
        } 
        return FXCollections.observableArrayList(listaDosRegistosWncontrados);
    }
    
}
