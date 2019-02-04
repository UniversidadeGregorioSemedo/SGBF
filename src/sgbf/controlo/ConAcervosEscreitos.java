/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.controlo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import sgbf.modelo.ModAcervo;
import sgbf.modelo.ModAcervosEscritos;
import sgbf.util.UtilControloExcessao;

/**
 *
 * @author Look
 */
public class ConAcervosEscreitos extends ConCRUD {

    @Override
    public boolean registar(Object objecto_registar, String operacao) {
        ModAcervo acervoMod = (ModAcervo) objecto_registar;
        ModAcervosEscritos acervosEscritosMod = new ModAcervosEscritos();
        try {
            if ((acervoMod.getAutorMod().getIdAutor() == 0) && (acervoMod.getAutorModRemover().getIdAutor() != 0)) {
                acervosEscritosMod.setAutorMod(acervoMod.getAutorModRemover(), operacao);
                return this.remover(acervosEscritosMod, operacao);
            } else {
                if (acervoMod.getAutorMod().getIdAutor() == 0) {
                    return acervoMod.getAutorMod().getIdAutor() == 0;
                } else {
                    super.query = "INSERT INTO tcc.acervosescritos(Acervos_idAcervos, Autor_idAutor) VALUES (?, ?)";
                    super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
                    super.preparedStatement.setInt(1, acervoMod.getIdAcervo());
                    super.preparedStatement.setInt(2, acervoMod.getAutorMod().getIdAutor());
                    return !super.preparedStatement.execute();
                }
            }
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " Acervos Escritos !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        } finally {
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }

    @Override
    public boolean alterar(Object objecto_alterar, String operacao) {
        ModAcervo acervoMod = (ModAcervo) objecto_alterar;
        try {
            if (this.pesquisar(acervoMod, operacao).isEmpty()) {
                return this.registar(objecto_alterar, operacao);
            } else {
                if (acervoMod.getAutorMod().getIdAutor() == 0) {
                    return this.registar(acervoMod, operacao);
                } else {
                    super.query = "update tcc.acervosescritos set Autor_idAutor=? where Acervos_idAcervos=?";
                    super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
                    super.preparedStatement.setInt(1, acervoMod.getAutorMod().getIdAutor());
                    super.preparedStatement.setInt(2, acervoMod.getIdAcervo());
                    return !super.preparedStatement.execute();
                }
            }
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " Estante !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        } finally {
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }

    @Override
    public boolean remover(Object objecto_remover, String operacao) {
        ModAcervosEscritos acervosEscritosMod = (ModAcervosEscritos) objecto_remover;
        try {
            super.query = "delete from tcc.acervosescritos where Acervos_idAcervos=? or Autor_idAutor=?";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setInt(1, acervosEscritosMod.getAcervoMod().getIdAcervo());
            super.preparedStatement.setInt(2, acervosEscritosMod.getAutorMod().getIdAutor());
            return !super.preparedStatement.execute();
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao Registos do Autor !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public List<Object> listarTodos(String operacao) {
        List<Object> todosRegistos = new ArrayList<>();
        try {
            super.query = "select * from tcc.Estante ";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.setResultset = super.preparedStatement.executeQuery();
            while (super.setResultset.next()) {
                todosRegistos.add(this.pegarRegistos(super.setResultset, operacao));
            }
            return todosRegistos;
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " Estante(s) !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        } finally {
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }

    @Override
    public List<Object> pesquisar(Object objecto_pesquisar, String operacao) {
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        ModAcervo acervoMod = (ModAcervo) objecto_pesquisar;
        try {
            super.query = "select * from tcc.acervosescritos where Acervos_idAcervos=?";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setInt(1, acervoMod.getIdAcervo());
            super.setResultset = super.preparedStatement.executeQuery();
            while (super.setResultset.next()) {
                todosRegistosEncontrados.add(this.pegarRegistos(super.setResultset, operacao));
            }
            return todosRegistosEncontrados;
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " Acrvos Escreitor!\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private Object pegarRegistos(ResultSet setResultset, String operacao) throws SQLException {
        ModAcervo acervoMod = new ModAcervo();
        acervoMod.setIdAcervo(setResultset.getInt("Acervos_idAcervos"), operacao);
        acervoMod.getAutorMod().setIdAutor(setResultset.getInt("Autor_idAutor"), operacao);
        return acervoMod;
    }

}
