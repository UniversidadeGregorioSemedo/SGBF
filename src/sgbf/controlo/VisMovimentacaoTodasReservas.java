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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import sgbf.modelo.ModAcervo;
import sgbf.modelo.ModItemSolicitado;
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
public class VisMovimentacaoTodasReservas implements Initializable {

    @FXML
    private JFXButton botaoPesquisar;
    @FXML
    private TextField textFieldPesquisar;
    @FXML
    private Label labelOperador;
    @FXML
    private Button   botaoCancelarReserva, botaoDevolverItem, botaoCancelar, botaoSair;
    @FXML
    private TableView<ModVisitante> tableVieVisitante;
    @FXML
    private TableColumn<ModVisitante, String> tableColumId, tableColumNome, tableColumIdTipoIdentificacao,
            tableColumNmeroIdentificacao, tableColumContacto, tableColumEndereco, tableColumCategoria;
    @FXML
    private TableView<ModReserva> tableViewReservas;
    @FXML
    private TableColumn<ModReserva, String> tableColumIdReserva,tableColumEstado,tableColumDataReserva,
            tableColumnDiasRemanescentes,tableColumnDataVencimento;
    @FXML
    private TableView<ModItemSolicitado> tableViewItensReservados;
    @FXML
    private TableColumn<ModItemSolicitado, String> textFieldTituloReserva, textFieldSubTituloReserva, textFieldISBNReserva,
            textFieldCodigoBarraReserva, textFieldTipoReserva, textFieldFormatoReserva, textFieldquantidadeReserva;
    @FXML
    private AnchorPane anchoPaneTodasReserva;

    private String operacao = null;
    private final ModAcervo acervoMod = new ModAcervo();
    private final ConAcervo acervoCon = new ConAcervo();
    private final ConUtente utenteCon = new ConUtente();
    private final ConEstoque estoqueCon = new ConEstoque();
    private final ConReserva reservaCon = new ConReserva();
    private final ModReserva reservaMod = new ModReserva();
    private final ModVisitante visitanteMod = new ModVisitante();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.tableVieVisitante.setPlaceholder(new Label("Utentes não listados"));
        this.tableViewReservas.setPlaceholder(new Label("Reservadas não listadas"));
        this.tableViewItensReservados.setPlaceholder(new Label("Itens não listados"));
        this.labelOperador.setText(UtilUsuarioLogado.getUsuarioLogado().getNome());
        //this.tableViewAcervo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> this.exibirAsQuantidades(newValue));
    }

    @FXML
    private void pesquisarUtente() {
        operacao = "Pesquisar Utente";
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        if (this.textFieldPesquisar.getText().isEmpty()) {
            throw new UtilControloExcessao(operacao, "Introduza o código ou nome do Utente", Alert.AlertType.INFORMATION);
        } else {
            todosRegistosEncontrados = this.utenteCon.pesquisar(this.pegarDadosDaPesquisaUtente(), operacao);
            if (todosRegistosEncontrados.isEmpty()) {
                this.tableVieVisitante.getItems().clear();
                throw new UtilControloExcessao(operacao, "Utente não encontradao", Alert.AlertType.INFORMATION);
            } else {
                this.carregarResultadosNaTableaUtente(todosRegistosEncontrados);
            }
        }
    }

    @FXML
    private void devolverAcervoReservado() {
        this.operacao = "Devolver Acervos";
        ModItemSolicitado itemPorRemover = this.tableViewItensReservados.getSelectionModel().getSelectedItem();
        if (itemPorRemover == null) {
            throw new UtilControloExcessao(operacao, "Seleccione o acervo a devolver", Alert.AlertType.NONE);
        } else {
            if (estoqueCon.devolverAcervoReservadoNoEstoque(itemPorRemover, operacao)) {
                /*this.tableViewReserva.getItems().remove(itemPorRemover);
                this.limparItensAcervos();*/
            }
        }
    }

    @FXML
    private void cancelar() {
        this.tableVieVisitante.getItems().clear();
       // this.tableViewReserva.getItems().clear();
    }

    @FXML
    private void sair(ActionEvent event) {
        anchoPaneTodasReserva.setVisible(false);
    }


    private ModVisitante pegarDadosDaPesquisaUtente() {
        if (UtilValidarDados.eNumero(this.textFieldPesquisar.getText())) {
            visitanteMod.setIdUtente(Integer.valueOf(this.textFieldPesquisar.getText()), operacao);
            visitanteMod.setPrimeiro_nome(this.textFieldPesquisar.getText(), operacao);
            return visitanteMod;
        } else {
            visitanteMod.setPrimeiro_nome(this.textFieldPesquisar.getText(), operacao);
            return visitanteMod;
        }
    }

    private void carregarResultadosNaTableaUtente(List<Object> todosRegistosEncontrados) {
        tableColumId.setCellValueFactory(new PropertyValueFactory<>("idUtente"));
        tableColumNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumIdTipoIdentificacao.setCellValueFactory(new PropertyValueFactory<>("tipo_identificacao"));
        tableColumNmeroIdentificacao.setCellValueFactory(new PropertyValueFactory<>("numero"));
        tableColumContacto.setCellValueFactory(new PropertyValueFactory<>("contacto"));
        tableColumEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        tableColumCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        tableVieVisitante.setItems(this.todosUtentesParaCarregar(todosRegistosEncontrados));
    }

    private ObservableList<ModVisitante> todosUtentesParaCarregar(List<Object> todosRegistosEncontrados) {
        List<ModVisitante> listaDosRegistosEncontrados = new ArrayList<>();
        for (Object utenteRegistado : todosRegistosEncontrados) {
            ModVisitante visitanteMod = (ModVisitante) utenteRegistado;
            listaDosRegistosEncontrados.add(visitanteMod);
        }
        return FXCollections.observableArrayList(listaDosRegistosEncontrados);
    }

}
