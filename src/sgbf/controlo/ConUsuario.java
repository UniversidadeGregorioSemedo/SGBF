/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.controlo;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import sgbf.modelo.ModUtente;
import sgbf.modelo.ModVisitante;
import sgbf.util.UtilControloExcessao;

/**
 *
 * @author Dell
 */
public class ConUsuario {

    private ConUtente utenteCon;

    public ConUsuario() {
        this.utenteCon = new ConUtente();
    }

    public ModVisitante autenticar(ModUtente utenteMod, String operacao) {
        try {
            this.utenteCon.query = "select * from utente where usuario=? and senha=?";
            this.utenteCon.preparedStatement = this.utenteCon.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(utenteCon.query);
            this.utenteCon.preparedStatement.setString(1, utenteMod.getUsuario());
            this.utenteCon.preparedStatement.setString(2, utenteMod.getSenha());
            this.utenteCon.setResultset = utenteCon.preparedStatement.executeQuery();
            return this.pegarDadosDoUtente(utenteCon.setResultset, operacao);
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao iniciar sessão\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private ModVisitante pegarDadosDoUtente(ResultSet setResult, String operacao) throws SQLException {
        if (setResult.next()) {
            ConUtente utenteCon = new ConUtente();
            return (ModVisitante) utenteCon.pegarRegistos(setResult, operacao);
        } else {
            return null;
        }
    }

}
