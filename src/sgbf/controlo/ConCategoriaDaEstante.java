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
import sgbf.modelo.ModCategoria;
import sgbf.modelo.ModCategoriaDaEstante;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilIconesDaJOPtionPane;

/**
 *
 * @author Look
 */
public class ConCategoriaDaEstante extends ConCRUD {
    
    @Override
    public boolean registar(Object objecto_registar, String operacao) {
        ModCategoriaDaEstante categoriaDaEstanteMod = (ModCategoriaDaEstante)objecto_registar;
        try{
            if(this.temCategoriaEEstante(categoriaDaEstanteMod)){
                super.query = "INSERT INTO tcc.categoriasdaestante(categoria_idcategoria, Estante_idEstante) VALUES (?, ?)";
                super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
                super.preparedStatement.setInt(1, categoriaDaEstanteMod.getCategoriaMod().getIdCategoria());
                super.preparedStatement.setInt(2, categoriaDaEstanteMod.getEstanteMod().getIdEstante());
                return !super.preparedStatement.execute();
            }else{
                return true;
            }
        }catch(SQLException erro){
            throw new UtilControloExcessao("Erro ao "+operacao+" Estante !\nErro: "+erro.getMessage(), operacao,UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }finally{
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }
    

    @Override
    public boolean alterar(Object objecto_alterar, String operacao) {
        ModCategoriaDaEstante categoriaDaEstanteMod = (ModCategoriaDaEstante)objecto_alterar;
        try{
            if(this.temCategoriaEEstante(categoriaDaEstanteMod)){
                super.query = "update tcc.categoriasdaestante set Estante_idEstante=? where categoria_idcategoria=?";
                super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
                super.preparedStatement.setInt(1, categoriaDaEstanteMod.getEstanteMod().getIdEstante());
                super.preparedStatement.setInt(2, categoriaDaEstanteMod.getCategoriaMod().getIdCategoria());
                return !super.preparedStatement.execute();
            }else{
                return this.remover(categoriaDaEstanteMod, operacao);
            }
        }catch(SQLException erro){
            throw new UtilControloExcessao("Erro ao "+operacao+" Categoria !\nErro: "+erro.getMessage(), operacao,UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }finally{
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }

    @Override
    public boolean remover(Object objecto_remover, String operacao) {
        ModCategoriaDaEstante categoriaDaEstanteMod = (ModCategoriaDaEstante)objecto_remover;
        try{
            super.query = "delete from tcc.categoriasdaestante where categoria_idcategoria=? and Estante_idEstante=?";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setInt(1, categoriaDaEstanteMod.getCategoriaMod().getIdCategoria());
            super.preparedStatement.setInt(2, categoriaDaEstanteMod.getEstanteMod().getIdEstante());
            return !super.preparedStatement.execute();
        }catch(SQLException erro){
           throw new UtilControloExcessao("Erro ao "+operacao+" Categoria !\nErro: "+erro.getMessage(), operacao, UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }finally{
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }

    private boolean temCategoriaEEstante(ModCategoriaDaEstante categoriaDaEstanteMod){
        if(categoriaDaEstanteMod.getCategoriaMod().getIdCategoria() !=0){
            if(categoriaDaEstanteMod.getCategoriaMod().getEstanteMod()!=null){
                if(categoriaDaEstanteMod.getCategoriaMod().getEstanteMod().getIdEstante()!=0){
                    return true;
                }else{
                    return false;
                }
            }else{
               return false;
            }
        }else{
            return false;
        }
    }
    
    @Override
    public List<Object> listarTodos(String operacao) {
        List<Object> todosRegistos = new ArrayList<>();
        try{
            super.query = "select * from view_CategoriaDaEstante";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.setResultset = super.preparedStatement.executeQuery();
            while(super.setResultset.next()){
                todosRegistos.add(this.pegarRegistos(super.setResultset,operacao));
            }
            return todosRegistos;
        }catch(SQLException erro){
            throw new UtilControloExcessao("Erro ao "+operacao+" Estante(s) !\nErro: "+erro.getMessage(), operacao, UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }
    }

    @Override
    public List<Object> pesquisar(Object objecto_pesquisar, String operacao) {
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        /*ModEstante estanteMod = (ModEstante)objecto_pesquisar;
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
        */
        return todosRegistosEncontrados;
    }
    
    private Object pegarRegistos(ResultSet setResultset,String operacao) throws SQLException{
        ModCategoria categoriaMod = new ModCategoria();
        categoriaMod.setIdCategoria(setResultset.getInt("idcategoria"), operacao);
        categoriaMod.getEstanteMod().setIdEstante(setResultset.getInt("idEstante"), operacao);
        categoriaMod.setDesignacao(setResultset.getString("categoria"), operacao);
        categoriaMod.getEstanteMod().setDesignacao(setResultset.getString("estante"), operacao);
        categoriaMod.getUtilControloDaData().setData_registo(setResultset.getTimestamp("data_registo"), operacao);
        categoriaMod.getUtilControloDaData().setData_modificacao(setResultset.getTimestamp("data_modificacao"), operacao);
        return categoriaMod;
    }

}
