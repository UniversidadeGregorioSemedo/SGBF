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
import sgbf.modelo.ModEditora;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilIconesDaJOPtionPane;

/**
 *
 * @author Look
 */
public class ConEditoraTest {
    
    public ConEditoraTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testRegistar() {
        final String operacao = "Regitar Editora";
        ModEditora editoraMod = new ModEditora();
        ConEditora editoraCon = new ConEditora();
        editoraMod.setNome("Porto editora", operacao);
        editoraMod.setContacto("929298318", operacao);
        editoraMod.setEmail("lazarodjanilson@gmail.com", operacao);
        editoraMod.setFax("4839", operacao);
        editoraMod.setEndereco("Predna Lote 2", operacao);
    }

    @Test
    public void testAlterar() {
    }

    @Test
    public void testRemover() {
    }

    @Test
    public void testListarTodos() {
    }

    @Test
    public void testPesquisar() {
    }
    
}
