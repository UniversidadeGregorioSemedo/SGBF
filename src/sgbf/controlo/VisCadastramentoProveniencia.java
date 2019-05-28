/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.controlo;

import sgbf.dao.ConProveniencia;
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
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
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
    private TextField TextFieldPesquisarProveniencia, textFieldCodigoProveniencia;
    @FXML
    private JFXButton botaoPesquisarProveniencia;
    @FXML
    private TableView<ModProveniencia> tabelaViewProveniencia;
    @FXML
    private ComboBox<String> combocadproveniencia;
    @FXML
    private Button botaoNovo, BotaoCadastrar, BotaoAlterar, BotaoRemover, BotaoSair, botaoCancelar;
    @FXML
    private AnchorPane anchoPaneProveniencia;
    @FXML
    private TableColumn<ModProveniencia, Integer> tabelaColunaId;
    @FXML
    private TableColumn<ModProveniencia, String> tabelaColunaTipo, tabelaColunaRegistro, tabelaColunaModificacao;
    
    private String operacao = null;
    private final ModProveniencia provenienciamod = new ModProveniencia();
    private final ConProveniencia provenienciacom = new ConProveniencia();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.bloquearItensDaJanela();
        this.tabelaViewProveniencia.setPlaceholder(new Label("Proveniencias não listadas"));
        this.TextFieldPesquisarProveniencia.setTooltip(new Tooltip("Introduza o código, designação ou use *( _ ) para listar todos registos "));
        tabelaViewProveniencia.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> this.exibirDadosNosCampos(newValue));
    }
    
    private void bloquearItensDaJanela() {
        //this.TextFieldPesquisarProveniencia.setDisable(false);
        this.textFieldCodigoProveniencia.setDisable(true);
        this.combocadproveniencia.setDisable(true);
        this.botaoNovo.setDisable(false);
        this.BotaoCadastrar.setDisable(true);
        this.BotaoAlterar.setDisable(true);
        this.BotaoRemover.setDisable(true);
    }
    
    private void desbloquearItensDaJanela() {
        this.textFieldCodigoProveniencia.setDisable(true);
        this.combocadproveniencia.setDisable(false);
        this.botaoNovo.setDisable(true);
        this.BotaoCadastrar.setDisable(false);
    }
    
    private void limparItensDaJanela() {
        this.TextFieldPesquisarProveniencia.setText(null);
        this.textFieldCodigoProveniencia.setText(null);
        this.tabelaViewProveniencia.getItems().clear();
        this.combocadproveniencia.getItems().clear();
    }
    
    @FXML
    private void novo() {
        this.desbloquearItensDaJanela();
        this.limparItensDaJanela();
        this.carregarValorNasComboxs();
    }
    
    @FXML
    private void cancelar() {
        this.bloquearItensDaJanela();
        this.limparItensDaJanela();
        this.combocadproveniencia.getItems().clear();
    }
    
    @FXML
    private void sair(ActionEvent event) {
        anchoPaneProveniencia.setVisible(false);
    }
    
    @FXML
    private void cadastrarProveniencia() {
        operacao = "Registar Proveniencia";
        provenienciamod.setTipo(combocadproveniencia.getSelectionModel().getSelectedItem(), operacao);
        if (provenienciacom.registar(provenienciamod, operacao)) {
            this.bloquearItensDaJanela();
            this.limparItensDaJanela();
            throw new UtilControloExcessao(operacao, "Proveniencia Cadastrada com sucesso", Alert.AlertType.CONFIRMATION);
        }
    }
    
    @FXML
    private void alterarProveniencia() {
        operacao = "Alterar Proveniencia";
        provenienciamod.setIdProveniencia(this.tabelaViewProveniencia.getSelectionModel().getSelectedItem().getIdProveniencia(), operacao);
        provenienciamod.setTipo(combocadproveniencia.getSelectionModel().getSelectedItem(), operacao);
        if (provenienciacom.alterar(provenienciamod, operacao)) {
            this.bloquearItensDaJanela();
            this.limparItensDaJanela();
            throw new UtilControloExcessao(operacao, "Proveniencia Alterada com sucesso", Alert.AlertType.CONFIRMATION);
        }
    }
    
    @FXML
    private void removerProvenienica() {
        operacao = "Remover Area";
        ModProveniencia provenienciaRemover = this.tabelaViewProveniencia.getSelectionModel().getSelectedItem();
        if (provenienciacom.remover(provenienciaRemover, operacao)) {
            this.tabelaViewProveniencia.getItems().remove(provenienciaRemover);
            this.bloquearItensDaJanela();
            throw new UtilControloExcessao(operacao, "Proveniencia removida com sucesso", Alert.AlertType.CONFIRMATION);
        }
    }
    
    @FXML
    private void pesquisarProveniencia() {
        operacao = "Pesquisar Proveniencia";
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        if (this.TextFieldPesquisarProveniencia.getText().isEmpty()) {
            throw new UtilControloExcessao(operacao, "Introduza o código ou Tipo de Doação", Alert.AlertType.INFORMATION);
        } else {
            todosRegistosEncontrados = this.provenienciacom.pesquisar(this.pegarDadosDaPesquisa(), operacao);
            if (todosRegistosEncontrados.isEmpty()) {
                this.bloquearItensDaJanela();
                this.limparItensDaJanela();
                throw new UtilControloExcessao(operacao, "Proveniencia não encontrada", Alert.AlertType.INFORMATION);
            } else {
                this.carregarResultadosNaTablea(todosRegistosEncontrados);
                this.bloquearItensDaJanela();
            }
        }
    }
    
    private void exibirDadosNosCampos(ModProveniencia provenienciamod) {
        if (tabelaViewProveniencia.getSelectionModel().getSelectedCells().size() == 1) {
            this.carregarValorNasComboxs();
            textFieldCodigoProveniencia.setText(String.valueOf(provenienciamod.getIdProveniencia()));
            combocadproveniencia.getSelectionModel().select(provenienciamod.getTipo());
            BotaoAlterar.setDisable(false);
            BotaoRemover.setDisable(false);
            this.desbloquearItensDaJanela();
            botaoNovo.setDisable(true);
            BotaoCadastrar.setDisable(true);
        } else {
            BotaoAlterar.setDisable(true);
            BotaoRemover.setDisable(true);
            botaoNovo.setDisable(false);
            this.limparItensDaJanela();
        }
    }
    
    private ModProveniencia pegarDadosDaPesquisa() {
        if (UtilValidarDados.eNumero(this.TextFieldPesquisarProveniencia.getText())) {
            provenienciamod.setIdProveniencia(Integer.valueOf(this.TextFieldPesquisarProveniencia.getText()), operacao);
            provenienciamod.setTipo(this.TextFieldPesquisarProveniencia.getText(), operacao);
            return provenienciamod;
        } else {
            provenienciamod.setTipo(this.TextFieldPesquisarProveniencia.getText(), operacao);
            return provenienciamod;
        }
    }
    
    private void carregarResultadosNaTablea(List<Object> todosRegistosEncontrados) {
        tabelaColunaId.setCellValueFactory(new PropertyValueFactory<>("idProveniencia"));
        tabelaColunaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        tabelaColunaRegistro.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModProveniencia, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModProveniencia, String> data) {
                return new ReadOnlyStringWrapper(data.getValue().getUtilControloDaData().getData_registo());
            }
        });
        tabelaColunaModificacao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModProveniencia, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModProveniencia, String> data) {
                return new ReadOnlyStringWrapper(data.getValue().getUtilControloDaData().getData_modificacao());
            }
        });
        tabelaViewProveniencia.setItems(this.todosRegistosParaCarregar(todosRegistosEncontrados));
    }
    
    private ObservableList<ModProveniencia> todosRegistosParaCarregar(List<Object> todosRegistosEncontrados) {
        List<ModProveniencia> listaDosRegistosEncontrados = new ArrayList<>();
        for (Object provenienciaRegistado : todosRegistosEncontrados) {
            ModProveniencia proveniencia = (ModProveniencia) provenienciaRegistado;
            listaDosRegistosEncontrados.add(proveniencia);
        }
        return FXCollections.observableArrayList(listaDosRegistosEncontrados);
    }
    
    private void carregarValorNasComboxs() {
        this.combocadproveniencia.setPromptText("Seleccione o tipo");
        this.combocadproveniencia.getItems().addAll("Doação", "Compra");
    }
    
}
