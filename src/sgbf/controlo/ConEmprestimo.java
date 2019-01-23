/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.controlo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import org.joda.time.DateTime;
import sgbf.modelo.ModEmprestimo;
import sgbf.modelo.ModEstante;
import sgbf.util.UtilControloDaData;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilIconesDaJOPtionPane;

/**
 *
 * @author Look
 */
public class ConEmprestimo extends ConCRUD {

    public ConEmprestimo() {
        this.actualizarDiasDeAtrazo();
    }
    
    @Override
    public boolean registar(Object objecto_registar, String operacao) {
        ModEmprestimo emprestimoteMod = (ModEmprestimo) objecto_registar;
        try {
            super.query = "INSERT INTO tcc.emprestimo (data_vencimento, Funcionario_idFuncionario, Reserva_idReserva)"
                        + " VALUES (?, ?, ?)";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setTimestamp(1, emprestimoteMod.getDataVencimento(emprestimoteMod.getDiasEmprestimo(), operacao));
            super.preparedStatement.setInt(2, emprestimoteMod.getFuncionarioMod().getIdFuncionario());
            super.preparedStatement.setInt(3, emprestimoteMod.getReservaMod().getIdReserva());
            return !super.preparedStatement.execute();
        } catch (SQLException erro) {
            throw new UtilControloExcessao( operacao, "Erro ao " + operacao + " \nErro: " + erro.getMessage(),Alert.AlertType.ERROR);
        } finally {
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }
    
    private boolean actualizarEmprestimo(ModEmprestimo emprestimoteMod, String operacao) {
        try {
            super.query = "update emprestimo set dias_atraso=? where idEmprestimo=?";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setInt(1, emprestimoteMod.getDias_atrazo());
            super.preparedStatement.setInt(2, emprestimoteMod.getIdEmprestimo());
            return !super.preparedStatement.execute();
        } catch (SQLException erro) {
            throw new UtilControloExcessao("Erro ao " + operacao + " Empréstimo !\nErro: " + erro.getMessage(), operacao, UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        } finally {
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }
    
    private void actualizarDiasDeAtrazo(){
        Integer diasDeAtraso = 0;
        DateTime dataActual = null;
        DateTime dataVencimento = null;
        final String operacao = "Actualizar Emprestimos";
        UtilControloDaData controloDaData = new UtilControloDaData();
        List<Object> todosEmprestimosActivas = this.temEmpresasActivas(operacao);
        if (!todosEmprestimosActivas.isEmpty()) {
            for (Object todEmprestimosActualizar : todosEmprestimosActivas) {
                ModEmprestimo emprestimoMod = (ModEmprestimo) todEmprestimosActualizar;
                dataVencimento = emprestimoMod.getData_vencimento();
                dataActual = controloDaData.dataActual();
                diasDeAtraso = controloDaData.numeroDeDiasEntreDuasDatas(dataVencimento, dataActual, operacao);
                emprestimoMod.setDias_atrazo(diasDeAtraso, operacao);
                this.actualizarEmprestimo(emprestimoMod, operacao);
            }
        }
    }

    
    private List<Object> temEmpresasActivas(String operacao) {
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        try {
            super.query = "select * from emprestimo where estado='Activo'";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.setResultset = super.preparedStatement.executeQuery();
            while (super.setResultset.next()) {
                todosRegistosEncontrados.add(this.pegarRegistos(super.setResultset, operacao));
            }
            return todosRegistosEncontrados;
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + "  !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }
    

    @Override
    public boolean alterar(Object objecto_alterar, String operacao) {
        ModEmprestimo emprestimoteMod = (ModEmprestimo) objecto_alterar;
        throw new UtilControloExcessao( operacao, "Operação indisponível no momento",Alert.AlertType.WARNING);
    }
    
    @Override
    public boolean remover(Object objecto_remover, String operacao) {
        ModEmprestimo emprestimoteMod = (ModEmprestimo) objecto_remover;
        throw new UtilControloExcessao( operacao, "Operação indisponível no momento",Alert.AlertType.WARNING);
    }

    @Override
    public List<Object> listarTodos(String operacao) {
        List<Object> todosRegistos = new ArrayList<>();
        /*
        try{
            super.query = "select * from tcc.Estante designacao by nome, data_modificacao asc";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.setResultset = super.preparedStatement.executeQuery();
            while(super.setResultset.next()){
                todosRegistos.add(this.pegarRegistos(super.setResultset,operacao));
            }
            return todosRegistos;
        }catch(SQLException erro){
            throw new UtilControloExcessao("Erro ao "+operacao+" Estante(s) !\nErro: "+erro.getMessage(), operacao, UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }finally{
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }*/
        return todosRegistos;
    }

    @Override
    public List<Object> pesquisar(Object objecto_pesquisar, String operacao) {
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        /*ModEstante estanteMod = (ModEstante)objecto_pesquisar;
        try{
            super.query = "select * from tcc.Estante where idEstante=? or "
                        + "designacao like '%"+estanteMod.getDesignacao()+"%'";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setInt(1, estanteMod.getIdEstante());
            super.setResultset  = super.preparedStatement.executeQuery();
            while(super.setResultset.next()){
                todosRegistosEncontrados.add(this.pegarRegistos(super.setResultset, operacao));
            }
            return todosRegistosEncontrados;
        }catch(SQLException erro){
            throw new UtilControloExcessao("Erro ao "+operacao+" Editora(s) !\nErro: "+erro.getMessage(), operacao,UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }finally{
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }*/
        return todosRegistosEncontrados;
    }

    private Object pegarRegistos(ResultSet setResult, String operacao) throws SQLException {
        ModEmprestimo emprestimoMod = new ModEmprestimo();
        emprestimoMod.setIdEmprestimo(setResult.getInt("idEmprestimo"), operacao);
        emprestimoMod.setEstado(setResult.getString("estado"), operacao);
        emprestimoMod.setDias_atrazo(setResult.getInt("dias_atraso"), operacao);
        emprestimoMod.setMulta(setResult.getDouble("multa"), operacao);
        emprestimoMod.getUtilControloDaData().setData_registo(setResult.getTimestamp("data_emprestimo"), operacao);
        emprestimoMod.setData_vencimento(UtilControloDaData.TimestampParaDatatime(setResult.getTimestamp("data_vencimento")), operacao);
        emprestimoMod.getFuncionarioMod().setIdFuncionario(setResult.getInt("Funcionario_idFuncionario"), operacao);
        emprestimoMod.getReservaMod().setIdReserva(setResult.getInt("Reserva_idReserva"), operacao);
        return emprestimoMod;
    }

}
