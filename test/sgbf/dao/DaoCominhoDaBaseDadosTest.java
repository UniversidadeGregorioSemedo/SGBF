/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.dao;

import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilIconesDaJOPtionPane;

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
            if(conectarA.baseDeDados(operacao).isValid(0)){
                throw new UtilControloExcessao("Conexão com o servidor establecido com sucesso !", operacao, UtilIconesDaJOPtionPane.Confirmacao.nomeDaImagem());
            }else{
                throw new UtilControloExcessao("Erro ao se conectar a base de dados !", operacao, UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
            }
        } catch (SQLException ex) {
            throw new UtilControloExcessao("Erro: "+ex.getMessage(), operacao, UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }
    }

    @Test
    public void testFecharTodasConexoes() {
    }
    
}
