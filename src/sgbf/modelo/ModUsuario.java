/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.modelo;

/**
 *
 * @author Look
 */
public class ModUsuario {
    
    private String nome;
    private String senha;
    private String estado;
    private String data_registo;
    private String data_modificacao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome, String operacao) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha, String operacao) {
        this.senha = senha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado, String operacao) {
        this.estado = estado;
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

    enum Estado{
        ACTIVO("Activo"),INACTIVO("Inactivo");
        private String estado;
        
        private Estado(String estado){
            this.estado = estado;
        }
        
        private String getEstado(){
            return this.estado;
        }
    }
    
}
