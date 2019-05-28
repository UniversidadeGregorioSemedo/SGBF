/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.dao;

import java.sql.SQLException;
import javafx.scene.control.Alert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sgbf.util.UtilControloExcessao;

/**
 *
 * @author Look
 */
public class DaoCominhoDaBaseDadosTest {

    public DaoCominhoDaBaseDadosTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testBaseDeDados() {
        final String operacao = "Conectando ao servidor de BD...";
        DaoCominhoDaBaseDados conectarA = new DaoCominhoDaBaseDados();
        try {
            if (conectarA.baseDeDados(operacao).isValid(0)) {
                throw new UtilControloExcessao(operacao, "Conexão com o servidor establecido com sucesso !", Alert.AlertType.CONFIRMATION);
            } else {
                throw new UtilControloExcessao(operacao, "Erro ao se conectar a base de dados !", Alert.AlertType.ERROR);
            }
        } catch (SQLException ex) {
            throw new UtilControloExcessao(operacao, "Erro: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Test
    public void testFecharTodasConexoes() {
    }

}
