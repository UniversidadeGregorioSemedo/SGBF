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
import sgbf.modelo.ModFuncionario;
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
            if(this.jaExiste(funcionarioMod, operacao)){
                throw new UtilControloExcessao(operacao,"Já existe registo deste funcionário ",Alert.AlertType.WARNING);
            }else{
                super.query = "INSERT INTO tcc.funcionario (cargo, cod_funcionario, Utente_idUtente)"
                            + " VALUES (?, ?, ?)";
                super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
                super.preparedStatement.setString(1, funcionarioMod.getCargo());
                super.preparedStatement.setString(2, funcionarioMod.getCodigoFuncionario());
                super.preparedStatement.setInt(3, funcionarioMod.getIdUtente());
                return !super.preparedStatement.execute();
            }
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
            if(this.jaExiste(funcionarioMod, operacao)){
                throw new UtilControloExcessao(operacao,"Já existe registo deste funcionário",Alert.AlertType.WARNING);
            }else{
                super.query = "update tcc.funcionario set cargo=?, cod_funcionario=? where Utente_idUtente=?";
                super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
                super.preparedStatement.setString(1, funcionarioMod.getCargo());
                super.preparedStatement.setString(2, funcionarioMod.getCodigoFuncionario());
                super.preparedStatement.setInt(3, funcionarioMod.getIdUtente());
                return !super.preparedStatement.execute();
            }
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
               throw new UtilControloExcessao( operacao,"Existem registo importantes deste Funcionário !", Alert.AlertType.INFORMATION);
            }else{
                super.query = "delete from tcc.funcionario where Utente_idUtente=?";
                super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
                super.preparedStatement.setInt(1,funcionarioMod.getIdUtente());
                return !super.preparedStatement.execute();
            }
        }catch(SQLException erro){
           throw new UtilControloExcessao( operacao,"Erro ao "+operacao+" !\nErro: "+erro.getMessage(), Alert.AlertType.ERROR);
        }finally{
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }
    
    @Override
    public List<Object> listarTodos(String operacao) {
        List<Object> todosRegistos = new ArrayList<>();
        try{
            super.query = "select * from view_funcionarios order by primeiro_nome";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.setResultset = super.preparedStatement.executeQuery();
            while(super.setResultset.next()){
                todosRegistos.add(this.pegarRegistos(super.setResultset,operacao));
            }
            return todosRegistos;
        }catch(SQLException erro){
            throw new UtilControloExcessao(operacao,"Erro ao "+operacao+" !\nErro: "+erro.getMessage(),Alert.AlertType.ERROR);
        }
    }

    @Override
    public List<Object> pesquisar(Object objecto_pesquisar, String operacao) {
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        ModFuncionario funcionarioMod = (ModFuncionario)objecto_pesquisar;
        try{
            super.query = "select * from tcc.view_funcionarios where idUtente=? or "
                        + "(primeiro_nome or segundo_nome) like '%"+funcionarioMod.getPrimeiro_nome()+"%'";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setInt(1, funcionarioMod.getIdUtente());
            super.setResultset  = super.preparedStatement.executeQuery();
            while(super.setResultset.next()){
                todosRegistosEncontrados.add(this.pegarRegistos(super.setResultset, operacao));
            }
            return todosRegistosEncontrados;
        }catch(SQLException erro){
            throw new UtilControloExcessao("Erro ao "+operacao+" !\nErro: "+erro.getMessage(), operacao,UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }
    }
    
    private Object pegarRegistos(ResultSet setResult,String operacao) throws SQLException{
        ModFuncionario funcionarioMod = new ModFuncionario();
        funcionarioMod.setIdUtente(setResult.getInt("Utente_idUtente"), operacao);
        funcionarioMod.setPrimeiro_nome(setResult.getString("primeiro_nome"), operacao);
        funcionarioMod.setSegundo_nome(setResult.getString("segundo_nome"), operacao);
        funcionarioMod.setNome(funcionarioMod.getPrimeiro_nome()+" "+funcionarioMod.getSegundo_nome(), operacao);
        funcionarioMod.setGenero(setResult.getString("genero"), operacao);
        funcionarioMod.setTipo_identificacao(setResult.getString("tipo_identidicacao"), operacao);
        funcionarioMod.setNumero(setResult.getString("numero_identidicacao"), operacao);
        funcionarioMod.setContacto(setResult.getString("contacto"), operacao);
        funcionarioMod.setEndereco(setResult.getString("endereco"), operacao);
        funcionarioMod.setEmail(setResult.getString("email"), operacao);
        funcionarioMod.setEndereco_imagem(setResult.getString("endereco_imagem"), operacao);
        funcionarioMod.setCategoria(setResult.getString("categoria"), operacao);
        funcionarioMod.setUsuario(setResult.getString("usuario"), operacao);
        funcionarioMod.setSenha(setResult.getString("senha"), operacao);
        funcionarioMod.getUtilControloDaData().setData_registo(setResultset.getTimestamp("data_registo"), operacao);
        funcionarioMod.getUtilControloDaData().setData_modificacao(setResultset.getTimestamp("data_modificacao"), operacao);
        funcionarioMod.setIdFuncionario(setResult.getInt("idFuncionario"), operacao);
        funcionarioMod.setCargo(setResult.getString("cargo"), operacao);
        funcionarioMod.setCodigoFuncionario(setResult.getString("cod_funcionario"), operacao);
        return funcionarioMod;
    }
    
    private boolean jaExiste(ModFuncionario funcionarioIntroduzido, String operacao){
        for(Object todosRegistos:  this.listarTodos(operacao)){
            ModFuncionario funcionarioRegistado = (ModFuncionario)todosRegistos;
            funcionarioRegistado.equals(funcionarioIntroduzido, operacao);
        }
        return false;
    }
    
    private boolean temDadosRelacionados(ModFuncionario funcionarioMod, String operacao) throws SQLException{
        super.query = "select *from reserva where Funcionario_idFuncionario=?";
        super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(super.query);
        super.preparedStatement.setInt(1, funcionarioMod.getIdFuncionario());
        if(super.setResultset.next()){
            throw new UtilControloExcessao( operacao,"Esta operação não pode ser executada\nO funcionário seleccionado possui registo de reservas!", Alert.AlertType.INFORMATION);
        }else{
            super.query = "select *from devolucao where Funcionario_idFuncionario=?";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(super.query);
            super.preparedStatement.setInt(1, funcionarioMod.getIdFuncionario());
            if(super.setResultset.next()){
                throw new UtilControloExcessao( operacao,"Esta operação não pode ser executada\nO funcionário seleccionado possui registo de Devolução!", Alert.AlertType.INFORMATION);
            }else{
                super.query = "select *from emprestimo where Funcionario_idFuncionario=?";
                super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(super.query);
                super.preparedStatement.setInt(1, funcionarioMod.getIdFuncionario());
                if(super.setResultset.next()){
                    throw new UtilControloExcessao( operacao,"Esta operação não pode ser executada\nO funcionário seleccionado possui registo de Emprestimo!", Alert.AlertType.INFORMATION);
                }else{
                    return false;
                }
            }
        }
    } 
    
}
