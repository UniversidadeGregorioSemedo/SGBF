/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.modelo;

import sgbf.util.UtilControloDaData;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilEmail;
import sgbf.util.UtilIconesDaJOPtionPane;

/**
 *
 * @author Look
 */
public class ModAutor {
    private Integer idAutor;
    private String primeiro_nome;
    private String segundo_nome;
    private String contacto;
    private String email;
    private UtilControloDaData utilControloDaData;
    
    public ModAutor(){
        this.idAutor = 0;
        this.primeiro_nome = null;
        this.segundo_nome = null;
        this.contacto = null;
        this.email = null;
        this.utilControloDaData = new UtilControloDaData();
    }

    public Integer getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Integer idAutor, String operacao) {
        this.idAutor = idAutor;
    }

    public String getPrimeiro_nome() {
        return primeiro_nome;
    }

    public void setPrimeiro_nome(String primeiro_nome, String operacao) {
        if(primeiro_nome == null){
            throw new UtilControloExcessao("Primeiro nome do Autor não definido !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
        }else{
            if(primeiro_nome.isEmpty()){
                throw new UtilControloExcessao("Primeiro nome do Autor não definido  !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
            }else{
                this.primeiro_nome = primeiro_nome;
            }
        }
    }

    public String getSegundo_nome() {
        return segundo_nome;
    }

    public void setSegundo_nome(String segundo_nome, String operacao) {
        if(segundo_nome == null){
            throw new UtilControloExcessao("Segundo nome do Autor não definido !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
        }else{
            if(segundo_nome.isEmpty()){
                throw new UtilControloExcessao("Segundo nome do Autor não definido  !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
            }else{
                this.segundo_nome = segundo_nome;
            }
        }
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto, String operacao) {
        this.contacto = contacto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email, String operacao) {
        UtilEmail emailUtil = new UtilEmail();
        if(emailUtil.emailValido(email)){
            this.email = email;
        }else{
            throw new UtilControloExcessao("Erro ao "+operacao+"\nErro: "+email+" é um email inválido !", operacao, UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }
    }

    public UtilControloDaData getUtilControloDaData() {
        return utilControloDaData;
    }

}
