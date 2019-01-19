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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sgbf.modelo.ModAcervo;
import sgbf.modelo.ModReserva;
import sgbf.modelo.ModVisitante;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilUsuarioLogado;
import sgbf.util.UtilValidarDados;

/**
 * FXML Controller class
 *
 * @author Marron
 */
public class VisMovimentacaoReserva implements Initializable {

    private JFXButton botaoPesquisar;
    @FXML
    private RadioButton radioButtonUtente, radioButtonAcervos;
    @FXML
    private ToggleGroup OpcoesDePesquisa;
    @FXML
    private TextField textFieldPesquisar, textFieldQuantidadeTotal,
            textFieldQuantidadeRemanescente, textFieldQuantidadeReservar, textFieldUtente;
    @FXML
    private Label labelOperador;
    @FXML
    private Button botaoReserva, botaoNovo, botaoDevolver, botaoTodasReservas, botaoCancelar, botaoSair;
    @FXML
    private TableView<ModVisitante> tableVieVisitante;
    @FXML
    private TableView<ModAcervo> tableViewAcervo;
    @FXML
    private TableView<ModReserva> tableViewReserva;
    @FXML
    private AnchorPane anchoPaneReserva;

    private String operacao = null;
    private final ModAcervo acervoMod = new ModAcervo();
    private final ConAcervo acervoCon = new ConAcervo();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.bloquearItensDaJanela();
        this.tableVieVisitante.setPlaceholder(new Label("Utentes não listados"));
        this.tableViewAcervo.setPlaceholder(new Label("Acervo não listados"));
        this.tableViewReserva.setPlaceholder(new Label("Nenhuma reserva feita"));
        this.labelOperador.setText(UtilUsuarioLogado.getUsuarioLogado().getNome());
        //tableAcervo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> this.exibirDadosNosCampos(newValue));
    }

    @FXML
    private void desbloquearItensDaJanela() {
        //    this.TextQuant.setDisable(false);
        //    this.TextDiasReman.setDisable(false);
        //    this.textNomeUtente.setDisable(false);
        this.botaoNovo.setDisable(true);
        this.botaoReserva.setDisable(false);
    }

    @FXML
    private void cancelar() {
        this.bloquearItensDaJanela();
        this.limparItensDaJanela();
    }

    @FXML
    private void sair(ActionEvent event) {
        anchoPaneReserva.setVisible(false);
    }

    private void bloquearItensDaJanela() {
        //  this.idReserva.setDisable(true);
        //  this.idAcervo.setDisable(true);
        //  this.textNomeUtente.setDisable(true);
        //  this.TextQuant.setDisable(true);
        //  this.TextDiasReman.setDisable(true);
        //  this.comboReserva.setDisable(true);
        this.botaoNovo.setDisable(false);
        this.botaoReserva.setDisable(true);
        this.botaoDevolver.setDisable(true);
    }

    private void limparItensDaJanela() {
        /*this.idAcervo.setText(null);
        this.idReserva.setText(null);
        this.textNomeUtente.setText(null);
        this.TextQuant.setText(null);
        this.tableAcervo.getItems().clear();*/
    }

    private void botaoPesquisarAcervo() {

        /*operacao = "Pesquisar Acervo";
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        if (this.TextPesquisar.getText().isEmpty()) {
            throw new UtilControloExcessao(operacao, "Introduza o código ou titulo do Acervo", Alert.AlertType.INFORMATION);
        } else {
            todosRegistosEncontrados = this.acervoCon.pesquisar(this.pegarDadosDaPesquisa(), operacao);
            if (todosRegistosEncontrados.isEmpty()) {
                this.bloquearItensDaJanela();
                this.limparItensDaJanela();
                throw new UtilControloExcessao(operacao, "Acervo não encontradao", Alert.AlertType.INFORMATION);
            } else {
                this.carregarResultadosNaTablea(todosRegistosEncontrados);
                this.bloquearItensDaJanela();
            }
        }*/
    }

    private void exibirDadosNosCampos(ModAcervo acervoMod) {
        /*if (tableAcervo.getSelectionModel().getSelectedCells().size() == 1) {
            textTitulo.setText(acervoMod.getTitulo());
            botaoDevolver.setDisable(false);
            botaoReserva.setDisable(false);
            botaoNovo.setDisable(true);
            this.desbloquearItensDaJanela();
        } else {
            botaoDevolver.setDisable(true);
            botaoReserva.setDisable(true);
            botaoNovo.setDisable(false);
            this.limparItensDaJanela();
        }*/
    }

    private ModAcervo pegarDadosDaPesquisa() {
        /*if (UtilValidarDados.eNumero(this.TextPesquisar.getText())) {
            acervoMod.setIdAcervo(Integer.valueOf(this.TextPesquisar.getText()), operacao);
            acervoMod.setTitulo(this.TextPesquisar.getText(), operacao);
            return acervoMod;
        } else {
            acervoMod.setTitulo(this.TextPesquisar.getText(), operacao);
            return acervoMod;
        }*/
        return null;
    }

    private void carregarResultadosNaTablea(List<Object> todosRegistosEncontrados) {
        /*titulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tipo.setCellValueFactory(new PropertyValueFactory<>("tipo_acervo"));
        isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        ano.setCellValueFactory(new PropertyValueFactory<>("ano_lancamento"));
        tableAcervo.setItems(this.todosRegistosParaCarregar(todosRegistosEncontrados));*/
    }

    private ObservableList<ModAcervo> todosRegistosParaCarregar(List<Object> todosRegistosEncontrados) {
        List<ModAcervo> listaDosRegistosEncontrados = new ArrayList<>();
        for (Object acervoRegistado : todosRegistosEncontrados) {
            ModAcervo acervoMod = (ModAcervo) acervoRegistado;
            listaDosRegistosEncontrados.add(acervoMod);
        }
        return FXCollections.observableArrayList(listaDosRegistosEncontrados);
    }

}
