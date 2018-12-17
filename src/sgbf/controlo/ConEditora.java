/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.controlo;

import java.sql.SQLException;
import java.util.List;
import sgbf.modelo.ModEditora;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilIconesDaJOPtionPane;

/**
 *
 * @author Look
 */
public class ConEditora extends ConCRUD{

    @Override
    public boolean registar(Object objecto_registar, String operacao) {
        try{
            ModEditora editoraMod = (ModEditora)objecto_registar;
            if(this.jaExisteEssaEditora(editoraMod, operacao)){
                throw new UtilControloExcessao("Erro ao verificar dados da Editora !", operacao,UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
            }else{
                super.query = "INSERT INTO tcc.Editora (nome, contacto, email, fax, endereco, data_registo, data_modificacao)"
                            + " VALUES (?, ?, ?, ?, ?, ?, ?)";
                super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
                super.preparedStatement.setString(1, editoraMod.getNome());
                super.preparedStatement.setString(2, editoraMod.getContacto());
                super.preparedStatement.setString(3, editoraMod.getEmail());
                super.preparedStatement.setString(4, editoraMod.getFax());
                super.preparedStatement.setString(5, editoraMod.getEndereco());
                super.preparedStatement.setString(6, editoraMod.getData_registo());
                super.preparedStatement.setString(7, editoraMod.getData_modificacao());
                return !super.preparedStatement.execute();
            }
        }catch(SQLException erro){
            throw new UtilControloExcessao("Erro ao "+operacao+" Editora !\nErro: "+erro.getMessage(), operacao,UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }finally{
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
     
    }

    @Override
    public boolean alterar(Object objecto_alterar, String operacao) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean remover(Object objecto_remover, String operacao) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object> listarTodos(String operacao) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object> pesquisar(Object objecto_pesquisar, String operacao) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private boolean jaExisteEssaEditora(ModEditora editoraMod, String operacao){
        for(Object todosRegistos: this.listarTodos(operacao)){
            ModEditora editoraRegistada = (ModEditora)todosRegistos;
            editoraRegistada.equals(editoraMod, operacao);
        }
        return false;
    }
    
}
