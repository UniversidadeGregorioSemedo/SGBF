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
public class  ModAcervo {
    private Integer idAcervo;
    private String titulo;
    private String sub_titulo;
    private String tipo_acervo;
    private String formato;
    private Byte edicao;
    private Byte volume;
    private Short numero_paginas;
    private Integer ano_lancamento;
    private String idioma;
    private String codigo_barra;
    private String isbn;
    private String endereco_acervo;
    private String sinopse;
    private ModEstoque estoqueMod;
    private ModEditora editoraMod;
    private ModAutor autorMod;
    private ModCategoria categoriaMod;
    private UtilControloDaData utilControloDaData;

    
    public ModAcervo(){
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
        this.endereco_acervo = null;
        this.sinopse = null;
        this.estoqueMod = new ModEstoque();
        this.editoraMod = new ModEditora();
        this.autorMod = new ModAutor();
        this.categoriaMod = new ModCategoria();
        this.utilControloDaData = new UtilControloDaData();
    }

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

    public ModEstoque getEstoqueMod() {
        return estoqueMod;
    }

    public ModEditora getEditoraMod() {
        return editoraMod;
    }

    public void setEditoraMod(ModEditora editoraMod, String operacao) {
        this.editoraMod = editoraMod;
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

    public ModAutor getAutorMod() {
        return autorMod;
    }

    public void setAutorMod(ModAutor autorMod, String operacao) {
        this.autorMod = autorMod;
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
