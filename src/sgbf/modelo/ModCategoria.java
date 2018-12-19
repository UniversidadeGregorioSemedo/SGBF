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
    private String data_registo;
    private String data_modificacao;
    
    public ModCategoria(){
        this.idCategoria = 0;
        this.designacao = null;
        this.data_registo = String.valueOf(UtilControloDaData.dataActual());
        this.data_modificacao = String.valueOf(UtilControloDaData.dataActual());
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

    public String getData_registo() {
        return data_registo;
    }

    public void setData_registo(String data_registo, String operacao) {
        this.data_registo = data_registo;
    }

    public String getData_modificacao() {
        return data_modificacao;
    }

    public void setData_modificacao(String data_modificacao, String opercao) {
        this.data_modificacao = data_modificacao;
    }
    
    public void equals(ModCategoria categoriaMod, String operacao){
        if(this.designacao.equalsIgnoreCase(categoriaMod.designacao)){
            throw new UtilControloExcessao("Já existe uma Categoria com este Nome !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
        }
    }
    
}
