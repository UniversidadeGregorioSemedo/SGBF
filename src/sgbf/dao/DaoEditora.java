/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import sgbf.modelo.ModEditora;
import sgbf.util.UtilControloExcessao;

/**
 *
 * @author Look
 */
public class DaoEditora extends DaoCRUD {

    @Override
    public boolean registar(Object objecto_registar, String operacao) {
        ModEditora editoraMod = (ModEditora) objecto_registar;
        try {
            if (this.jaExiste(editoraMod, operacao)) {
                throw new UtilControloExcessao(operacao, "Erro ao verificar dados da Editora !", Alert.AlertType.INFORMATION);
            } else {
                super.query = "INSERT INTO tcc.Editora (nome, contacto, email, fax, endereco)"
                        + " VALUES (?, ?, ?, ?, ?)";
                super.preparedStatement = super.caminhoDaBaseDados.conectarBaseDeDados().prepareStatement(query);
                super.preparedStatement.setString(1, editoraMod.getNome());
                super.preparedStatement.setString(2, editoraMod.getContacto());
                super.preparedStatement.setString(3, editoraMod.getEmail());
                super.preparedStatement.setString(4, editoraMod.getFax());
                super.preparedStatement.setString(5, editoraMod.getEndereco());
                return !super.preparedStatement.execute();
            }
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " Editora !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        } finally {
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset);
        }
    }

    @Override
    public boolean alterar(Object objecto_alterar, String operacao) {
        ModEditora editoraMod = (ModEditora) objecto_alterar;
        try {
            if (this.jaExiste(editoraMod, operacao)) {
                throw new UtilControloExcessao(operacao, "Erro ao verificar dados da Editora !", Alert.AlertType.ERROR);
            } else {
                super.query = "UPDATE tcc.Editora set nome=?, contacto=?, email=?, fax=?, endereco=?,"
                        + "  data_modificacao = default where idEditora=?";
                super.preparedStatement = super.caminhoDaBaseDados.conectarBaseDeDados().prepareStatement(query);
                super.preparedStatement.setString(1, editoraMod.getNome());
                super.preparedStatement.setString(2, editoraMod.getContacto());
                super.preparedStatement.setString(3, editoraMod.getEmail());
                super.preparedStatement.setString(4, editoraMod.getFax());
                super.preparedStatement.setString(5, editoraMod.getEndereco());
                super.preparedStatement.setInt(6, editoraMod.getiEditora());
                return !super.preparedStatement.execute();
            }
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " Editora !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        } finally {
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset);
        }
    }

    @Override
    public boolean remover(Object objecto_remover, String operacao) {
        ModEditora editoraMod = (ModEditora) objecto_remover;
        try {
            if (this.temDadosRelacionados(editoraMod, operacao)) {
                throw new UtilControloExcessao(operacao, "Erro ao verificar dados da Editora", Alert.AlertType.WARNING);
            } else {
                super.query = "delete from tcc.Editora where idEditora=?";
                super.preparedStatement = super.caminhoDaBaseDados.conectarBaseDeDados().prepareStatement(query);
                super.preparedStatement.setInt(1, editoraMod.getiEditora());
                return !super.preparedStatement.execute();
            }
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " Editora !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        } finally {
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset);
        }
    }

    @Override
    public List<Object> listarTodos(String operacao) {
        List<Object> todosRegistos = new ArrayList<>();
        try {
            super.query = "select * from tcc.editora order by nome asc";
            super.preparedStatement = super.caminhoDaBaseDados.conectarBaseDeDados().prepareStatement(query);
            super.setResultset = super.preparedStatement.executeQuery();
            while (super.setResultset.next()) {
                todosRegistos.add(this.pegarRegistos(super.setResultset, operacao));
            }
            return todosRegistos;
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " Editora !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public List<Object> pesquisar(Object objecto_pesquisar, String operacao) {
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        ModEditora editoraMod = (ModEditora) objecto_pesquisar;
        try {
            super.query = "select * from tcc.Editora where idEditora=? or "
                    + "nome like '%" + editoraMod.getNome() + "%'";
            super.preparedStatement = super.caminhoDaBaseDados.conectarBaseDeDados().prepareStatement(query);
            super.preparedStatement.setInt(1, editoraMod.getiEditora());
            super.setResultset = super.preparedStatement.executeQuery();
            while (super.setResultset.next()) {
                todosRegistosEncontrados.add(this.pegarRegistos(super.setResultset, operacao));
            }
            return todosRegistosEncontrados;
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " Editora(s) !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private Object pegarRegistos(ResultSet setResultset, String operacao) throws SQLException {
        ModEditora editoraMod = new ModEditora();
        editoraMod.setiEditora(setResultset.getInt("idEditora"), operacao);
        editoraMod.setNome(setResultset.getString("nome"), operacao);
        editoraMod.setContacto(setResultset.getString("contacto"), operacao);
        editoraMod.setEmail(setResultset.getString("email"), operacao);
        editoraMod.setFax(setResultset.getString("fax"), operacao);
        editoraMod.setEndereco(setResultset.getString("endereco"), operacao);
        editoraMod.getUtilControloDaData().setData_registo(setResultset.getTimestamp("data_registo"), operacao);
        editoraMod.getUtilControloDaData().setData_modificacao(setResultset.getTimestamp("data_modificacao"), operacao);
        return editoraMod;
    }

    private boolean jaExiste(ModEditora editoraMod, String operacao) {
        for (Object todosRegistos : this.listarTodos(operacao)) {
            ModEditora editoraRegistada = (ModEditora) todosRegistos;
            editoraRegistada.equals(editoraMod, operacao);
        }
        return false;
    }

    private boolean temDadosRelacionados(ModEditora editoraMod, String operacao) throws SQLException {
        super.query = "select * from tcc.acervos where Editora_idEditora =?";
        super.preparedStatement = super.caminhoDaBaseDados.conectarBaseDeDados().prepareStatement(super.query);
        super.preparedStatement.setInt(1, editoraMod.getiEditora());
        super.setResultset = super.preparedStatement.executeQuery();
        return super.setResultset.next();
    }

}
