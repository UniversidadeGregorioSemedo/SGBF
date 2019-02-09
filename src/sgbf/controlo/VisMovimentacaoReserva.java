
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
import javafx.scene.control.Tooltip;
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
public class VisMovimentacaoReserva implements Initializable {

    @FXML
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
    private Button botaoRegistar, botaoReserva, botaoDevolver, botaoTodasReservas, botaoCancelar, botaoSair;
    @FXML
    private TableView<ModVisitante> tableVieVisitante;
    @FXML
    private TableColumn<ModVisitante, String> tableColumId, tableColumNome, tableColumIdTipoIdentificacao,
            tableColumNmeroIdentificacao, tableColumContacto, tableColumEndereco, tableColumCategoria;
    @FXML
    private TableView<ModAcervo> tableViewAcervo;
    @FXML
    private TableColumn<ModAcervo, String> tableColumTitulo, tableColumSubTitulo, tableColumEdicao, tableColumISBN,
            tableColumnCodigoBarra, tableColumnTipo, tableColumnFormato;
    @FXML
    private TableView<ModItemSolicitado> tableViewReserva;
    @FXML
    private TableColumn<ModItemSolicitado, String> textFieldTituloReserva, textFieldSubTituloReserva, textFieldISBNReserva,
            textFieldCodigoBarraReserva, textFieldTipoReserva, textFieldFormatoReserva, textFieldquantidadeReserva;
    @FXML
    private AnchorPane anchoPaneReserva;

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
        this.bloquearItensDaJanela();
        this.tableVieVisitante.setPlaceholder(new Label("Utentes não listados"));
        this.tableViewAcervo.setPlaceholder(new Label("Acervo não listados"));
        this.tableViewReserva.setPlaceholder(new Label("Nenhuma reserva feita"));
        this.textFieldPesquisar.setTooltip(new Tooltip("Introduza o código, nome do utente ou use *( _ ) para listar todos registos "));
        this.tableViewAcervo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> this.exibirAsQuantidades(newValue));
    }

    @FXML
    private void pesquisar() {
        RadioButton itemPesquisar = (RadioButton) OpcoesDePesquisa.getSelectedToggle();
        if (itemPesquisar != null) {
            if (radioButtonUtente.getText().equalsIgnoreCase(itemPesquisar.getText())) {
                this.pesquisarUtente();
            } else {
                if (radioButtonAcervos.getText().equalsIgnoreCase(itemPesquisar.getText())) {
                    this.pesquisarAcervos();
                }
            }
        } else {
            throw new UtilControloExcessao("Pesquisar item", "Seleccione o item a pesquisar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void registarAcervoReservado() {
        try {
            this.operacao = "Registar Acervos";
            ModItemSolicitado itemSolicitado = new ModItemSolicitado();
            itemSolicitado.setAcervoMod(this.tableViewAcervo.getSelectionModel().getSelectedItem(), operacao);
            itemSolicitado.setQuantidade_revervada(Byte.valueOf(textFieldQuantidadeReservar.getText()), operacao);
            if (estoqueCon.descontarAcervoReservadoNoEstoque(itemSolicitado, operacao)) {
                reservaMod.adionarItemItensRegistados(itemSolicitado);
                this.actualizarQuantidade(itemSolicitado.getAcervoMod(), operacao);
                this.carregarResultadosNaTabelaReservas(reservaMod.getItensRegistados());
                this.botaoReserva.setDisable(false);
            }
        } catch (NumberFormatException  | NullPointerException erro) {
            throw new UtilControloExcessao(operacao, "Introduza uma Quantidade válida", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void reservar() {
        operacao = "Reversar acervos";
        ConItemSolicitado itemSolicitadoCon = new ConItemSolicitado();
        reservaMod.setFuncionarioMod(UtilUsuarioLogado.getUsuarioLogado(), operacao);
        reservaMod.setUtenteMod(tableVieVisitante.getSelectionModel().getSelectedItem(), operacao);
        if(reservaMod.getItensRegistados().isEmpty()){
            throw new UtilControloExcessao(operacao, "Seleccione os itens a reservar", Alert.AlertType.WARNING);
        }else{
            if (reservaCon.registar(reservaMod, operacao)) {
                reservaMod.setIdReserva(reservaCon.utlimoCodigoRegistado(operacao), operacao);
                if(itemSolicitadoCon.registar(reservaMod, operacao)){
                    this.bloquearItensDaJanela();
                    this.limparItensAcervos();
                    this.tableVieVisitante.getItems().clear();
                    this.tableViewReserva.getItems().clear();
                    throw new UtilControloExcessao(operacao, "Reserva efectuada com sucesso", Alert.AlertType.CONFIRMATION);
                }else{
                    throw new UtilControloExcessao(operacao, "Erro ao registar acervos", Alert.AlertType.CONFIRMATION);
                }
            }
        }
    }

    @FXML
    private void devolverAcervoReservado() {
        this.operacao = "Devolver Acervos";
        ModItemSolicitado itemPorRemover = this.tableViewReserva.getSelectionModel().getSelectedItem();
        if (itemPorRemover == null) {
            throw new UtilControloExcessao(operacao, "Seleccione o acervo a devolver", Alert.AlertType.NONE);
        } else {
            if (estoqueCon.devolverAcervoReservadoNoEstoque(itemPorRemover, operacao)) {
                this.tableViewReserva.getItems().remove(itemPorRemover);
                this.limparItensAcervos();
            }
        }
    }

    @FXML
    private void cancelar() {
        final String operaco = "Reservar acervos";
        UtilControloExcessao excessaoControloUtil = new UtilControloExcessao();
        if (this.reservaMod.getItensRegistados().size() == 0) {
            this.bloquearItensDaJanela();
            this.limparItensAcervos();
            this.tableVieVisitante.getItems().clear();
            this.tableViewReserva.getItems().clear();
        } else {
            throw new UtilControloExcessao(operaco, "Esta operação não pode ser cancelada\n"
                    + "Devolva os acervos previamente para continuar", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void sair(ActionEvent event) {
        final String operaco = "Reservar acervos";
        if (this.reservaMod.getItensRegistados().size() == 0) {
            anchoPaneReserva.setVisible(false);
        } else {
            throw new UtilControloExcessao(operaco, "Esta operação não pode ser executada\n"
                    + "Devolva os acervos previamente para continuar", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void validarDadosNumericos(KeyEvent evt) {
        String caracateresValidos = "1234567890";
        if (!caracateresValidos.contains(evt.getCharacter() + "")) {
            evt.consume();
        }
    }

    private void bloquearItensDaJanela() {
        this.bloquearItensAcervos();
        this.botaoReserva.setDisable(false);
        this.botaoDevolver.setDisable(false);
    }

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

    private void pesquisarAcervos() {
        operacao = "Pesquisar Acervos";
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        if (this.textFieldPesquisar.getText().isEmpty()) {
            throw new UtilControloExcessao(operacao, "Introduza o código ou nome do Acervo", Alert.AlertType.INFORMATION);
        } else {
            todosRegistosEncontrados = this.acervoCon.pesquisar(this.pegarDadosDaPesquisaAcervos(), operacao);
            if (todosRegistosEncontrados.isEmpty()) {
                this.bloquearItensAcervos();
                this.limparItensAcervos();
                throw new UtilControloExcessao(operacao, "Acervo não encontradao", Alert.AlertType.INFORMATION);
            } else {
                this.carregarResultadosNaTableAcervos(todosRegistosEncontrados);
            }
        }
    }

    private ModAcervo pegarDadosDaPesquisaAcervos() {
        if (UtilValidarDados.eNumero(this.textFieldPesquisar.getText())) {
            acervoMod.setIdAcervo(Integer.valueOf(this.textFieldPesquisar.getText()), operacao);
            acervoMod.setTitulo(this.textFieldPesquisar.getText(), operacao);
            return acervoMod;
        } else {
            acervoMod.setTitulo(this.textFieldPesquisar.getText(), operacao);
            return acervoMod;
        }
    }

    private void carregarResultadosNaTableAcervos(List<Object> todosRegistosEncontrados) {
        tableColumTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tableColumSubTitulo.setCellValueFactory(new PropertyValueFactory<>("sub_titulo"));
        tableColumISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        tableColumnCodigoBarra.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        tableColumnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo_acervo"));
        tableColumnFormato.setCellValueFactory(new PropertyValueFactory<>("formato"));
        tableViewAcervo.setItems(this.todosAcervosParaCarregar(todosRegistosEncontrados));
    }

    private ObservableList<ModAcervo> todosAcervosParaCarregar(List<Object> todosRegistosEncontrados) {
        List<ModAcervo> listaDosRegistosWncontrados = new ArrayList<>();
        for (Object acervoRegistado : todosRegistosEncontrados) {
            ModAcervo acervoMod = (ModAcervo) acervoRegistado;
            listaDosRegistosWncontrados.add(acervoMod);
        }
        return FXCollections.observableArrayList(listaDosRegistosWncontrados);
    }

    private void exibirAsQuantidades(ModAcervo acervoMod) {
        if (acervoMod != null) {
            this.textFieldQuantidadeTotal.setText(String.valueOf(acervoMod.getEstoqueMod().getQuantidade_total()));
            this.textFieldQuantidadeRemanescente.setText(String.valueOf(acervoMod.getEstoqueMod().getQuantidadeRemanescente()));
            this.desbloquearItensAcervos();
        }
    }

    private void actualizarQuantidade(ModAcervo acervoMod, String operacao) {
        ModAcervo quantidadeActualizada = new ModAcervo();
        ConAcervo acervoCon = new ConAcervo();
        for (Object todosRegistos : acervoCon.pesquisar(acervoMod, operacao)) {
            quantidadeActualizada = (ModAcervo) todosRegistos;
        }
        this.textFieldQuantidadeTotal.setText(String.valueOf(quantidadeActualizada.getEstoqueMod().getQuantidade_total()));
        this.textFieldQuantidadeRemanescente.setText(String.valueOf(quantidadeActualizada.getEstoqueMod().getQuantidadeRemanescente()));
    }

    private void carregarResultadosNaTabelaReservas(List<ModItemSolicitado> itemSolicitadoMod) {
        ObservableList itemACarregar = FXCollections.observableList(itemSolicitadoMod);
        textFieldTituloReserva.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModItemSolicitado, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModItemSolicitado, String> itemSolicitado) {
                return new ReadOnlyStringWrapper(itemSolicitado.getValue().getAcervoMod().getTitulo());
            }
        });
        textFieldSubTituloReserva.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModItemSolicitado, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModItemSolicitado, String> itemSolicitado) {
                return new ReadOnlyStringWrapper(itemSolicitado.getValue().getAcervoMod().getSub_titulo());
            }
        });
        textFieldISBNReserva.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModItemSolicitado, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModItemSolicitado, String> itemSolicitado) {
                return new ReadOnlyStringWrapper(itemSolicitado.getValue().getAcervoMod().getIsbn());
            }
        });
        textFieldCodigoBarraReserva.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModItemSolicitado, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModItemSolicitado, String> itemSolicitado) {
                return new ReadOnlyStringWrapper(itemSolicitado.getValue().getAcervoMod().getCodigo_barra());
            }
        });
        textFieldTipoReserva.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModItemSolicitado, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModItemSolicitado, String> itemSolicitado) {
                return new ReadOnlyStringWrapper(itemSolicitado.getValue().getAcervoMod().getTipo_acervo());
            }
        });
        textFieldFormatoReserva.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModItemSolicitado, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModItemSolicitado, String> itemSolicitado) {
                return new ReadOnlyStringWrapper(itemSolicitado.getValue().getAcervoMod().getFormato());
            }
        });
        textFieldquantidadeReserva.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModItemSolicitado, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModItemSolicitado, String> itemSolicitado) {
                return new ReadOnlyStringWrapper(String.valueOf(itemSolicitado.getValue().getQuantidade_revervada()));
            }
        });
        tableViewReserva.setItems(itemACarregar);
    }

    private void limparItensAcervos() {
        this.textFieldQuantidadeTotal.setText(null);
        this.textFieldQuantidadeRemanescente.setText(null);
        this.textFieldQuantidadeReservar.setText(null);
        this.tableViewAcervo.getItems().clear();
    }

    private void bloquearItensAcervos() {
        this.textFieldQuantidadeReservar.setDisable(true);
        this.botaoRegistar.setDisable(true);
    }

    private void desbloquearItensAcervos() {
        this.textFieldQuantidadeReservar.setDisable(false);
        this.botaoRegistar.setDisable(false);
    }

}
