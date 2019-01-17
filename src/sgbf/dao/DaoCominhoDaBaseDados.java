
package sgbf.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilIconesDaJOPtionPane;

/**
 *
 * @author Look
 */
public class DaoCominhoDaBaseDados {
    private final String driver = "org.mysql.Driver";
    private final String caminho_base_dados = "jdbc:mysql://localhost:3306/tcc";
    private final String usuario = "root";
    private final String senha = "lazaro1A";
    
   
    public Connection baseDeDados(String nome_da_operacao){
        try {
            System.setProperty("jdbc.Driver", driver);
            return DriverManager.getConnection(caminho_base_dados, usuario, senha);
        } catch (SQLException ex) {
            throw new UtilControloExcessao(nome_da_operacao,"Erro ao Efectuar Conexão com o Servidor de Base de Dados !\nErro: "+ex.getMessage(),Alert.AlertType.ERROR);
        }
    }
    
    public void fecharTodasConexoes(PreparedStatement statementPrepared, ResultSet setResult, String nome_da_operacao){
        try{
            if(statementPrepared != null ){
                if(setResult != null){
                    this.fecharRespostaDaBaseDados(this.baseDeDados(nome_da_operacao), statementPrepared, setResult);
                }else{
                    this.fecharSolicitacaoABaseDados(this.baseDeDados(nome_da_operacao), statementPrepared);
                }
            }
        }catch(SQLException ex){
            throw new UtilControloExcessao("Erro de comunicação com a Servidor de Base de Dados !", nome_da_operacao, UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }
    }
    
    private void fecharRespostaDaBaseDados(Connection link, PreparedStatement stm, ResultSet rs) throws SQLException{
       rs.close();
       this.fecharSolicitacaoABaseDados(link, stm);
    }
    
    private void fecharSolicitacaoABaseDados(Connection link, PreparedStatement stm) throws SQLException{
        if(stm == null){
            link.close();
        }else{
            stm.close();
            link.close();
        }
    }


}
