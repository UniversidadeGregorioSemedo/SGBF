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
import sgbf.modelo.ModProveniencia;
import sgbf.util.UtilControloExcessao;

/**
 *
 * @author Look
 */
public class DaoProveniencia extends DaoCRUD {

    @Override
    public boolean registar(Object objecto_registar, String operacao) {
        ModProveniencia provenienciaMod = (ModProveniencia) objecto_registar;
        try {
            if (this.jaExiste(provenienciaMod, operacao)) {
                throw new UtilControloExcessao(operacao, "Erro ao verificar dados da Proveniência", Alert.AlertType.ERROR);
            } else {
                super.query = "INSERT INTO tcc.proveniencia (tipo) VALUES (?)";
                super.preparedStatement = super.caminhoDaBaseDados.conectarBaseDeDados().prepareStatement(query);
                super.preparedStatement.setString(1, provenienciaMod.getTipo());
                return !super.preparedStatement.execute();
            }
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " Proveniencia !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public boolean alterar(Object objecto_alterar, String operacao) {
        ModProveniencia provenienciaMod = (ModProveniencia) objecto_alterar;
        try {
            if (this.jaExiste(provenienciaMod, operacao)) {
                throw new UtilControloExcessao(operacao, "Erro ao verificar dados da Proveniência", Alert.AlertType.ERROR);
            } else {
                super.query = "update tcc.proveniencia set tipo=?, data_modificao = default where idProveniencia=?";
                super.preparedStatement = super.caminhoDaBaseDados.conectarBaseDeDados().prepareStatement(query);
                super.preparedStatement.setString(1, provenienciaMod.getTipo());
                super.preparedStatement.setInt(2, provenienciaMod.getIdProveniencia());
                return !super.preparedStatement.execute();
            }
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " Proveniencia !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public boolean remover(Object objecto_remover, String operacao) {
        ModProveniencia provenienciaMod = (ModProveniencia) objecto_remover;
        try {
            if (this.temDadosRelacionados(provenienciaMod, operacao)) {
                throw new UtilControloExcessao(operacao, "Esta operação não pode ser executada !\nErro: A proveniencia seleccionada tem dados registados !", Alert.AlertType.ERROR);
            } else {
                super.query = "delete from tcc.proveniencia where idProveniencia=?";
                super.preparedStatement = super.caminhoDaBaseDados.conectarBaseDeDados().prepareStatement(query);
                super.preparedStatement.setInt(1, provenienciaMod.getIdProveniencia());
                return !super.preparedStatement.execute();
            }
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " Proveniência !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public List<Object> listarTodos(String operacao) {
        List<Object> todosRegistos = new ArrayList<>();
        try {
            super.query = "select * from tcc.proveniencia";
            super.preparedStatement = super.caminhoDaBaseDados.conectarBaseDeDados().prepareStatement(query);
            super.setResultset = super.preparedStatement.executeQuery();
            while (super.setResultset.next()) {
                todosRegistos.add(this.pegarRegistos(super.setResultset, operacao));
            }
            return todosRegistos;
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " Proveniência !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public List<Object> pesquisar(Object objecto_pesquisar, String operacao) {
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        ModProveniencia provenienciaMod = (ModProveniencia) objecto_pesquisar;
        try {
            super.query = "select * from tcc.proveniencia where idProveniencia=? or "
                    + "tipo like '%" + provenienciaMod.getTipo() + "%'";
            super.preparedStatement = super.caminhoDaBaseDados.conectarBaseDeDados().prepareStatement(query);
            super.preparedStatement.setInt(1, provenienciaMod.getIdProveniencia());
            super.setResultset = super.preparedStatement.executeQuery();
            while (super.setResultset.next()) {
                todosRegistosEncontrados.add(this.pegarRegistos(super.setResultset, operacao));
            }
            return todosRegistosEncontrados;
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " Proveniência(s) !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private Object pegarRegistos(ResultSet setResultset, String operacao) throws SQLException {
        ModProveniencia provenienciaMod = new ModProveniencia();
        provenienciaMod.setIdProveniencia(setResultset.getInt("idProveniencia"), operacao);
        provenienciaMod.setTipo(setResultset.getString("tipo"), operacao);
        provenienciaMod.getUtilControloDaData().setData_registo(setResultset.getTimestamp("data_registo"), operacao);
        provenienciaMod.getUtilControloDaData().setData_modificacao(setResultset.getTimestamp("data_modificao"), operacao);
        return provenienciaMod;
    }

    private boolean jaExiste(ModProveniencia provenienciaIntroduzido, String operacao) {
        for (Object todosRegistos : this.listarTodos(operacao)) {
            ModProveniencia provenienciaRegistada = (ModProveniencia) todosRegistos;
            provenienciaRegistada.equals(provenienciaIntroduzido, operacao);
        }
        return false;
    }

    private boolean temDadosRelacionados(ModProveniencia provenienciaMod, String operacao) throws SQLException {
        super.query = "select *from itensProvenientes where Proveniencia_idProveniencia=?";
        super.preparedStatement = super.caminhoDaBaseDados.conectarBaseDeDados().prepareStatement(super.query);
        super.preparedStatement.setInt(1, provenienciaMod.getIdProveniencia());
        super.setResultset = super.preparedStatement.executeQuery();
        return super.setResultset.next();
    }

}
