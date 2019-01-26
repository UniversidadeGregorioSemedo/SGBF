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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import sgbf.modelo.ModAcervo;
import sgbf.modelo.ModAcervosEscritos;
import sgbf.modelo.ModAutor;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilValidarDados;

/**
 * FXML Controller class
 *
 * @author Marron
 */
public class VisVerAutor implements Initializable {

    @FXML
    private TextField texteFiedPesquisar;
    @FXML
    private JFXButton botaoPesquisar;
    @FXML
    private Button botaoSair;
    @FXML
    private TableView<ModAutor> tableViewAutor;
    @FXML
    private TableColumn<ModAutor, Integer> tableColumId;
    @FXML
    private TableColumn<ModAutor, String> tableColumNome;
    @FXML
    private TableColumn<ModAutor, String> tableColumContacto, tableColumEmail;
    @FXML
    private TableColumn<ModAutor, String> tableColumUltimaModificacao, tableColumDataRegisto;
    @FXML
    private TableView<ModAcervo> tableViewAcervo;
    @FXML
    private TableColumn<ModAcervo, String> tableColumTitulo, tableColumSubTitulo, tableColumISBN,
            tableColumnAno, tableColumnCodigoBarra, tableColumnTipo, tableColumnFormato;
    @FXML
    private Label labeTotalAcervos, labeDataRegisto, labeUltimaModificacao,
            labelHoraDataCorrente;

    @FXML
    private AnchorPane anchorPaneVisAutor;

    private String operacao = null;
    private final ModAutor autorMod = new ModAutor();
    private final ConAutor autorCon = new ConAutor();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.texteFiedPesquisar.setTooltip(new Tooltip("Introduza o código, nome do autor ou use *( _ ) para listar todos registos "));
        tableViewAutor.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> this.carregarResultadosAcervos(newValue));
        tableViewAcervo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> this.carregarResultadosAcervos(newValue));
    }

    @FXML
    private void pesquisarAutor() {
        operacao = "Pesquisar Autor";
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        if (this.texteFiedPesquisar.getText().isEmpty()) {
            throw new UtilControloExcessao(operacao, "Introduza o código ou nome do Autor", Alert.AlertType.INFORMATION);
        } else {
            todosRegistosEncontrados = this.autorCon.pesquisar(this.pegarDadosDaPesquisa(), operacao);
            if (todosRegistosEncontrados.isEmpty()) {
                this.limparItensDaJanela();
                throw new UtilControloExcessao(operacao, "Autor não encontradao", Alert.AlertType.INFORMATION);
            } else {
                this.carregarResultadosNaTablea(todosRegistosEncontrados);
            }
        }
    }

    @FXML
    private void sair() {
        this.anchorPaneVisAutor.setVisible(false);
    }

    private ModAutor pegarDadosDaPesquisa() {
        if (UtilValidarDados.eNumero(this.texteFiedPesquisar.getText())) {
            autorMod.setIdAutor(Integer.valueOf(this.texteFiedPesquisar.getText()), operacao);
            autorMod.setPrimeiro_nome(this.texteFiedPesquisar.getText(), operacao);
            return autorMod;
        } else {
            autorMod.setPrimeiro_nome(this.texteFiedPesquisar.getText(), operacao);
            return autorMod;
        }
    }

    private void carregarResultadosNaTablea(List<Object> todosRegistosEncontrados) {
        tableColumId.setCellValueFactory(new PropertyValueFactory<>("idAutor"));
        tableColumNome.setCellValueFactory(new PropertyValueFactory<>("nomeCompleto"));
        tableColumContacto.setCellValueFactory(new PropertyValueFactory<>("contacto"));
        tableColumEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumDataRegisto.setCellValueFactory(new Callback<CellDataFeatures<ModAutor, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModAutor, String> data) {
                return new ReadOnlyStringWrapper(data.getValue().getUtilControloDaData().getData_registo());
            }
        });
        tableColumUltimaModificacao.setCellValueFactory(new Callback<CellDataFeatures<ModAutor, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModAutor, String> data) {
                return new ReadOnlyStringWrapper(data.getValue().getUtilControloDaData().getData_modificacao());
            }
        });
        tableViewAutor.setItems(this.todosRegistosParaCarregar(todosRegistosEncontrados));
    }

    private ObservableList<ModAutor> todosRegistosParaCarregar(List<Object> todosRegistosEncontrados) {
        List<ModAutor> listaDosRegistosEncontrados = new ArrayList<>();
        for (Object autorRegistado : todosRegistosEncontrados) {
            ModAutor autorMod = (ModAutor) autorRegistado;
            listaDosRegistosEncontrados.add(autorMod);
        }
        return FXCollections.observableArrayList(listaDosRegistosEncontrados);
    }

    @FXML
    private void limparItensDaJanela() {
        this.labeTotalAcervos.setText(null);
        this.labeDataRegisto.setText(null);
        this.labeUltimaModificacao.setText(null);
        this.texteFiedPesquisar.setText(null);
        this.tableViewAutor.getItems().clear();
        this.tableViewAcervo.getItems().clear();
    }

    private void carregarResultadosAcervos(ModAcervo acervoMod) {
        labeDataRegisto.setText(acervoMod.getUtilControloDaData().getData_registo());
        labeUltimaModificacao.setText(acervoMod.getUtilControloDaData().getData_modificacao());
    }
    private void carregarResultadosAcervos(ModAutor autorMod) {
        tableColumTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tableColumSubTitulo.setCellValueFactory(new PropertyValueFactory<>("sub_titulo"));
        tableColumISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        tableColumnCodigoBarra.setCellValueFactory(new PropertyValueFactory<>("codigo_barra"));
        tableColumnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo_acervo"));
        tableColumnFormato.setCellValueFactory(new PropertyValueFactory<>("formato"));
        tableColumnAno.setCellValueFactory(new PropertyValueFactory<>("ano_lancamento"));
        tableViewAcervo.setItems(this.todosRegistosParaCarregar(autorMod,operacao));
    }
    
    
    private ObservableList<ModAcervo> todosRegistosParaCarregar(ModAutor autorMod, String operacao) {
        List<ModAcervo> listaDosRegistosEncontrados = new ArrayList<>();
        for (ModAcervosEscritos acervoRegistado : autorCon.acervosEscritos(autorMod, operacao)) {
            ModAcervo acervosEscritos = acervoRegistado.getAcervoMod();
            listaDosRegistosEncontrados.add(acervosEscritos);
        }
        labeTotalAcervos.setText(String.valueOf(listaDosRegistosEncontrados.size()));
        return FXCollections.observableArrayList(listaDosRegistosEncontrados);
    }
    
    

}
