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
import sgbf.modelo.ModItemProveniente;
import sgbf.util.UtilControloExcessao;

/**
 *
 * @author Look
 */
public class ContItemProveniente extends ConCRUD {

    @Override
    public boolean registar(Object objecto_registar, String operacao) {
        ModItemProveniente itemProvenienteMod = (ModItemProveniente) objecto_registar;
        try {
            if (this.temRegistoDital(itemProvenienteMod, operacao)) {
                throw new UtilControloExcessao(operacao, "Erro ao registar entrada de acervo\n"
                        + "Erro: Acervos digitais não podem ter mais de duas proveniências", Alert.AlertType.WARNING);
            } else {
                super.query = "call pr_registarItensEntradas(?, ?, ?, ?, ?)";
                super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
                super.preparedStatement.setInt(1, itemProvenienteMod.getAcervoMod().getEstoqueMod().getIdEstoque());
                super.preparedStatement.setInt(2, itemProvenienteMod.getProvenienciaMod().getIdProveniencia());
                super.preparedStatement.setInt(3, itemProvenienteMod.getQuantidade_entrada());
                super.preparedStatement.setDouble(4, itemProvenienteMod.getCusto_unitario());
                super.preparedStatement.setString(5, itemProvenienteMod.getAcervoMod().getFormato());
                return !super.preparedStatement.execute();
            }
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " Entradas \nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        } finally {
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }

   

    @Override
    public boolean alterar(Object objecto_alterar, String operacao) {
        ModItemProveniente itemProvenienteMod = (ModItemProveniente) objecto_alterar;
        try {
            super.query = "update tcc.itensprovenientes set quantidade_entrada=?, subtotal=? where Estoque_idEstoque=? and Proveniencia_idProveniencia=?";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setInt(1, itemProvenienteMod.getQuantidade_entrada());
            super.preparedStatement.setDouble(2, itemProvenienteMod.getSubTotal());
            super.preparedStatement.setInt(3, itemProvenienteMod.getAcervoMod().getEstoqueMod().getIdEstoque());
            super.preparedStatement.setInt(4, itemProvenienteMod.getProvenienciaMod().getIdProveniencia());
            return !super.preparedStatement.execute();
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " Estoque !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        } finally {
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }

    @Override
    public boolean remover(Object objecto_remover, String operacao) {
        ModItemProveniente itemProvenienteMod = (ModItemProveniente) objecto_remover;
        try {
            super.query = "delete from tcc.itensprovenientes where Estoque_idEstoque=? or Proveniencia_idProveniencia=?";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setInt(1, itemProvenienteMod.getAcervoMod().getEstoqueMod().getIdEstoque());
            super.preparedStatement.setInt(2, itemProvenienteMod.getProvenienciaMod().getIdProveniencia());
            return !super.preparedStatement.execute();
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + "!\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public List<Object> listarTodos(String operacao) {
        List<Object> todosRegistos = new ArrayList<>();
        throw new UtilControloExcessao(operacao, "Operação não disponível", Alert.AlertType.ERROR);
    }

    @Override
    public List<Object> pesquisar(Object objecto_pesquisar, String operacao) {
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        ModItemProveniente itemProvenienteMod = (ModItemProveniente) objecto_pesquisar;
        try {
            super.query = "select * from tcc.view_itensProvenientes where idAcervos=? or "
                    + "titulo like '%" + itemProvenienteMod.getAcervoMod().getTitulo() + "%'";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setInt(1, itemProvenienteMod.getAcervoMod().getIdAcervo());
            super.setResultset = super.preparedStatement.executeQuery();
            while (super.setResultset.next()) {
                todosRegistosEncontrados.add(this.pegarRegistos(super.setResultset, operacao));
            }
            return todosRegistosEncontrados;
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " Entrada de Acervos !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private Object pegarRegistos(ResultSet setResultset, String operacao) throws SQLException {
        ModItemProveniente itemProvenienteMod = new ModItemProveniente();
        itemProvenienteMod.getAcervoMod().setIdAcervo(setResultset.getInt("idAcervos"), operacao);
        itemProvenienteMod.getAcervoMod().setTitulo(setResultset.getString("titulo"), operacao);
        itemProvenienteMod.getAcervoMod().setFormato(setResultset.getString("formato"), operacao);
        itemProvenienteMod.setQuantidade_entrada(setResultset.getShort("quantidade_entrada"), operacao);
        itemProvenienteMod.getProvenienciaMod().setIdProveniencia(setResultset.getInt("idProveniencia"), operacao);
        itemProvenienteMod.getProvenienciaMod().setTipo(setResultset.getString("tipo"), operacao);
        itemProvenienteMod.setCusto_unitario(setResultset.getDouble("custo_unitario"), operacao);
        itemProvenienteMod.setSubTotal(setResultset.getDouble("subtotal"), operacao);
        itemProvenienteMod.getAcervoMod().getEstoqueMod().setIdEstoque(setResultset.getInt("estoque"), operacao);
        itemProvenienteMod.getAcervoMod().getEstoqueMod().setQuantidade_total(setResultset.getShort("quantidade_total"), operacao);
        itemProvenienteMod.getAcervoMod().getEstoqueMod().setQuantidade_em_falta(setResultset.getShort("quantidade_em_falta"), operacao);
        itemProvenienteMod.getUtilControloDaData().setData_registo(setResultset.getTimestamp("data_registo"), operacao);
        itemProvenienteMod.getUtilControloDaData().setData_modificacao(setResultset.getTimestamp("data_modificao"), operacao);
        return itemProvenienteMod;
    }
    
    private boolean temRegistoDital(ModItemProveniente itemProvenienteMod, String operacao) {
        if (itemProvenienteMod.getAcervoMod().getFormato().equalsIgnoreCase("Digital")) {
            return !this.pesquisar(itemProvenienteMod, operacao).isEmpty();
        } else {
            return itemProvenienteMod.getAcervoMod().getFormato().equalsIgnoreCase("Digital");
        }
    }

}
