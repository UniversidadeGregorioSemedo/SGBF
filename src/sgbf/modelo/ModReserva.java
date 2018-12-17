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
public class ModReserva {
    
    private Integer idReserva;
    private String estado;
    private Byte dias_remanescente;
    private ModUtente utenteMod;
    private ModFuncionario funcionarioMod;
    private String data_registo;
    private String data_modificacao;

    public ModReserva() {
        this.idReserva = 0;
        this.estado = null;
        this.dias_remanescente = 0;
        this.utenteMod = null;
        this.funcionarioMod = new ModFuncionario();
        this.data_registo = String.valueOf(UtilControloDaData.dataActual());
        this.data_modificacao = String.valueOf(UtilControloDaData.dataActual());
    }

    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Byte getDias_remanescente() {
        return dias_remanescente;
    }

    public void setDias_remanescente(Byte dias_remanescente) {
        this.dias_remanescente = dias_remanescente;
    }

    public ModUtente getUtenteMod() {
        return utenteMod;
    }

    public void setUtenteMod(ModUtente utenteMod) {
        this.utenteMod = utenteMod;
    }

    public ModFuncionario getFuncionarioMod() {
        return funcionarioMod;
    }

    public void setFuncionarioMod(ModFuncionario funcionarioMod) {
        this.funcionarioMod = funcionarioMod;
    }

    public String getData_registo() {
        return data_registo;
    }

    public void setData_registo(String data_registo) {
        this.data_registo = data_registo;
    }

    public String getData_modificacao() {
        return data_modificacao;
    }

    public void setData_modificacao(String data_modificacao) {
        this.data_modificacao = data_modificacao;
    }
    
    enum Estado{
        ACTIVO("Activo"),INACTIVO("Inactivo");
        private String estado;
        
        private Estado(String estado){
            this.estado = estado;
        }
        
        private String getEstado(){
            return this.estado;
        }
    }
    
}
