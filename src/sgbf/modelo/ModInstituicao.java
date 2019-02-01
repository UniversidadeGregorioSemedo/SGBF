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
public class ModInstituicao {
    
    private Integer idInstituicao;
    private String nome;
    private String enderecoMod;
    private String data_registo;
    private String data_modificacao;

    public Integer getIdInstituicao() {
        return idInstituicao;
    }

    public void setIdInstituicao(Integer idInstituicao, String operacao) {
        this.idInstituicao = idInstituicao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome, String operacao) {
        this.nome = nome;
    }

    public String getEnderecoMod() {
        return enderecoMod;
    }

    public void setEnderecoMod(String enderecoMod, String operacao) {
        this.enderecoMod = enderecoMod;
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
