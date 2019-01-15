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
import sgbf.modelo.ModCategoriaDaEstante;
import sgbf.modelo.ModEstante;
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
            if(this.jaExiste(categoriaMod, operacao)){
                throw new UtilControloExcessao(operacao, "Erro ao verificar dados da Categoria", Alert.AlertType.ERROR);
            }else{
                if(this.relacionarComOutraEstante(categoriaMod, operacao)){
                    return true;
                }else{
                    super.query = "INSERT INTO tcc.categoria (designacao)"
                                + " VALUES (?)";
                    super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
                    super.preparedStatement.setString(1, categoriaMod.getDesignacao());
                    return !super.preparedStatement.execute();
                }
            }
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
            if(this.retirarCategoriaDaEstante(categoriaMod, operacao)){
                return true;
            }else{
                if(this.jaExiste(categoriaMod, operacao)){
                    throw new UtilControloExcessao(operacao, "Erro ao verificar dados da Categoria", Alert.AlertType.ERROR);
                }else{
                    super.query = "UPDATE tcc.categoria set designacao=? where idcategoria=?";
                    super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
                    super.preparedStatement.setString(1, categoriaMod.getDesignacao());
                    super.preparedStatement.setInt(2, categoriaMod.getIdCategoria());
                    return !super.preparedStatement.execute();
                }
            }
        }catch(SQLException erro){
            throw new UtilControloExcessao(operacao,"Erro ao "+operacao+" Categoria !\nErro: "+erro.getMessage(),Alert.AlertType.ERROR);
        }finally{
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }
    
    private boolean retirarCategoriaDaEstante(ModCategoria categoriaMod, String operacao){
        ConCategoriaDaEstante categoriaDaEstanteCon = new ConCategoriaDaEstante();
        if(categoriaMod.getEstanteMod().getIdEstante() != categoriaMod.getEstanteAntiga().getIdEstante()){
            return categoriaDaEstanteCon.remover(categoriaMod, operacao);
        }else{
            return categoriaMod.getEstanteMod().getIdEstante() != categoriaMod.getEstanteAntiga().getIdEstante();
        }
    }

    @Override
    public boolean remover(Object objecto_remover, String operacao) {
        ModCategoria categoriaMod = (ModCategoria)objecto_remover;
        try{
            if(this.removerTodosRegistos(categoriaMod, operacao)){
            super.query = "delete from tcc.categoria where idcategoria=?";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setInt(1,categoriaMod.getIdCategoria());
            return !super.preparedStatement.execute();
            }else{
                super.query = "delete from tcc.categoria where idcategoria=?";
                super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
                super.preparedStatement.setInt(1,categoriaMod.getIdCategoria());
                return !super.preparedStatement.execute();
            }
        }catch(SQLException erro){
            throw new UtilControloExcessao(operacao,"Erro ao "+operacao+" Categoria !\nErro: "+erro.getMessage(),Alert.AlertType.ERROR);
        }finally{
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }

    @Override
    public List<Object> listarTodos(String operacao) {
        List<Object> todosRegistos = new ArrayList<>();
        try{
            super.query = "select * from tcc.view_categorias";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.setResultset = super.preparedStatement.executeQuery();
            while(super.setResultset.next()){
                todosRegistos.add(this.pegarRegistos(super.setResultset,operacao));
            }
            return todosRegistos;
        }catch(SQLException erro){
            throw new UtilControloExcessao("Erro ao "+operacao+" Categoria(s) !\nErro: "+erro.getMessage(), operacao, UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }
    }

    @Override
    public List<Object> pesquisar(Object objecto_pesquisar, String operacao) {
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        ModCategoria categoriaMod = (ModCategoria)objecto_pesquisar;
        try{
            super.query = "select * from tcc.view_categorias where idcategoria=? or "
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
        if(setResultset.getString("idEstante") != null){
            categoriaMod.getEstanteMod().setIdEstante(setResultset.getInt("idEstante"), operacao);
            categoriaMod.getEstanteMod().setDesignacao(setResultset.getString("estante"), operacao);
        }
        categoriaMod.getUtilControloDaData().setData_registo(setResultset.getTimestamp("data_registo"), operacao);
        categoriaMod.getUtilControloDaData().setData_modificacao(setResultset.getTimestamp("data_modificacao"), operacao);
        
        return categoriaMod;
    }
    
    private boolean removerTodosRegistos(ModCategoria categoriModMod, String operacao) throws SQLException{
        ModCategoriaDaEstante categoriaDaEstanteMod = new ModCategoriaDaEstante();
        ConCategoriaDaEstante categoriasDaEstanteCon = new ConCategoriaDaEstante();
        categoriaDaEstanteMod.setCategoriaMod(categoriModMod, operacao);
        categoriaDaEstanteMod.setEstanteMod(new ModEstante(), operacao);
        return categoriasDaEstanteCon.remover(categoriaDaEstanteMod, operacao);
    }
    
    private boolean jaExiste(ModCategoria categoriaIntroduzida, String operacao){
        for(Object todosRegistos:  this.listarTodos(operacao)){
            ModCategoria categoriaRegistado = (ModCategoria)todosRegistos;
            categoriaRegistado.equals(categoriaIntroduzida, operacao);
        }
        return false;
    }
    
     private boolean relacionarComOutraEstante(ModCategoria categoriaIntroduzida, String operacao){
        if((categoriaIntroduzida.getIdCategoria() !=0) && (categoriaIntroduzida.getEstanteMod().getIdEstante()!=0)){
            for(Object todosRegistos:  this.listarTodos(operacao)){
                ModCategoria categoriaRegistado = (ModCategoria)todosRegistos;
                if(categoriaIntroduzida.getDesignacao().equalsIgnoreCase(categoriaRegistado.getDesignacao())){
                    return true;
                }
            }
            return false;
        }else{
            if(categoriaIntroduzida.getEstanteMod().getIdEstante() == 0){
                return false;
            }else{
                return true;
            }
        }
    }

}
