/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.modelo;

import javafx.scene.control.Alert;
import sgbf.util.UtilControloDaData;
import sgbf.util.UtilControloExcessao;

/**
 *
 * @author Look
 */
public class ModDevolucao {

    private Integer idDevolucao;
    private String tipo_devolucao;
    private Integer quantidade_devolvida;
    private ModEmprestimo emprestimoMod;
    private UtilControloDaData utilControloDaData;

    public ModDevolucao() {
        this.idDevolucao = 0;
        this.tipo_devolucao = null;
        this.quantidade_devolvida = 0;
        this.emprestimoMod = new ModEmprestimo();
        this.utilControloDaData = new UtilControloDaData();
    }

    public Integer getIdDevolucao() {
        return idDevolucao;
    }

    public void setIdDevolucao(Integer idDevolucao, String operacao) {
        this.idDevolucao = idDevolucao;
    }

    public String getTipo_devolucao() {
        return tipo_devolucao;
    }

    public void setTipo_devolucao(String tipo_devolucao, String operacao) {
        this.tipo_devolucao = tipo_devolucao;
    }

    public Integer getQuantidade_devolvida() {
        return quantidade_devolvida;
    }

    public void setQuantidade_devolvida(Integer quantidade_devolvida, String operacao) {
        this.quantidade_devolvida = quantidade_devolvida;
    }

    public ModEmprestimo getEmprestimoMod() {
        return emprestimoMod;
    }

    public void setEmprestimoMod(ModEmprestimo emprestimoMo, String operacao) {
        if(emprestimoMo == null){
            throw new UtilControloExcessao(operacao, "Seleccione o  Emprestimo", Alert.AlertType.WARNING);
        }else{
            this.emprestimoMod = emprestimoMo;
        }
    }
    
    public UtilControloDaData getUtilControloDaData() {
        return utilControloDaData;
    }
    
}
