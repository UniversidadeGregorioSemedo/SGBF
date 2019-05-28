/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.controlo;

import sgbf.dao.DaoArea;
import javafx.scene.control.Alert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import sgbf.modelo.ModArea;
import sgbf.util.UtilControloExcessao;

/**
 *
 * @author Look
 */
public class ConAreaTest {
    
    public ConAreaTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testRegistar() {
        final String operacao = "Registar Área";
        ModArea areaMod = new ModArea();
        DaoArea areaCon = new DaoArea();
        
        areaMod.setSector("Sector de engenharia", operacao);
        
        if(areaCon.registar(areaMod, operacao)){
            throw new UtilControloExcessao(operacao, "Área registada com sucesso !", Alert.AlertType.CONFIRMATION);
        }else{
            throw new UtilControloExcessao(operacao,"Erro ao registar Área !", Alert.AlertType.ERROR);
        }
    }

    @Test
    @Ignore
    public void testAlterar() {
    }

    @Test
    @Ignore
    public void testRemover() {
    }

    @Test
    @Ignore
    public void testListarTodos() {
    }

    @Test
    @Ignore
    public void testPesquisar() {
    }
    
}
