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
import sgbf.modelo.ModCategoria;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilIconesDaJOPtionPane;

/**
 *
 * @author Look
 */
public class ConCategoria extends ConCRUD {
    @Override
    public boolean registar(Object objecto_registar, String operacao) {
        ModCategoria categoriaMod = (ModCategoria)objecto_registar;
        try{
            super.query = "INSERT INTO tcc.categoria (designacao)"
                        + " VALUES (?)";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setString(1, categoriaMod.getDesignacao());
            return !super.preparedStatement.execute();
        }catch(SQLException erro){
            throw new UtilControloExcessao(operacao,"Erro ao "+operacao+" Categoria !\nErro: "+erro.getMessage(),Alert.AlertType.ERROR);
        }finally{
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }

    @Override
    public boolean alterar(Object objecto_alterar, String operacao) {
        ModCategoria categoriaMod = (ModCategoria)objecto_alterar;
        try{
            super.query = "UPDATE tcc.categoria set designacao=? where idcategoria=?";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setString(1, categoriaMod.getDesignacao());
            super.preparedStatement.setInt(2, categoriaMod.getIdCategoria());
            return !super.preparedStatement.execute();
        }catch(SQLException erro){
            throw new UtilControloExcessao("Erro ao "+operacao+" Categoria !\nErro: "+erro.getMessage(), operacao,UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }finally{
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }

    @Override
    public boolean remover(Object objecto_remover, String operacao) {
        ModCategoria categoriaMod = (ModCategoria)objecto_remover;
        try{
            if(this.temDadosRelacionados(categoriaMod, operacao)){
               throw new UtilControloExcessao("Esta operação não pode ser executada\nA Categoria seleccionada tem registo !", operacao, UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
            }else{
                super.query = "delete from tcc.categoria where idcategoria=?";
                super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
                super.preparedStatement.setInt(1,categoriaMod.getIdCategoria());
                return !super.preparedStatement.execute();
            }
        }catch(SQLException erro){
           throw new UtilControloExcessao("Erro ao "+operacao+" Categoria !\nErro: "+erro.getMessage(), operacao, UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }finally{
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }

    @Override
    public List<Object> listarTodos(String operacao) {
        List<Object> todosRegistos = new ArrayList<>();
        try{
            super.query = "select * from tcc.categoria order by designacao, data_modificacao asc";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.setResultset = super.preparedStatement.executeQuery();
            while(super.setResultset.next()){
                todosRegistos.add(this.pegarRegistos(super.setResultset,operacao));
            }
            return todosRegistos;
        }catch(SQLException erro){
            throw new UtilControloExcessao("Erro ao "+operacao+" Categoria(s) !\nErro: "+erro.getMessage(), operacao, UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }finally{
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }

    @Override
    public List<Object> pesquisar(Object objecto_pesquisar, String operacao) {
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        ModCategoria categoriaMod = (ModCategoria)objecto_pesquisar;
        try{
            super.query = "select * from tcc.categoria where idcategoria=? or "
                        + "designacao like '%"+categoriaMod.getDesignacao()+"%'";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setInt(1, categoriaMod.getIdCategoria());
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
    
    public Integer proximoCodigoASerRegistado(String operacao) {
        try{
            super.query= "select max(idcategoria) from categoria";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.setResultset  = super.preparedStatement.executeQuery();
            if(super.setResultset.next()){
                return super.setResultset.getInt("max(idcategoria)")+1;
            }else{
                return 1;
            }
        }catch(SQLException ex){
            throw new UtilControloExcessao(operacao,"Erro ao Listar Código da Categoria!\nErro: "+ex.getMessage(),Alert.AlertType.ERROR);
        }
    }
    
    
    private Object pegarRegistos(ResultSet setResultset,String operacao) throws SQLException{
        ModCategoria categoriaMod = new ModCategoria();
        categoriaMod.setIdCategoria(setResultset.getInt("idcategoria"), operacao);
        categoriaMod.setDesignacao(setResultset.getString("designacao"), operacao);
        categoriaMod.getUtilControloDaData().setData_registo(setResultset.getTimestamp("data_registo"), operacao);
        categoriaMod.getUtilControloDaData().setData_modificacao(setResultset.getTimestamp("data_modificacao"), operacao);
        return categoriaMod;
    }
    private boolean temDadosRelacionados(ModCategoria categoriModMod, String operacao) throws SQLException{
        super.query = "select *from categoriasdaestante where categoria_idcategoria=?";
        super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(super.query);
        super.preparedStatement.setInt(1, categoriModMod.getIdCategoria());
        return super.setResultset.next();
    }
    
    

}
