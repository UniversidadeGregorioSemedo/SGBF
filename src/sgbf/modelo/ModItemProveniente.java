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
public class ModItemProveniente {
    
    
    private ModEstoque estoqueMod;
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
        this.estoqueMod = new ModEstoque();
        this.provenienciaMod = new ModProveniencia();
        this.data_registo = String.valueOf(UtilControloDaData.dataActual());
        this.data_modificacao = String.valueOf(UtilControloDaData.dataActual());
       
    }

    
    public Short getQuantidade_entrada() {
        return quantidade_entrada;
    }

    public void setQuantidade_entrada(Short quantidade_entrada, String operacao) {
        this.quantidade_entrada = quantidade_entrada;
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
        this.subtotaltotal = subtotaltotal;
    }

    public ModEstoque getEstoqueMod() {
        return estoqueMod;
    }

    public void setEstoqueMod(ModEstoque estoqueMod, String operacao) {
        this.estoqueMod = estoqueMod;
    }

    public ModProveniencia getProvenienciaMod() {
        return provenienciaMod;
    }

    public void setProvenienciaMod(ModProveniencia provenienciaMod, String operacao) {
        this.provenienciaMod = provenienciaMod;
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
