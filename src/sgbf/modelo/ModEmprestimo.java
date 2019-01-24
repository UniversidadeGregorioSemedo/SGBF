/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.modelo;

import java.sql.Timestamp;
import javafx.scene.control.Alert;
import org.joda.time.DateTime;
import sgbf.util.UtilControloDaData;
import sgbf.util.UtilControloExcessao;

/**
 *
 * @author Look
 */
public class ModEmprestimo {
    
    private Integer idEmprestimo;
    private ModReserva reservaMod;
    private String estado;
    private Double multa;
    private Integer dias_atrazo;
    private DateTime data_vencimento;
    private final Byte diasEmprestimo = 3; 
    private ModFuncionario funcionarioMod;
    private UtilControloDaData utilControloDaData;
    
    public ModEmprestimo() {
        this.idEmprestimo = 0;
        this.estado = null;
        this.multa = 0.0;
        this.dias_atrazo = 0;
        this.data_vencimento = null;
        this.reservaMod = new ModReserva();
        this.funcionarioMod = new ModFuncionario();
        this.utilControloDaData = new UtilControloDaData();
    }

    public Integer getIdEmprestimo() {
        return idEmprestimo;
    }

    public void setIdEmprestimo(Integer idEmprestimo, String operacao) {
        this.idEmprestimo = idEmprestimo;
    }

    public ModReserva getReservaMod() {
        return reservaMod;
    }

    public void setReservaMod(ModReserva reservaMod, String operacao) {
        if(reservaMod == null){
            throw new UtilControloExcessao(operacao, "Erro ao verificar reserva", Alert.AlertType.WARNING);
        }else{
            this.reservaMod = reservaMod;
        }
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado, String operacao) {
        this.estado = estado;
    }

    public Double getMulta() {
        return multa;
    }

    public void setMulta(Double multa, String operacao) {
        this.multa = multa;
    }

    public Integer getDias_atrazo() {
        return dias_atrazo;
    }

    public void setDias_atrazo(Integer dias_atrazo, String operacao) {
        this.dias_atrazo = dias_atrazo;
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

    public Byte getDiasEmprestimo() {
        return diasEmprestimo;
    }
    
    public UtilControloDaData getUtilControloDaData() {
        return utilControloDaData;
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

}
