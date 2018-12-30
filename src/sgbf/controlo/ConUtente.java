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
            super.query = "INSERT INTO tcc.utente (primeiro_nome, segundo_nome, genero,"
                        + " tipo_identidicacao, numero_identidicacao, contacto, email, "
                        + "endereco, endereco_imagem, categoria, usuario, senha, Instiuicao_idInstiuicao)"
                        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            //super.preparedStatement.setInt(13, utenteMod.get().getIdArea());
            return !super.preparedStatement.execute();
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
            super.query = "update tcc.utente set primeiro_nome=?, segundo_nome=?, genero=?,"
                        + " tipo_identidicacao=?, numero_identidicacao=?, contacto=?, email=?, "
                        + "endereco=?, endereco_imagem=?, categoria=?, usuario=?, senha=?, Instiuicao_idInstiuicao=? where idUtente=?";
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
            //super.preparedStatement.setInt(13, utenteMod.get().getIdArea());
            super.preparedStatement.setInt(14, utenteMod.getIdUtente());
            return !super.preparedStatement.execute();
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
           throw new UtilControloExcessao("Erro ao "+operacao+" Utente !\nErro: "+erro.getMessage(), operacao, UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
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

}
