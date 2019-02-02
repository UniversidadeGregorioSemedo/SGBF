/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.modelo;

import javafx.scene.control.Alert;
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
    private String nomeCompleto;
    private String primeiro_nome;
    private String segundo_nome;
    private String contacto;
    private String email;
    private UtilControloDaData utilControloDaData;
    
    public ModAutor(){
        this.idAutor = 0;
        this.nomeCompleto = null;
        this.primeiro_nome = null;
        this.segundo_nome = "";
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

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto, String operacao) {
        this.nomeCompleto = nomeCompleto;
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
        if(segundo_nome != null){
            this.segundo_nome = segundo_nome;
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
        if(email == null){
            this.email = email;
        }else{
            if(email.isEmpty()){
                this.email = email;
            }else{
                if (emailUtil.emailValido(email)) {
                    this.email = email;
                }else{
                    throw new UtilControloExcessao(operacao, "Email inconsistente !", Alert.AlertType.INFORMATION);
                }
            }
        }
    }

    @Override
    public String toString() {
        return nomeCompleto;
    }
    
    
    
    public boolean equals(ModAutor autorMod, String operacao){
        if(this.idAutor != autorMod.idAutor){
            if(this.contacto.equalsIgnoreCase(autorMod.contacto)){
                throw new UtilControloExcessao(operacao, "Já existe um autor com este contacto !", Alert.AlertType.INFORMATION);
            }
        }
        return false;
    }

    public UtilControloDaData getUtilControloDaData() {
        return utilControloDaData;
    }

}
