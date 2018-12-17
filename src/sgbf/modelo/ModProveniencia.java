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
public class ModProveniencia {
   
    private Integer idProveniencia;
    private String tipo;
    private String data_registo;
    private String data_modificacao;

    public ModProveniencia() {
        this.idProveniencia = 0;
        this.tipo = null;
        this.data_registo = String.valueOf(UtilControloDaData.dataActual());
        this.data_modificacao = String.valueOf(UtilControloDaData.dataActual());
    }

    public Integer getIdProveniencia() {
        return idProveniencia;
    }

    public void setIdProveniencia(Integer idProveniencia) {
        this.idProveniencia = idProveniencia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
    
    enum Tipo{
        DOACAO("Doação"),COMPRA("Compra"), OUTROS("Outros");
        private String tipo;
        
        private Tipo(String tipo){
            this.tipo = tipo;
        }
        
        private String getTipo(){
            return this.tipo;
        }
    }
    
}
