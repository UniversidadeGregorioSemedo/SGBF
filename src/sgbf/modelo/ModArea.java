/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.modelo;

import javafx.scene.control.Alert;
import sgbf.util.UtilControloDaData;
import sgbf.util.UtilControloExcessao;
import sgbf.util.UtilIconesDaJOPtionPane;

/**
 *
 * @author Look
 */
public class ModArea {
    
    private Integer idArea;
    private String sector;
    private UtilControloDaData utilControloDaData;
    
    public ModArea(){
        this.idArea = 0;
        this.sector = null;
        this.utilControloDaData = new UtilControloDaData();
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea, String operacao) {
        this.idArea = idArea;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector, String operacao) {
        if(sector == null){
            throw new UtilControloExcessao("Sector não definido !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
        }else{
            if(sector.isEmpty()){
                throw new UtilControloExcessao("Sector não definido !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
            }else{
                this.sector = sector;
            }
        }
    }

     @Override
    public String toString() {
        return this.getSector();
    }
    public UtilControloDaData getUtilControloDaData() {
        return utilControloDaData;
    }

    public void setUtilControloDaData(UtilControloDaData utilControloDaData) {
        this.utilControloDaData = utilControloDaData;
    }
 
    public void equals(ModArea areaMod, String operacao){
        if(this.idArea != areaMod.idArea){
            if(this.sector.equalsIgnoreCase(areaMod.sector)){
                throw new UtilControloExcessao( operacao,"Já existe Sector com esta designação !", Alert.AlertType.WARNING);
            }
        }
    }
    
}
