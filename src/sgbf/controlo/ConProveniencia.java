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
import sgbf.modelo.ModEstante;
import sgbf.modelo.ModProveniencia;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilIconesDaJOPtionPane;

/**
 *
 * @author Look
 */
public class ConProveniencia extends ConCRUD {
    
    @Override
    public boolean registar(Object objecto_registar, String operacao) {
        ModProveniencia provenienciaMod = (ModProveniencia)objecto_registar;
        try{
            super.query = "INSERT INTO tcc.proveniencia (tipo) VALUES (?)";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setString(1, provenienciaMod.getTipo());
            return !super.preparedStatement.execute();
        }catch(SQLException erro){
            throw new UtilControloExcessao("Erro ao "+operacao+" Proveniencia !\nErro: "+erro.getMessage(), operacao,UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }finally{
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }

    @Override
    public boolean alterar(Object objecto_alterar, String operacao) {
        ModProveniencia provenienciaMod = (ModProveniencia)objecto_alterar;
        try{
            super.query = "update tcc.proveniencia set tipo=? where idProveniencia=?";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setString(1, provenienciaMod.getTipo());
            super.preparedStatement.setInt(2, provenienciaMod.getIdProveniencia());
            return !super.preparedStatement.execute();
        }catch(SQLException erro){
            throw new UtilControloExcessao("Erro ao "+operacao+" Proveniencia !\nErro: "+erro.getMessage(), operacao,UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }finally{
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }

    @Override
    public boolean remover(Object objecto_remover, String operacao) {
        ModProveniencia provenienciaMod = (ModProveniencia)objecto_remover;
        try{
            if(this.temDadosRelacionados(provenienciaMod, operacao)){
               throw new UtilControloExcessao("Esta operação não pode ser executada !\nErro: A proveniencia seleccionada tem dados registados !", operacao, UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
            }else{
                super.query = "delete from tcc.proveniencia where idProveniencia=?";
                super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
                super.preparedStatement.setInt(1,provenienciaMod.getIdProveniencia());
                return !super.preparedStatement.execute();
            }
        }catch(SQLException erro){
           throw new UtilControloExcessao("Erro ao "+operacao+" Estante !\nErro: "+erro.getMessage(), operacao, UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }finally{
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }
    
    @Override
    public List<Object> listarTodos(String operacao) {
        List<Object> todosRegistos = new ArrayList<>();
        try{
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
        }
    }

    @Override
    public List<Object> pesquisar(Object objecto_pesquisar, String operacao) {
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        ModEstante estanteMod = (ModEstante)objecto_pesquisar;
        try{
            super.query = "select * from tcc.Estante where idEstante=? or "
                        + "designacao like '%"+estanteMod.getDesignacao()+"%'";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setInt(1, estanteMod.getIdEstante());
            super.setResultset  = super.preparedStatement.executeQuery();
            while(super.setResultset.next()){
                todosRegistosEncontrados.add(this.pegarRegistos(super.setResultset, operacao));
            }
            return todosRegistosEncontrados;
        }catch(SQLException erro){
            throw new UtilControloExcessao("Erro ao "+operacao+" Editora(s) !\nErro: "+erro.getMessage(), operacao,UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }finally{
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }
    
    private Object pegarRegistos(ResultSet setResultset,String operacao) throws SQLException{
        ModEstante estanteMod = new ModEstante();
        estanteMod.setIdEstante(setResultset.getInt("idEstante"), operacao);
        estanteMod.setDesignacao(setResultset.getString("designacao"), operacao);
        estanteMod.setDescricao(setResultset.getString("descricacao"), operacao);
        estanteMod.setLinha(setResultset.getByte("linha"), operacao);
        estanteMod.setColuna(setResultset.getByte("coluna"), operacao);
        estanteMod.getAreaMod().setIdArea(setResultset.getInt("Area_idArea"), operacao);
        estanteMod.getUtilControloDaData().setData_registo(setResultset.getTimestamp("data_registo"), operacao);
        estanteMod.getUtilControloDaData().setData_modificacao(setResultset.getTimestamp("data_modificacao"), operacao);
        return estanteMod;
    }
    
    private boolean temDadosRelacionados(ModProveniencia provenienciaMod, String operacao) throws SQLException{
        super.query = "select *from itensProvenientes where Proveniencia_idProveniencia=?";
        super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(super.query);
        super.preparedStatement.setInt(1, provenienciaMod.getIdProveniencia());
        return super.setResultset.next();
    }

}
