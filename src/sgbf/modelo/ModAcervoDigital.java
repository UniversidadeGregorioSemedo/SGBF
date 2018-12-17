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
public final class ModAcervoDigital extends ModAcervo {
    
    private String endereco_acervo;
    private String sinopse;

    public ModAcervoDigital(){
        this.idAcervo = 0;
        this.titulo = null;
        this.sub_titulo = null;
        this.tipo_acervo = null;
        this.formato = null;
        this.edicao = 0;
        this.volume = 0;
        this.numero_paginas = 0;
        this.ano_lancamento = 0;
        this.idioma = null;
        this.endereco_acervo = null;
        this.sinopse = null;
        this.estoqueMod = new ModEstoque();
        this.categoriaMod = new ModCategoria();
        this.data_registo = String.valueOf(UtilControloDaData.dataActual());
        this.data_modificacao = String.valueOf(UtilControloDaData.dataActual());
    }
    
    public String getEndereco_acervo() {
        return endereco_acervo;
    }

    public void setEndereco_acervo(String endereco_acervo, String operacao) {
        if(endereco_acervo == null){
            this.endereco_acervo = endereco_acervo;
        }else{
            if(endereco_acervo.isEmpty()){
                
            }else{
                this.endereco_acervo = endereco_acervo;
            }
        }
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse, String operacao) {
        this.sinopse = sinopse;
    }
    
}
