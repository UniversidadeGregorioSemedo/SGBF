/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.modelo;

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
    private String data_registo;
    private String data_modificacao;
    
    public ModArea(){
        this.idArea = 0;
        this.sector = null;
        this.data_registo = String.valueOf(UtilControloDaData.dataActual());
        this.data_modificacao = String.valueOf(UtilControloDaData.dataActual());
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
    
      public void equals(ModArea areaMod, String operacao){
        if(this.sector.equalsIgnoreCase(areaMod.sector)){
            throw new UtilControloExcessao("Já existe Sector com esta designação !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
        }
    }
    
}
