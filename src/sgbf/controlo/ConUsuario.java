/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.controlo;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import sgbf.modelo.ModFuncionario;
import sgbf.modelo.ModUtente;
import sgbf.util.UtilControloExcessao;

/**
 *
 * @author Dell
 */
public class ConUsuario {

    private ConUtente utenteCon;
    private ConFuncionario funcionarioCon;

    public ConUsuario() {
        this.utenteCon = new ConUtente();
        this.funcionarioCon = new ConFuncionario();
    }

    public ModFuncionario autenticar(ModUtente utenteMod, String operacao) {
        try {
            this.utenteCon.query = "select * from utente where usuario=? and senha=?"
                                 + "and (categoria='Administrador' or categoria='Funcionário')";
            this.utenteCon.preparedStatement = this.utenteCon.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(utenteCon.query);
            this.utenteCon.preparedStatement.setString(1, utenteMod.getUsuario());
            this.utenteCon.preparedStatement.setString(2, utenteMod.getSenha());
            this.utenteCon.setResultset = utenteCon.preparedStatement.executeQuery();
            return this.eUmFuncionario(utenteCon.setResultset, operacao);
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao iniciar sessão\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    private ModFuncionario eUmFuncionario(ResultSet setResult, String operacao) throws SQLException{
        ModFuncionario funcionarioMod = this.pegarDadosDoUtente(setResult, operacao);
        if(funcionarioMod == null){
            return null;
        }else{
            if(funcionarioCon.pesquisar(funcionarioMod, operacao).isEmpty()){
                return null;
            }else{
                for(Object todosRegistos: funcionarioCon.pesquisar(funcionarioMod, operacao)){
                    return funcionarioMod = (ModFuncionario)todosRegistos;
                }
                throw new UtilControloExcessao(operacao, "Categoria incosistente !", Alert.AlertType.WARNING);
            }
        }
    }
    
    private ModFuncionario pegarDadosDoUtente(ResultSet setResult, String operacao) throws SQLException {
        if (setResult.next()) {
            ModFuncionario funcionarioMod = new ModFuncionario();
            funcionarioMod.setIdUtente(setResult.getInt("idUtente"), operacao);
            return funcionarioMod;
        } else {
            return null;
        }
    }

}
