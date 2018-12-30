/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.controlo;

import java.sql.SQLException;
import sgbf.modelo.ModFuncionario;
import sgbf.modelo.ModUtente;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilIconesDaJOPtionPane;


/**
 *
 * @author Dell
 */
public class ConUsuario {
    
    private ConUtente utenteCon;
    
    public ConUsuario(){
        this.utenteCon = new ConUtente();
    }
    
    public boolean autenticar(ModUtente utenteMod, String operacao){
        try{
            utenteCon.query = "select * from usuario where usuario=? and senha=?";
            utenteCon.preparedStatement = utenteCon.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(utenteCon.query);
            utenteCon.preparedStatement.setString(1, utenteMod.getUsuario());
            utenteCon.preparedStatement.setString(2, utenteMod.getSenha());
            utenteCon.setResultset = utenteCon.preparedStatement.executeQuery();
            return !utenteCon.setResultset.next();
        }catch(SQLException erro){
            throw new UtilControloExcessao("Erro ao iniciar sessão\nErro: "+erro.getMessage(), operacao, UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }
    }
    
}
