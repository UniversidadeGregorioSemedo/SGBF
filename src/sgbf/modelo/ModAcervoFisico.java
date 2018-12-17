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
public final class ModAcervoFisico extends ModAcervo{
    
    private String codigo_barra;
    private String isbn;
    
    public ModAcervoFisico(){
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
        this.codigo_barra = null;
        this.isbn = null;
        this.estoqueMod = new ModEstoque();
        this.categoriaMod = new ModCategoria();
        this.data_registo = String.valueOf(UtilControloDaData.dataActual());
        this.data_modificacao = String.valueOf(UtilControloDaData.dataActual());
    }

    public String getCodigo_barra() {
        return codigo_barra;
    }

    public void setCodigo_barra(String codigo_barra, String operacao) {
        this.codigo_barra = codigo_barra;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn, String operacao) {
        this.isbn = isbn;
    }
    
}
