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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
public class VisVerAcervo implements Initializable {
    
    @FXML
    private JFXButton botaoPesquisar;
    @FXML
    private Button  botaoCancelar, botaoSair;
    @FXML
    private TextField textFieldPesquisar;
    @FXML
    private TableView<ModAcervo> tableViewAcervo,tableViewAcervoLocalizacao ;
    @FXML
    private TableColumn<ModAcervo, String> tableColumId,tableColumTitulo, tableColumSubTitulo, tableColumEdicao,
            tableColumISBN,tableColumnCodigoBarra, tableColumnTipo, tableColumnFormato,tableColumVolume,
            tableColumArea,tableColumEstante,tableColumCategoria;
    @FXML
    private Label labelAnoLancamento,labelNumeroPaginas,labelIdioma,labelDataRegisto,labelDataModificacao;
    @FXML
    private AnchorPane AnchorPaneLocalizarAcervos;
    
    private String operacao = null;
    private final ModAcervo acervoMod = new ModAcervo();
    private final ConAcervo acervoCon = new ConAcervo();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.tableViewAcervo.setPlaceholder(new Label("Acervo não listados"));
        this.tableViewAcervoLocalizacao.setPlaceholder(new Label("Nenhum acervo seleccionado"));
        this.tableViewAcervo.getSelectionModel().selectedItemProperty().addListener((obersave, oldObserv, newValue) ->exibirMaidDeatalhesDoAcervo(newValue));
    } 
    
    @FXML
    private void localizarAcervos() {
        operacao = "Localizar acervos";
        List<ModAcervo> todosRegistosEncontrados = new ArrayList<>();
        if (this.textFieldPesquisar.getText().isEmpty()) {
            throw new UtilControloExcessao(operacao, "Introduza o código ou nome do Acervo", Alert.AlertType.INFORMATION);
        } else {
            todosRegistosEncontrados = this.acervoCon.localizarAcervo(this.pegarDadosDaPesquisaAcervos(), operacao);
            if (todosRegistosEncontrados.isEmpty()) {
                this.cancelar();
                throw new UtilControloExcessao(operacao, "Acervo não encontradao", Alert.AlertType.INFORMATION);
            } else {
                this.carregarResultadosNaTableAcervos(todosRegistosEncontrados);
            }
        }
    }
    
    @FXML
    private void cancelar() {
        this.textFieldPesquisar.setText(null);
        this.tableViewAcervo.getItems().clear();
        this.limparDadosPesquisa();
    }
 

    @FXML
    private void sair(ActionEvent event) {
        AnchorPaneLocalizarAcervos.setVisible(false);
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
    
    private void carregarResultadosNaTableAcervos(List<ModAcervo> todosRegistosEncontrados) {
        tableColumId.setCellValueFactory(new PropertyValueFactory<>("idAcervo"));
        tableColumTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tableColumnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo_acervo"));
        tableColumEdicao.setCellValueFactory(new PropertyValueFactory<>("edicao"));
        tableColumVolume.setCellValueFactory(new PropertyValueFactory<>("volume"));
        tableColumnCodigoBarra.setCellValueFactory(new PropertyValueFactory<>("codigo_barra"));
        tableColumISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        tableColumnFormato.setCellValueFactory(new PropertyValueFactory<>("formato"));
        tableViewAcervo.setItems(this.todosAcervosParaCarregar(todosRegistosEncontrados));
    }

    private ObservableList<ModAcervo> todosAcervosParaCarregar(List<ModAcervo> todosRegistosEncontrados) {
        List<ModAcervo> listaDosRegistosWncontrados = new ArrayList<>();
        for (Object acervoRegistado : todosRegistosEncontrados) {
            ModAcervo acervoMod = (ModAcervo) acervoRegistado;
            listaDosRegistosWncontrados.add(acervoMod);
        }
        return FXCollections.observableArrayList(listaDosRegistosWncontrados);
    }
    
    private void exibirMaidDeatalhesDoAcervo(ModAcervo acervoMod){
        labelAnoLancamento.setText(String.valueOf(acervoMod.getAno_lancamento()));
        labelNumeroPaginas.setText(String.valueOf(acervoMod.getNumero_paginas()));
        labelIdioma.setText(String.valueOf(acervoMod.getIdioma()));
        labelDataRegisto.setText(acervoMod.getUtilControloDaData().getData_registo());
        labelDataModificacao.setText(acervoMod.getUtilControloDaData().getData_modificacao());
        this.exbirDadosNaLocalizacao(acervoMod);
    }
    
    private void exbirDadosNaLocalizacao(ModAcervo acervoMod){
        List<ModAcervo> listaDosRegistosWncontrados = new ArrayList<>();
        ObservableList dadosCarregar = null;
        tableColumArea.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModAcervo,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModAcervo, String> localizacao) {
                return new ReadOnlyStringWrapper(localizacao.getValue().getCategoriaMod().getEstanteMod().getAreaMod().getSector());
            }
        });
        tableColumEstante.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModAcervo,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModAcervo, String> localizacao) {
                return new ReadOnlyStringWrapper(localizacao.getValue().getCategoriaMod().getEstanteMod().getDesignacao());
            }
        });
        tableColumCategoria.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModAcervo,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModAcervo, String> localizacao) {
                return new ReadOnlyStringWrapper(localizacao.getValue().getCategoriaMod().getDesignacao());
            }
        });
        listaDosRegistosWncontrados.add(acervoMod);
        dadosCarregar =  FXCollections.observableArrayList(listaDosRegistosWncontrados);
        this.tableViewAcervoLocalizacao.setItems(dadosCarregar);
    }
    
    private void limparDadosPesquisa(){
        labelAnoLancamento.setText("Nenhuma informação disponível");
        labelNumeroPaginas.setText("Nenhuma informação disponível");
        labelIdioma.setText("Nenhuma informação disponível");
        labelDataRegisto.setText("Nenhuma informação disponível");
        labelDataModificacao.setText("Nenhuma informação disponível"); 
        this.tableViewAcervoLocalizacao.getItems().clear();
    }
    
}
