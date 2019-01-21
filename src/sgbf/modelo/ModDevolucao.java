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
public class ModDevolucao {

    private Integer idDevolucao;
    private String tipo_devolucao;
    private Integer quantidade_devolvida;
    private String data_devolucao;
    private ModFuncionario funcionarioMod;
    private ModItemSolicitado solicitadoItemMod;
    private UtilControloDaData utilControloDaData;

    public ModDevolucao() {
        this.idDevolucao = 0;
        this.tipo_devolucao = null;
        this.quantidade_devolvida = 0;
        this.funcionarioMod = new ModFuncionario();
        this.solicitadoItemMod = new ModItemSolicitado();
        this.utilControloDaData = new UtilControloDaData();
    }

    public Integer getIdDevolucao() {
        return idDevolucao;
    }

    public void setIdDevolucao(Integer idDevolucao, String operacao) {
        this.idDevolucao = idDevolucao;
    }

    public String getTipo_devolucao() {
        return tipo_devolucao;
    }

    public void setTipo_devolucao(String tipo_devolucao, String operacao) {
        this.tipo_devolucao = tipo_devolucao;
    }

    public Integer getQuantidade_devolvida() {
        return quantidade_devolvida;
    }

    public void setQuantidade_devolvida(Integer quantidade_devolvida, String operacao) {
        this.quantidade_devolvida = quantidade_devolvida;
    }

    public String getData_devolucao() {
        return data_devolucao;
    }

    public void setData_devolucao(String data_devolucao, String operacao) {
        this.data_devolucao = data_devolucao;
    }

    public ModFuncionario getFuncionarioMod() {
        return funcionarioMod;
    }

    public void setFuncionarioMod(ModFuncionario funcionarioMod, String operacao) {
        this.funcionarioMod = funcionarioMod;
    }

    public ModItemSolicitado getSolicitadoItemMod() {
        return solicitadoItemMod;
    }

    public void setSolicitadoItemMod(ModItemSolicitado solicitadoItemMod, String operacao) {
        this.solicitadoItemMod = solicitadoItemMod;
    }
    
    public UtilControloDaData getUtilControloDaData() {
        return utilControloDaData;
    }
    
}
