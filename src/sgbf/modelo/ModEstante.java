/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.modelo;

import sgbf.util.UtilControloDaData;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilIconesDaJOPtionPane;

/**
 *
 * @author Look
 */
public class ModEstante {
    private Integer idEstante;
    private String designacao;
    private String descricao;
    private Byte linha;
    private Byte coluna;
    private ModArea areaMod;
    private UtilControloDaData utilControloDaData;
    
    public ModEstante(){
        this.idEstante = 0;
        this.designacao = null;
        this.descricao = null;
        this.linha = 0;
        this.coluna = 0;
        this.areaMod = new ModArea();
        this.utilControloDaData = new UtilControloDaData();
    }

    public Integer getIdEstante() {
        return idEstante;
    }

    public void setIdEstante(Integer idEstante, String operacao) {
        this.idEstante = idEstante;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao, String operacao) {
        if(designacao == null){
            throw new UtilControloExcessao("Designação não definida !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
        }else{
            if(designacao.isEmpty()){
                throw new UtilControloExcessao("Designação não definida!", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
            }else{
                this.designacao = designacao;
            }
        }
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao, String operacao) {
        this.descricao = descricao;
    }

    public Byte getLinha() {
        return linha;
    }

    public void setLinha(Byte linha, String operacao) {
        if(linha <=0 ){
            throw new UtilControloExcessao("O número de linha é inválido !", operacao, UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }else{
            if(linha >125){
                throw new UtilControloExcessao("O número de linhas máximo é de 125 !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
            }else{
                this.linha = linha;
            }
        }
    }

    public Byte getColuna() {
        return coluna;
    }

    public void setColuna(Byte coluna, String operacao) {
        if(linha <=0 ){
            throw new UtilControloExcessao("O número de colunas é inválido !", operacao, UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }else{
            if(linha >125){
                throw new UtilControloExcessao("O número de colunas máximo é de 125 !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
            }else{
                this.coluna = coluna;
            }
        }
    }

    public ModArea getAreaMod() {
        return areaMod;
    }

    public UtilControloDaData getUtilControloDaData() {
        return utilControloDaData;
    }
 
}
