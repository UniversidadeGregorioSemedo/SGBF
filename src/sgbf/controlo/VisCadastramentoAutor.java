package sgbf.controlo;

import sgbf.dao.ConAutor;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sgbf.modelo.ModAutor;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilValidarDados;

/**
 * FXML Controller class
 *
 * @author Marron
 */
public class VisCadastramentoAutor implements Initializable {

    @FXML
    private Button botaoCadastrar, botaoAlterar, botaoRemover, botaoNovo, botaoCancelar, botaoFechar;
    @FXML
    private JFXButton botaoPesquisar;
    @FXML
    private TextField texteFiedPesquisar, texteFiedPrimeiroNome, texteFiedSegundoNome, texteFiedContacto, texteFiedEmail;
    @FXML
    private TableView<ModAutor> tableViewAutor;
    @FXML
    private TableColumn<ModAutor, Integer> tableColumId;
    @FXML
    private TableColumn<ModAutor, String> tableColumNome, tableColumContacto, tableColumEmail;
    @FXML
    private AnchorPane AnchorPaneAutor;

    private String operacao = null;
    private final ModAutor autorMod = new ModAutor();
    private final ConAutor autorCon = new ConAutor();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.bloquearItensDaJanela();
        this.tableViewAutor.setPlaceholder(new Label("Autores não listados"));
        this.texteFiedPesquisar.setTooltip(new Tooltip("Introduza o código, nome do autor ou use *( _ ) para listar todos registos "));
        tableViewAutor.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> this.exibirDadosNosCampos(newValue));
    }

    @FXML
    private void cadastrarAutor() {
        operacao = "Registar Autor";
        autorMod.setPrimeiro_nome(texteFiedPrimeiroNome.getText(), operacao);
        autorMod.setSegundo_nome(texteFiedSegundoNome.getText(), operacao);
        autorMod.setContacto(texteFiedContacto.getText(), operacao);
        autorMod.setEmail(texteFiedEmail.getText(), operacao);
        if (autorCon.registar(autorMod, operacao)) {
            this.bloquearItensDaJanela();
            this.limparItensDaJanela();
            throw new UtilControloExcessao(operacao, "Utente Autor com sucesso", Alert.AlertType.CONFIRMATION);
        }
    }

    @FXML
    private void alterarAutor() {
        operacao = "Editar Autor";
        autorMod.setIdAutor(this.tableViewAutor.getSelectionModel().getSelectedItem().getIdAutor(), operacao);
        autorMod.setPrimeiro_nome(texteFiedPrimeiroNome.getText(), operacao);
        autorMod.setSegundo_nome(texteFiedSegundoNome.getText(), operacao);
        autorMod.setContacto(texteFiedContacto.getText(), operacao);
        autorMod.setEmail(texteFiedEmail.getText(), operacao);
        if (autorCon.alterar(autorMod, operacao)) {
            this.bloquearItensDaJanela();
            this.limparItensDaJanela();
            throw new UtilControloExcessao(operacao, "Autor alterado com sucesso", Alert.AlertType.CONFIRMATION);
        }
    }

    @FXML
    private void removerAutor() {
        operacao = "Remover Autor";
        ModAutor autorARemover = this.tableViewAutor.getSelectionModel().getSelectedItem();
        if (autorCon.remover(autorARemover, operacao)) {
            this.tableViewAutor.getItems().remove(autorARemover);
            this.bloquearItensDaJanela();
            throw new UtilControloExcessao(operacao, "Autor removido com sucesso", Alert.AlertType.CONFIRMATION);
        }
    }

    @FXML
    private void pesquisarUtente() {
        operacao = "Pesquisar Autor";
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        if (this.texteFiedPesquisar.getText().isEmpty()) {
            throw new UtilControloExcessao(operacao, "Introduza o código ou nome do Autor", Alert.AlertType.INFORMATION);
        } else {
            todosRegistosEncontrados = this.autorCon.pesquisar(this.pegarDadosDaPesquisa(), operacao);
            if (todosRegistosEncontrados.isEmpty()) {
                this.bloquearItensDaJanela();
                this.limparItensDaJanela();
                throw new UtilControloExcessao(operacao, "Autor não encontradao", Alert.AlertType.INFORMATION);
            } else {
                this.carregarResultadosNaTablea(todosRegistosEncontrados);
                this.bloquearItensDaJanela();
            }
        }
    }

    @FXML
    private void novo() {
        this.desbloquearItensDaJanela();
        this.limparItensDaJanela();
    }

    @FXML
    private void cancelar() {
        this.bloquearItensDaJanela();
        this.limparItensDaJanela();
    }

    @FXML
    private void sair(ActionEvent event) {
        AnchorPaneAutor.setVisible(false);
    }

    private void desbloquearItensDaJanela() {
        this.texteFiedPrimeiroNome.setDisable(false);
        this.texteFiedSegundoNome.setDisable(false);
        this.texteFiedContacto.setDisable(false);
        this.texteFiedEmail.setDisable(false);
        this.botaoNovo.setDisable(true);
        this.botaoCadastrar.setDisable(false);
    }

    private void bloquearItensDaJanela() {
        this.texteFiedPrimeiroNome.setDisable(true);
        this.texteFiedSegundoNome.setDisable(true);
        this.texteFiedContacto.setDisable(true);
        this.texteFiedEmail.setDisable(true);
        this.botaoNovo.setDisable(false);
        this.botaoCadastrar.setDisable(true);
        this.botaoAlterar.setDisable(true);
        this.botaoRemover.setDisable(true);
    }

    private void limparItensDaJanela() {
        this.texteFiedPrimeiroNome.setText(null);
        this.texteFiedSegundoNome.setText(null);
        this.texteFiedContacto.setText(null);
        this.texteFiedEmail.setText(null);
        this.texteFiedPesquisar.setText(null);
        this.tableViewAutor.getItems().clear();
    }

    private void exibirDadosNosCampos(ModAutor autorMod) {
        if (tableViewAutor.getSelectionModel().getSelectedCells().size() == 1) {
            texteFiedPrimeiroNome.setText(autorMod.getPrimeiro_nome());
            texteFiedSegundoNome.setText(autorMod.getSegundo_nome());
            texteFiedContacto.setText(autorMod.getContacto());
            texteFiedEmail.setText(autorMod.getEmail());
            botaoAlterar.setDisable(false);
            botaoRemover.setDisable(false);
            this.desbloquearItensDaJanela();
            botaoNovo.setDisable(true);
            botaoCadastrar.setDisable(true);
        } else {
            botaoAlterar.setDisable(true);
            botaoRemover.setDisable(true);
            botaoNovo.setDisable(false);
            this.limparItensDaJanela();
        }
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

}
