package sgbf.util;

import java.sql.Timestamp;
import javafx.scene.control.Alert;
import org.joda.time.DateTime;
import org.joda.time.Days;

/**
 * @author Francisco Lourenço
 */
public class UtilControloDaData {

    private Timestamp data_registo;
    private Timestamp data_modificacao;

    public UtilControloDaData() {
        this.data_registo = null;
        this.data_modificacao = null;
    }

    public String getData_registo() {
        return String.valueOf(data_registo);
    }
    public Timestamp getDataRegistoEmTimestapm() {
        return data_registo;
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

    public DateTime dataActual() {
        DateTime dataActual = new DateTime();
        return dataActual;
    }

    public static Timestamp DataTimeParaTimeStamp(DateTime dateTime) {
        return new Timestamp(dateTime.toDateTime().getMillis());
    }
    
    public static DateTime TimestampParaDatatime(Timestamp timeStamp) {
        return new DateTime(timeStamp.getTime());
    }

    public Integer numeroDeDiasEntreDuasDatas(DateTime data_inicial, DateTime data_final, String operacao) {
        if (UtilControloDaData.dataFoiIntroduzida(data_inicial)) {
            if (UtilControloDaData.dataFoiIntroduzida(data_final)) {
                return Days.daysBetween(data_inicial, data_final).getDays();
            } else {
                throw new UtilControloExcessao(operacao, "Introduza a data final !", Alert.AlertType.WARNING);
            }
        } else {
            throw new UtilControloExcessao(operacao, "Introduza a data inicial !", Alert.AlertType.WARNING);
        }
    }

    private static boolean dataFoiIntroduzida(DateTime data_introduzida) {
        return (data_introduzida != null);
    }

}
