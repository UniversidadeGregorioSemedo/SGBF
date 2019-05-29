package sgbf.dao;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javafx.scene.control.Alert;
import sgbf.util.UtilControloExcessao;

public class DaoCominhoDaBaseDados {

    private final String driver = "org.mysql.Driver";
    private final String caminho_base_dados = "jdbc:mysql://localhost:3307/tcc";
    private final String usuario = "root";
    private final String senha = "__Gestline18";
    private String operacaoEmExecucao;

    public DaoCominhoDaBaseDados(String operacaoEmExecucao) {
        this.operacaoEmExecucao = operacaoEmExecucao;

    }

    public Connection conectarBaseDeDados() {
        try {
            System.setProperty("jdbc.Driver", driver);
            return DriverManager.getConnection(caminho_base_dados, usuario, senha);
        } catch (SQLException ex) {
            throw new UtilControloExcessao(operacaoEmExecucao, "Erro ao Efectuar Conexão com o Servidor de Base de Dados !\nErro: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void fecharTodasConexoes(PreparedStatement statementPrepared, ResultSet setResult) {
        try {
            if (statementPrepared != null) {
                if (setResult != null) {
                    this.fecharRespostaDaBaseDados(this.conectarBaseDeDados(), statementPrepared, setResult);
                } else {
                    this.fecharSolicitacaoABaseDados(this.conectarBaseDeDados(), statementPrepared);
                }
            }
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacaoEmExecucao, "Erro ao se conectar a base de dados", Alert.AlertType.ERROR);
        }
    }

    private void fecharRespostaDaBaseDados(Connection link, PreparedStatement stm, ResultSet rs) throws SQLException {
        rs.close();
        this.fecharSolicitacaoABaseDados(link, stm);
    }

    private void fecharSolicitacaoABaseDados(Connection link, PreparedStatement stm) throws SQLException {
        if (stm == null) {
            link.close();
        } else {
            stm.close();
            link.close();
        }
    }

}
