/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package sgbf.controlo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import sgbf.modelo.ModAcervo;
import sgbf.modelo.ModEstoque;
import sgbf.modelo.ModItemProveniente;
import sgbf.modelo.ModItemSolicitado;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilIconesDaJOPtionPane;

/**
 *
 * @author Look
 */
public class ConEstoque extends ConCRUD {

    @Override
    public boolean alterar(Object objecto_alterar, String operacao) {
        ModEstoque estoqueMod = (ModEstoque) objecto_alterar;
        try {
            super.query = "update tcc.estoque quantidade_total=?, quantidade_em_falta=?, quantidade_acervos_emprestados=?,"
                    + " quantidade_acervos_reservados=? where idEstoque=?";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setInt(1, estoqueMod.getQuantidade_total());
            super.preparedStatement.setInt(2, estoqueMod.getQuantidade_em_falta());
            super.preparedStatement.setInt(3, estoqueMod.getQuantidade_acervos_emprestados());
            super.preparedStatement.setInt(4, estoqueMod.getQuantidade_acervos_resercados());
            super.preparedStatement.setInt(5, estoqueMod.getQuantidade_acervos_resercados());
            return !super.preparedStatement.execute();
        } catch (SQLException erro) {
            throw new UtilControloExcessao("Erro ao " + operacao + " Estoque !\nErro: " + erro.getMessage(), operacao, UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        } finally {
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }

    @Override
    public boolean remover(Object objecto_remover, String operacao) {
        ModAcervo acervoMod = (ModAcervo) objecto_remover;
        ModItemProveniente itemProvenienteMod = new ModItemProveniente();
        ContItemProveniente itemProvenienteCon = new ContItemProveniente();
        itemProvenienteMod.setAcervoMod(acervoMod, operacao);
        try {
            if (itemProvenienteCon.remover(itemProvenienteMod, operacao)) {
                super.query = "delete from estoque where Acervos_idAcervos=?";
                super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(super.query);
                super.preparedStatement.setInt(1, acervoMod.getIdAcervo());
                return !super.preparedStatement.execute();
            } else {
                throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " Estoque !", Alert.AlertType.ERROR);
            }
        } catch (SQLException erro) {
            throw new UtilControloExcessao("Erro ao " + operacao + " Estante !\nErro: " + erro.getMessage(), operacao, UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }
    }

    public boolean descontarAcervoReservadoNoEstoque(ModItemSolicitado itemSolicitadoMod, String operacao) {
        try {
            if (this.temQuantidadeSuficiente(itemSolicitadoMod, operacao)) {
                super.query = "call pr_descontarAcervoRegistadoNaReserva(?,?)";
                super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
                super.preparedStatement.setInt(1, itemSolicitadoMod.getAcervoMod().getEstoqueMod().getIdEstoque());
                super.preparedStatement.setInt(2, itemSolicitadoMod.getQuantidade_revervada());
                return !super.preparedStatement.execute();
            } else {
                throw new UtilControloExcessao(operacao, "Quantidade inválida", Alert.AlertType.ERROR);
            }
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    public boolean devolverAcervoReservadoNoEstoque(ModItemSolicitado itemSolicitadoMod, String operacao) {
        try {
            super.query = "call pr_devolverAcervoReservadosNoEstoque(?,?)";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setInt(1, itemSolicitadoMod.getAcervoMod().getEstoqueMod().getIdEstoque());
            super.preparedStatement.setInt(2, itemSolicitadoMod.getQuantidade_revervada());
            return !super.preparedStatement.execute();
        } catch (SQLException erro) {
            throw new UtilControloExcessao( operacao,"Erro ao " + operacao + " !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    public boolean actualizarEstoqueDeEmprestimo(List<ModItemSolicitado> todosItensSolicitado, String operacao) {
        try {
            for(ModItemSolicitado itemSolicitadoMod: todosItensSolicitado){
                super.query = "call pr_emprestarAcervos(?,?)";
                super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
                super.preparedStatement.setInt(1, itemSolicitadoMod.getAcervoMod().getEstoqueMod().getIdEstoque());
                super.preparedStatement.setInt(2, itemSolicitadoMod.getQuantidade_revervada());
                super.preparedStatement.execute();
            }
            return true;
        } catch (SQLException erro) {
            throw new UtilControloExcessao( operacao,"Erro ao " + operacao + " !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    private boolean temQuantidadeSuficiente(ModItemSolicitado itemSolicitadoMod, String operacao) {
        ConAcervo acervoCon = new ConAcervo();
        if (acervoCon.pesquisar(itemSolicitadoMod.getAcervoMod(), operacao).isEmpty()) {
            throw new UtilControloExcessao(operacao, "O Acervo Seleccionado não existe", Alert.AlertType.WARNING);
        } else {
            for (Object todosAcervos : acervoCon.pesquisar(itemSolicitadoMod.getAcervoMod(), operacao)) {
                ModAcervo acervoMod = (ModAcervo) todosAcervos;
                if (acervoMod.getIdAcervo() == itemSolicitadoMod.getAcervoMod().getIdAcervo()) {
                    return Integer.valueOf(itemSolicitadoMod.getQuantidade_revervada()) < acervoMod.getEstoqueMod().getQuantidadeRemanescente();
                }
            }
            return false;
        }
    }

    @Override
    public boolean registar(Object objecto_registar, String operacao) {
        ModEstoque estoqueMod = (ModEstoque) objecto_registar;
        throw new UtilControloExcessao( operacao,"Operação indisponível no momento ! ",Alert.AlertType.WARNING);
    }
    
    @Override
    public List<Object> listarTodos(String operacao) {
        List<Object> todosRegistos = new ArrayList<>();
        throw new UtilControloExcessao( operacao,"Operação indisponível no momento ! ",Alert.AlertType.WARNING);
    }

    @Override
    public List<Object> pesquisar(Object objecto_pesquisar, String operacao) {
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        throw new UtilControloExcessao( operacao,"Operação indisponível no momento ! ",Alert.AlertType.WARNING);
    }
    
}
