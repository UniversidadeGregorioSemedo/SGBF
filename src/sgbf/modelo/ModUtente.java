/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.modelo;

import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilIconesDaJOPtionPane;

/**
 *
 * @author Look
 */
public abstract class ModUtente {
    
    protected Integer idUtente;
    protected String primeiro_nome;
    protected String segundo_nome;
    protected String genero;
    protected String tipo_identificacao;
    protected String numero;
    protected String contacto;
    protected String email;
    protected String endereco;
    protected String endereco_imagem;
    protected String categoria;
    protected String usuario;
    protected String senha;
    protected String data_registo;
    protected String data_modificacao;

 
    public Integer getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Integer idUtente, String operacao) {
        this.idUtente = idUtente;
    }

    public String getPrimeiro_nome() {
        return primeiro_nome;
    }

    public void setPrimeiro_nome(String primeiro_nome, String operacao) {
        this.primeiro_nome = primeiro_nome;
    }

    public String getSegundo_nome() {
        return segundo_nome;
    }

    public void setSegundo_nome(String segundo_nome, String operacao) {
        this.segundo_nome = segundo_nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero, String operacao) {
        this.genero = genero;
    }

    public String getTipo_identificacao() {
        return tipo_identificacao;
    }

    public void setTipo_identificacao(String tipo_identificacao, String operacao) {
        this.tipo_identificacao = tipo_identificacao;
    }

    public String getNumero() {
        return numero;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    

    public void setNumero(String numero, String operacao) {
        this.numero = numero;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco, String operacao) {
        this.endereco = endereco;
    }

    public String getEndereco_imagem() {
        return endereco_imagem;
    }

    public void setEndereco_imagem(String endereco_imagem) {
        this.endereco_imagem = endereco_imagem;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario, String operacao) {
        if(usuario == null){
            throw new UtilControloExcessao("Introduza o nome do Usuário !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
        }else{
            if(usuario.isEmpty()){
                throw new UtilControloExcessao("Introduza o nome do Usuário !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
            }else{
                this.usuario = usuario;
            }
        }
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha, String operacao) {
        if(senha == null){
            throw new UtilControloExcessao("Introduza a senha !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
        }else{
            if(senha.isEmpty()){
                throw new UtilControloExcessao("Introduza a senha !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
            }else{
                this.senha = senha;
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

    public void setData_modificacao(String data_modificacao, String operacao) {
        this.data_modificacao = data_modificacao;
    }
    
    
    
}
