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
public class ModCategoria {
    private Integer idCategoria;
    private String designacao;
    private ModEstante estanteMod;
    private UtilControloDaData utilControloDaData;
    
    public ModCategoria(){
        this.idCategoria = 0;
        this.designacao = null;
        this.estanteMod = new ModEstante();
        this.utilControloDaData = new UtilControloDaData();
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria, String operacao) {
        this.idCategoria = idCategoria;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao, String operacao) {
        if(designacao == null){
            throw new UtilControloExcessao( operacao,"Categoria não definida !",Alert.AlertType.INFORMATION);
        }else{
            if(designacao.isEmpty()){
                throw new UtilControloExcessao( operacao,"Categoria não definida !",Alert.AlertType.INFORMATION);
            }else{
                this.designacao = designacao;
            }
        }
    }

    @Override
    public String toString() {
        return designacao ;
    }

    
    
    public ModEstante getEstanteMod() {
        return estanteMod;
    }

    public void setEstanteMod(ModEstante estanteMod, String operacao) {
        if(estanteMod == null){
            this.estanteMod = new ModEstante();
        }else{
            this.estanteMod = estanteMod;
        }
    }
    
    public UtilControloDaData getUtilControloDaData() {
        return utilControloDaData;
    }

    public void equals(ModCategoria categoriaMod, String operacao){
        if(this.getEstanteMod()!= null){
            if(this.getEstanteMod().getIdEstante() != 0){
                if(this.getEstanteMod().getIdEstante() == categoriaMod.getEstanteMod().getIdEstante()){
                    if(this.designacao.equalsIgnoreCase(categoriaMod.designacao)){
                        throw new UtilControloExcessao( operacao,"Já existe uma Categoria com esta designação nesta Estante !",Alert.AlertType.WARNING);
                    }
                }
            }
        }
    }
    
    
    
}
