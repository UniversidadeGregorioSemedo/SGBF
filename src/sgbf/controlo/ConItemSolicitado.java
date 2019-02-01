/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.controlo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import sgbf.modelo.ModAcervo;
import sgbf.modelo.ModEstante;
import sgbf.modelo.ModItemSolicitado;
import sgbf.modelo.ModReserva;
import sgbf.util.UtilControloExcessao;

/**
 *
 * @author Look
 */
public class ConItemSolicitado extends ConCRUD {

    @Override
    public boolean registar(Object objecto_registar, String operacao) {
        ModReserva reservaMod = (ModReserva) objecto_registar;
        try {
            for (ModItemSolicitado itemSolicitado : reservaMod.getItensRegistados()) {
                if (this.existeRegistoDoProduto(reservaMod, itemSolicitado, operacao)) {
                    super.query = "update itenssolicitados set quantidade=quantidade+? where Acervos_idAcervos=? and Reserva_idReserva=?";
                    super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
                    super.preparedStatement.setByte(1, itemSolicitado.getQuantidade_revervada());
                    super.preparedStatement.setInt(2, itemSolicitado.getAcervoMod().getIdAcervo());
                    super.preparedStatement.setInt(3, reservaMod.getIdReserva());
                    super.preparedStatement.execute();
                } else {
                    super.query = "INSERT INTO tcc.itenssolicitados(Acervos_idAcervos, Reserva_idReserva, quantidade)"
                            + " VALUES (?, ?, ?)";
                    super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
                    super.preparedStatement.setInt(1, itemSolicitado.getAcervoMod().getIdAcervo());
                    super.preparedStatement.setInt(2, reservaMod.getIdReserva());
                    super.preparedStatement.setByte(3, itemSolicitado.getQuantidade_revervada());
                    super.preparedStatement.execute();
                }
            }
            return true;
        } catch (SQLException erro) {
            throw new UtilControloExcessao(operacao, "Erro ao " + operacao + " Reserva !\nErro: " + erro.getMessage(), Alert.AlertType.ERROR);
        } finally {
            super.caminhoDaBaseDados.fecharTodasConexoes(preparedStatement, setResultset, operacao);
        }
    }

    private boolean existeRegistoDoProduto(ModReserva reservaMod, ModItemSolicitado itemSolicitado, String operacao) {
        try{
            super.query = "select * from itenssolicitados where Acervos_idAcervos=? and Reserva_idReserva=?";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setInt(1, itemSolicitado.getAcervoMod().getIdAcervo());
            super.preparedStatement.setInt(2, reservaMod.getIdReserva());
            super.setResultset = super.preparedStatement.executeQuery();
            return super.setResultset.next();
        }catch(SQLException erro){
            throw new UtilControloExcessao(operacao, "Erro ao verificar dados Acervo" + erro.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    @Override
    public List<Object> pesquisar(Object objecto_pesquisar, String operacao) {
        List<Object> todosRegistosEncontrados = new ArrayList<>();
        ModReserva reservaMod = (ModReserva)objecto_pesquisar;
        try{
            super.query = "select * from view_itensReservados where idReserva=?";
            super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
            super.preparedStatement.setInt(1, reservaMod.getIdReserva());
            super.setResultset  = super.preparedStatement.executeQuery();
            while(super.setResultset.next()){
                todosRegistosEncontrados.add(this.pegarRegistos(super.setResultset, operacao));
            }
            return todosRegistosEncontrados;
        }catch(SQLException erro){
            throw new UtilControloExcessao(operacao,"Erro ao "+operacao+"  !\nErro: "+erro.getMessage(),Alert.AlertType.ERROR);
        }
    }

    @Override
    public boolean remover(Object objecto_remover, String operacao) {
        ModReserva reservaMod = (ModReserva)objecto_remover;
        try{
            for(ModItemSolicitado itemSolicitado: reservaMod.getItensRegistados()){
                super.query = "delete from itenssolicitados where Acervos_idAcervos=? and Reserva_idReserva=?";
                super.preparedStatement = super.caminhoDaBaseDados.baseDeDados(operacao).prepareStatement(query);
                super.preparedStatement.setInt(1, itemSolicitado.getAcervoMod().getIdAcervo());
                super.preparedStatement.setInt(2, reservaMod.getIdReserva());
                super.preparedStatement.execute();
            }
            return true;
        }catch(SQLException erro){
            throw new UtilControloExcessao(operacao,"Erro ao "+operacao+"  !\nErro: "+erro.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    private Object pegarRegistos(ResultSet setResultset, String operacao) throws SQLException {
        ConAcervo acervoCon = new ConAcervo();
        ModItemSolicitado  itemSolicitado = new ModItemSolicitado();
        itemSolicitado.getAcervoMod().setIdAcervo(setResultset.getInt("idAcervos"), operacao);
        
        for(Object todosAcervosRegistaddos: acervoCon.pesquisar(itemSolicitado.getAcervoMod(), operacao)){
            ModAcervo acervoMod = (ModAcervo)todosAcervosRegistaddos;
            itemSolicitado.setAcervoMod(acervoMod, operacao);
        }
        
        itemSolicitado.setQuantidade_revervada(setResultset.getByte("quantidade"), operacao);
        return itemSolicitado;
    }
    
    @Override
    public boolean alterar(Object objecto_alterar, String operacao) {
        ModItemSolicitado itemSolicitadoMod = (ModItemSolicitado) objecto_alterar;
        throw new UtilControloExcessao(operacao, "Operação indisponível no momento ", Alert.AlertType.ERROR);
    }


    @Override
    public List<Object> listarTodos(String operacao) {
        List<Object> todosRegistos = new ArrayList<>();
        throw new UtilControloExcessao(operacao, "Operação indisponível no momento ", Alert.AlertType.ERROR);
    }


    
    
}
