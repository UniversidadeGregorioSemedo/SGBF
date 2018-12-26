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
import sgbf.modelo.ModFuncionario;
import sgbf.modelo.ModProveniencia;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilIconesDaJOPtionPane;

/**
 *
 * @author Dell
 */
public class ConFuncionario extends ConCRUD {
   @Override
    public boolean registar(Object objecto_registar, String operacao) {
        ModFuncionario funcionarioMod = (ModFuncionario)objecto_registar;
        try{
            super.query = "INSERT INTO tcc.funcionario (cargo, cod_funcionario, Utente_idUtente)"
                        + " VALUES (?, ?, ?)";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setString(1, funcionarioMod.getCargo());
            super.preparedStatement.setInt(2, funcionarioMod.getCodigo_funcionario());
            super.preparedStatement.setInt(3, funcionarioMod.getIdUtente());
            return !super.preparedStatement.execute();
        }catch(SQLException erro){
            throw new UtilControloExcessao("Erro ao "+operacao+" Funcionários !\nErro: "+erro.getMessage(), operacao,UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }finally{
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }

    @Override
    public boolean alterar(Object objecto_alterar, String operacao) {
        ModFuncionario funcionarioMod = (ModFuncionario)objecto_alterar;
        try{
            super.query = "update tcc.funcionario set cargo=?, cod_funcionario=? where Utente_idUtente=?";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setString(1, funcionarioMod.getCargo());
            super.preparedStatement.setInt(2, funcionarioMod.getCodigo_funcionario());
            super.preparedStatement.setInt(3, funcionarioMod.getIdUtente());
            return !super.preparedStatement.execute();
        }catch(SQLException erro){
            throw new UtilControloExcessao("Erro ao "+operacao+" Funcionários !\nErro: "+erro.getMessage(), operacao,UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }finally{
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }

    @Override
    public boolean remover(Object objecto_remover, String operacao) {
        ModFuncionario funcionarioMod = (ModFuncionario)objecto_remover;
        try{
            if(this.temDadosRelacionados(funcionarioMod, operacao)){
               throw new UtilControloExcessao("Esta operação não pode ser executada !\nErro: Ofuncionario seleccionado tem resgisto seleccionada tem dados registados !", operacao, UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
            }else{
                super.query = "delete from tcc.funcionario where idFuncionario=?";
                super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
                super.preparedStatement.setInt(1,funcionarioMod.getCodigo_funcionario());
                return !super.preparedStatement.execute();
            }
        }catch(SQLException erro){
           throw new UtilControloExcessao("Erro ao "+operacao+" Funcionario !\nErro: "+erro.getMessage(), operacao, UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
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
    
    private boolean temDadosRelacionados(ModFuncionario funcionarioMod, String operacao) throws SQLException{
        super.query = "select *from reserva where Funcionario_idFuncionario=?";
        super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(super.query);
        super.preparedStatement.setInt(1, funcionarioMod.getCodigo_funcionario());
        if(super.setResultset.next()){
            return super.setResultset.next();
        }else{
            super.query = "select *from devolucao where Funcionario_idFuncionario=?";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(super.query);
            super.preparedStatement.setInt(1, funcionarioMod.getCodigo_funcionario());
            if(super.setResultset.next()){
                return super.setResultset.next();
            }else{
                super.query = "select *from emprestimo where Funcionario_idFuncionario=?";
                super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(super.query);
                super.preparedStatement.setInt(1, funcionarioMod.getCodigo_funcionario());
                return super.setResultset.next();
            }
        }
    } 
    
}
