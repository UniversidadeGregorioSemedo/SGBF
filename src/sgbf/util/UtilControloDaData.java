
package sgbf.util;

import java.sql.Timestamp;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
/**
 * @author Johni Branda
 */

public class UtilControloDaData {
    
    private Timestamp data_registo;
    private Timestamp data_modificacao;
    
    public UtilControloDaData(){
        this.data_registo = null;
        this.data_modificacao = null;
    }

    public String getData_registo() {
        return String.valueOf(data_registo);
    }

    public void setData_registo(Timestamp data_registo, String operacao) {
        this.data_registo = data_registo;
    }

    public String getData_modificacao() {
        return String.valueOf(data_modificacao);
    }

    public void setData_modificacao(Timestamp data_modificacao, String operacao) {
        this.data_modificacao = data_modificacao;
    }
    
    public static LocalDate dataActual(){
        LocalDate dataActual = new LocalDate();
        return dataActual;
    }
  
    public  Integer anoDaDataIntroduzida(DateTime data_introduzida, String operacao){
        if(UtilControloDaData.dataFoiIntroduzida(data_introduzida)){
            return data_introduzida.getYear();
        }else{
            throw new UtilControloExcessao("Introduza a Data !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
        }
    }
    
    public  Integer mesDaDataIntroduzida(DateTime data_introduzida, String operacao){
        if(UtilControloDaData.dataFoiIntroduzida(data_introduzida)){
           return data_introduzida.getMonthOfYear();
        }else{
            throw new UtilControloExcessao("Introduza a Data !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
        }
    }
    
    public  Integer diaDaDataIntroduzida(DateTime data_introduzida, String operacao){
        if(UtilControloDaData.dataFoiIntroduzida(data_introduzida)){
            return data_introduzida.getDayOfMonth();
        }else{
            throw new UtilControloExcessao("Introduza a Data !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
        }
    }
    
    public  Integer numeroDeDiasEntreDuasDatas(DateTime data_inicial, DateTime data_final, String operacao){
        if(UtilControloDaData.dataFoiIntroduzida(data_inicial)){
            if(UtilControloDaData.dataFoiIntroduzida(data_final)){
                return Days.daysBetween(data_inicial, data_final).getDays(); 
            }else{
                throw new UtilControloExcessao("Introduza a data final !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
            }
        }else{
            throw new UtilControloExcessao("Introduza a data inicial !", operacao, UtilIconesDaJOPtionPane.Advertencia.nomeDaImagem());
        }
    }

    private static boolean dataFoiIntroduzida(DateTime data_introduzida){
        return (data_introduzida != null);
    }
    
}
