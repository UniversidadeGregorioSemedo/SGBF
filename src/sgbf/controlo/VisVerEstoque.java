/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.controlo;

import sgbf.dao.ConAcervo;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.util.Callback;
import sgbf.modelo.ModAcervo;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilValidarDados;

/**
 * FXML Controller class
 *
 * @author Marron
 */
public class VisVerEstoque implements Initializable {

    @FXML
    private JFXButton botaoPesquisar;
    @FXML
    private Button  botaoSair, botaoCancelar;
    @FXML
    private TableView<ModAcervo> tableViewAcervo, tableViewEstoque;
    @FXML
    private TableColumn<ModAcervo, String> tableColumTitulo, tableColumSubTitulo, tableColumISBN,
            tableColumnAno, tableColumnCodigoBarra, tableColumnTipo, tableColumnFormato, tableColumnId;
    ;
    @FXML
    private TableColumn<ModAcervo, String> tableColumQTotal, tableColumnQFal,
            tableColumQEmp, tableColumQRes;
    @FXML
    private TextField textFQTotal, textFQFal, textFQEmp, textFQRes, texteFiedPesquisar;
    @FXML
    private AnchorPane paneAnchorEstoque;

    private String operacao = null;
    private final ModAcervo acervoMod = new ModAcervo();
    private final ConAcervo acervoCon = new ConAcervo();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableViewAcervo.setPlaceholder(new Label("Acervos não listados"));
        tableViewEstoque.setPlaceholder(new Label("Estoque não listado"));
        this.texteFiedPesquisar.setTooltip(new Tooltip("Introduza o código, título do acervo ou use *( _ ) para listar todos registos "));
        tableViewEstoque.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> this.carregarResultadosAcervos(newValue));
    }

    @FXML
    private void pesquisarAcervo() {
        operacao = "Pesquisar Acervos";
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        if (this.texteFiedPesquisar.getText().isEmpty()) {
            throw new UtilControloExcessao(operacao, "Introduza o código ou título do acervos", Alert.AlertType.INFORMATION);
        } else {
            todosRegistosEncontrados = this.acervoCon.pesquisar(this.pegarDadosDaPesquisa(), operacao);
            if (todosRegistosEncontrados.isEmpty()) {
                this.bloquearItensDaJanela();
                this.limparItensDaJanela();
                throw new UtilControloExcessao(operacao, "Acervo não encontrada", Alert.AlertType.INFORMATION);
            } else {
                this.carregarResultadosNaTablea(todosRegistosEncontrados);
                this.bloquearItensDaJanela();
            }
        }
    }

    @FXML
    private void limparItensDaJanela() {
        this.textFQTotal.setText(null);
        this.textFQFal.setText(null);
        this.textFQEmp.setText(null);
        this.textFQRes.setText(null);
        this.texteFiedPesquisar.setText(null);
        this.tableViewAcervo.getItems().clear();
        this.tableViewEstoque.getItems().clear();
    }

    @FXML
    private void sair() {
        this.paneAnchorEstoque.setVisible(false);
    }

    private ModAcervo pegarDadosDaPesquisa() {
        if (UtilValidarDados.eNumero(this.texteFiedPesquisar.getText())) {
            acervoMod.setIdAcervo(Integer.valueOf(this.texteFiedPesquisar.getText()), operacao);
            acervoMod.setTitulo(this.texteFiedPesquisar.getText(), operacao);
            return acervoMod;
        } else {
            acervoMod.setTitulo(this.texteFiedPesquisar.getText(), operacao);
            return acervoMod;
        }
    }

    private void carregarResultadosNaTablea(List<Object> todosRegistosEncontrados) {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idAcervo"));
        tableColumTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tableColumSubTitulo.setCellValueFactory(new PropertyValueFactory<>("sub_titulo"));
        tableColumnCodigoBarra.setCellValueFactory(new PropertyValueFactory<>("codigo_barra"));
        tableColumISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        tableColumnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo_acervo"));
        tableColumnFormato.setCellValueFactory(new PropertyValueFactory<>("formato"));
        tableColumnAno.setCellValueFactory(new PropertyValueFactory<>("ano_lancamento"));
        tableColumQTotal.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModAcervo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModAcervo, String> estoque) {
                return new ReadOnlyStringWrapper(String.valueOf(estoque.getValue().getEstoqueMod().getQuantidade_total()));
            }
        });
        tableColumnQFal.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModAcervo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModAcervo, String> estoque) {
                return new ReadOnlyStringWrapper(String.valueOf(estoque.getValue().getEstoqueMod().getQuantidade_em_falta()));
            }
        });
        tableColumQEmp.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModAcervo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModAcervo, String> estoque) {
                return new ReadOnlyStringWrapper(String.valueOf(estoque.getValue().getEstoqueMod().getQuantidade_acervos_emprestados()));
            }
        });
        tableColumQRes.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModAcervo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModAcervo, String> estoque) {
                return new ReadOnlyStringWrapper(String.valueOf(estoque.getValue().getEstoqueMod().getQuantidade_acervos_resercados()));
            }
        });
        tableViewAcervo.setItems(this.todosRegistosParaCarregar(todosRegistosEncontrados));
        tableViewEstoque.setItems(this.todosRegistosParaCarregar(todosRegistosEncontrados));
    }

    private ObservableList<ModAcervo> todosRegistosParaCarregar(List<Object> todosRegistosEncontrados) {
        List<ModAcervo> listaDosRegistosWncontrados = new ArrayList<>();
        for (Object acervoRegistado : todosRegistosEncontrados) {
            ModAcervo acervoMod = (ModAcervo) acervoRegistado;
            listaDosRegistosWncontrados.add(acervoMod);
        }
        return FXCollections.observableArrayList(listaDosRegistosWncontrados);
    }

    private void bloquearItensDaJanela() {
        this.textFQTotal.setDisable(true);
        this.textFQFal.setDisable(true);
        this.textFQEmp.setDisable(true);
        this.textFQRes.setDisable(true);
    }

    private void carregarResultadosAcervos(ModAcervo acervoMod) {
        if (acervoMod != null) {
            textFQTotal.setText(String.valueOf(acervoMod.getEstoqueMod().getQuantidade_total()));
            textFQFal.setText(String.valueOf(acervoMod.getEstoqueMod().getQuantidade_em_falta()));
            textFQEmp.setText(String.valueOf(acervoMod.getEstoqueMod().getQuantidade_acervos_emprestados()));
            textFQRes.setText(String.valueOf(acervoMod.getEstoqueMod().getQuantidade_acervos_resercados()));
        }
    }
}
