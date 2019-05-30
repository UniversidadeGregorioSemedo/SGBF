package sgbf.controlo;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sgbf.modelo.ModFuncionario;
import sgbf.util.ProriedadesDaJanela;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilUsuarioLogado;

/**
 * @author Marron
 */
public class VisTelaPrincipal implements Initializable {

    @FXML
    private MenuItem menuitemCadastroAcervo;
    @FXML
    private MenuItem menuitemCadastroAutor;
    @FXML
    private MenuItem menuitemCadastroCategoria;
    @FXML
    private MenuItem menuitemCadastroEditora;
    @FXML
    private MenuItem menuitemCadastroEstante;
    @FXML
    private MenuItem menuitemCadastroFuncionario;
    @FXML
    private MenuItem menuitemCadastroProveniencia;
    @FXML
    private MenuItem menuitemCadastroItemProveniencia;
    @FXML
    private MenuItem menuitemCadastroUtente;
    @FXML
    private MenuItem menuitemVerUtentes;
    @FXML
    private MenuItem menuitemVerProveniencias;
    @FXML
    private MenuItem menuitemVerFuncionarios;
    @FXML
    private MenuItem menuitemVerEstoques;
    @FXML
    private MenuItem menuitemVerEstantes;
    @FXML
    private MenuItem menuitemVerEditoras;
    @FXML
    private MenuItem menuitemVerCategorias;
    @FXML
    private MenuItem menuitemVerAutores;
    @FXML
    private MenuItem menuitemVerAreas;
    @FXML
    private MenuItem menuitenmVerAcervos;
    @FXML
    private MenuItem menuitemMovimentacaoEmprestimo;
    @FXML
    private MenuItem menuitemMovimentacaoTodosEmprestimo;
    @FXML
    private MenuItem menuitemMovimentacaoReserva;
    @FXML
    private MenuItem menuitemMovimentacaoTodasReserva;
    @FXML
    private MenuItem menuitemMovimentacaoDevolucao;
    @FXML
    private MenuItem menuItemTerminarSessao;
    @FXML
    private MenuItem menuItemSairDoSistema;
    @FXML
    private MenuItem menuItemAjuda;
    @FXML
    private Menu menuArquivo, menuCadastramento, menuVer, menuMovimentacao, menuAjuda;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Label labelOperador;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        habilitarFuncionalidadeParaFuncionario(UtilUsuarioLogado.getUsuarioLogado());
    }

    @FXML
    public void BotaoMenuItemUtente() throws IOException {
        AnchorPane x = (AnchorPane) FXMLLoader.load(getClass().getResource("/sgbf/visao/VisCadastramentoUtente.fxml"));
        anchorPane.getChildren().setAll(x);
    }

    @FXML
    public void BotaoMenuItemFuncionario() throws IOException {
        AnchorPane x = (AnchorPane) FXMLLoader.load(getClass().getResource("/sgbf/visao/VisCadastramentoFuncionario.fxml"));
        anchorPane.getChildren().setAll(x);
    }

    @FXML
    public void BotaoMenuItemEstante() throws IOException {
        AnchorPane x = (AnchorPane) FXMLLoader.load(getClass().getResource("/sgbf/visao/VisCadastramentoEstante.fxml"));
        anchorPane.getChildren().setAll(x);
    }

    @FXML
    public void BotaoMenuItemEditora() throws IOException {
        AnchorPane x = (AnchorPane) FXMLLoader.load(getClass().getResource("/sgbf/visao/VisCadastramentoEditora.fxml"));
        anchorPane.getChildren().setAll(x);
    }

    @FXML
    public void BotaoMenuItemCategoria() throws IOException {
        AnchorPane x = (AnchorPane) FXMLLoader.load(getClass().getResource("/sgbf/visao/VisCadastramentoCategoria.fxml"));
        anchorPane.getChildren().setAll(x);
    }

    @FXML
    public void BotaoMenuItemVerArea() throws IOException {
        AnchorPane x = (AnchorPane) FXMLLoader.load(getClass().getResource("/sgbf/visao/VisCadastramentoArea.fxml"));
        anchorPane.getChildren().setAll(x);
    }

    @FXML
    public void BotaoMenuItemAcervo() throws IOException {
        AnchorPane x = (AnchorPane) FXMLLoader.load(getClass().getResource("/sgbf/visao/VisCadastramentoAcervo.fxml"));
        anchorPane.getChildren().setAll(x);
    }

    @FXML
    public void BotaoMenuItemAutor() throws IOException {
        AnchorPane x = (AnchorPane) FXMLLoader.load(getClass().getResource("/sgbf/visao/VisCadastramentoAutor.fxml"));
        anchorPane.getChildren().setAll(x);
    }

    @FXML
    public void BotaoMenuItemProveniencia() throws IOException {
        AnchorPane x = (AnchorPane) FXMLLoader.load(getClass().getResource("/sgbf/visao/VisCadastramentoProveniencia.fxml"));
        anchorPane.getChildren().setAll(x);
    }

    @FXML
    public void BotaoMenuItemItensProveniencia() throws IOException {
        AnchorPane x = (AnchorPane) FXMLLoader.load(getClass().getResource("/sgbf/visao/VisCadastramentoItensProvenientes.fxml"));
        anchorPane.getChildren().setAll(x);
    }

    @FXML
    public void BotaoMenuItemMovimentacaoEmprestimo() throws IOException {
        AnchorPane x = (AnchorPane) FXMLLoader.load(getClass().getResource("/sgbf/visao/VisMovimentacaoEmprestimo.fxml"));
        anchorPane.getChildren().setAll(x);
    }

    @FXML
    public void BotaoMenuItemMovimentacaoTodosEmprestimos() throws IOException {
        AnchorPane x = (AnchorPane) FXMLLoader.load(getClass().getResource("/sgbf/visao/VisMovimentacaoTodosEmprestimos.fxml"));
        anchorPane.getChildren().setAll(x);
    }

    @FXML
    public void BotaoMenuItemMovimentacaoReserva() throws IOException {
        AnchorPane x = (AnchorPane) FXMLLoader.load(getClass().getResource("/sgbf/visao/VisMovimentacaoReserva.fxml"));
        anchorPane.getChildren().setAll(x);
    }

    @FXML
    public void BotaoMenuItemMovimentacaoTodasReserva() throws IOException {
        AnchorPane x = (AnchorPane) FXMLLoader.load(getClass().getResource("/sgbf/visao/VisMovimentacaoTodasReservas.fxml"));
        anchorPane.getChildren().setAll(x);
    }

    @FXML
    public void BotaoMenuItemMovimentacaoDevolucao() throws IOException {
        AnchorPane x = (AnchorPane) FXMLLoader.load(getClass().getResource("/sgbf/visao/VisMovimentacaoDevolucao.fxml"));
        anchorPane.getChildren().setAll(x);
    }

    @FXML
    public void BotaoMenuItemVerUtente() throws IOException {
        AnchorPane x = (AnchorPane) FXMLLoader.load(getClass().getResource("/sgbf/visao/VisVerUtente.fxml"));
        anchorPane.getChildren().setAll(x);
    }

    @FXML
    public void BotaoMenuItemVerAcervo() throws IOException {
        AnchorPane x = (AnchorPane) FXMLLoader.load(getClass().getResource("/sgbf/visao/VisVerAcervo.fxml"));
        anchorPane.getChildren().setAll(x);
    }

    @FXML
    public void BotaoMenuItemVerEditora() throws IOException {
        AnchorPane x = (AnchorPane) FXMLLoader.load(getClass().getResource("/sgbf/visao/VisVerEditora.fxml"));
        anchorPane.getChildren().setAll(x);
    }

    @FXML
    public void BotaoMenuItemVerAutor() throws IOException {
        AnchorPane x = (AnchorPane) FXMLLoader.load(getClass().getResource("/sgbf/visao/VisVerAutor.fxml"));
        anchorPane.getChildren().setAll(x);
    }

    @FXML
    public void BotaoMenuItemVerEstoque() throws IOException {
        AnchorPane x = (AnchorPane) FXMLLoader.load(getClass().getResource("/sgbf/visao/VisVerEstoque.fxml"));
        anchorPane.getChildren().setAll(x);
    }

    @FXML
    public void botaoMenuItemTerminarSessao() throws IOException {
        UtilControloExcessao confirmar = new UtilControloExcessao();
        final String mensagem = "Tem a certeza que pretende terminar sessão ?";
        if (confirmar.temCerteza(menuItemTerminarSessao.getText(), mensagem)) {
            Stage propriedadeDaJanela = (Stage) menuBar.getScene().getWindow();
            propriedadeDaJanela.close();

            Parent root = FXMLLoader.load(this.getClass().getResource("..\\visao\\VisLogin.fxml"));
            Scene scene = new Scene(root);
            propriedadeDaJanela.setScene(scene);
            ProriedadesDaJanela.barraDeTitulo(propriedadeDaJanela);
            propriedadeDaJanela.setResizable(false);
            propriedadeDaJanela.setMaximized(false);
            propriedadeDaJanela.show();
        }
    }

    @FXML
    public void BotaoMenuAjuda() {
        final String operacao = "Guia de utilização";
        Desktop configuracoesPadrao = Desktop.getDesktop();
        String endereco = "https://drive.google.com/file/d/0B0FOuZ3NycZAV0NMZUNIMS1PNmhnc1ktcmtLQWZHeXpubThZ/view";
        try {
            configuracoesPadrao.browse(new URI(endereco));
        } catch (IOException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao verificar endereço", Alert.AlertType.WARNING);
        } catch (URISyntaxException erro) {
            throw new UtilControloExcessao(operacao, "Enderço incorreto", Alert.AlertType.WARNING);
        }
    }

    @FXML
    public void botaoMenuItemSairDoSistema() {
        Stage propriedadeDaJanela = (Stage) menuBar.getScene().getWindow();
        Main.sairdoSistema(menuItemSairDoSistema.getText(), propriedadeDaJanela);
    }

    private void habilitarFuncionalidadeParaFuncionario(ModFuncionario funcionarioMod) {
        if (funcionarioMod.getCargo().equalsIgnoreCase("Administrador")) {
            menuCadastramento.setVisible(true);
        } else {
            if (funcionarioMod.getCargo().equalsIgnoreCase("Bibliotecario")) {
                menuCadastramento.setVisible(false);
            }
        }
    }

}
