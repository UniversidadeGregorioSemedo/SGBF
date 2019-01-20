/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.modelo;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import sgbf.util.UtilControloDaData;
import sgbf.util.UtilControloExcessao;

/**
 *
 * @author Look
 */
public class ModReserva {
    
    private String estado;
    private Integer idReserva;
    private Byte dias_remanescente;
    private ModVisitante utenteMod;
    private ModVisitante funcionarioMod;
    private List<ModItemSolicitado> itensRegistados;
    private UtilControloDaData utilControloDaData;
  

    public ModReserva() {
        this.estado = null;
        this.idReserva = 0;
        this.dias_remanescente = 0;
        this.utenteMod = null;
        this.funcionarioMod = null;
        this.itensRegistados = new ArrayList<>();
        this.utilControloDaData = new UtilControloDaData();
    }

    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva, String operacao) {
        this.idReserva = idReserva;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado, String operacao) {
        this.estado = estado;
    }

    public Byte getDias_remanescente() {
        return dias_remanescente;
    }

    public void setDias_remanescente(Byte dias_remanescente, String operacao) {
        this.dias_remanescente = dias_remanescente;
    }

    public ModUtente getUtenteMod() {
        return utenteMod;
    }

    public void setUtenteMod(ModVisitante utenteMod, String operacao) {
        if(utenteMod == null){
            throw new UtilControloExcessao(operacao, "Seleccione o Utente", Alert.AlertType.ERROR);
        }else{
            this.utenteMod = utenteMod;
        }
    }

    public ModVisitante getFuncionarioMod() {
        return funcionarioMod;
    }

    public void setFuncionarioMod(ModVisitante funcionarioMod, String operacao) {
        if(funcionarioMod == null){
            throw new UtilControloExcessao(operacao, "Funcionário não identificado", Alert.AlertType.ERROR);
        }else{
            this.funcionarioMod = funcionarioMod;
        }
    }

    public List<ModItemSolicitado> getItensRegistados() {
        return itensRegistados;
    }

    public void adionarItemItensRegistados(ModItemSolicitado itensRegistados) {
        System.out.println("Item: "+itensRegistados);
        this.itensRegistados.add(itensRegistados);
    }
    
    public UtilControloDaData getUtilControloDaData() {
        return utilControloDaData;
    }

}
