package sgbf.controlo;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sgbf.modelo.ModAcervo;
import sgbf.modelo.ModAutor;
import sgbf.modelo.ModCategoria;
import sgbf.modelo.ModEditora;
import sgbf.modelo.ModEstante;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilValidarDados;

/**
 * FXML Controller class
 *
 * @author Marron
 */
public class VisCadastramentoAcervo implements Initializable {

    @FXML
    private JFXButton botaoPesquisar;
    @FXML
    private Button botaoCarregar, botaoCadastrar, botaoAlterar, botaoRemover, botaoNovo, botaoCancelar, botaoSair;
    @FXML
    private TextField texteFiedPesquisar, texteFiedTitulo, texteFiedSubTitulo, texteFiedEdicao,
            texteFiedVolume, texteFiedNumPaginas, texteFieldAno, texteFiedCodigoBarra, texteFiedISBN,
            texteFiedEndereco;
    @FXML
    private ComboBox<String> comboBoxTipo, comboBoxFormato, comboBoxIdioma;
    @FXML
    private ComboBox<ModAutor> comboBoxAutor;
    @FXML
    private ComboBox<ModEditora> comboBoxEditora;
    @FXML
    private ComboBox<ModEstante> comboBoxEstante;
    @FXML
    private ComboBox<ModCategoria> comboBoxCategoria;
    @FXML
    private TableView<ModAcervo> tableViewAcervo;
    @FXML
    private TableColumn<ModAcervo, String> tableColumTitulo, tableColumSubTitulo, tableColumEdicao, tableColumISBN,
            tableColumnAno, tableColumnTipo, tableColumnFormato;
    @FXML
    private Label labelEstante, labelEndereco;
    @FXML
    private JFXTextArea textAreaSinopese;
    @FXML
    private AnchorPane AnchorPaneAcervo;

    private String operacao = null;
    private final ModAcervo acervoMod = new ModAcervo();
    private final ConAcervo acervoCon = new ConAcervo();
    private final ConAcervosEscreitos escreitosAcervosCon = new ConAcervosEscreitos();
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.carregarValoresPadraoNasComboxs();
        this.bloquearItensDaJanela();
        this.tableViewAcervo.setPlaceholder(new Label("Acervos não listados"));
        this.texteFiedPesquisar.setTooltip(new Tooltip("Introduza o código, título do acervo ou use *( _ ) para listar todos registos "));
        this.tableViewAcervo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> this.exibirDadosNosCampos(newValue));
    }

    @FXML
    private void cadastrarAcervos() {
        operacao = "Registar Acervos";
        try {
            acervoMod.setTitulo(texteFiedTitulo.getText(), operacao);
            acervoMod.setSub_titulo(texteFiedSubTitulo.getText(), operacao);
            acervoMod.setTipo_acervo(comboBoxTipo.getSelectionModel().getSelectedItem(), operacao);
            acervoMod.setEdicao(Byte.valueOf(UtilValidarDados.validarInteiro(texteFiedEdicao.getText())), operacao);
            acervoMod.setVolume(Byte.valueOf(UtilValidarDados.validarInteiro(texteFiedVolume.getText())), operacao);
            acervoMod.setCodigo_barra(texteFiedCodigoBarra.getText(), operacao);
            acervoMod.setIsbn(texteFiedISBN.getText(), operacao);
            acervoMod.setIdioma(comboBoxIdioma.getSelectionModel().getSelectedItem(), operacao);
            acervoMod.setAno_lancamento(Integer.valueOf(UtilValidarDados.validarInteiro(texteFieldAno.getText())), operacao);
            acervoMod.setSinopse(textAreaSinopese.getText(), operacao);
            acervoMod.setFormato(this.validarLocalizacao(comboBoxFormato.getSelectionModel().getSelectedItem()), operacao);
            acervoMod.setCategoriaMod(comboBoxCategoria.getSelectionModel().getSelectedItem(), operacao);
            acervoMod.setEndereco_acervo(texteFiedEndereco.getText(), operacao);
            acervoMod.setNumero_paginas(Short.valueOf(UtilValidarDados.validarInteiro(texteFiedNumPaginas.getText())), operacao);
            acervoMod.setEditoraMod(comboBoxEditora.getSelectionModel().getSelectedItem(), operacao);
            acervoMod.setAutorMod(comboBoxAutor.getSelectionModel().getSelectedItem(), operacao);
            if (acervoCon.registar(acervoMod, operacao)) {
                acervoMod.setIdAcervo(acervoCon.ultimoCodigoRegistado(operacao), operacao);
                if (escreitosAcervosCon.registar(acervoMod, operacao)) {
                    this.bloquearItensDaJanela();
                    this.limparItensDaJanela();
                    throw new UtilControloExcessao(operacao, "Acervo Cadastrado com sucesso", Alert.AlertType.CONFIRMATION);
                }
            }
        } catch (NumberFormatException erro) {
            throw new UtilControloExcessao(operacao, "Edição ou volume ultrapassou o limite permitdo", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void alterarAcervo() {
        operacao = "Editar Acervo";
        try {
            acervoMod.setIdAcervo(this.tableViewAcervo.getSelectionModel().getSelectedItem().getIdAcervo(), operacao);
            acervoMod.setTitulo(texteFiedTitulo.getText(), operacao);
            acervoMod.setSub_titulo(texteFiedSubTitulo.getText(), operacao);
            acervoMod.setTipo_acervo(comboBoxTipo.getSelectionModel().getSelectedItem(), operacao);
            acervoMod.setEdicao(Byte.valueOf(UtilValidarDados.validarInteiro(texteFiedEdicao.getText())), operacao);
            acervoMod.setVolume(Byte.valueOf(UtilValidarDados.validarInteiro(texteFiedVolume.getText())), operacao);
            acervoMod.setCodigo_barra(texteFiedCodigoBarra.getText(), operacao);
            acervoMod.setIsbn(texteFiedISBN.getText(), operacao);
            acervoMod.setIdioma(comboBoxIdioma.getSelectionModel().getSelectedItem(), operacao);
            acervoMod.setAno_lancamento(Integer.valueOf(UtilValidarDados.validarInteiro(texteFieldAno.getText())), operacao);
            acervoMod.setSinopse(textAreaSinopese.getText(), operacao);
            acervoMod.setFormato(this.validarLocalizacao(comboBoxFormato.getSelectionModel().getSelectedItem()), operacao);
            acervoMod.setCategoriaMod(comboBoxCategoria.getSelectionModel().getSelectedItem(), operacao);
            acervoMod.setEndereco_acervo(texteFiedEndereco.getText(), operacao);
            acervoMod.setNumero_paginas(Short.valueOf(UtilValidarDados.validarInteiro(texteFiedNumPaginas.getText())), operacao);
            acervoMod.setEditoraMod(comboBoxEditora.getSelectionModel().getSelectedItem(), operacao);
            acervoMod.setAutorMod(comboBoxAutor.getSelectionModel().getSelectedItem(), operacao);
            if (acervoCon.alterar(acervoMod, operacao)) {
                if (escreitosAcervosCon.alterar(acervoMod, operacao)) {
                    this.bloquearItensDaJanela();
                    this.limparItensDaJanela();
                    throw new UtilControloExcessao(operacao, "Acervo alterado com sucesso", Alert.AlertType.CONFIRMATION);
                }
            }
        } catch (NumberFormatException erro) {
            throw new UtilControloExcessao(operacao, "Edição ou volume ultrapassou o limite permitdo", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void removerAcervo() {
        operacao = "Remover Acervo";
        ModAcervo acervoARemover = this.tableViewAcervo.getSelectionModel().getSelectedItem();
        if (acervoCon.remover(acervoARemover, operacao)) {
            this.tableViewAcervo.getItems().remove(acervoARemover);
            this.bloquearItensDaJanela();
            throw new UtilControloExcessao(operacao, "Acervo removido com sucesso", Alert.AlertType.CONFIRMATION);
        }
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
    private void carregarAcervo() {
        FileChooser carregarAcervo = new FileChooser();
        carregarAcervo.setTitle("Seleccione o Acervo");
        carregarAcervo.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        Stage propriedadeDaJanela = (Stage) AnchorPaneAcervo.getScene().getWindow();
        String enderecoAcervo = carregarAcervo.showOpenDialog(propriedadeDaJanela).getPath();
        texteFiedEndereco.setText(enderecoAcervo);
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
        AnchorPaneAcervo.setVisible(false);
    }

    private void desbloquearItensDaJanela() {
        this.texteFiedTitulo.setDisable(false);
        this.texteFiedSubTitulo.setDisable(false);
        this.comboBoxTipo.setDisable(false);
        this.comboBoxFormato.setDisable(false);
        this.texteFiedEdicao.setDisable(false);
        this.texteFiedVolume.setDisable(false);
        this.texteFiedNumPaginas.setDisable(false);
        this.texteFieldAno.setDisable(false);
        this.comboBoxIdioma.setDisable(false);
        this.comboBoxEditora.setDisable(false);
        this.texteFiedCodigoBarra.setDisable(false);
        this.texteFiedISBN.setDisable(false);
        this.textAreaSinopese.setDisable(false);
        this.comboBoxAutor.setDisable(false);
        this.comboBoxEstante.setDisable(false);
        this.comboBoxCategoria.setDisable(false);
        this.botaoNovo.setDisable(true);
        this.botaoCadastrar.setDisable(false);
    }

    private void bloquearItensDaJanela() {
        this.texteFiedTitulo.setDisable(true);
        this.texteFiedSubTitulo.setDisable(true);
        this.comboBoxTipo.setDisable(true);
        this.comboBoxFormato.setDisable(true);
        this.texteFiedEdicao.setDisable(true);
        this.texteFiedVolume.setDisable(true);
        this.texteFiedNumPaginas.setDisable(true);
        this.texteFieldAno.setDisable(true);
        this.comboBoxIdioma.setDisable(true);
        this.comboBoxEditora.setDisable(true);
        this.texteFiedCodigoBarra.setDisable(true);
        this.texteFiedISBN.setDisable(true);
        this.textAreaSinopese.setDisable(true);
        this.texteFiedEndereco.setDisable(true);
        this.comboBoxAutor.setDisable(true);
        this.comboBoxEstante.setDisable(true);
        this.comboBoxCategoria.setDisable(true);
        this.botaoCarregar.setDisable(true);
        this.botaoNovo.setDisable(false);
        this.botaoCadastrar.setDisable(true);
        this.botaoAlterar.setDisable(true);
        this.botaoRemover.setDisable(true);
    }

    private void limparItensDaJanela() {
        this.texteFiedTitulo.setText(null);
        this.texteFiedSubTitulo.setText(null);
        this.texteFiedEdicao.setText(null);
        this.texteFiedVolume.setText(null);
        this.texteFiedNumPaginas.setText(null);
        this.texteFieldAno.setText(null);
        this.texteFiedCodigoBarra.setText(null);
        this.texteFiedISBN.setText(null);
        this.textAreaSinopese.setText(null);
        this.texteFiedPesquisar.setText(null);
        this.texteFiedEndereco.setText(null);
        this.comboBoxAutor.getItems().clear();
        this.comboBoxCategoria.getItems().clear();
        this.comboBoxEditora.getItems().clear();
        this.comboBoxEstante.getItems().clear();
        this.tableViewAcervo.getItems().clear();
    }

    private void carregarValoresPadraoNasComboxs() {
        this.comboBoxTipo.getItems().addAll("Jornal", "Livro", "Monografia", "Revista", "Trabalho académico");
        this.comboBoxFormato.getItems().addAll("Digital", "Físico");
        this.comboBoxIdioma.getItems().addAll("Protuguês", "Inglês", "Outro");
    }

    private void carregarValorNasComboxs() {
        ConEditora editoraCon = new ConEditora();
        ConEstante estanteCon = new ConEstante();
        ConAutor autorCon = new ConAutor();
        List<ModEditora> todasEditoras = new ArrayList<>();
        List<ModEstante> todasEstante = new ArrayList<>();
        List<ModAutor> todosAutores = new ArrayList<>();
        ObservableList todasEditorasParaCombox = null;
        ObservableList todasEstanteParaCombox = null;
        ObservableList todasAutoresParaCombox = null;
        //Listar Editoras
        todasEditoras.add(new ModEditora());
        for (Object todosRegistos : editoraCon.listarTodos(operacao)) {
            ModEditora editoraRegistada = (ModEditora) todosRegistos;
            todasEditoras.add(editoraRegistada);
        }
        todasEditorasParaCombox = FXCollections.observableArrayList(todasEditoras);
        this.comboBoxEditora.setItems(todasEditorasParaCombox);
        //Listar Estantes
        todasEstante.add(new ModEstante());
        for (Object todosRegistos : estanteCon.listarTodos(operacao)) {
            ModEstante estanteMod = (ModEstante) todosRegistos;
            todasEstante.add(estanteMod);
        }
        todasEstanteParaCombox = FXCollections.observableArrayList(todasEstante);
        this.comboBoxEstante.setItems(todasEstanteParaCombox);
        //Listar as categorias
        this.carregarCategorias();
        //Listar Autores
        todosAutores.add(new ModAutor());
        for (Object todosRegistos : autorCon.listarTodos(operacao)) {
            ModAutor autorRegistado = (ModAutor) todosRegistos;
            todosAutores.add(autorRegistado);
        }
        todasAutoresParaCombox = FXCollections.observableArrayList(todosAutores);
        this.comboBoxAutor.setItems(todasAutoresParaCombox);
    }

    private void carregarCategorias() {
        ConCategoria categoriaCon = new ConCategoria();
        List<ModCategoria> todasCategorias = new ArrayList<>();
        ObservableList todasCategoriasPAraCombox = null;
        if (categoriaCon.listarTodos(operacao).isEmpty()) {
            this.AnchorPaneAcervo.setVisible(false);
            throw new UtilControloExcessao(operacao, "Não há registo de Categorias", Alert.AlertType.WARNING);
        } else {
            for (Object todosRegistos : categoriaCon.listarTodos(operacao)) {
                ModCategoria categoriaMod = (ModCategoria) todosRegistos;
                todasCategorias.add(categoriaMod);
            }
            todasCategoriasPAraCombox = FXCollections.observableArrayList(todasCategorias);
            this.comboBoxCategoria.setItems(todasCategoriasPAraCombox);
        }
    }

    private void exibirDadosNosCampos(ModAcervo acervoMod) {
        if (tableViewAcervo.getSelectionModel().getSelectedCells().size() == 1) {
            this.carregarValorNasComboxs();
            this.texteFiedTitulo.setText(acervoMod.getTitulo());
            this.texteFiedSubTitulo.setText(acervoMod.getSub_titulo());
            this.texteFiedEdicao.setText(String.valueOf(acervoMod.getEdicao()));
            this.texteFiedVolume.setText(String.valueOf(acervoMod.getEdicao()));
            this.texteFiedNumPaginas.setText(String.valueOf(acervoMod.getNumero_paginas()));
            this.texteFieldAno.setText(String.valueOf(acervoMod.getAno_lancamento()));
            this.texteFiedCodigoBarra.setText(acervoMod.getCodigo_barra());
            this.texteFiedISBN.setText(acervoMod.getIsbn());
            this.textAreaSinopese.setText(acervoMod.getSinopse());
            this.texteFiedEndereco.setText(acervoMod.getEndereco_acervo());
            comboBoxTipo.getSelectionModel().select(acervoMod.getTipo_acervo());
            comboBoxFormato.getSelectionModel().select(acervoMod.getFormato());
            comboBoxIdioma.getSelectionModel().select(acervoMod.getIdioma());
            this.seleccionarDadosDasCombox(acervoMod);
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

    private void seleccionarDadosDasCombox(ModAcervo acervoMod) {
        for (int i = 0; i < comboBoxCategoria.getItems().size(); i++) {
            comboBoxCategoria.getSelectionModel().select(i);
            if (acervoMod.getCategoriaMod().getIdCategoria() == comboBoxCategoria.getSelectionModel().getSelectedItem().getIdCategoria()) {
                break;
            }
        }
        for (int i = 0; i < comboBoxEditora.getItems().size(); i++) {
            comboBoxEditora.getSelectionModel().select(i);
            if (acervoMod.getEditoraMod().getiEditora() == comboBoxEditora.getSelectionModel().getSelectedItem().getiEditora()) {
                break;
            }
        }
        for (Object todosResultados : escreitosAcervosCon.pesquisar(acervoMod, operacao)) {
            ModAcervo autorEncontrado = (ModAcervo) todosResultados;
            for (int i = 0; i < comboBoxAutor.getItems().size(); i++) {
                comboBoxAutor.getSelectionModel().select(i);
                if (autorEncontrado.getAutorMod().getIdAutor() == comboBoxAutor.getSelectionModel().getSelectedItem().getIdAutor()) {
                    this.acervoMod.setAutorModRemover(autorEncontrado.getAutorMod());
                    break;
                }
            }
        }
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
        tableColumTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tableColumSubTitulo.setCellValueFactory(new PropertyValueFactory<>("sub_titulo"));
        tableColumEdicao.setCellValueFactory(new PropertyValueFactory<>("edicao"));
        tableColumISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        tableColumnAno.setCellValueFactory(new PropertyValueFactory<>("ano_lancamento"));
        tableColumnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo_acervo"));
        tableColumnFormato.setCellValueFactory(new PropertyValueFactory<>("formato"));
        tableViewAcervo.setItems(this.todosRegistosParaCarregar(todosRegistosEncontrados));
    }

    private ObservableList<ModAcervo> todosRegistosParaCarregar(List<Object> todosRegistosEncontrados) {
        List<ModAcervo> listaDosRegistosWncontrados = new ArrayList<>();
        for (Object acervoRegistado : todosRegistosEncontrados) {
            ModAcervo acervoMod = (ModAcervo) acervoRegistado;
            listaDosRegistosWncontrados.add(acervoMod);
        }
        return FXCollections.observableArrayList(listaDosRegistosWncontrados);
    }

    @FXML
    public void validarDadosNumericos(KeyEvent evt) {
        String caracateresValidos = "1234567890";
        if (!caracateresValidos.contains(evt.getCharacter() + "")) {
            evt.consume();
        }
    }

    @FXML
    public void categoriasDaEstante() {
        ModEstante estanteMod = comboBoxEstante.getSelectionModel().getSelectedItem();
        comboBoxCategoria.getItems().clear();
        if (estanteMod != null) {
            if (estanteMod.getIdEstante() == 0) {
                this.carregarCategorias();
            } else {
                List<ModCategoria> todasCategorias = new ArrayList<>();
                ConCategoriaDaEstante categoriaDaEstante = new ConCategoriaDaEstante();
                ObservableList todasCategoriasPAraCombox = null;
                for (Object todosRegistos : categoriaDaEstante.pesquisar(estanteMod, operacao)) {
                    ModCategoria categoriaMod = (ModCategoria) todosRegistos;
                    todasCategorias.add(categoriaMod);
                }
                todasCategoriasPAraCombox = FXCollections.observableArrayList(todasCategorias);
                this.comboBoxCategoria.setItems(todasCategoriasPAraCombox);
            }
        }
    }

    @FXML
    private void introduzirEndereco() {
        if (this.comboBoxFormato.getSelectionModel().getSelectedItem().equalsIgnoreCase("Físico")) {
            labelEstante.setText("Estante *");
            labelEndereco.setText("Endereço acervo");
            texteFiedEndereco.setText(null);
            texteFiedEndereco.setDisable(true);
            botaoCarregar.setDisable(true);
        } else {
            if (this.comboBoxFormato.getSelectionModel().getSelectedItem().equalsIgnoreCase("Digital")) {
                labelEstante.setText("Estante");
                labelEndereco.setText("Endereço acervo *");
                texteFiedEndereco.setDisable(false);
                texteFiedEndereco.setEditable(false);
                botaoCarregar.setDisable(false);
            }
        }
    }

    private String validarLocalizacao(String formato) {
        if (formato.equalsIgnoreCase("Físico")) {
            if (this.comboBoxEstante.getSelectionModel().getSelectedItem() == null) {
                throw new UtilControloExcessao(operacao, "Seleccione a estante", Alert.AlertType.WARNING);
            } else {
                if (this.comboBoxEstante.getSelectionModel().getSelectedItem().getIdEstante() == 0) {
                    throw new UtilControloExcessao(operacao, "Seleccione a estante", Alert.AlertType.WARNING);
                } else {
                    return formato;
                }
            }
        } else {
            return formato;
        }
    }

}
