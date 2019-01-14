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
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;
import sgbf.modelo.ModAcervo;
import sgbf.modelo.ModArea;
import sgbf.modelo.ModItemProveniente;
import sgbf.modelo.ModProveniencia;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilValidarDados;

/**
 * FXML Controller class
 *
 * @author Marron
 */
public class VisCadastramentoItensProvenientes implements Initializable {

     @FXML
    private JFXButton botaoPesquisarAcervo,botaoPesquisarEntrada;
    @FXML
    private Button botaoCadastrar, botaoAlterar, botaoRemover, botaoNovo, botaoCancelar, botaoSair;
    @FXML
    private TextField texteFiedPesquisarAcervo, texteFiedPesquisarEntrada, texteFiedQuantidade,
            texteFiedSubtotal;
    @FXML
    private TableView<ModAcervo> tableViewAcervo;
    @FXML
    private ComboBox<ModProveniencia> comboProveniencia;
    @FXML
    private TableView<ModItemProveniente> tableViewItemProveniente;
    @FXML
    private TableColumn<ModAcervo, Integer> tableColumId,tableColumEdicao, tableColumAno;
    @FXML
    private TableColumn<ModAcervo, String> tableColumQuantidade;
    @FXML
    private TableColumn<ModAcervo, String> tableColumTitulo,tableColumFormato,tableColumISBN;
    @FXML
    private AnchorPane AnchorPaneItemProveniente;
    
    private String operacao = null;
    private final ModItemProveniente itemProvenienteMod = new ModItemProveniente();
    private final ModAcervo acervoMod = new ModAcervo();
    private final ContItemProveniente itemProvenienteCon = new ContItemProveniente();
    private final ConAcervo acervoCon = new ConAcervo();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //this.bloquearItensDaJanela();
        this.tableViewAcervo.setPlaceholder(new Label("Acervos não listadas"));
        this.tableViewItemProveniente.setPlaceholder(new Label("Entradas não listadas"));
        tableViewAcervo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> this.exibirDadosNosCamposAcervo(newValue));
        //tableViewItemProveniente.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> this.exibirDadosNosProveniencias(newValue));
    }    
    
    @FXML
    private void cadastrarProveniencia() {
        /*operacao = "Registar Area";
        areaMod.setSector(texteFiedSector.getText(), operacao);
        if (areaCon.registar(areaMod, operacao)) {
            this.bloquearItensDaJanela();
            this.limparItensDaJanela();
            throw new UtilControloExcessao(operacao, "Area Cadastrada com sucesso", Alert.AlertType.CONFIRMATION);
        }*/
    }

    @FXML
    private void alterarProveniencia() {
        /*operacao = "Editar Area";
        areaMod.setIdArea(this.tableViewArea.getSelectionModel().getSelectedItem().getIdArea(), operacao);
        areaMod.setSector(texteFiedSector.getText(), operacao);
        if (areaCon.alterar(areaMod, operacao)) {
            this.bloquearItensDaJanela();
            this.limparItensDaJanela();
            throw new UtilControloExcessao(operacao, "Area Alterada com sucesso", Alert.AlertType.CONFIRMATION);
        }*/
    }

    @FXML
    private void removerProveniencia() {
        /*operacao = "Remover Area";
        ModArea areaARemover = this.tableViewArea.getSelectionModel().getSelectedItem();
        if (areaCon.remover(areaARemover, operacao)) {
            this.tableViewArea.getItems().remove(areaARemover);
            this.bloquearItensDaJanela();
            throw new UtilControloExcessao(operacao, "Area removida com sucesso", Alert.AlertType.CONFIRMATION);
        }*/
    }

    @FXML
    private void pesquisarProveniencia() {
        /*operacao = "Pesquisar Area";
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        if (this.texteFiedPesquisar.getText().isEmpty()) {
            throw new UtilControloExcessao(operacao, "Introduza o código ou Sector do Area", Alert.AlertType.INFORMATION);
        } else {
            todosRegistosEncontrados = this.areaCon.pesquisar(this.pegarDadosDaPesquisa(), operacao);
            if (todosRegistosEncontrados.isEmpty()) {
                this.bloquearItensDaJanela();
                this.limparItensDaJanela();
                throw new UtilControloExcessao(operacao, "Area não encontrada", Alert.AlertType.INFORMATION);
            } else {
                this.carregarResultadosNaTablea(todosRegistosEncontrados);
                this.bloquearItensDaJanela();
            }
        }*/
    }
    
    @FXML
    private void pesquisarAcervos() {
         operacao = "Pesquisar Acervos";
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        if(this.texteFiedPesquisarAcervo.getText().isEmpty()){
           throw new UtilControloExcessao(operacao, "Introduza o código ou título do acervos", Alert.AlertType.INFORMATION);
        }else{
            todosRegistosEncontrados = this.acervoCon.pesquisar(this.pegarDadosDaPesquisaAcervo(), operacao);
            if(todosRegistosEncontrados.isEmpty()){
                this.bloquearItensDaJanela();
                this.limparItensDaJanela();
               throw new UtilControloExcessao(operacao, "Acervo não encontrada", Alert.AlertType.INFORMATION);
            }else{
                this.carregarResultadosNaTableaAcervo(todosRegistosEncontrados);
                this.bloquearItensDaJanela();
            }
        }
    }

    @FXML
    private void novo() {
        this.desbloquearItensDaJanela();
        this.limparItensDaJanela();
    }

    @FXML
    private void cancelar() {
        this.bloquearItensDaJanela();
        this.limparItensDaJanela();
    }

    @FXML
    private void sair(ActionEvent event) {
        AnchorPaneItemProveniente.setVisible(false);
    }

    @FXML
    private void desbloquearItensDaJanela() {
        this.texteFiedQuantidade.setDisable(false);
        this.texteFiedSubtotal.setDisable(false);
        this.comboProveniencia.setDisable(false);
        this.botaoNovo.setDisable(true);
        this.botaoCadastrar.setDisable(false);
    }

    private void bloquearItensDaJanela() {
        this.texteFiedQuantidade.setDisable(true);
        this.texteFiedSubtotal.setDisable(true);
        this.comboProveniencia.setDisable(true);
        this.botaoNovo.setDisable(false);
        this.botaoCadastrar.setDisable(true);
        this.botaoAlterar.setDisable(true);
        this.botaoRemover.setDisable(true);
    }

    private void limparItensDaJanela() {
        this.texteFiedPesquisarAcervo.setText(null);
        this.texteFiedPesquisarEntrada.setText(null);
        this.texteFiedQuantidade.setText(null);
        this.texteFiedSubtotal.setText(null);
        this.comboProveniencia.getItems().clear();
        this.tableViewAcervo.getItems().clear();
        this.tableViewItemProveniente.getItems().clear();
    }

    private void exibirDadosNosCamposAcervo(ModAcervo acervo) {
       
        /*if (tableViewArea.getSelectionModel().getSelectedCells().size() == 1) {
            texteFiedSector.setText(areaMod.getSector());
            botaoAlterar.setDisable(false);
            botaoRemover.setDisable(false);
            this.desbloquearItensDaJanela();
            botaoNovo.setDisable(true);
            botaoCadastrar.setDisable(true);
        } else {
            botaoAlterar.setDisable(true);
            botaoRemover.setDisable(true);
            botaoNovo.setDisable(false);
            this.limparItensDaJanela();
        }*/
    }
    
    private void exibirDadosNosProveniencias(ModArea areaMod) {
       /* if (tableViewArea.getSelectionModel().getSelectedCells().size() == 1) {
            texteFiedSector.setText(areaMod.getSector());
            botaoAlterar.setDisable(false);
            botaoRemover.setDisable(false);
            this.desbloquearItensDaJanela();
            botaoNovo.setDisable(true);
            botaoCadastrar.setDisable(true);
        } else {
            botaoAlterar.setDisable(true);
            botaoRemover.setDisable(true);
            botaoNovo.setDisable(false);
            this.limparItensDaJanela();
        }*/
    }

    private ModArea pegarDadosDaPesquisaProveniencia() {
       /* if (UtilValidarDados.eNumero(this.texteFiedPesquisar.getText())) {
            areaMod.setIdArea(Integer.valueOf(this.texteFiedPesquisar.getText()), operacao);
            areaMod.setSector(this.texteFiedPesquisar.getText(), operacao);
            return areaMod;
        } else {
            areaMod.setSector(this.texteFiedPesquisar.getText(), operacao);
            return areaMod;
        }*/
       return null;
    }
    
    private ModAcervo pegarDadosDaPesquisaAcervo(){
        if(UtilValidarDados.eNumero(this.texteFiedPesquisarAcervo.getText())){
           acervoMod.setIdAcervo(Integer.valueOf(this.texteFiedPesquisarAcervo.getText()), operacao);
           acervoMod.setTitulo(this.texteFiedPesquisarAcervo.getText(), operacao);
           return acervoMod;
        }else{
           acervoMod.setTitulo(this.texteFiedPesquisarAcervo.getText(), operacao);
           return acervoMod;
        }
    }

    private void carregarResultadosNaTableaAcervo(List<Object> todosRegistosEncontrados) {
        tableColumId.setCellValueFactory(new PropertyValueFactory<>("idAcervo"));
        tableColumTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tableColumFormato.setCellValueFactory(new PropertyValueFactory<>("formato"));
        tableColumEdicao.setCellValueFactory(new PropertyValueFactory<>("edicao"));
        tableColumISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        tableColumAno.setCellValueFactory(new PropertyValueFactory<>("ano_lancamento"));
        tableColumQuantidade.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModAcervo,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModAcervo, String> acervo) {
                return new ReadOnlyStringWrapper(String.valueOf(acervo.getValue().getEstoqueMod().getQuantidade_total()));
            }
        });
        tableViewAcervo.setItems(this.todosRegistosParaCarregarAcervo(todosRegistosEncontrados));
    }
    
    private void carregarResultadosNaTableaProveniencia(List<Object> todosRegistosEncontrados) {
        /*tableColumId.setCellValueFactory(new PropertyValueFactory<>("idArea"));
        tableColumSector.setCellValueFactory(new PropertyValueFactory<>("sector"));
        tableColumIDataRegisto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModArea,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModArea, String> data) {
                return new ReadOnlyStringWrapper(data.getValue().getUtilControloDaData().getData_registo());
            }
        });
        tableColumNmeroDataModificacao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModArea,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModArea, String> data) {
                return new ReadOnlyStringWrapper(data.getValue().getUtilControloDaData().getData_modificacao());
            }
        });
        tableViewArea.setItems(this.todosRegistosParaCarregar(todosRegistosEncontrados));*/
    }

    private ObservableList<ModAcervo> todosRegistosParaCarregarAcervo(List<Object> todosRegistosEncontrados){
        List<ModAcervo> listaDosRegistosWncontrados = new ArrayList<>();
        for(Object acervoRegistado: todosRegistosEncontrados){
            ModAcervo acervoMod = (ModAcervo)acervoRegistado;
            listaDosRegistosWncontrados.add(acervoMod);
        } 
        return FXCollections.observableArrayList(listaDosRegistosWncontrados);
    }
      
    private ObservableList<ModArea> todosRegistosParaCarregarProveniencia(List<Object> todosRegistosEncontrados) {
        List<ModArea> listaDosRegistosWncontrados = new ArrayList<>();
        for (Object utenteRegistado : todosRegistosEncontrados) {
            ModArea areaMod = (ModArea) utenteRegistado;
            listaDosRegistosWncontrados.add(areaMod);
        }
        return FXCollections.observableArrayList(listaDosRegistosWncontrados);
    }

}
