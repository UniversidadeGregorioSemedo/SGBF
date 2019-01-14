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
import sgbf.modelo.ModEstante;
import sgbf.modelo.ModItemProveniente;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilIconesDaJOPtionPane;

/**
 *
 * @author Look
 */
public class ContItemProveniente extends ConCRUD {
    
    @Override
    public boolean registar(Object objecto_registar, String operacao) {
        ModItemProveniente itemProvenienteMod = (ModItemProveniente)objecto_registar;
        try{
            super.query = "INSERT INTO tcc.itensprovenientes (Estoque_idEstoque, Proveniencia_idProveniencia, quantidade_entrada, subtotal)"
                        + " VALUES (?, ?, ?, ?);";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setInt(1, itemProvenienteMod.getAcervoMod().getEstoqueMod().getIdEstoque());
            super.preparedStatement.setInt(2, itemProvenienteMod.getProvenienciaMod().getIdProveniencia());
            super.preparedStatement.setInt(3, itemProvenienteMod.getQuantidade_entrada());
            super.preparedStatement.setDouble(4, itemProvenienteMod.getSubTotal());
            return !super.preparedStatement.execute();
        }catch(SQLException erro){
            throw new UtilControloExcessao("Erro ao "+operacao+" Estoque !\nErro: "+erro.getMessage(), operacao,UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }finally{
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }

    @Override
    public boolean alterar(Object objecto_alterar, String operacao) {
        ModItemProveniente itemProvenienteMod = (ModItemProveniente)objecto_alterar;
        try{
            super.query = "update tcc.itensprovenientes set quantidade_entrada=?, subtotal=? where Estoque_idEstoque=? and Proveniencia_idProveniencia=?";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setInt(1, itemProvenienteMod.getQuantidade_entrada());
            super.preparedStatement.setDouble(2, itemProvenienteMod.getSubTotal());
            super.preparedStatement.setInt(3, itemProvenienteMod.getAcervoMod().getEstoqueMod().getIdEstoque());
            super.preparedStatement.setInt(4, itemProvenienteMod.getProvenienciaMod().getIdProveniencia());
            return !super.preparedStatement.execute();
        }catch(SQLException erro){
            throw new UtilControloExcessao("Erro ao "+operacao+" Estoque !\nErro: "+erro.getMessage(), operacao,UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }finally{
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }

    @Override
    public boolean remover(Object objecto_remover, String operacao) {
        ModItemProveniente itemProvenienteMod = (ModItemProveniente)objecto_remover;
        try{
            super.query = "delete from tcc.itensprovenientes where Estoque_idEstoque=? or Proveniencia_idProveniencia=?";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setInt(1,itemProvenienteMod.getAcervoMod().getEstoqueMod().getIdEstoque());
            super.preparedStatement.setInt(2,itemProvenienteMod.getProvenienciaMod().getIdProveniencia());
            return !super.preparedStatement.execute();
        }catch(SQLException erro){
           throw new UtilControloExcessao( operacao, "Erro ao "+operacao+"!\nErro: "+erro.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public List<Object> listarTodos(String operacao) {
        List<Object> todosRegistos = new ArrayList<>();
        /*try{
            super.query = "select * from tcc.Estante designacao by nome, data_modificacao asc";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.setResultset = super.preparedStatement.executeQuery();
            while(super.setResultset.next()){
                todosRegistos.add(this.pegarRegistos(super.setResultset,operacao));
            }
            return todosRegistos;
        }catch(SQLException erro){
            throw new UtilControloExcessao("Erro ao "+operacao+" Estante(s) !\nErro: "+erro.getMessage(), operacao, UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }finally{
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }*/
        return todosRegistos;
    }

    @Override
    public List<Object> pesquisar(Object objecto_pesquisar, String operacao) {
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        ModItemProveniente itemProvenienteMod = (ModItemProveniente)objecto_pesquisar;
        try{
            super.query = "select * from tcc.view_itensProvenientes where idAcervos=? or "
                        + "titulo like '%"+itemProvenienteMod.getAcervoMod().getTitulo()+"%'";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setInt(1, itemProvenienteMod.getAcervoMod().getIdAcervo());
            super.setResultset  = super.preparedStatement.executeQuery();
            while(super.setResultset.next()){
                todosRegistosEncontrados.add(this.pegarRegistos(super.setResultset, operacao));
            }
            return todosRegistosEncontrados;
        }catch(SQLException erro){
            throw new UtilControloExcessao( operacao,"Erro ao "+operacao+" Entrada de Acervos !\nErro: "+erro.getMessage(),Alert.AlertType.ERROR);
        }
    }
    
    private Object pegarRegistos(ResultSet setResultset,String operacao) throws SQLException{
        ModItemProveniente itemProvenienteMod = new ModItemProveniente();
        itemProvenienteMod.getAcervoMod().setIdAcervo(setResultset.getInt("idAcervos"), operacao);
        itemProvenienteMod.getAcervoMod().setTitulo(setResultset.getString("titulo"), operacao);
        itemProvenienteMod.setQuantidade_entrada(setResultset.getShort("quantidade_entrada"), operacao);
        itemProvenienteMod.setCusto_unitario(setResultset.getDouble("custo_unitario"), operacao);
        itemProvenienteMod.setSubTotal(setResultset.getDouble("subtotal"), operacao);
        itemProvenienteMod.getProvenienciaMod().setIdProveniencia(setResultset.getInt("idProveniencia"), operacao);
        itemProvenienteMod.getProvenienciaMod().setTipo(setResultset.getString("tipo"), operacao);
        
        return itemProvenienteMod;
    }

    
}
