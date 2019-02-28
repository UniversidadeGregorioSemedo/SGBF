/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.modelo;

import javafx.scene.control.Alert;
import sgbf.util.UtilControloDaData;
import sgbf.util.UtilControloExcessao;

/**
 *
 * @author Look
 */
public class ModProveniencia {

    private Integer idProveniencia;
    private String tipo;
    private String data_registo;
    private String data_modificacao;
    private UtilControloDaData utilControloDaData;

    public ModProveniencia() {
        this.idProveniencia = 0;
        this.tipo = null;
        this.utilControloDaData = new UtilControloDaData();
    }

    public Integer getIdProveniencia() {
        return idProveniencia;
    }

    public void setIdProveniencia(Integer idProveniencia, String operacao) {
        this.idProveniencia = idProveniencia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo, String operacao) {
        if(tipo == null){
            throw new UtilControloExcessao(operacao, "Seleccione o tipo de proveniência", Alert.AlertType.WARNING);
        }else{
            if(tipo.isEmpty()){
                throw new UtilControloExcessao(operacao, "Seleccione o tipo de proveniência", Alert.AlertType.WARNING);
            }else{
                this.tipo = tipo;
            }
        }
    }

    @Override
    public String toString() {
        return tipo;
    }

    public UtilControloDaData getUtilControloDaData() {
        return utilControloDaData;
    }

    public void equals(ModProveniencia provenienciaMod, String operacao) {
        if (this.idProveniencia != provenienciaMod.getIdProveniencia()) {
            if (this.tipo.equalsIgnoreCase(provenienciaMod.getTipo())) {
                throw new UtilControloExcessao(operacao, "A proveniência introduzida já existe !", Alert.AlertType.WARNING);
            }
        }
    }

}
