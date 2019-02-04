package sgbf.controlo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import sgbf.modelo.ModAcervo;
import sgbf.modelo.ModAcervosEscritos;
import sgbf.modelo.ModItemSolicitado;
import sgbf.util.UtilControloExcessao;

/**
 *
 * @author Look
 */
public class ConAcervo extends ConCRUD {

    @Override
    public boolean registar(Object objecto_registar, String operacao) {
        ModAcervo acervoMod = (ModAcervo) objecto_registar;
        try {
            super.query = "INSERT INTO tcc.acervos (titulo, subtittulo, tipo_acervo, formato, edicao, volume,"
                    + " numero_paginas, codigo_barra, isbn, idioma, ano_lancamento, sinopse, endereco_acervo,"
                    + " categoria_idcategoria, Editora_idEditora)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setString(1, acervoMod.getTitulo());
            super.preparedStatement.setString(2, acervoMod.getSub_titulo());
            super.preparedStatement.setString(3, acervoMod.getTipo_acervo());
            super.preparedStatement.setString(4, acervoMod.getFormato());
            super.preparedStatement.setByte(5, acervoMod.getEdicao());
            super.preparedStatement.setByte(6, acervoMod.getVolume());
            super.preparedStatement.setInt(7, acervoMod.getNumero_paginas());
            super.preparedStatement.setString(8, acervoMod.getCodigo_barra());
            super.preparedStatement.setString(9, acervoMod.getIsbn());
            super.preparedStatement.setString(10, acervoMod.getIdioma());
            super.preparedStatement.setInt(11, acervoMod.getAno_lancamento());
            super.preparedStatement.setString(12, acervoMod.getSinopse());
            super.preparedStatement.setString(13, acervoMod.getEndereco_acervo());
            super.preparedStatement.setInt(14, acervoMod.getCategoriaMod().getIdCategoria());
            if (acervoMod.getEditoraMod().getiEditora() == 0) {
                super.preparedStatement.setNull(15, Types.INTEGER);
            } else {
                super.preparedStatement.setInt(15, acervoMod.getEditoraMod().getiEditora());
            }
            return !super.preparedStatement.execute();
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + "\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        } finally {
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }

    @Override
    public boolean alterar(Object objecto_alterar, String operacao) {
        ModAcervo acervoMod = (ModAcervo) objecto_alterar;
        try {
            super.query = "UPDATE tcc.acervos set titulo=?, subtittulo=?, tipo_acervo=?, formato=?, edicao=?, volume=?,"
                    + " numero_paginas=?, codigo_barra=?, isbn=?, idioma=?, ano_lancamento=?, sinopse=?, endereco_acervo=?,"
                    + " categoria_idcategoria=?, Editora_idEditora=?,data_modificacao = default where idAcervos=?";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setString(1, acervoMod.getTitulo());
            super.preparedStatement.setString(2, acervoMod.getSub_titulo());
            super.preparedStatement.setString(3, acervoMod.getTipo_acervo());
            super.preparedStatement.setString(4, acervoMod.getFormato());
            super.preparedStatement.setByte(5, acervoMod.getEdicao());
            super.preparedStatement.setByte(6, acervoMod.getVolume());
            super.preparedStatement.setInt(7, acervoMod.getNumero_paginas());
            super.preparedStatement.setString(8, acervoMod.getCodigo_barra());
            super.preparedStatement.setString(9, acervoMod.getIsbn());
            super.preparedStatement.setString(10, acervoMod.getIdioma());
            super.preparedStatement.setInt(11, acervoMod.getAno_lancamento());
            super.preparedStatement.setString(12, acervoMod.getSinopse());
            super.preparedStatement.setString(13, acervoMod.getEndereco_acervo());
            super.preparedStatement.setInt(14, acervoMod.getCategoriaMod().getIdCategoria());
            if (acervoMod.getEditoraMod().getiEditora() == 0) {
                super.preparedStatement.setNull(15, Types.INTEGER);
            } else {
                super.preparedStatement.setInt(15, acervoMod.getEditoraMod().getiEditora());
            }
            super.preparedStatement.setInt(16, acervoMod.getIdAcervo());
            return !super.preparedStatement.execute();
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " Acervo !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        } finally {
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }

    @Override
    public boolean remover(Object objecto_remover, String operacao) {
        ModAcervosEscritos acervosEscritosMod = new ModAcervosEscritos();
        ConAcervosEscreitos registoDeAutor = new ConAcervosEscreitos();
        ModAcervo acervoMod = (ModAcervo) objecto_remover;
        ConEstoque estoqueCon = new ConEstoque();
        try {
            if (this.temDadosRelacionados(acervoMod, operacao)) {
                throw new UtilControloExcessao(operacao, "Esta operação não pode ser executada\n"
                        + "O acervo seleccionado possui registos de reservas ", Alert.AlertType.ERROR);
            } else {
                acervosEscritosMod.setAcervoMod(acervoMod, operacao);
                if (registoDeAutor.remover(acervosEscritosMod, operacao)) {
                    if (estoqueCon.remover(acervoMod, operacao)) {
                        super.query = "delete from tcc.acervos where idAcervos=?";
                        super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
                        super.preparedStatement.setInt(1, acervoMod.getIdAcervo());
                        return !super.preparedStatement.execute();
                    } else {
                        throw new UtilControloExcessao(operacao, "Erro ao remover estoque", Alert.AlertType.ERROR);
                    }
                } else {
                    throw new UtilControloExcessao(operacao, "Erro ao remover registro de autor ", Alert.AlertType.ERROR);
                }
            }
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " Acervo !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        } finally {
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }

    private boolean temDadosRelacionados(ModAcervo acervoMod, String operacao) throws SQLException {
        super.query = "select *from itensSolicitados where Acervos_idAcervos=?";
        super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(super.query);
        super.preparedStatement.setInt(1, acervoMod.getIdAcervo());
        super.setResultset = super.preparedStatement.executeQuery();
        return super.setResultset.next();
    }

    @Override
    public List<Object> listarTodos(String operacao) {
        List<Object> todosRegistos = new ArrayList<>();
        try {
            super.query = "select * from tcc.view_acervoComEstoque order by titulo, data_modificacao asc";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.setResultset = super.preparedStatement.executeQuery();
            while (super.setResultset.next()) {
                todosRegistos.add(this.pegarRegistos(super.setResultset, operacao));
            }
            return todosRegistos;
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " Acervo(s) !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public List<Object> pesquisar(Object objecto_pesquisar, String operacao) {
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        ModAcervo acervoeMod = (ModAcervo) objecto_pesquisar;
        try {
            super.query = "select * from tcc.view_acervoComEstoque where idAcervos=? or "
                    + "titulo like '%" + acervoeMod.getTitulo() + "%'";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setInt(1, acervoeMod.getIdAcervo());
            super.setResultset = super.preparedStatement.executeQuery();
            while (super.setResultset.next()) {
                todosRegistosEncontrados.add(this.pegarRegistos(super.setResultset, operacao));
            }
            return todosRegistosEncontrados;
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " Acervo !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public List<ModAcervo> localizarAcervo(ModAcervo acervoMod, String operacao) {
        List<ModAcervo> todosRegistosEncontrados = new ArrayList<>();
        try {
            super.query = "select * from tcc.view_localizarAcervo where idAcervos=? or "
                    + "titulo like '%" + acervoMod.getTitulo() + "%'";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setInt(1, acervoMod.getIdAcervo());
            super.setResultset = super.preparedStatement.executeQuery();
            while (super.setResultset.next()) {
                todosRegistosEncontrados.add(this.pegarRegistosLocalizados(super.setResultset, operacao));
            }
            return todosRegistosEncontrados;
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " Acervo !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private Object pegarRegistos(ResultSet setResultset, String operacao) throws SQLException {
        ModAcervo acervoMod = new ModAcervo();
        acervoMod.setIdAcervo(setResultset.getInt("idAcervos"), operacao);
        acervoMod.setTitulo(setResultset.getString("titulo"), operacao);
        acervoMod.setSub_titulo(setResultset.getString("subtittulo"), operacao);
        acervoMod.setTipo_acervo(setResultset.getString("tipo_acervo"), operacao);
        acervoMod.setFormato(setResultset.getString("formato"), operacao);
        acervoMod.setEdicao(setResultset.getByte("edicao"), operacao);
        acervoMod.setVolume(setResultset.getByte("volume"), operacao);
        acervoMod.setNumero_paginas(setResultset.getShort("numero_paginas"), operacao);
        acervoMod.setCodigo_barra(setResultset.getString("codigo_barra"), operacao);
        acervoMod.setIsbn(setResultset.getString("isbn"), operacao);
        acervoMod.setIdioma(setResultset.getString("idioma"), operacao);
        acervoMod.setAno_lancamento(setResultset.getInt("ano_lancamento"), operacao);
        acervoMod.setSinopse(setResultset.getString("sinopse"), operacao);
        acervoMod.setEndereco_acervo(setResultset.getString("endereco_acervo"), operacao);
        acervoMod.getCategoriaMod().setIdCategoria(setResultset.getInt("categoria_idcategoria"), operacao);
        if (setResultset.getString("Editora_idEditora") != null) {
            acervoMod.getEditoraMod().setiEditora(setResultset.getInt("Editora_idEditora"), operacao);
        }
        acervoMod.getEstoqueMod().setIdEstoque(setResultset.getInt("idAcervos"), operacao);
        acervoMod.getEstoqueMod().setQuantidade_total(setResultset.getShort("quantidade_total"), operacao);
        acervoMod.getEstoqueMod().setQuantidade_em_falta(setResultset.getShort("quantidade_em_falta"), operacao);
        acervoMod.getEstoqueMod().setQuantidade_acervos_emprestados(setResultset.getShort("quantidade_acervos_emprestados"), operacao);
        acervoMod.getEstoqueMod().setQuantidade_acervos_resercados(setResultset.getShort("quantidade_acervos_reservados"), operacao);
        acervoMod.getUtilControloDaData().setData_registo(setResultset.getTimestamp("data_registo"), operacao);
        acervoMod.getUtilControloDaData().setData_modificacao(setResultset.getTimestamp("data_modificacao"), operacao);
        return acervoMod;
    }

    private ModAcervo pegarRegistosLocalizados(ResultSet setResultset, String operacao) throws SQLException {
        ModAcervo acervoMod = new ModAcervo();
        acervoMod.setIdAcervo(setResultset.getInt("idAcervos"), operacao);
        acervoMod.setTitulo(setResultset.getString("titulo"), operacao);
        acervoMod.setSub_titulo(setResultset.getString("subtittulo"), operacao);
        acervoMod.setTipo_acervo(setResultset.getString("tipo_acervo"), operacao);
        acervoMod.setEdicao(setResultset.getByte("edicao"), operacao);
        acervoMod.setVolume(setResultset.getByte("volume"), operacao);
        acervoMod.setCodigo_barra(setResultset.getString("codigo_barra"), operacao);
        acervoMod.setIsbn(setResultset.getString("isbn"), operacao);
        acervoMod.setFormato(setResultset.getString("formato"), operacao);
        acervoMod.setAno_lancamento(setResultset.getInt("ano_lancamento"), operacao);
        acervoMod.setNumero_paginas(setResultset.getShort("numero_paginas"), operacao);
        acervoMod.setIdioma(setResultset.getString("idioma"), operacao);
        acervoMod.getUtilControloDaData().setData_registo(setResultset.getTimestamp("data_registo"), operacao);
        acervoMod.getUtilControloDaData().setData_modificacao(setResultset.getTimestamp("data_modificacao"), operacao);
        acervoMod.getCategoriaMod().setDesignacao(setResultset.getString("categoria"), operacao);
        if (setResultset.getString("idEstante") != null) {
            acervoMod.getCategoriaMod().getEstanteNova().setDesignacao(setResultset.getString("estante"), operacao);
            acervoMod.getCategoriaMod().getEstanteNova().getAreaMod().setSector(setResultset.getString("sector"), operacao);
        }
        return acervoMod;
    }

    public Integer ultimoCodigoRegistado(String operacao) {
        try {
            super.query = "select max(idAcervos) from acervos";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.setResultset = super.preparedStatement.executeQuery();
            if (super.setResultset.next()) {
                return super.setResultset.getInt("max(idAcervos)");
            } else {
                return 1;
            }
        } catch (SQLException ex) {
            throw new UtilControloExcessao(operacao, "Erro ao Listar Código do Acervos!\nErro: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
