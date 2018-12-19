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
        this.utilControloDaData = new UtilControloDaData();
    }
    
    public String getEndereco_acervo() {
        return endereco_acervo;
    }

    public void setEndereco_acervo(String endereco_acervo, String operacao) {
        if(endereco_acervo == null){
            throw new UtilControloExcessao("Erro ao inserir Imagem", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
        }else{
            if(endereco_acervo.isEmpty()){
                throw new UtilControloExcessao("Erro ao inserir Imagem", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
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
