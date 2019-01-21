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
public class ModEstoque {
    
    private Integer idEstoque;
    private Short quantidade_total;
    private Short quantidade_em_falta;
    private Short quantidade_acervos_emprestados;
    private Short quantidade_acervos_resercados;
    private Short quantidade_remanescente;
    private UtilControloDaData utilControloDaData;

    public ModEstoque() {
        this.idEstoque = 0;
        this.quantidade_total = 0;
        this.quantidade_em_falta = 0;
        this.quantidade_acervos_emprestados = 0;
        this.quantidade_acervos_resercados = 0;
        this.quantidade_remanescente = 0;
        this.utilControloDaData = new UtilControloDaData();
    }
    
    public Integer getIdEstoque() {
        return idEstoque;
    }

    public void setIdEstoque(Integer idEstoque, String operacao) {
        this.idEstoque = idEstoque;
    }

    public Short getQuantidade_total() {
        return quantidade_total;
    }

    public void setQuantidade_total(Short quantidade_total, String operacao) {
        this.quantidade_total = quantidade_total;
    }

    public Short getQuantidade_em_falta() {
        return quantidade_em_falta;
    }

    public void setQuantidade_em_falta(Short quantidade_em_falta, String operacao) {
        this.quantidade_em_falta = quantidade_em_falta;
    }

    public Short getQuantidade_acervos_emprestados() {
        return quantidade_acervos_emprestados;
    }

    public void setQuantidade_acervos_emprestados(Short quantidade_acervos_emprestados, String operacao) {
        this.quantidade_acervos_emprestados = quantidade_acervos_emprestados;
    }

    public Short getQuantidade_acervos_resercados() {
        return quantidade_acervos_resercados;
    }

    public void setQuantidade_acervos_resercados(Short quantidade_acervos_resercados, String operacao) {
        this.quantidade_acervos_resercados = quantidade_acervos_resercados;
    }
    
    public Integer getQuantidadeRemanescente(){
        return this.quantidade_total - this.quantidade_em_falta;
    }

    public UtilControloDaData getUtilControloDaData() {
        return utilControloDaData;
    }
    
}
