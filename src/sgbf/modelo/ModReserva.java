/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.modelo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import org.joda.time.DateTime;
import sgbf.util.UtilControloDaData;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilUsuarioLogado;

/**
 *
 * @author Look
 */
public class ModReserva {
    
    private Integer idReserva;
    private String estado;
    private DateTime data_vencimento;
    private Byte dias_remanescente;
    private ModVisitante utenteMod;
    private ModFuncionario funcionarioMod;
    private List<ModItemSolicitado> itensRegistados;
    private UtilControloDaData utilControloDaData;
  
    public ModReserva() {
        this.estado = null;
        this.data_vencimento = null;
        this.idReserva = 0;
        this.dias_remanescente = 3;
        this.utenteMod = null;
        this.funcionarioMod = UtilUsuarioLogado.getUsuarioLogado();
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

    public DateTime getData_vencimento() {
        return data_vencimento;
    }

    public void setData_vencimento(DateTime data_vencimento, String operacao) {
        this.data_vencimento = data_vencimento;
    }
    
    public Timestamp getDataVencimento(Byte dias, String operacao){
        UtilControloDaData data = new UtilControloDaData();
        DateTime dataDeVecimento = data.dataActual().plusDays(dias);
        return UtilControloDaData.DataTimeParaTimeStamp(dataDeVecimento);
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

    public ModFuncionario getFuncionarioMod() {
        return funcionarioMod;
    }

    public void setFuncionarioMod(ModFuncionario funcionarioMod, String operacao) {
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
        this.itensRegistados.add(itensRegistados);
    }
    
    public UtilControloDaData getUtilControloDaData() {
        return utilControloDaData;
    }

}
