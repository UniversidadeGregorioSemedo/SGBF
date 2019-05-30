/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import org.joda.time.DateTime;
import sgbf.modelo.ModEmprestimo;
import sgbf.modelo.ModItemSolicitado;
import sgbf.modelo.ModReserva;
import sgbf.modelo.ModVisitante;
import sgbf.util.UtilControloDaData;
import sgbf.util.UtilControloExcessao;

/**
 *
 * @author Look
 */
public class DaoEmprestimo extends DaoCRUD {

    public DaoEmprestimo() {
        this.actualizarDiasDeAtrazo();
    }

    @Override
    public boolean registar(Object objecto_registar, String operacao) {
        ModEmprestimo emprestimoteMod = (ModEmprestimo) objecto_registar;
        try {
            if (this.actualizarEstoqueDoAcervo(emprestimoteMod.getReservaMod(), operacao)) {
                super.query = "INSERT INTO tcc.emprestimo (data_vencimento, Funcionario_idFuncionario, Reserva_idReserva)"
                        + " VALUES (?, ?, ?)";
                super.preparedStatement = super.caminhoDaBaseDados.conectarBaseDeDados().prepareStatement(query);
                super.preparedStatement.setTimestamp(1, emprestimoteMod.getDataVencimento(emprestimoteMod.getDiasEmprestimo(), operacao));
                super.preparedStatement.setInt(2, emprestimoteMod.getFuncionarioMod().getIdFuncionario());
                super.preparedStatement.setInt(3, emprestimoteMod.getReservaMod().getIdReserva());
                return !super.preparedStatement.execute();
            } else {
                throw new UtilControloExcessao(operacao, "Erro ao actualizar Estoque do acervo", Alert.AlertType.ERROR);
            }
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " \nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        } finally {
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset);
        }
    }

    @Override
    public boolean alterar(Object objecto_alterar, String operacao) {
        ModEmprestimo emprestimoteMod = (ModEmprestimo) objecto_alterar;
        try {
            super.query = "update emprestimo set estado=? where idEmprestimo=?";
            super.preparedStatement = super.caminhoDaBaseDados.conectarBaseDeDados().prepareStatement(query);
            super.preparedStatement.setString(1, emprestimoteMod.getEstado());
            super.preparedStatement.setInt(2, emprestimoteMod.getIdEmprestimo());
            return !super.preparedStatement.execute();
        } catch (SQLException erro) {
            throw new UtilControloExcessao( operacao,"Erro ao " + operacao + " Empréstimo !\nErro: " + erro.getMessage(),Alert.AlertType.ERROR);
        }
    }
    
    @Override
    public List<Object> pesquisar(Object objecto_pesquisar, String operacao) {
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        ModVisitante visitanteMod = (ModVisitante) objecto_pesquisar;
        try {
            super.query = "select * from view_emprestimosDoUtente where idUtente=? order by estado";
            super.preparedStatement = super.caminhoDaBaseDados.conectarBaseDeDados().prepareStatement(query);
            super.preparedStatement.setInt(1, visitanteMod.getIdUtente());
            super.setResultset = super.preparedStatement.executeQuery();
            while (super.setResultset.next()) {
                todosRegistosEncontrados.add(this.pegarRegistosComUtente(super.setResultset, operacao));
            }
            return todosRegistosEncontrados;
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " \nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        } finally {
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset);
        }
    }

    private void actualizarDiasDeAtrazo() {
        Integer diasDeAtraso = 0;
        DateTime dataActual = null;
        DateTime dataVencimento = null;
        final String operacao = "Verificar Emprestimos";
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
            super.preparedStatement = super.caminhoDaBaseDados.conectarBaseDeDados().prepareStatement(query);
            super.setResultset = super.preparedStatement.executeQuery();
            while (super.setResultset.next()) {
                todosRegistosEncontrados.add(this.pegarRegistos(super.setResultset, operacao));
            }
            return todosRegistosEncontrados;
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + "  !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
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

    private Object pegarRegistosComUtente(ResultSet setResult, String operacao) throws SQLException {
        ModEmprestimo emprestimoMod = new ModEmprestimo();
        emprestimoMod.setIdEmprestimo(setResult.getInt("idEmprestimo"), operacao);
        emprestimoMod.setEstado(setResult.getString("estado"), operacao);
        emprestimoMod.setDias_atrazo(setResult.getInt("dias_atraso"), operacao);
        emprestimoMod.setMulta(setResult.getDouble("multa"), operacao);
        emprestimoMod.getUtilControloDaData().setData_registo(setResult.getTimestamp("data_emprestimo"), operacao);
        emprestimoMod.setData_vencimento(UtilControloDaData.TimestampParaDatatime(setResult.getTimestamp("data_vencimento")), operacao);
        emprestimoMod.getFuncionarioMod().setIdFuncionario(setResult.getInt("Funcionario_idFuncionario"), operacao);
        emprestimoMod.getReservaMod().setIdReserva(setResult.getInt("Reserva_idReserva"), operacao);
        emprestimoMod.getReservaMod().setUtenteMod(new ModVisitante(), operacao); //Limpar dados do Utentes
        emprestimoMod.getReservaMod().getUtenteMod().setIdUtente(setResult.getInt("idUtente"), operacao);
        return emprestimoMod;
    }

    private boolean actualizarEmprestimo(ModEmprestimo emprestimoteMod, String operacao) {
        try {
            super.query = "update emprestimo set dias_atraso=? where idEmprestimo=?";
            super.preparedStatement = super.caminhoDaBaseDados.conectarBaseDeDados().prepareStatement(query);
            super.preparedStatement.setInt(1, emprestimoteMod.getDias_atrazo());
            super.preparedStatement.setInt(2, emprestimoteMod.getIdEmprestimo());
            return !super.preparedStatement.execute();
        } catch (SQLException erro) {
            throw new UtilControloExcessao( operacao,"Erro ao " + operacao + " Empréstimo !\nErro: " + erro.getMessage(),Alert.AlertType.ERROR);
        } finally {
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset);
        }
    }

    private boolean actualizarEstoqueDoAcervo(ModReserva reservaMod, String operacao) {
        DaoItemSolicitado itemSolicitadoCon = new DaoItemSolicitado();
        DaoEstoque estoqueCon = new DaoEstoque();
        for (Object todosRegisto : itemSolicitadoCon.pesquisar(reservaMod, operacao)) {
            ModItemSolicitado itensEncontrados = (ModItemSolicitado) todosRegisto;
            reservaMod.adionarItemItensRegistados(itensEncontrados);
        }
        return estoqueCon.actualizarEstoqueDeEmprestimo(reservaMod.getItensRegistados(), operacao);
    }
    
    public void passarEstadoParaInactivo(ModEmprestimo emprestimoteMod, String operacao) {
        emprestimoteMod.setEstado("Inactivo", operacao);
        this.alterar(emprestimoteMod, operacao);
    }

    

    @Override
    public boolean remover(Object objecto_remover, String operacao) {
        ModEmprestimo emprestimoteMod = (ModEmprestimo) objecto_remover;
        throw new UtilControloExcessao(operacao, "Operação indisponível no momento", Alert.AlertType.WARNING);
    }

    @Override
    public List<Object> listarTodos(String operacao) {
        List<Object> todosRegistos = new ArrayList<>();
        throw new UtilControloExcessao(operacao, "Operação indisponível no momento", Alert.AlertType.WARNING);
    }

}
