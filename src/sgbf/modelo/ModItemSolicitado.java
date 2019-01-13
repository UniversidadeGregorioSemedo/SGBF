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
public class ModItemSolicitado {
    
    private ModAcervo acervoMod;
    private ModReserva reservaMod;
    private Byte quantidade_revervada;

    public ModItemSolicitado() {
        this.acervoMod = new ModAcervo();
        this.reservaMod = new ModReserva();
        this.quantidade_revervada = 0;
    }

    public ModAcervo getFisicoAcervoMod() {
        return acervoMod;
    }

    public void setFisicoAcervoMod(ModAcervo fisicoAcervoMod, String operacao) {
        this.acervoMod = fisicoAcervoMod;
    }

    public ModReserva getReservaMod() {
        return reservaMod;
    }

    public void setReservaMod(ModReserva reservaMod, String operacao) {
        this.reservaMod = reservaMod;
    }

    public Byte getQuantidade_revervada() {
        return quantidade_revervada;
    }

    public void setQuantidade_revervada(Byte quantidade_revervada, String operacao) {
        if(quantidade_revervada <= 0){
            throw new UtilControloExcessao("Quantidade solicicata inválida !", operacao, UtilIconesDaJOPtionPane.Erro.nomeDaImagem());
        }else{
            if(quantidade_revervada > 125){
                throw new UtilControloExcessao("A quantidade de solicitação máxima é de 125 !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
            }else{
                this.quantidade_revervada = quantidade_revervada;
            }
        }
    }
    
    
}
