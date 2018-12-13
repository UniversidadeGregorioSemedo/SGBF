
package sgbf.util;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
/**
 * @author Johni Branda
 */

public class UtilControloDaData {
    
    
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
    
    //Método para verificar se uma data é superior a outra
    //Método para calcular a próxima data baseado no número de dias  
}
