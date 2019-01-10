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
import sgbf.modelo.ModAutor;
import sgbf.modelo.ModUtente;
import sgbf.modelo.ModVisitante;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilIconesDaJOPtionPane;

/**
 *
 * @author Look
 */
public class ConUtente extends ConCRUD {
    
    @Override
    public boolean registar(Object objecto_registar, String operacao) {
        ModUtente utenteMod = (ModUtente)objecto_registar;
        try{
            if(this.jaExiste(utenteMod, operacao)){
                throw new UtilControloExcessao(operacao, "Erro ao verificar dados do Utente", Alert.AlertType.ERROR);
            }else{
                super.query = "INSERT INTO tcc.utente (primeiro_nome, segundo_nome, genero,"
                            + " tipo_identidicacao, numero_identidicacao, contacto, email, "
                            + "endereco, endereco_imagem, categoria, usuario, senha)"
                            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
                super.preparedStatement.setString(1, utenteMod.getPrimeiro_nome());
                super.preparedStatement.setString(2, utenteMod.getSegundo_nome());
                super.preparedStatement.setString(3, utenteMod.getGenero());
                super.preparedStatement.setString(4, utenteMod.getTipo_identificacao());
                super.preparedStatement.setString(5, utenteMod.getNumero());
                super.preparedStatement.setString(6, utenteMod.getContacto());
                super.preparedStatement.setString(7, utenteMod.getEmail());
                super.preparedStatement.setString(8, utenteMod.getEndereco());
                super.preparedStatement.setString(9, utenteMod.getEndereco_imagem());
                super.preparedStatement.setString(10, utenteMod.getCategoria());
                super.preparedStatement.setString(11, utenteMod.getUsuario());
                super.preparedStatement.setString(12, utenteMod.getSenha());
                return !super.preparedStatement.execute();
            }
        }catch(SQLException erro){
            throw new UtilControloExcessao(operacao, "Erro ao "+operacao+" !\nErro: "+erro.getMessage(), Alert.AlertType.ERROR);
        }finally{
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }
    
    @Override
    public boolean alterar(Object objecto_alterar, String operacao) {
        ModUtente utenteMod = (ModUtente)objecto_alterar;
        try{
            if(this.jaExiste(utenteMod, operacao)){
                throw new UtilControloExcessao(operacao, "Erro ao verificar dados do Utente", Alert.AlertType.ERROR);
            }else{
                super.query = "update tcc.utente set primeiro_nome=?, segundo_nome=?, genero=?,"
                            + " tipo_identidicacao=?, numero_identidicacao=?, contacto=?, email=?, "
                            + "endereco=?, endereco_imagem=?, categoria=?, usuario=?, senha=? where idUtente=?";
                super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
                super.preparedStatement.setString(1, utenteMod.getPrimeiro_nome());
                super.preparedStatement.setString(2, utenteMod.getSegundo_nome());
                super.preparedStatement.setString(3, utenteMod.getGenero());
                super.preparedStatement.setString(4, utenteMod.getTipo_identificacao());
                super.preparedStatement.setString(5, utenteMod.getNumero());
                super.preparedStatement.setString(6, utenteMod.getContacto());
                super.preparedStatement.setString(7, utenteMod.getEmail());
                super.preparedStatement.setString(8, utenteMod.getEndereco());
                super.preparedStatement.setString(9, utenteMod.getEndereco_imagem());
                super.preparedStatement.setString(10, utenteMod.getCategoria());
                super.preparedStatement.setString(11, utenteMod.getUsuario());
                super.preparedStatement.setString(12, utenteMod.getSenha());
                super.preparedStatement.setInt(13, utenteMod.getIdUtente());
                return !super.preparedStatement.execute();
            }
        }catch(SQLException erro){
            throw new UtilControloExcessao(operacao, "Erro ao "+operacao+" !\nErro: "+erro.getMessage(), Alert.AlertType.ERROR);
        }finally{
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }

    @Override
    public boolean remover(Object objecto_remover, String operacao) {
       ModUtente utenteMod = (ModUtente)objecto_remover;
        try{
            if(this.temDadosRelacionados(utenteMod, operacao)){
               throw new UtilControloExcessao("Esta operação não pode ser executada\nO Utente selecionado tem registo ! ", operacao, UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
            }else{
                super.query = "delete from tcc.utente where idUtente=?";
                super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
                super.preparedStatement.setInt(1,utenteMod.getIdUtente());
                return !super.preparedStatement.execute();
            }
        }catch(SQLException erro){
            throw new UtilControloExcessao( operacao,"Erro ao "+operacao+"!\nErro: "+erro.getMessage(), Alert.AlertType.ERROR);
        }finally{
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }
    

    @Override
    public List<Object> listarTodos(String operacao) {
        List<Object> todosRegistos = new ArrayList<>();
        try{
            super.query = "select * from tcc.utente order by primeiro_nome, data_modificacao asc";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.setResultset = super.preparedStatement.executeQuery();
            while(super.setResultset.next()){
                todosRegistos.add(this.pegarRegistos(super.setResultset,operacao));
            }
            return todosRegistos;
        }catch(SQLException erro){
            throw new UtilControloExcessao( operacao,"Erro ao "+operacao+"Utente(s) !\nErro: "+erro.getMessage(), Alert.AlertType.ERROR);
        }finally{
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }

    @Override
    public List<Object> pesquisar(Object objecto_pesquisar, String operacao) {
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        ModVisitante visitanteMod = (ModVisitante)objecto_pesquisar;
        try{
            super.query = "select * from tcc.utente where idUtente=? or \n" +
                        "primeiro_nome like '%"+visitanteMod.getPrimeiro_nome()+"%'"
                        + " or segundo_nome like '%"+visitanteMod.getPrimeiro_nome()+"%'";
            System.out.println("Nome: "+visitanteMod.getIdUtente());
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setInt(1, visitanteMod.getIdUtente());
            super.setResultset  = super.preparedStatement.executeQuery();
            while(super.setResultset.next()){
                todosRegistosEncontrados.add(this.pegarRegistos(super.setResultset, operacao));
            }
            return todosRegistosEncontrados;
        }catch(SQLException erro){
            throw new UtilControloExcessao( operacao,"Erro ao "+operacao+"Autor(es) !\nErro: "+erro.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    private Object pegarRegistos(ResultSet setResult,String operacao) throws SQLException{
        ModVisitante visitanteMod = new ModVisitante();
        visitanteMod.setIdUtente(setResult.getInt("idUtente"), operacao);
        visitanteMod.setPrimeiro_nome(setResult.getString("primeiro_nome"), operacao);
        visitanteMod.setSegundo_nome(setResult.getString("segundo_nome"), operacao);
        visitanteMod.setNome(visitanteMod.getPrimeiro_nome()+" "+visitanteMod.getSegundo_nome(), operacao);
        visitanteMod.setGenero(setResult.getString("genero"), operacao);
        visitanteMod.setTipo_identificacao(setResult.getString("tipo_identidicacao"), operacao);
        visitanteMod.setNumero(setResult.getString("numero_identidicacao"), operacao);
        visitanteMod.setContacto(setResult.getString("contacto"), operacao);
        visitanteMod.setEndereco(setResult.getString("endereco"), operacao);
        visitanteMod.setEmail(setResult.getString("email"), operacao);
        visitanteMod.setEndereco_imagem(setResult.getString("endereco_imagem"), operacao);
        visitanteMod.setCategoria(setResult.getString("categoria"), operacao);
        visitanteMod.setUsuario(setResult.getString("usuario"), operacao);
        visitanteMod.setSenha(setResult.getString("senha"), operacao);
        visitanteMod.getUtilControloDaData().setData_registo(setResultset.getTimestamp("data_registo"), operacao);
        visitanteMod.getUtilControloDaData().setData_modificacao(setResultset.getTimestamp("data_modificacao"), operacao);
        return visitanteMod;
    }
    
    private boolean temDadosRelacionados(ModUtente utenteMod, String operacao) throws SQLException{
        super.query = "select *from funcionario where Utente_idUtente=?";
        super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(super.query);
        super.preparedStatement.setInt(1, utenteMod.getIdUtente());
        if(super.setResultset.next()){
            return super.setResultset.next();
        }else{
            super.query = "select *from reserva where Utente_idUtente=?";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(super.query);
            super.preparedStatement.setInt(1, utenteMod.getIdUtente());
            return super.setResultset.next();
        }
    }
    
    private boolean jaExiste(ModUtente utenteIntroduzido, String operacao){
        for(Object todosRegistos:  this.listarTodos(operacao)){
            ModUtente utenteRegistado = (ModUtente)todosRegistos;
            utenteRegistado.equals(utenteIntroduzido, operacao);
        }
        return false;
    }

}
