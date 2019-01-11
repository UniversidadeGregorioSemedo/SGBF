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
public class ModCategoria {
    private Integer idCategoria;
    private String designacao;
    private ModEstante estanteMod;
    private UtilControloDaData utilControloDaData;
    
    public ModCategoria(){
        this.idCategoria = 0;
        this.designacao = null;
        this.estanteMod = null;
        this.utilControloDaData = new UtilControloDaData();
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria, String operacao) {
        this.idCategoria = idCategoria;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao, String operacao) {
        if(designacao == null){
            throw new UtilControloExcessao("Categoria não definida !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
        }else{
            if(designacao.isEmpty()){
                throw new UtilControloExcessao("Categoria não definida !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
            }else{
                this.designacao = designacao;
            }
        }
    }

    public ModEstante getEstanteMod() {
        return estanteMod;
    }

    public void setEstanteMod(ModEstante estanteMod, String operacao) {
        this.estanteMod = estanteMod;
    }
    
    public UtilControloDaData getUtilControloDaData() {
        return utilControloDaData;
    }

    public void equals(ModCategoria categoriaMod, String operacao){
        if(this.designacao.equalsIgnoreCase(categoriaMod.designacao)){
            throw new UtilControloExcessao("Já existe uma Categoria com este Nome !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
        }
    }
    
    
    
}
