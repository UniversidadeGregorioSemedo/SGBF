/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.dao;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

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
        DaoCominhoDaBaseDados dadosBaseDaCaminhoDao = new DaoCominhoDaBaseDados();
        try {
            if(dadosBaseDaCaminhoDao.baseDeDados("Iniciar").isValid(0)){
                System.out.println("Deu certo !");
            }else{
                System.out.println("Não deu certo ");
            }
        } catch (SQLException ex) {
            System.out.println("Ao se conectar com a Base de dados !\nErro: "+ex.getMessage());
        }
                
    }

    @Test
    public void testFecharTodasConexoes() {
    }
    
}
