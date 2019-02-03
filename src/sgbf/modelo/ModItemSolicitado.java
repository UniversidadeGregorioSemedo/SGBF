/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.modelo;

import javafx.scene.control.Alert;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilIconesDaJOPtionPane;

/**
 *
 * @author Look
 */
public class ModItemSolicitado {

    private ModAcervo acervoMod;
    private Byte quantidade_revervada;

    public ModItemSolicitado() {
        this.acervoMod = new ModAcervo();
        this.quantidade_revervada = 0;
    }

    public ModAcervo getAcervoMod() {
        return acervoMod;
    }

    public void setAcervoMod(ModAcervo acervoMod, String operacao) {
        if (acervoMod == null) {
            throw new UtilControloExcessao(operacao, "Seleccione o acervo que pretende registar", Alert.AlertType.WARNING);
        } else {
            this.acervoMod = acervoMod;
        }
    }

    public Byte getQuantidade_revervada() {
        return quantidade_revervada;
    }

    public void setQuantidade_revervada(Byte quantidade_revervada, String operacao) {
        if (quantidade_revervada <= 0) {
            throw new UtilControloExcessao(operacao, "Quantidade solicicata inválida !", Alert.AlertType.ERROR);
        } else {
            if (quantidade_revervada > 125) {
                throw new UtilControloExcessao(operacao, "A quantidade de solicitação máxima é de 125 !", Alert.AlertType.WARNING);
            } else {
                this.quantidade_revervada = quantidade_revervada;
            }
        }
    }

}
