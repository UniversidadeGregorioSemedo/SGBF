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
import sgbf.modelo.ModArea;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilIconesDaJOPtionPane;

/**
 *
 * @author Look
 */
public class ConArea extends ConCRUD {

    @Override
    public boolean registar(Object objecto_registar, String operacao) {
        ModArea areaMod = (ModArea) objecto_registar;
        try {
            if (this.jaExiste(areaMod, operacao)) {
                throw new UtilControloExcessao(operacao, "Erro ao verificar dados da Área !", Alert.AlertType.ERROR);
            } else {
                super.query = "INSERT INTO tcc.area (sector) VALUES (?)";
                super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
                super.preparedStatement.setString(1, areaMod.getSector());
                return !super.preparedStatement.execute();
            }
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        } finally {
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }

    @Override
    public boolean alterar(Object objecto_alterar, String operacao) {
        ModArea areaMod = (ModArea) objecto_alterar;
        try {
            if (this.jaExiste(areaMod, operacao)) {
                throw new UtilControloExcessao(operacao, "Erro ao verificar dados da Área !", Alert.AlertType.ERROR);
            } else {
                super.query = "UPDATE tcc.area set sector=?, data_modificacao = default where idArea=?";
                super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
                super.preparedStatement.setString(1, areaMod.getSector());
                super.preparedStatement.setInt(2, areaMod.getIdArea());
                return !super.preparedStatement.execute();
            }
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " Área !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        } finally {
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }

    @Override
    public boolean remover(Object objecto_remover, String operacao) {
        ModArea areaMod = (ModArea) objecto_remover;
        try {
            if (this.temDadosRelacionados(areaMod, operacao)) {
                throw new UtilControloExcessao(operacao, "Esta operação não pode ser executada\n A Área seleccionada possui Registos !", Alert.AlertType.WARNING);
            } else {
                super.query = "delete from tcc.area where idArea=?";
                super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
                super.preparedStatement.setInt(1, areaMod.getIdArea());
                return !super.preparedStatement.execute();
            }
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " Área !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public List<Object> listarTodos(String operacao) {
        List<Object> todosRegistos = new ArrayList<>();
        try {
            super.query = "select * from tcc.area order by sector asc";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.setResultset = super.preparedStatement.executeQuery();
            while (super.setResultset.next()) {
                todosRegistos.add(this.pegarRegistos(super.setResultset, operacao));
            }
            return todosRegistos;
        } catch (SQLException erro) {
            throw new UtilControloExcessao( operacao, "Erro ao " + operacao + " Area(s) !\nErro: " + erro.getMessage(),Alert.AlertType.ERROR);
        }
    }

    @Override
    public List<Object> pesquisar(Object objecto_pesquisar, String operacao) {
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        ModArea areaMod = (ModArea) objecto_pesquisar;
        try {
            super.query = "select * from tcc.area where idArea=? or "
                    + "sector like '%" + areaMod.getSector() + "%'";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setInt(1, areaMod.getIdArea());
            super.setResultset = super.preparedStatement.executeQuery();
            while (super.setResultset.next()) {
                todosRegistosEncontrados.add(this.pegarRegistos(super.setResultset, operacao));
            }
            return todosRegistosEncontrados;
        } catch (SQLException erro) {
            throw new UtilControloExcessao( operacao,"Erro ao " + operacao + " Editora(s) !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private boolean jaExiste(ModArea areaMod, String operacao) {
        for (Object todosRegistos : this.listarTodos(operacao)) {
            ModArea areaRegistada = (ModArea) todosRegistos;
            areaRegistada.equals(areaMod, operacao);
        }
        return false;
    }

    private boolean temDadosRelacionados(ModArea areaMod, String operacao) throws SQLException {
        super.query = "select * from estante where Area_idArea=?";
        super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(super.query);
        super.preparedStatement.setInt(1, areaMod.getIdArea());
        super.setResultset = super.preparedStatement.executeQuery();
        return super.setResultset.next();
    }

    private Object pegarRegistos(ResultSet setResultset, String operacao) throws SQLException {
        ModArea areaMod = new ModArea();
        areaMod.setIdArea(setResultset.getInt("idArea"), operacao);
        areaMod.setSector(setResultset.getString("sector"), operacao);
        areaMod.getUtilControloDaData().setData_registo(setResultset.getTimestamp("data_registo"), operacao);
        areaMod.getUtilControloDaData().setData_modificacao(setResultset.getTimestamp("data_modificacao"), operacao);
        return areaMod;
    }
}
