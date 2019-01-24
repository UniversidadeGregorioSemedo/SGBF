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
import sgbf.modelo.ModDevolucao;
import sgbf.modelo.ModEmprestimo;
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
public class VisMovimentacaoDevolucao implements Initializable {

    @FXML
    private JFXButton botaoPesquisar;
    @FXML
    private TextField textFieldPesquisar;
    @FXML
    private Label labelOperador;
    @FXML
    private Button botaoDevolver, botaoDevolverItem, botaoCancelar, botaoSair;
    @FXML
    private TableView<ModVisitante> tableViewVisitante;
    @FXML
    private TableColumn<ModVisitante, String> tableColumId, tableColumNome, tableColumIdTipoIdentificacao,
            tableColumNmeroIdentificacao, tableColumContacto, tableColumEndereco, tableColumCategoria;
    @FXML
    private TableView<ModEmprestimo> tableViewEmprestimos;
    @FXML
    private TableColumn<ModEmprestimo, String> tableColumIdEmprestimo, tableColumEstado, tableColumDiasAtraso,
            tableColumMulta, tableColumDataEmprestimo, tableColumnDataVencimento;
    @FXML
    private TableView<ModItemSolicitado> tableViewItensReservados;
    @FXML
    private TableColumn<ModItemSolicitado, String> textFieldTituloReserva, textFieldSubTituloReserva, textFieldISBNReserva,
            textFieldCodigoBarraReserva, textFieldTipoReserva, textFieldFormatoReserva, textFieldquantidadeReserva;
    @FXML
    private AnchorPane anchoPaneTodosEmprestimo;

    private String operacao = null;
    private final ConUtente utenteCon = new ConUtente();
    private final ConEmprestimo emprestimoCon = new ConEmprestimo();
    private final ModVisitante visitanteMod = new ModVisitante();
    private final ModDevolucao devolucaoMod = new ModDevolucao();
    private final ConDevolucao devolucaoCon = new ConDevolucao();
    private final ConItemSolicitado itemSolicitadoCon = new ConItemSolicitado();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.tableViewVisitante.setPlaceholder(new Label("Utentes não listados"));
        this.tableViewEmprestimos.setPlaceholder(new Label("Emprestimos não listadas"));
        this.tableViewItensReservados.setPlaceholder(new Label("Itens não listados"));
        this.labelOperador.setText(UtilUsuarioLogado.getUsuarioLogado().getNome());
        this.tableViewVisitante.getSelectionModel().selectedItemProperty().addListener((observable, odlValue, newValue) -> exibirTodosEmprestimosDoUtente(newValue));
        this.tableViewEmprestimos.getSelectionModel().selectedItemProperty().addListener((observalbe, oldValue, newValue) -> exibirTodosItensSolicitados(newValue));
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
                throw new UtilControloExcessao(operacao, "Utente não encontradao", Alert.AlertType.INFORMATION);
            } else {
                this.tableViewEmprestimos.getItems().clear();
                this.tableViewItensReservados.getItems().clear();
                this.carregarResultadosNaTableaUtente(todosRegistosEncontrados);
            }
        }
    }

    @FXML
    private void devolver() {
        operacao = "Devolver todos acervos";
        ModReserva reservaADevolver = null;
        devolucaoMod.setEmprestimoMod(this.tableViewEmprestimos.getSelectionModel().getSelectedItem(), operacao);
        reservaADevolver = this.pegarOsItensSolicitados(devolucaoMod.getEmprestimoMod(), operacao);
        devolucaoMod.setTipo_devolucao("Emprestimo", operacao);
        devolucaoMod.getEmprestimoMod().setReservaMod(reservaADevolver, operacao);
        devolucaoMod.getEmprestimoMod().setFuncionarioMod(UtilUsuarioLogado.getUsuarioLogado(), operacao);
        if (devolucaoCon.registar(devolucaoMod, operacao)) {
            this.botaoDevolver.setDisable(true);
            this.botaoDevolverItem.setDisable(true);
            this.tableViewItensReservados.getItems().clear();
            emprestimoCon.passarEstadoParaInactivo(devolucaoMod.getEmprestimoMod(), operacao);
            throw new UtilControloExcessao(operacao, "Itens devolvidos", Alert.AlertType.CONFIRMATION);
        }
    }

    private ModReserva pegarOsItensSolicitados(ModEmprestimo emprestimoMod, String operacao) {
        for (ModItemSolicitado todosItensSolicitados : this.tableViewItensReservados.getItems()) {
            emprestimoMod.getReservaMod().adionarItemItensRegistados(todosItensSolicitados);
        }
        return emprestimoMod.getReservaMod();
    }

    @FXML
    private void cancelar() {
        this.limparTodosItens();
    }

    @FXML
    private void sair(ActionEvent event) {
        anchoPaneTodosEmprestimo.setVisible(false);
    }

    private void desabilitarItens() {
        this.tableViewItensReservados.getItems().clear();
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

    private void exibirTodosEmprestimosDoUtente(ModVisitante visitanteMod) {
        final String operacao = "Listar todos os emprestimos";
        ObservableList<ModEmprestimo> todosEmprestimosDoUtente = this.todasReservasParaCarregar(visitanteMod, operacao);
        if (todosEmprestimosDoUtente.isEmpty()) {
            tableViewEmprestimos.getItems().clear();
            throw new UtilControloExcessao(operacao, "O utente seleccionado não tem registo de emprestimos", Alert.AlertType.WARNING);
        } else {
            this.tableViewItensReservados.getItems().clear();
            tableColumIdEmprestimo.setCellValueFactory(new PropertyValueFactory<>("idEmprestimo"));
            tableColumEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
            tableColumDiasAtraso.setCellValueFactory(new PropertyValueFactory<>("dias_atrazo"));
            tableColumMulta.setCellValueFactory(new PropertyValueFactory<>("multa"));
            tableColumDataEmprestimo.setCellValueFactory(new PropertyValueFactory<>("estado"));
            tableColumDataEmprestimo.setCellValueFactory(new Callback<CellDataFeatures<ModEmprestimo, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<ModEmprestimo, String> data) {
                    return new ReadOnlyStringWrapper(data.getValue().getUtilControloDaData().getData_registo());
                }
            });
            tableColumnDataVencimento.setCellValueFactory(new PropertyValueFactory<>("data_vencimento"));
            tableViewEmprestimos.setItems(todosEmprestimosDoUtente);
        }
    }

    private ObservableList<ModEmprestimo> todasReservasParaCarregar(ModVisitante visitanteMo, String operacao) {
        List<ModEmprestimo> todoRegistosEncontrados = new ArrayList<>();
        for (Object todosEmprestimos : emprestimoCon.pesquisar(visitanteMo, operacao)) {
            ModEmprestimo emprestimoMod = (ModEmprestimo) todosEmprestimos;
            todoRegistosEncontrados.add(emprestimoMod);
        }
        return FXCollections.observableArrayList(todoRegistosEncontrados);
    }

    private void exibirTodosItensSolicitados(ModEmprestimo empestimoMod) {
        final String operacao = "Listar todos os itens solicitados";
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
        tableViewItensReservados.setItems(this.todItensSocilitadosParaCarregar(empestimoMod.getReservaMod(), operacao));
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
        this.botaoDevolver.setDisable(true);
        this.botaoDevolverItem.setDisable(true);
        this.textFieldPesquisar.setText(null);
        this.tableViewVisitante.getItems().clear();
        this.tableViewEmprestimos.getItems().clear();
        this.tableViewItensReservados.getItems().clear();
    }

}
