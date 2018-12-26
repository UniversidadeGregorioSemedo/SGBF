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
import sgbf.modelo.ModEstoque;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilIconesDaJOPtionPane;

/**
 *
 * @author Look
 */
public class ConEstoque extends ConCRUD {
    
        @Override
    public boolean registar(Object objecto_registar, String operacao) {
        ModEstoque estoqueMod = (ModEstoque)objecto_registar;
        try{
            super.query = "INSERT INTO tcc.estoque (quantidade_total, quantidade_em_falta, quantidade_acervos_emprestados, quantidade_acervos_reservados)"
                        + " VALUES (?, ?, ?, ?)";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setInt(1, estoqueMod.getQuantidade_total());
            super.preparedStatement.setInt(2, estoqueMod.getQuantidade_em_falta());
            super.preparedStatement.setInt(3, estoqueMod.getQuantidade_acervos_emprestados());
            super.preparedStatement.setInt(4, estoqueMod.getQuantidade_acervos_resercados());
            return !super.preparedStatement.execute();
        }catch(SQLException erro){
            throw new UtilControloExcessao("Erro ao "+operacao+" Estoque !\nErro: "+erro.getMessage(), operacao,UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }finally{
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }

    @Override
    public boolean alterar(Object objecto_alterar, String operacao) {
        ModEstoque estoqueMod = (ModEstoque)objecto_alterar;
        try{
            super.query = "update tcc.estoque quantidade_total=?, quantidade_em_falta=?, quantidade_acervos_emprestados=?,"
                        + " quantidade_acervos_reservados=? where idEstoque=?";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setInt(1, estoqueMod.getQuantidade_total());
            super.preparedStatement.setInt(2, estoqueMod.getQuantidade_em_falta());
            super.preparedStatement.setInt(3, estoqueMod.getQuantidade_acervos_emprestados());
            super.preparedStatement.setInt(4, estoqueMod.getQuantidade_acervos_resercados());
            super.preparedStatement.setInt(5, estoqueMod.getQuantidade_acervos_resercados());
            return !super.preparedStatement.execute();
        }catch(SQLException erro){
            throw new UtilControloExcessao("Erro ao "+operacao+" Estoque !\nErro: "+erro.getMessage(), operacao,UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }finally{
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }

    @Override
    public boolean remover(Object objecto_remover, String operacao) {
       ModEstoque estoqueMod = (ModEstoque)objecto_remover;
        try{
            if(this.temDadosRelacionados(estoqueMod, operacao)){
                throw new UtilControloExcessao("Esta operação não pode ser executada\nO estoque seleccionado possui registos ", operacao,UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
            }else{
                super.query = "delete from tcc.estoque where idEstoque=?";
                super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
                super.preparedStatement.setInt(1,estoqueMod.getIdEstoque());
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
        }*/
        return todosRegistosEncontrados;
    }
    
    private Object pegarRegistos(ResultSet setResultset,String operacao) throws SQLException{
        ModEstante estanteMod = new ModEstante();
        /*estanteMod.setIdEstante(setResultset.getInt("idEstante"), operacao);
        estanteMod.setDesignacao(setResultset.getString("designacao"), operacao);
        estanteMod.setDescricao(setResultset.getString("descricacao"), operacao);
        estanteMod.setLinha(setResultset.getByte("linha"), operacao);
        estanteMod.setColuna(setResultset.getByte("coluna"), operacao);
        estanteMod.getAreaMod().setIdArea(setResultset.getInt("Area_idArea"), operacao);
        estanteMod.getUtilControloDaData().setData_registo(setResultset.getTimestamp("data_registo"), operacao);
        estanteMod.getUtilControloDaData().setData_modificacao(setResultset.getTimestamp("data_modificacao"), operacao);
        */
        return estanteMod;
    }
    
    private boolean temDadosRelacionados(ModEstoque estoqueMod, String operacao) throws SQLException{
        super.query = "select *from itensProvenientes where Estoque_idEstoque=?";
        super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(super.query);
        super.preparedStatement.setInt(1, estoqueMod.getIdEstoque());
        return super.setResultset.next();
    }

    
}
