/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.controlo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import sgbf.dao.DaoCominhoDaBaseDados;

/**
 *
 * @author Look
 */
public abstract class ConCRUD {
    protected DaoCominhoDaBaseDados caminhoDaBaseDados = null;
    protected PreparedStatement preparedStatement = null;
    protected ResultSet setResultset = null;
    protected String query = null;
    
    public abstract boolean registar(Object objecto_registar, String operacao);
    public abstract boolean alterar(Object objecto_alterar, String operacao);
    public abstract boolean remover(Object objecto_remover, String operacao);
    public abstract List<Object> listarTodos(String operacao);
    public abstract List<Object> pesquisar(Object objecto_pesquisar, String operacao);
    
}
