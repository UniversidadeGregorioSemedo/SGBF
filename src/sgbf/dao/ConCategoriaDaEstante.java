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
import sgbf.modelo.ModCategoria;
import sgbf.modelo.ModCategoriaDaEstante;
import sgbf.modelo.ModEstante;
import sgbf.util.UtilControloExcessao;

/**
 *
 * @author Look
 */
public class ConCategoriaDaEstante extends ConCRUD {

    @Override
    public boolean registar(Object objecto_registar, String operacao) {
        ModCategoriaDaEstante categoriaDaEstanteMod = (ModCategoriaDaEstante) objecto_registar;
        try {
            if (this.registarCategoriaNaEstante(categoriaDaEstanteMod)) {
                super.query = "INSERT INTO tcc.categoriasdaestante (categoria_idcategoria, Estante_idEstante) VALUES (?, ?)";
                super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
                super.preparedStatement.setInt(1, categoriaDaEstanteMod.getCategoriaMod().getIdCategoria());
                super.preparedStatement.setInt(2, categoriaDaEstanteMod.getEstanteMod().getIdEstante());
                return !super.preparedStatement.execute();
            } else {
                return true;
            }
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " Estante !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public boolean alterar(Object objecto_alterar, String operacao) {
        ModCategoria categoriaMod = (ModCategoria) objecto_alterar;
        ModCategoriaDaEstante categoriaDaEstanteMod = new ModCategoriaDaEstante();
        try {
            if (categoriaMod.getEstanteNova().getIdEstante() == 0) {
                return this.remover(categoriaMod, operacao);
            } else {
                if (this.pesquisar(categoriaMod, operacao).isEmpty()) {
                    categoriaDaEstanteMod.setCategoriaMod(categoriaMod, operacao);
                    categoriaDaEstanteMod.setEstanteMod(categoriaMod.getEstanteNova(), operacao);
                    return this.registar(categoriaDaEstanteMod, operacao);
                } else {
                    super.query = "update tcc.categoriasdaestante set Estante_idEstante=? where categoria_idcategoria=? and Estante_idEstante=?";
                    super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
                    super.preparedStatement.setInt(1, categoriaMod.getEstanteNova().getIdEstante());
                    super.preparedStatement.setInt(2, categoriaMod.getIdCategoria());
                    super.preparedStatement.setInt(3, categoriaMod.getEstanteActual().getIdEstante());
                    return !super.preparedStatement.execute();
                }
            }
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + "\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        } finally {
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }

    @Override
    public boolean remover(Object objecto_remover, String operacao) {
        ModCategoria categoriaDaEstanteMod = (ModCategoria) objecto_remover;
        try {
            super.query = "delete from tcc.categoriasdaestante where categoria_idcategoria=? and Estante_idEstante=?";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setInt(1, categoriaDaEstanteMod.getIdCategoria());
            if (categoriaDaEstanteMod.getEstanteActual().getIdEstante() == 0) {
                super.preparedStatement.setInt(2, categoriaDaEstanteMod.getEstanteNova().getIdEstante());
            } else {
                super.preparedStatement.setInt(2, categoriaDaEstanteMod.getEstanteActual().getIdEstante());
            }
            return !super.preparedStatement.execute();
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " Estante !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private boolean registarCategoriaNaEstante(ModCategoriaDaEstante categoriaDaEstanteMod) {
        return (categoriaDaEstanteMod.getCategoriaMod().getIdCategoria() != 0)
                && (categoriaDaEstanteMod.getEstanteMod().getIdEstante() != 0);
    }

    @Override
    public List<Object> listarTodos(String operacao) {
        List<Object> todosRegistos = new ArrayList<>();
        try {
            super.query = "select * from view_CategoriaDaEstante";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.setResultset = super.preparedStatement.executeQuery();
            while (super.setResultset.next()) {
                todosRegistos.add(this.pegarRegistos(super.setResultset, operacao));
            }
            return todosRegistos;
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " Estante !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public List<Object> pesquisar(Object objecto_pesquisar, String operacao) {
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        ModCategoria categoriaMod = (ModCategoria) objecto_pesquisar;
        try {
            super.query = "select * from tcc.view_CategoriaDaEstante where idcategoria=? and idEstante=?";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setInt(1, categoriaMod.getIdCategoria());
            super.preparedStatement.setInt(2, categoriaMod.getEstanteActual().getIdEstante());
            super.setResultset = super.preparedStatement.executeQuery();
            while (super.setResultset.next()) {
                todosRegistosEncontrados.add(this.pegarRegistos(super.setResultset, operacao));
            }
            return todosRegistosEncontrados;
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " Categoria(s) !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public List<Object> pesquisar(ModEstante estanteMod, String operacao) {
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        try {
            super.query = "select * from tcc.view_CategoriaDaEstante where idEstante=?";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setInt(1, estanteMod.getIdEstante());
            super.setResultset = super.preparedStatement.executeQuery();
            while (super.setResultset.next()) {
                todosRegistosEncontrados.add(this.pegarRegistos(super.setResultset, operacao));
            }
            return todosRegistosEncontrados;
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + "(s)\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private Object pegarRegistos(ResultSet setResultset, String operacao) throws SQLException {
        ModCategoria categoriaMod = new ModCategoria();
        categoriaMod.setIdCategoria(setResultset.getInt("idcategoria"), operacao);
        categoriaMod.getEstanteNova().setIdEstante(setResultset.getInt("idEstante"), operacao);
        categoriaMod.setDesignacao(setResultset.getString("categoria"), operacao);
        categoriaMod.getEstanteNova().setDesignacao(setResultset.getString("estante"), operacao);
        categoriaMod.getUtilControloDaData().setData_registo(setResultset.getTimestamp("data_registo"), operacao);
        categoriaMod.getUtilControloDaData().setData_modificacao(setResultset.getTimestamp("data_modificacao"), operacao);
        return categoriaMod;
    }

}
