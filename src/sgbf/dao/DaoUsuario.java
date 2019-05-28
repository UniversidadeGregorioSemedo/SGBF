/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import sgbf.modelo.ModFuncionario;
import sgbf.modelo.ModUtente;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilCriptografia;

/**
 *
 * @author Dell
 */
public class DaoUsuario {

    private DaoUtente utenteCon;
    private DaoFuncionario funcionarioCon;

    public DaoUsuario() {
        this.utenteCon = new DaoUtente();
        this.funcionarioCon = new DaoFuncionario();
    }

    public ModFuncionario identificarUsuario(ModUtente utenteMod, String operacao) throws SQLException {
        this.utenteCon.query = "select * from view_funcionarios where usuario=? and senha=?";
        this.utenteCon.preparedStatement = this.utenteCon.caminhoDaBaseDados.conectarBaseDeDados().prepareStatement(utenteCon.query);
        this.utenteCon.preparedStatement.setString(1, UtilCriptografia.encrypt(utenteMod.getUsuario(), operacao));
        this.utenteCon.preparedStatement.setString(2, UtilCriptografia.encrypt(utenteMod.getSenha(), operacao));
        this.utenteCon.setResultset = utenteCon.preparedStatement.executeQuery();
        return this.resultadoEntrado(utenteCon.setResultset, operacao);
    }

    private ModFuncionario resultadoEntrado(ResultSet setResult, String operacao) throws SQLException {
        ModFuncionario funcionarioMod = this.pegarDadosDoUtente(setResult, operacao);
        
        if (funcionarioMod == null) {
            return null;
        } else {
            if (funcionarioCon.pesquisar(funcionarioMod, operacao).isEmpty()) {
                return null;
            } else {
                for (Object todosRegistos : funcionarioCon.pesquisar(funcionarioMod, operacao)) {
                    return funcionarioMod = (ModFuncionario) todosRegistos;
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
