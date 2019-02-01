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
public class ModEditora {
    
    private Integer iEditora;
    private String nome;
    private String contacto;
    private String email;
    private String fax;
    private String endereco;
    private UtilControloDaData utilControloDaData;
    
    public ModEditora(){
        this.iEditora = 0;
        this.nome = null;
        this.contacto = null;
        this.email = null;
        this.fax = null;
        this.endereco = null;
        this.utilControloDaData = new UtilControloDaData();
    }

    public Integer getiEditora() {
        return iEditora;
    }

    public void setiEditora(Integer iEditora, String operacao) {
        this.iEditora = iEditora;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome, String operacao) {
        if(nome ==  null){
            throw new UtilControloExcessao("Introduza o nome da Editora !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
        }else{
            if(nome.isEmpty()){
                throw new UtilControloExcessao("Introduza o nome da Editora !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
            }else{
                this.nome = nome;
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

    public String getFax() {
        return fax;
    }

    public void setFax(String fax, String operacao) {
        this.fax = fax;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco, String operacao) {
        this.endereco = endereco;
    }

    public UtilControloDaData getUtilControloDaData() {
        return utilControloDaData;
    }

    @Override
    public String toString() {
        return this.nome;
    }

    
    
    public void equals(ModEditora editoraMod, String operacao){
        if(this.iEditora != editoraMod.iEditora){
            if(this.nome.equalsIgnoreCase(editoraMod.nome)){
                throw new UtilControloExcessao(operacao,"Já existe uma Editora com este Nome !",Alert.AlertType.WARNING);
            }
        }
    }
    
    
    
}
