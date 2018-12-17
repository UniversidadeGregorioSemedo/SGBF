/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.modelo;

import sgbf.util.UtilControloDaData;

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
    private String data_emprestimo;
    private String data_vencimento;
    private ModUtente utenteMod;

    public ModEmprestimo() {
        this.idEmprestimo = 0;
        this.reservaMod = new ModReserva();
        this.estado = null;
        this.multa = 0.0;
        this.dias_atrazo = 0;
        this.data_emprestimo = String.valueOf(UtilControloDaData.dataActual());
        this.data_vencimento = null;
    }

    public Integer getIdEmprestimo() {
        return idEmprestimo;
    }

    public void setIdEmprestimo(Integer idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public ModReserva getReservaMod() {
        return reservaMod;
    }

    public void setReservaMod(ModReserva reservaMod) {
        this.reservaMod = reservaMod;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getMulta() {
        return multa;
    }

    public void setMulta(Double multa) {
        this.multa = multa;
    }

    public Integer getDias_atrazo() {
        return dias_atrazo;
    }

    public void setDias_atrazo(Integer dias_atrazo) {
        this.dias_atrazo = dias_atrazo;
    }

    public String getData_emprestimo() {
        return data_emprestimo;
    }

    public void setData_emprestimo(String data_emprestimo) {
        this.data_emprestimo = data_emprestimo;
    }

    public String getData_vencimento() {
        return data_vencimento;
    }

    public void setData_vencimento(String data_vencimento) {
        this.data_vencimento = data_vencimento;
    }

    public ModUtente getUtenteMod() {
        return utenteMod;
    }

    public void setUtenteMod(ModUtente utenteMod) {
        this.utenteMod = utenteMod;
    }
    
    
    
}
