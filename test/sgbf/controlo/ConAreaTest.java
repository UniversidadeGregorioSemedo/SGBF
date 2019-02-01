/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.controlo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import sgbf.modelo.ModArea;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilIconesDaJOPtionPane;

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
        ConArea areaCon = new ConArea();
        
        areaMod.setSector("Sector de engenharia", operacao);
        
        if(areaCon.registar(areaMod, operacao)){
            throw new UtilControloExcessao("Área registada com sucesso !", operacao, UtilIconesDaJOPtionPane.Confirmacao.nomeDaImagem());
        }else{
            throw new UtilControloExcessao("Erro ao registar Área !", operacao, UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
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
