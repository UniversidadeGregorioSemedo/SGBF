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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import sgbf.modelo.ModAcervo;
import sgbf.modelo.ModEditora;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilValidarDados;

public class VisVerEditora implements Initializable {
    
    @FXML
    private JFXButton botaoPesquisar;
    @FXML
    private TextField texteFiedPesquisar;
    @FXML
    private TableView<ModEditora> tableViewEditora; 
    @FXML
    private TableColumn<ModEditora, Integer> tableColumId;
    @FXML
    private TableColumn<ModEditora, String> tableColumNome,tableColumContacto,tableColumEmail,
            tableColumEndereco,tableColumFax,tableColumDataDeRegisto,tableColumDataDeModificacao;
    @FXML
     private TableView<ModAcervo> tableViewAcervo;
    @FXML
    private TableColumn<ModAcervo, String> tableColumTitulo, tableColumSubTitulo, tableColumISBN,
            tableColumnAno, tableColumnCodigoBarra, tableColumnTipo, tableColumnFormato;
    @FXML
    private AnchorPane AnchorPaneVerEditoras;
    
    private String operacao = null;
    private final ModEditora editoraMod = new ModEditora();
    private final ConEditora editoraCon = new ConEditora();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
    @FXML
    private void pesquisarEditora(){
        operacao = "Pesquisar Editora";
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        if(this.texteFiedPesquisar.getText().isEmpty()){
           throw new UtilControloExcessao(operacao, "Introduza o código ou nome da Editora", Alert.AlertType.INFORMATION);
        }else{
            todosRegistosEncontrados = this.editoraCon.pesquisar(this.pegarDadosDaPesquisa(), operacao);
            if(todosRegistosEncontrados.isEmpty()){
                this.limparItensDaJanela();
               throw new UtilControloExcessao(operacao, "Editora não encontrada", Alert.AlertType.INFORMATION);
            }else{
                this.carregarResultadosNaTablea(todosRegistosEncontrados);
            }
        }
    }
    
    @FXML
    private void sair(ActionEvent event) {
        AnchorPaneVerEditoras.setVisible(false);
    }
    
    private ModEditora pegarDadosDaPesquisa(){
       if(UtilValidarDados.eNumero(this.texteFiedPesquisar.getText())){
           editoraMod.setiEditora(Integer.valueOf(this.texteFiedPesquisar.getText()), operacao);
           editoraMod.setNome(this.texteFiedPesquisar.getText(), operacao);
           return editoraMod;
        }else{
           editoraMod.setNome(this.texteFiedPesquisar.getText(), operacao);
           return editoraMod;
        }
    }
    
     private void carregarResultadosNaTablea(List<Object> todosRegistosEncontrados){
        tableColumId.setCellValueFactory(new PropertyValueFactory<>("iEditora"));
        tableColumNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumContacto.setCellValueFactory(new PropertyValueFactory<>("contacto"));
        tableColumEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        tableColumDataDeRegisto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModEditora,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModEditora, String> data) {
                return new ReadOnlyStringWrapper(data.getValue().getUtilControloDaData().getData_registo());
            }
        });
        tableColumDataDeModificacao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModEditora,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ModEditora, String> data) {
                return new ReadOnlyStringWrapper(data.getValue().getUtilControloDaData().getData_modificacao());
            }
        });
        tableViewEditora.setItems(this.todosRegistosParaCarregar(todosRegistosEncontrados));
    }
    
    private ObservableList<ModEditora> todosRegistosParaCarregar(List<Object> todosRegistosEncontrados){
        List<ModEditora> listaDosRegistosWncontrados = new ArrayList<>();
        for(Object utenteRegistado: todosRegistosEncontrados){
            ModEditora editoraMod = (ModEditora)utenteRegistado;
            listaDosRegistosWncontrados.add(editoraMod);
        } 
        return FXCollections.observableArrayList(listaDosRegistosWncontrados);
    }
    @FXML
    private void limparItensDaJanela(){
        this.texteFiedPesquisar.setText(null);
        this.tableViewAcervo.getItems().clear();
        this.tableViewEditora.getItems().clear();
    }
    
}
