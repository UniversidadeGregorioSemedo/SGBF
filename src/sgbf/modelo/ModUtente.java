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

/**
 *
 * @author Look
 */
public abstract class ModUtente {

    protected Integer idUtente;
    protected String nome;
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
    private UtilControloDaData utilControloDaData = new UtilControloDaData();

    public Integer getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Integer idUtente, String operacao) {
        this.idUtente = idUtente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome, String operacao) {
        this.nome = nome;
    }
    
    

    public String getPrimeiro_nome() {
        return primeiro_nome;
    }

    public void setPrimeiro_nome(String primeiro_nome, String operacao) {
        if ((primeiro_nome == null) || (primeiro_nome.isEmpty())) {
            throw new UtilControloExcessao(operacao, "Introduza o primeiro nome", Alert.AlertType.INFORMATION);
        } else {
            this.primeiro_nome = primeiro_nome;
        }
    }

    public String getSegundo_nome() {
        return segundo_nome;
    }

    public void setSegundo_nome(String segundo_nome, String operacao) {
        if ((segundo_nome == null) || (segundo_nome.isEmpty())) {
            throw new UtilControloExcessao(operacao, "Introduza o segundo nome", Alert.AlertType.INFORMATION);
        } else {
            this.segundo_nome = segundo_nome;
        }
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero, String operacao) {
        if ((genero == null) || (genero.isEmpty())) {
            throw new UtilControloExcessao(operacao, "Seleccione o Gênero", Alert.AlertType.INFORMATION);
        } else {
            this.genero = genero;
        }

    }

    public String getTipo_identificacao() {
        return tipo_identificacao;
    }

    public void setTipo_identificacao(String tipo_identificacao, String operacao) {
        if ((tipo_identificacao == null) || (tipo_identificacao.isEmpty())) {
            throw new UtilControloExcessao(operacao, "Seleccione o tipo de identificação", Alert.AlertType.INFORMATION);
        } else {
            this.tipo_identificacao = tipo_identificacao;
        }
    }

    public String getNumero() {
        return numero;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto, String operacao) {
        if ((contacto == null) || (contacto.isEmpty())) {
            throw new UtilControloExcessao(operacao, "Introduza o Contacto", Alert.AlertType.INFORMATION);
        } else {
            this.contacto = contacto;
        }
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

    public void setNumero(String numero, String operacao) {
        if ((numero == null) || (numero.isEmpty())) {
            throw new UtilControloExcessao(operacao, "Introduza o número de identificação", Alert.AlertType.INFORMATION);
        } else {
            this.numero = numero;
        }
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco, String operacao) {
        if ((endereco == null) || (endereco.isEmpty())) {
            throw new UtilControloExcessao(operacao, "Introduza o Endereço do Utente", Alert.AlertType.INFORMATION);
        } else {
            this.endereco = endereco;
        }
    }

    public String getEndereco_imagem() {
        return endereco_imagem;
    }

    public void setEndereco_imagem(String endereco_imagem, String operacao) {
        this.endereco_imagem = endereco_imagem;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria, String operacao) {
        if ((categoria == null) || (categoria.isEmpty())) {
            throw new UtilControloExcessao(operacao, "Seleccione a categoria", Alert.AlertType.INFORMATION);
        } else {
            this.categoria = categoria;

        }
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario, String operacao) {
        if ((usuario == null) || (usuario.isEmpty())) {
            throw new UtilControloExcessao(operacao, "Introduza o nome do usuário !", Alert.AlertType.INFORMATION);
        } else {
            this.usuario = usuario;
        }
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha, String operacao) {
        if ((senha == null) || (senha.isEmpty())) {
            throw new UtilControloExcessao(operacao, "Introduza a senha !", Alert.AlertType.INFORMATION);
        } else {
            this.senha = senha;
        }
    }

    public UtilControloDaData getUtilControloDaData() {
        return utilControloDaData;
    }
    
    public void equals(ModUtente utenteMod, String operacao){
        if(this.idUtente != utenteMod.idUtente){
            if(this.tipo_identificacao.equalsIgnoreCase(utenteMod.tipo_identificacao)){
                if(this.numero.equalsIgnoreCase(utenteMod.numero)){
                    throw new UtilControloExcessao(operacao, "Já existe um Utente com esta identificação", Alert.AlertType.WARNING);
                }else{
                    if(this.contacto.equalsIgnoreCase(utenteMod.contacto)){
                        throw new UtilControloExcessao(operacao, "Já existe um Utente com este Contacto", Alert.AlertType.WARNING);
                    }else{
                        if(this.usuario.equalsIgnoreCase(utenteMod.usuario)){
                            throw new UtilControloExcessao(operacao, "O usuário introduzido já existe", Alert.AlertType.WARNING);
                        }
                    }
                }
            }else{
                if(this.contacto.equalsIgnoreCase(utenteMod.contacto)){
                    throw new UtilControloExcessao(operacao, "Já existe um Utente com este Contacto", Alert.AlertType.WARNING);
                }else{
                    if(this.usuario.equalsIgnoreCase(utenteMod.usuario)){
                        throw new UtilControloExcessao(operacao, "O usuário introduzido já existe", Alert.AlertType.WARNING);
                    }
                }
            }
        }
    }

}
