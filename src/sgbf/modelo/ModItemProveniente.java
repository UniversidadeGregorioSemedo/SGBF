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
public class ModItemProveniente {
    
    
    private ModAcervo acervoMod;
    private ModProveniencia provenienciaMod;
    private Short quantidade_entrada;
    private Double custo_unitario;
    private Double subtotaltotal;
    private String data_registo;
    private String data_modificacao;
  

    public ModItemProveniente() {
        this.quantidade_entrada = 0;
        this.custo_unitario = 0.0;
        this.subtotaltotal = 0.0;
        this.acervoMod = new ModAcervo();
        this.provenienciaMod = new ModProveniencia();
        this.data_registo = String.valueOf(UtilControloDaData.dataActual());
        this.data_modificacao = String.valueOf(UtilControloDaData.dataActual());
       
    }

    
    public Short getQuantidade_entrada() {
        return quantidade_entrada;
    }

    public void setQuantidade_entrada(Short quantidade_entrada, String operacao) {
        if(quantidade_entrada <= 0){
            throw new UtilControloExcessao(operacao, "A quantidade introduzida não é válida", Alert.AlertType.ERROR);
        }else{
            this.quantidade_entrada = quantidade_entrada;
        }
    }

    public Double getCusto_unitario() {
        return custo_unitario;
    }

    public void setCusto_unitario(Double custo_unitario, String operacao) {
        this.custo_unitario = custo_unitario;
    }

    public Double getSubTotal() {
        return subtotaltotal;
    }

    public void setSubTotal(Double subtotaltotal, String operacao) {
        if(this.provenienciaMod.getTipo() == null){
            throw new UtilControloExcessao(operacao, "Introduza o tipo de proveniência", Alert.AlertType.WARNING);
        }else{
            if(this.provenienciaMod.getTipo().equalsIgnoreCase("Compra")){
                if(subtotaltotal <= 0){
                    throw new UtilControloExcessao(operacao, "Introduza o subTotal", Alert.AlertType.WARNING);
                }else{
                    this.subtotaltotal = subtotaltotal;
                }
            }else{
                this.subtotaltotal = subtotaltotal;
            }
        }
    }

    public ModAcervo getAcervoMod() {
        return acervoMod;
    }

    public void setAcervoMod(ModAcervo acervoMod, String operacao) {
        if(acervoMod == null){
            throw new UtilControloExcessao(operacao, "Seleccione o Acervo", Alert.AlertType.WARNING);
        }else{
            if(acervoMod.getIdAcervo() == 0){
                throw new UtilControloExcessao(operacao, "Dados do acervo inconsistente", Alert.AlertType.WARNING);
            }else{
                this.acervoMod = acervoMod;
            }
        }
    }

    public ModProveniencia getProvenienciaMod() {
        return provenienciaMod;
    }

    public void setProvenienciaMod(ModProveniencia provenienciaMod, String operacao) {
        if(provenienciaMod == null){
            throw new UtilControloExcessao(operacao, "Seleccione a Proveniência", Alert.AlertType.WARNING);
        }else{
            if(provenienciaMod.getIdProveniencia()== 0){
                throw new UtilControloExcessao(operacao, "Selccione uma proveniência válida", Alert.AlertType.WARNING);
            }else{
                this.provenienciaMod = provenienciaMod;
            }
        }
    }

    public String getData_registo() {
        return data_registo;
    }

    public void setData_registo(String data_registo, String operacao) {
        this.data_registo = data_registo;
    }

    public String getData_modificacao() {
        return data_modificacao;
    }

    public void setData_modificacao(String data_modificacao, String operacao) {
        this.data_modificacao = data_modificacao;
    }

    
}
