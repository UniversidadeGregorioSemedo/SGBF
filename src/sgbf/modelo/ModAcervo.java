/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.modelo;

import javafx.scene.control.Alert;
import sgbf.util.UtilControloDaData;
import sgbf.util.UtilControloExcessao;

/**
 *
 * @author Look
 */
public class ModAcervo {

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
    private ModAutor autorModRemover;
    private ModCategoria categoriaMod;
    private UtilControloDaData utilControloDaData;

    public ModAcervo() {
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
        this.autorModRemover = new ModAutor();
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
        if (titulo == null) {
            throw new UtilControloExcessao(operacao, "Título do Acervo não foi definido !", Alert.AlertType.WARNING);
        } else {
            if (titulo.isEmpty()) {
                throw new UtilControloExcessao(operacao, "Título do Acervo não foi definido !", Alert.AlertType.WARNING);
            } else {
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
        if (tipo_acervo == null) {
            throw new UtilControloExcessao(operacao, "Tipo de Acervo não foi definido !", Alert.AlertType.WARNING);
        } else {
            if (tipo_acervo.isEmpty()) {
                throw new UtilControloExcessao(operacao, "Tipo de Acervo não foi definido !", Alert.AlertType.WARNING);
            } else {
                this.tipo_acervo = tipo_acervo;
            }
        }
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato, String operacao) {
        if (formato == null) {
            throw new UtilControloExcessao(operacao, "Tipo de Formato não foi definido !", Alert.AlertType.WARNING);
        } else {
            if (formato.isEmpty()) {
                throw new UtilControloExcessao(operacao, "Tipo de Formato não foi definido !", Alert.AlertType.WARNING);
            } else {
                this.formato = formato;
            }
        }
    }

    public Byte getEdicao() {
        return edicao;
    }

    public void setEdicao(Byte edicao, String operacao) {
        if (edicao >= 0 && edicao <= 125) {
            this.edicao = edicao;
        } else {
            throw new UtilControloExcessao(operacao, "A edição introduzida não é válida\nValores permitidos 1-125 ", Alert.AlertType.WARNING);
        }
    }

    public Byte getVolume() {
        return volume;
    }

    public void setVolume(Byte volume, String operacao) {
        if (volume >= 0 && volume <= 125) {
            this.volume = volume;
        } else {
            throw new UtilControloExcessao(operacao, "O volume introduzido não é válida\nValores permitidos 1-125 ", Alert.AlertType.WARNING);
        }
    }

    public Short getNumero_paginas() {
        return numero_paginas;
    }

    public void setNumero_paginas(Short numero_paginas, String operacao) {
        if (numero_paginas <= 0) {
            throw new UtilControloExcessao(operacao, "Numero de Páginas inválido !", Alert.AlertType.ERROR);
        } else {
            this.numero_paginas = numero_paginas;
        }
    }

    public Integer getAno_lancamento() {
        return ano_lancamento;
    }

    public void setAno_lancamento(Integer ano_lancamento, String operacao) {
        if (ano_lancamento >= 1901 && ano_lancamento <= 2155) {
            this.ano_lancamento = ano_lancamento;
        } else {
            if (ano_lancamento == 0) {
                this.ano_lancamento = ano_lancamento;
            } else {
                throw new UtilControloExcessao("O ano de lançamento é inválido !", operacao, Alert.AlertType.WARNING);
            }
        }
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma, String operacao) {
        if (idioma == null) {
            throw new UtilControloExcessao(operacao, "O idioma não foi definido !", Alert.AlertType.WARNING);
        } else {
            if (idioma.isEmpty()) {
                throw new UtilControloExcessao(operacao, "O idioma não foi definido !", Alert.AlertType.WARNING);
            } else {
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
        if (editoraMod == null) {
            editoraMod = new ModEditora();
        } else {
            this.editoraMod = editoraMod;
        }
    }

    public String getEndereco_acervo() {
        return endereco_acervo;
    }

    public void setEndereco_acervo(String endereco_acervo, String operacao) {
        if (this.formato.equalsIgnoreCase("Digital")) {
            if (endereco_acervo == null) {
                throw new UtilControloExcessao(operacao, "Introduza o Acervo", Alert.AlertType.WARNING);
            } else {
                if (endereco_acervo.isEmpty()) {
                    throw new UtilControloExcessao(operacao, "Introduza o Acervo", Alert.AlertType.WARNING);
                } else {
                    this.endereco_acervo = endereco_acervo;
                }
            }
        } else {
            if (this.formato.equalsIgnoreCase("Físico")) {
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
        if (autorMod == null) {
            autorMod = new ModAutor();
        } else {
            this.autorMod = autorMod;
        }
    }

    public ModAutor getAutorModRemover() {
        return autorModRemover;
    }

    public void setAutorModRemover(ModAutor autorModRemover) {
        this.autorModRemover = autorModRemover;
    }
    
    public void setCategoriaMod(ModCategoria categoriaMod, String operacao) {
        if (categoriaMod == null) {
            throw new UtilControloExcessao(operacao, "Seleccione a Categoria !", Alert.AlertType.INFORMATION);
        } else {
            this.categoriaMod = categoriaMod;
        }
    }

    public ModCategoria getCategoriaMod() {
        return categoriaMod;
    }

    public UtilControloDaData getUtilControloDaData() {
        return utilControloDaData;
    }

}
