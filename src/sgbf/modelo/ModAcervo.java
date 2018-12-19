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
public abstract class  ModAcervo {
    protected Integer idAcervo;
    protected String titulo;
    protected String sub_titulo;
    protected String tipo_acervo;
    protected String formato;
    protected Byte edicao;
    protected Byte volume;
    protected Short numero_paginas;
    protected Integer ano_lancamento;
    protected String idioma;
    protected ModEstoque estoqueMod;
    protected ModCategoria categoriaMod;
    protected UtilControloDaData utilControloDaData;


    public Integer getIdAcervo() {
        return idAcervo;
    }

    public void setIdAcervo(Integer idAcervo, String operacao) {
        this.idAcervo = idAcervo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo, String operacao) {
        if(titulo == null){
            throw new UtilControloExcessao("Título do Acervo não foi definido !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
        }else{
            if(titulo.isEmpty()){
                throw new UtilControloExcessao("Título do Acervo não foi definido !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
            }else{
                this.titulo = titulo;
            }
        }
    }

    public String getSub_titulo() {
        return sub_titulo;
    }

    public void setSub_titulo(String sub_titulo, String operacao) {
        this.sub_titulo = sub_titulo;
    }

    public String getTipo_acervo() {
        return tipo_acervo;
    }

    public void setTipo_acervo(String tipo_acervo, String operacao) {
        if(tipo_acervo == null){
            throw new UtilControloExcessao("Tipo de Acervo não foi definido !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
        }else{
            if(tipo_acervo.isEmpty()){
                throw new UtilControloExcessao("Tipo de Acervo não foi definido !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
            }else{
                this.tipo_acervo = tipo_acervo;
            }
        }
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato, String operacao) {
        if(formato == null){
            throw new UtilControloExcessao("Tipo de Formato não foi definido !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
        }else{
            if(formato.isEmpty()){
                throw new UtilControloExcessao("Tipo de Formato não foi definido !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
            }else{
                this.formato = formato;
            }
        }
    }

    public Byte getEdicao() {
        return edicao;
    }

    public void setEdicao(Byte edicao, String operacao) {
        if(edicao <= 0){
            throw new UtilControloExcessao("Numero da Edição inválida !", operacao, UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }else{
            if(edicao > 125){
                throw new UtilControloExcessao("O Numero da Edição máximo é de 125 !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
            }else{
                this.edicao = edicao;
            }
        }
    }

    public Byte getVolume() {
        return volume;
    }

    public void setVolume(Byte volume, String operacao) {
        if(volume <= 0){
            throw new UtilControloExcessao("Numero do Volume inválido !", operacao, UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }else{
            if(volume > 125){
                throw new UtilControloExcessao("O Numero do Volume máximo é de 125 !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
            }else{
                this.volume = volume;
            }
        }
    }

    public Short getNumero_paginas() {
        return numero_paginas;
    }

    public void setNumero_paginas(Short numero_paginas, String operacao) {
        if(volume <= 0){
            throw new UtilControloExcessao("Numero de Páginas inválido !", operacao, UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }else{
            this.numero_paginas = numero_paginas;
        }
    }

    public Integer getAno_lancamento() {
        return ano_lancamento;
    }

    public void setAno_lancamento(Integer ano_lancamento, String operacao) {
        if(volume <= 0){
            throw new UtilControloExcessao("O ano de lançamento é inválido !", operacao, UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }else{
            this.ano_lancamento = ano_lancamento;
        }
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma, String operacao) {
        if(idioma == null){
            throw new UtilControloExcessao("O idioma não foi definido !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
        }else{
            if(idioma.isEmpty()){
                throw new UtilControloExcessao("O idioma não foi definido !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
            }else{
                this.idioma = idioma;
            }
        }
    }

    public ModEstoque getEstoqueMod() {
        return estoqueMod;
    }

    public ModCategoria getCategoriaMod() {
        return categoriaMod;
    }

    
    public UtilControloDaData getUtilControloDaData() {
        return utilControloDaData;
    }
    
    enum tipo_acervo{
        Monografia("Monografia"),Jornal("Jornal"), Livro("Livro"), Revista("Revista"), Apostilha("Apostilha"), Trabalho("Trabalho");
        private String tipo_acervo = null;
        
        private tipo_acervo(String tipo){
            this.tipo_acervo = tipo;
        }
        
        public String getTipoAcervo(){
            return this.tipo_acervo;
        }
    }
    
    enum formato{
        Fisico("Físico"), Digital("Digital");
        private String formato = null;
        
        private formato(String formato){
            this.formato = formato;
        }
        
        public String getFormato(){
            return this.formato;
        }
    }
}
