/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.controlo;

import sgbf.dao.ConEditora;
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
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sgbf.modelo.ModEditora;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilValidarDados;

/**
 * FXML Controller class
 *
 * @author Marron
 */
public class VisCadastramentoEditora implements Initializable {

    @FXML
    private JFXButton botaoPesquisar;
    @FXML
    private Button botaoCadastrar, botaoAlterar, botaoRemover, botaoNovo, botaoCancelar, botaoSair;
    @FXML
    private TextField texteFiedPesquisar, texteFiedNome, texteFiedContacto, texteFiedEmail;
    @FXML
    private TextField texteFiedFax, texteFiedEndereco;
    @FXML
    private TableView<ModEditora> tableViewEditora;
    @FXML
    private TableColumn<ModEditora, String> tableColumNome, tableColumContacto, tableColumEmail, tableColumEndereco;
    @FXML
    private AnchorPane AnchorPaneUtente;

    private String operacao = null;
    private final ModEditora editoraMod = new ModEditora();
    private final ConEditora editoraCon = new ConEditora();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.bloquearItensDaJanela();
        this.tableViewEditora.setPlaceholder(new Label("Editoras não listadas"));
        this.texteFiedPesquisar.setTooltip(new Tooltip("Introduza o código, nome da editora ou use *( _ ) para listar todos registos "));
        tableViewEditora.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> this.exibirDadosNosCampos(newValue));
    }

    @FXML
    private void cadastrarEditora() {
        operacao = "Registar Editora";
        editoraMod.setNome(texteFiedNome.getText(), operacao);
        editoraMod.setContacto(texteFiedContacto.getText(), operacao);
        editoraMod.setEmail(texteFiedEmail.getText(), operacao);
        editoraMod.setFax(texteFiedFax.getText(), operacao);
        editoraMod.setEndereco(texteFiedEndereco.getText(), operacao);
        if (editoraCon.registar(editoraMod, operacao)) {
            this.bloquearItensDaJanela();
            this.limparItensDaJanela();
            throw new UtilControloExcessao(operacao, "Editora Cadastrada com sucesso", Alert.AlertType.CONFIRMATION);
        }
    }

    @FXML
    private void alterarEditora() {
        operacao = "Editar Editora";
        editoraMod.setiEditora(this.tableViewEditora.getSelectionModel().getSelectedItem().getiEditora(), operacao);
        editoraMod.setNome(texteFiedNome.getText(), operacao);
        editoraMod.setContacto(texteFiedContacto.getText(), operacao);
        editoraMod.setEmail(texteFiedEmail.getText(), operacao);
        editoraMod.setFax(texteFiedFax.getText(), operacao);
        editoraMod.setEndereco(texteFiedEndereco.getText(), operacao);
        if (editoraCon.alterar(editoraMod, operacao)) {
            this.bloquearItensDaJanela();
            this.limparItensDaJanela();
            throw new UtilControloExcessao(operacao, "Editora Editada com sucesso", Alert.AlertType.CONFIRMATION);
        }
    }

    @FXML
    private void removerEditora() {
        operacao = "Remover Editora";
        ModEditora editoraARemover = this.tableViewEditora.getSelectionModel().getSelectedItem();
        if (editoraCon.remover(editoraARemover, operacao)) {
            this.tableViewEditora.getItems().remove(editoraARemover);
            this.bloquearItensDaJanela();
            throw new UtilControloExcessao(operacao, "Editora removida com sucesso", Alert.AlertType.CONFIRMATION);
        }
    }

    @FXML
    private void pesquisarUtente() {
        operacao = "Pesquisar Editora";
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        if (this.texteFiedPesquisar.getText().isEmpty()) {
            throw new UtilControloExcessao(operacao, "Introduza o código ou nome da Editora", Alert.AlertType.INFORMATION);
        } else {
            todosRegistosEncontrados = this.editoraCon.pesquisar(this.pegarDadosDaPesquisa(), operacao);
            if (todosRegistosEncontrados.isEmpty()) {
                this.bloquearItensDaJanela();
                this.limparItensDaJanela();
                throw new UtilControloExcessao(operacao, "Editora não encontrada", Alert.AlertType.INFORMATION);
            } else {
                this.carregarResultadosNaTablea(todosRegistosEncontrados);
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
        AnchorPaneUtente.setVisible(false);
    }

    @FXML
    private void desbloquearItensDaJanela() {
        this.texteFiedNome.setDisable(false);
        this.texteFiedContacto.setDisable(false);
        this.texteFiedEmail.setDisable(false);
        this.texteFiedFax.setDisable(false);
        this.texteFiedEndereco.setDisable(false);
        this.botaoNovo.setDisable(true);
        this.botaoCadastrar.setDisable(false);
    }

    private void bloquearItensDaJanela() {
        this.texteFiedNome.setDisable(true);
        this.texteFiedContacto.setDisable(true);
        this.texteFiedEmail.setDisable(true);
        this.texteFiedFax.setDisable(true);
        this.texteFiedEndereco.setDisable(true);
        this.botaoNovo.setDisable(false);
        this.botaoCadastrar.setDisable(true);
        this.botaoAlterar.setDisable(true);
        this.botaoRemover.setDisable(true);
    }

    private void limparItensDaJanela() {

        this.texteFiedNome.setText(null);
        this.texteFiedContacto.setText(null);
        this.texteFiedEmail.setText(null);
        this.texteFiedFax.setText(null);
        this.texteFiedEndereco.setText(null);
        this.texteFiedPesquisar.setText(null);
        this.tableViewEditora.getItems().clear();
    }

    private void exibirDadosNosCampos(ModEditora editoraMod) {
        if (tableViewEditora.getSelectionModel().getSelectedCells().size() == 1) {
            texteFiedNome.setText(editoraMod.getNome());
            texteFiedContacto.setText(editoraMod.getContacto());
            texteFiedEmail.setText(editoraMod.getEmail());
            texteFiedFax.setText(editoraMod.getFax());
            texteFiedEndereco.setText(editoraMod.getEndereco());
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
        }
    }

    private ModEditora pegarDadosDaPesquisa() {
        if (UtilValidarDados.eNumero(this.texteFiedPesquisar.getText())) {
            editoraMod.setiEditora(Integer.valueOf(this.texteFiedPesquisar.getText()), operacao);
            editoraMod.setNome(this.texteFiedPesquisar.getText(), operacao);
            return editoraMod;
        } else {
            editoraMod.setNome(this.texteFiedPesquisar.getText(), operacao);
            return editoraMod;
        }
    }

    private void carregarResultadosNaTablea(List<Object> todosRegistosEncontrados) {
        tableColumNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumContacto.setCellValueFactory(new PropertyValueFactory<>("contacto"));
        tableColumEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        tableViewEditora.setItems(this.todosRegistosParaCarregar(todosRegistosEncontrados));
    }

    private ObservableList<ModEditora> todosRegistosParaCarregar(List<Object> todosRegistosEncontrados) {
        List<ModEditora> listaDosRegistosWncontrados = new ArrayList<>();
        for (Object utenteRegistado : todosRegistosEncontrados) {
            ModEditora editoraMod = (ModEditora) utenteRegistado;
            listaDosRegistosWncontrados.add(editoraMod);
        }
        return FXCollections.observableArrayList(listaDosRegistosWncontrados);
    }

}
