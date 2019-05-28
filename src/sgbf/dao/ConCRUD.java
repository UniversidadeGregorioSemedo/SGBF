
package sgbf.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import sgbf.dao.DaoCominhoDaBaseDados;

/**
 *
 * @author Look
 */
public abstract class ConCRUD {
    final protected DaoCominhoDaBaseDados caminhoDaBaseDados = new DaoCominhoDaBaseDados();
    protected PreparedStatement preparedStatement = null;
    protected ResultSet setResultset = null;
    protected String query = null;
    
    public abstract boolean registar(Object objecto_registar, String operacao);
    public abstract boolean alterar(Object objecto_alterar, String operacao);
    public abstract boolean remover(Object objecto_remover, String operacao);
    public abstract List<Object> listarTodos(String operacao);
    public abstract List<Object> pesquisar(Object objecto_pesquisar, String operacao);
   
}
