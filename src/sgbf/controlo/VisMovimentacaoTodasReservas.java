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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private Button botaoCancelarReserva, botaoDevolverItem, botaoCancelar, botaoSair;
    @FXML
    private TableView<ModVisitante> tableViewVisitante;
    @FXML
    private TableColumn<ModVisitante, String> tableColumId, tableColumNome, tableColumIdTipoIdentificacao,
            tableColumNmeroIdentificacao, tableColumContacto, tableColumEndereco, tableColumCategoria;
    @FXML
    private TableView<ModReserva> tableViewReservas;
    @FXML
    private TableColumn<ModReserva, String> tableColumIdReserva, tableColumEstado, tableColumDataReserva,
            tableColumnDiasRemanescentes, tableColumnDataVencimento;
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
    private final ConItemSolicitado itemSolicitadoCon = new ConItemSolicitado();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.bloquearBotoes();
        this.tableViewVisitante.setPlaceholder(new Label("Utentes não listados"));
        this.tableViewReservas.setPlaceholder(new Label("Reservadas não listadas"));
        this.tableViewItensReservados.setPlaceholder(new Label("Itens não listados"));
        this.labelOperador.setText(UtilUsuarioLogado.getUsuarioLogado().getNome());
        this.tableViewVisitante.getSelectionModel().selectedItemProperty().addListener((observable, odlValue, newValue) -> exibirTodasReservasDoUtente(newValue));
        this.tableViewReservas.getSelectionModel().selectedItemProperty().addListener((observalbe, oldValue, newValue) -> exibirTodosItensSolicitados(newValue));
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
                this.limparTodosItens();
                this.bloquearBotoes();
                throw new UtilControloExcessao(operacao, "Utente não encontradao", Alert.AlertType.INFORMATION);
            } else {
                this.bloquearBotoes();
                this.tableViewReservas.getItems().clear();
                this.tableViewItensReservados.getItems().clear();
                this.carregarResultadosNaTableaUtente(todosRegistosEncontrados);
            }
        }
    }

    @FXML
    private void cancelarReserva() {
        this.operacao = "Cancelar Reserva";
        String mensagem = "Este operação é irreversível !\nDeseja Continuar ?";
        UtilControloExcessao excessaoControlo = new UtilControloExcessao();
        if(excessaoControlo.temCerteza(operacao, mensagem)){
            ModReserva reservaPorRemover = this.tableViewReservas.getSelectionModel().getSelectedItem();
            //Devolver todos os Acervos no Estoque
            for (ModItemSolicitado itemPorRemover : this.tableViewItensReservados.getItems()) {
                reservaPorRemover.adionarItemItensRegistados(itemPorRemover);
                estoqueCon.devolverAcervoReservadoNoEstoque(itemPorRemover, operacao);
            }
            //Remover todos os icones solicitados
            if (itemSolicitadoCon.remover(reservaPorRemover, operacao)) {
                this.tableViewItensReservados.getItems().clear();
                this.tableViewReservas.getItems().clear();
                this.passarEstadoParaInactivo(reservaPorRemover, operacao);
            }
        }
    }


    @FXML
    private void devolverAcervoReservado() {
        this.operacao = "Devolver Acervos";
        ModReserva reservaPorRemover = this.tableViewReservas.getSelectionModel().getSelectedItem();
        ModItemSolicitado itemPorRemover = this.tableViewItensReservados.getSelectionModel().getSelectedItem();
        if (itemPorRemover == null) {
            throw new UtilControloExcessao(operacao, "Seleccione o acervo a devolver", Alert.AlertType.NONE);
        } else {
            reservaPorRemover.adionarItemItensRegistados(itemPorRemover);
            if (estoqueCon.devolverAcervoReservadoNoEstoque(itemPorRemover, operacao)) {
                if (itemSolicitadoCon.remover(reservaPorRemover, operacao)) {
                    this.tableViewItensReservados.getItems().remove(itemPorRemover);
                    if(this.tableViewItensReservados.getItems().isEmpty()){
                        this.passarEstadoParaInactivo(reservaPorRemover, operacao);
                        this.tableViewReservas.getItems().clear();
                    }
                }
            }
        }
    }
   
    private void passarEstadoParaInactivo(ModReserva reservaMod, String operacao) {
        reservaMod.setEstado("Inactivo", operacao);
        reservaCon.alterar(reservaMod, operacao);
    }

    @FXML
    private void cancelar() {
        this.limparTodosItens();
    }

    @FXML
    private void sair(ActionEvent event) {
        anchoPaneTodasReserva.setVisible(false);
    }

    private void desabilitarItens() {
        this.botaoDevolverItem.setVisible(true);
        this.tableViewItensReservados.getItems().clear();
    }

    private void bloquearBotoes() {
        this.botaoCancelarReserva.setDisable(true);
        this.botaoDevolverItem.setDisable(true);
    }

    private void desbloquearBotoes() {
        this.botaoCancelarReserva.setDisable(false);
        this.botaoDevolverItem.setDisable(false);
    }

    private void habilitarBotoes(ModReserva reservaMod) {
        if (reservaMod.getEstado().equalsIgnoreCase("Activo")) {
            this.desbloquearBotoes();
        } else {
            this.bloquearBotoes();
        }
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
        tableViewVisitante.setItems(this.todosUtentesParaCarregar(todosRegistosEncontrados));
    }

    private ObservableList<ModVisitante> todosUtentesParaCarregar(List<Object> todosRegistosEncontrados) {
        List<ModVisitante> listaDosRegistosEncontrados = new ArrayList<>();
        for (Object utenteRegistado : todosRegistosEncontrados) {
            ModVisitante visitanteMod = (ModVisitante) utenteRegistado;
            listaDosRegistosEncontrados.add(visitanteMod);
        }
        return FXCollections.observableArrayList(listaDosRegistosEncontrados);
    }

    private void exibirTodasReservasDoUtente(ModVisitante visitanteMod) {
        final String operacao = "Listar todas as reservas";
        this.desabilitarItens();
        ObservableList<ModReserva> todasReservasDoUtente = this.todasReservasParaCarregar(visitanteMod, operacao);
        if (todasReservasDoUtente.isEmpty()) {
            tableViewReservas.getItems().clear();
            throw new UtilControloExcessao(operacao, "O utente seleccionado não tem registo de reservas", Alert.AlertType.WARNING);
        } else {
            this.tableViewItensReservados.getItems().clear();
            tableColumIdReserva.setCellValueFactory(new PropertyValueFactory<>("idReserva"));
            tableColumEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
            tableColumDataReserva.setCellValueFactory(new Callback<CellDataFeatures<ModReserva, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<ModReserva, String> data) {
                    return new ReadOnlyStringWrapper(data.getValue().getUtilControloDaData().getData_registo());
                }
            });
            tableColumnDiasRemanescentes.setCellValueFactory(new PropertyValueFactory<>("dias_remanescente"));
            tableColumnDataVencimento.setCellValueFactory(new PropertyValueFactory<>("data_vencimento"));
            tableViewReservas.setItems(todasReservasDoUtente);
        }
    }

    private ObservableList<ModReserva> todasReservasParaCarregar(ModVisitante visitanteMo, String operacao) {
        List<ModReserva> todoRegistosEncontrados = new ArrayList<>();
        for (Object todasReservadas : reservaCon.pesquisar(visitanteMo, operacao)) {
            ModReserva reservaMod = (ModReserva) todasReservadas;
            todoRegistosEncontrados.add(reservaMod);
        }
        return FXCollections.observableArrayList(todoRegistosEncontrados);
    }

    private void exibirTodosItensSolicitados(ModReserva reservaMod) {
        final String operacao = "Listar todos os itens solicitados";
        this.habilitarBotoes(reservaMod);
        textFieldTituloReserva.setCellValueFactory(new Callback<CellDataFeatures<ModItemSolicitado, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<ModItemSolicitado, String> acervo) {
                return new ReadOnlyStringWrapper(acervo.getValue().getAcervoMod().getTitulo());
            }
        });
        textFieldSubTituloReserva.setCellValueFactory(new Callback<CellDataFeatures<ModItemSolicitado, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<ModItemSolicitado, String> acervo) {
                return new ReadOnlyStringWrapper(acervo.getValue().getAcervoMod().getSub_titulo());
            }
        });
        textFieldISBNReserva.setCellValueFactory(new Callback<CellDataFeatures<ModItemSolicitado, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<ModItemSolicitado, String> acervo) {
                return new ReadOnlyStringWrapper(acervo.getValue().getAcervoMod().getIsbn());
            }
        });
        textFieldCodigoBarraReserva.setCellValueFactory(new Callback<CellDataFeatures<ModItemSolicitado, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<ModItemSolicitado, String> acervo) {
                return new ReadOnlyStringWrapper(acervo.getValue().getAcervoMod().getCodigo_barra());
            }
        });
        textFieldTipoReserva.setCellValueFactory(new Callback<CellDataFeatures<ModItemSolicitado, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<ModItemSolicitado, String> acervo) {
                return new ReadOnlyStringWrapper(acervo.getValue().getAcervoMod().getTipo_acervo());
            }
        });
        textFieldFormatoReserva.setCellValueFactory(new Callback<CellDataFeatures<ModItemSolicitado, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<ModItemSolicitado, String> acervo) {
                return new ReadOnlyStringWrapper(acervo.getValue().getAcervoMod().getFormato());
            }
        });
        textFieldquantidadeReserva.setCellValueFactory(new PropertyValueFactory<>("quantidade_revervada"));
        tableViewItensReservados.setItems(this.todItensSocilitadosParaCarregar(reservaMod, operacao));
    }

    private ObservableList<ModItemSolicitado> todItensSocilitadosParaCarregar(ModReserva reservaMod, String operacao) {
        List<ModItemSolicitado> todoRegistosEncontrados = new ArrayList<>();
        for (Object todosItens : itemSolicitadoCon.pesquisar(reservaMod, operacao)) {
            ModItemSolicitado todosOsItensSolicitados = (ModItemSolicitado) todosItens;
            todoRegistosEncontrados.add(todosOsItensSolicitados);
        }
        return FXCollections.observableArrayList(todoRegistosEncontrados);
    }

    private void limparTodosItens() {
        this.textFieldPesquisar.setText(null);
        this.tableViewVisitante.getItems().clear();
        this.tableViewReservas.getItems().clear();
        this.tableViewItensReservados.getItems().clear();
    }

}
