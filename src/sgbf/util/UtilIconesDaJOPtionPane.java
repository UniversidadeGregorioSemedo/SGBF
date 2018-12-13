/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.util;

/**
 *
 * @author Look
 */
public enum UtilIconesDaJOPtionPane {
    
    Advertencia("Advertencia.png"), Confirmacao("Confirmacao.png"), Erro("Erro.png");
    private String nome_imagem;
    private UtilIconesDaJOPtionPane(String nome_imagem){
       this.nome_imagem = nome_imagem;
    }
    
    public String nomeDaImagem(){
        return this.nome_imagem;
    }
   
}
