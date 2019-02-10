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
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import org.controlsfx.control.Notifications;
import sgbf.modelo.ModAcervo;
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
    private JFXButton botaoPesquisarAcervo, botaoPesquisarEntrada;
    @FXML
    private Button botaoCadastrar, botaoAlterar, botaoRemover, botaoCancelar, botaoSair;
    @FXML
    private TextField texteFiedPesquisarAcervo, texteFiedPesquisarEntrada, texteFiedQuantidade,
            texteFiedCustoUnitario;
    @FXML
    private ComboBox<ModProveniencia> comboxProveniencia;
    @FXML
    private TableView<ModItemProveniente> tableViewItemProveniente;
    @FXML
    private TableColumn<ModItemProveniente, String> tableColumTituloProvaniente, tableColumQuantidadeEntrada,
            tableColumCustoUnitario, tableColumSubTotal;
    @FXML
    private TableView<ModAcervo> tableViewAcervo;
    @FXML
    private TableColumn<ModAcervo, Integer> tableColumId, tableColumEdicao, tableColumAno;
    @FXML
    private TableColumn<ModAcervo, String> tableColumQuantidade;
    @FXML
    private TableColumn<ModAcervo, String> tableColumTitulo, tableColumFormato, tableColumISBN;
    @FXML
    private AnchorPane AnchorPaneItemProveniente;
    @FXML
    private Label labelQuantidadeDeEntrada, labelCusto;

    private String operacao = null;
    private final ModAcervo acervoMod = new ModAcervo();
    private final ConAcervo acervoCon = new ConAcervo();
    private final ModItemProveniente itemProvenienteMod = new ModItemProveniente();
    private final ContItemProveniente itemProvenienteCon = new ContItemProveniente();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.alterta();
        this.bloquearItensDaJanela();
        this.tableViewAcervo.setPlaceholder(new Label("Acervos não listadas"));
        this.tableViewItemProveniente.setPlaceholder(new Label("Entradas não listadas"));
        this.texteFiedPesquisarAcervo.setTooltip(new Tooltip("Introduza o código, título do acervo ou use *( _ ) para listar todos registos "));
        this.texteFiedPesquisarEntrada.setTooltip(new Tooltip("Introduza o código, título do acervo ou use *( _ ) para listar todos registos "));
        tableViewAcervo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> this.exibirDadosNosCamposAcervo(newValue));
    }

    @FXML
    private void cadastrarProveniencia() {
        operacao = "Registar Entrada de Acervo";
        UtilControloExcessao controloExcessao = new UtilControloExcessao();
        itemProvenienteMod.setAcervoMod(tableViewAcervo.getSelectionModel().getSelectedItem(), operacao);
        itemProvenienteMod.setProvenienciaMod(this.comboxProveniencia.getValue(), operacao);
        itemProvenienteMod.setQuantidade_entrada(UtilValidarDados.validarQuantidade(texteFiedQuantidade.getText(), operacao), operacao);
        itemProvenienteMod.setCusto_unitario(UtilValidarDados.custoUnitario(texteFiedCustoUnitario.getText(), operacao), operacao);
        if (controloExcessao.temCerteza(operacao, "Esta operação é irreversível deseja continuar ?")) {
            if (itemProvenienteCon.registar(itemProvenienteMod, operacao)) {
                this.bloquearItensDaJanela();
                this.limparItensDaJanela();
                throw new UtilControloExcessao(operacao, "Entrada Cadastrada com sucesso", Alert.AlertType.CONFIRMATION);
            }
        }
    }

    @FXML
    private void alterarProveniencia() {
        operacao = "Editar Itens provenientes";
        throw new UtilControloExcessao(operacao, "Operação temporariamente indisponivel !", Alert.AlertType.ERROR);
    }

    @FXML
    private void removerProveniencia() {
        operacao = "Remover Itens provenientes";
        throw new UtilControloExcessao(operacao, "Operação temporariamente indisponivel !", Alert.AlertType.ERROR);
    }

    @FXML
    private void pesquisarProveniencia() {
        operacao = "Pesquisar Entradas";
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        if (this.texteFiedPesquisarEntrada.getText().isEmpty()) {
            throw new UtilControloExcessao(operacao, "Introduza o código ou título do Acervo", Alert.AlertType.INFORMATION);
        } else {
            todosRegistosEncontrados = this.itemProvenienteCon.pesquisar(this.pegarDadosDaPesquisaProveniencia(), operacao);
            if (todosRegistosEncontrados.isEmpty()) {
                this.bloquearItensDaJanela();
                this.limparItensDaJanela();
                throw new UtilControloExcessao(operacao, "Não há registo de entradas", Alert.AlertType.INFORMATION);
            } else {
                this.carregarResultadosNaTableaProveniencia(todosRegistosEncontrados);
                this.bloquearItensDaJanela();
            }
        }
    }

    @FXML
    private void pesquisarAcervos() {
        operacao = "Pesquisar Acervos";
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        if (this.texteFiedPesquisarAcervo.getText().isEmpty()) {
            throw new UtilControloExcessao(operacao, "Introduza o código ou título do acervos", Alert.AlertType.INFORMATION);
        } else {
            todosRegistosEncontrados = this.acervoCon.pesquisar(this.pegarDadosDaPesquisaAcervo(), operacao);
            if (todosRegistosEncontrados.isEmpty()) {
                this.bloquearItensDaJanela();
                this.limparItensDaJanela();
                throw new UtilControloExcessao(operacao, "Acervo não encontrada", Alert.AlertType.INFORMATION);
            } else {
                this.carregarResultadosNaTableaAcervo(todosRegistosEncontrados);
                this.bloquearItensDaJanela();
            }
        }
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
    }

    @FXML
    private void sair(ActionEvent event) {
        AnchorPaneItemProveniente.setVisible(false);
    }

    @FXML
    private void desbloquearItensDaJanela() {
        this.texteFiedQuantidade.setDisable(false);
        this.comboxProveniencia.setDisable(false);
        this.botaoCadastrar.setDisable(false);
    }

    @FXML
    private void habilitarCusto() {
        ModProveniencia proveniencia = this.comboxProveniencia.getSelectionModel().getSelectedItem();
        if (proveniencia == null) {
            this.texteFiedCustoUnitario.setDisable(true);
            this.texteFiedCustoUnitario.setText("0.0");
            this.labelCusto.setText("Custo Unitário");
        } else {
            if (proveniencia.getTipo() == null) {
                this.texteFiedCustoUnitario.setDisable(true);
                this.texteFiedCustoUnitario.setText("0.0");
                this.labelCusto.setText("Custo Unitário");
            } else {
                if (proveniencia.getTipo().equalsIgnoreCase("Compra")) {
                    this.texteFiedCustoUnitario.setDisable(false);
                    this.labelCusto.setText("Custo Unitário *");
                    this.texteFiedCustoUnitario.setText(null);
                } else {
                    this.texteFiedCustoUnitario.setDisable(true);
                    this.texteFiedCustoUnitario.setText("0.0");
                    this.labelCusto.setText("Custo Unitário");
                }
            }
        }
    }

    private void bloquearItensDaJanela() {
        this.texteFiedQuantidade.setDisable(true);
        this.texteFiedCustoUnitario.setDisable(true);
        this.comboxProveniencia.setDisable(true);
        this.botaoCadastrar.setDisable(true);
        this.botaoAlterar.setDisable(true);
        this.botaoRemover.setDisable(true);
    }

    private void limparItensDaJanela() {
        this.texteFiedPesquisarAcervo.setText(null);
        this.texteFiedPesquisarEntrada.setText(null);
        this.texteFiedQuantidade.setText(null);
        this.texteFiedCustoUnitario.setText(null);
        this.comboxProveniencia.getItems().clear();
        this.tableViewAcervo.getItems().clear();
        this.tableViewItemProveniente.getItems().clear();
        this.labelCusto.setText("Custo Unitário");
        this.labelQuantidadeDeEntrada.setText("Quantidade de entrada");
    }

    private void carregarValorNasComboxs() {
        ConProveniencia provenienciaCon = new ConProveniencia();
        List<ModProveniencia> todasProveniencias = new ArrayList<>();
        ObservableList todasProvenienciasParaCombox = null;
        todasProveniencias.add(new ModProveniencia());
        for (Object todosRegistos : provenienciaCon.listarTodos(operacao)) {
            ModProveniencia provenienciaRegistada = (ModProveniencia) todosRegistos;
            todasProveniencias.add(provenienciaRegistada);
        }
        todasProvenienciasParaCombox = FXCollections.observableArrayList(todasProveniencias);
        this.comboxProveniencia.setItems(todasProvenienciasParaCombox);
    }

    private void exibirDadosNosCamposAcervo(ModAcervo acervo) {
        if (tableViewAcervo.getSelectionModel().getSelectedCells().size() == 1) {
            this.carregarValorNasComboxs();
            botaoAlterar.setDisable(true);
            botaoRemover.setDisable(true);
            this.desbloquearItensDaJanela();
            botaoCadastrar.setDisable(false);
            this.habilitarQuantidade(acervo);
        } else {
            botaoCadastrar.setDisable(true);
            botaoAlterar.setDisable(true);
            botaoRemover.setDisable(true);
            this.limparItensDaJanela();
            this.labelQuantidadeDeEntrada.setText("Quantidade de entrada *");
        }
    }

    private void habilitarQuantidade(ModAcervo acervo) {
        if (acervo.getFormato().equalsIgnoreCase("Físico")) {
            this.texteFiedQuantidade.setText(null);
            this.texteFiedQuantidade.setDisable(false);
            this.labelQuantidadeDeEntrada.setText("Quantidade de entrada *");
        } else {
            this.texteFiedQuantidade.setDisable(true);
            this.labelQuantidadeDeEntrada.setText("Quantidade de entrada");
            this.texteFiedQuantidade.setText(String.valueOf(acervo.getEstoqueMod().getQuantidade_total()));
        }
    }

    private ModItemProveniente pegarDadosDaPesquisaProveniencia() {
        if (UtilValidarDados.eNumero(this.texteFiedPesquisarEntrada.getText())) {
            itemProvenienteMod.getAcervoMod().setIdAcervo(Integer.valueOf(this.texteFiedPesquisarEntrada.getText()), operacao);
            itemProvenienteMod.getAcervoMod().setTitulo(this.texteFiedPesquisarEntrada.getText(), operacao);
            return itemProvenienteMod;
        } else {
            itemProvenienteMod.getAcervoMod().setTitulo(this.texteFiedPesquisarEntrada.getText(), operacao);
            return itemProvenienteMod;
        }
    }

    private ModAcervo pegarDadosDaPesquisaAcervo() {
        if (UtilValidarDados.eNumero(this.texteFiedPesquisarAcervo.getText())) {
            acervoMod.setIdAcervo(Integer.valueOf(this.texteFiedPesquisarAcervo.getText()), operacao);
            acervoMod.setTitulo(this.texteFiedPesquisarAcervo.getText(), operacao);
            return acervoMod;
        } else {
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
        tableColumQuantidade.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModAcervo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModAcervo, String> acervo) {
                return new ReadOnlyStringWrapper(String.valueOf(acervo.getValue().getEstoqueMod().getQuantidade_total()));
            }
        });
        tableViewAcervo.setItems(this.todosRegistosParaCarregarAcervo(todosRegistosEncontrados));
    }

    private void carregarResultadosNaTableaProveniencia(List<Object> todosRegistosEncontrados) {
        tableColumTituloProvaniente.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModItemProveniente, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModItemProveniente, String> item) {
                return new ReadOnlyStringWrapper(item.getValue().getAcervoMod().getTitulo());
            }
        });
        tableColumQuantidadeEntrada.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModItemProveniente, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModItemProveniente, String> item) {
                return new ReadOnlyStringWrapper(String.valueOf(item.getValue().getQuantidade_entrada()));
            }
        });
        tableColumCustoUnitario.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModItemProveniente, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModItemProveniente, String> item) {
                return new ReadOnlyStringWrapper(String.valueOf(item.getValue().getCusto_unitario()));
            }
        });
        tableColumSubTotal.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModItemProveniente, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModItemProveniente, String> item) {
                return new ReadOnlyStringWrapper(String.valueOf(item.getValue().getSubTotal()));
            }
        });
        tableViewItemProveniente.setItems(this.todosRegistosParaCarregarProveniencia(todosRegistosEncontrados));
    }

    private ObservableList<ModAcervo> todosRegistosParaCarregarAcervo(List<Object> todosRegistosEncontrados) {
        List<ModAcervo> listaDosRegistosWncontrados = new ArrayList<>();
        for (Object acervoRegistado : todosRegistosEncontrados) {
            ModAcervo acervoMod = (ModAcervo) acervoRegistado;
            listaDosRegistosWncontrados.add(acervoMod);
        }
        return FXCollections.observableArrayList(listaDosRegistosWncontrados);
    }

    private ObservableList<ModItemProveniente> todosRegistosParaCarregarProveniencia(List<Object> todosRegistosEncontrados) {
        List<ModItemProveniente> listaDosRegistosEncontrados = new ArrayList<>();
        for (Object entradasRegistadas : todosRegistosEncontrados) {
            ModItemProveniente itemProvenienteMod = (ModItemProveniente) entradasRegistadas;
            listaDosRegistosEncontrados.add(itemProvenienteMod);
        }
        return FXCollections.observableArrayList(listaDosRegistosEncontrados);
    }

    @FXML
    public void validarDadosNumericos(KeyEvent evt) {
        String caracateresValidos = "1234567890";
        if (!caracateresValidos.contains(evt.getCharacter() + "")) {
            evt.consume();
        }
    }

    private void alterta() {
        operacao = "Entrada de acervos";
        Notifications.create().title(operacao).
                text("A remoção e alteração de uma entrada estão\n"
                        + "temporariamente indisponíveis").showWarning();;
        Notifications.create().title(operacao).
                text("A entra de acervos digitais está temporariamente\n indisponível").showWarning();
    }

}
