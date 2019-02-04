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
    private ModEstante estanteAntiga;
    private UtilControloDaData utilControloDaData;

    public ModCategoria() {
        this.idCategoria = 0;
        this.designacao = null;
        this.estanteAntiga = new ModEstante();
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
        if (designacao == null) {
            throw new UtilControloExcessao(operacao, "Categoria não definida !", Alert.AlertType.INFORMATION);
        } else {
            if (designacao.isEmpty()) {
                throw new UtilControloExcessao(operacao, "Categoria não definida !", Alert.AlertType.INFORMATION);
            } else {
                this.designacao = designacao;
            }
        }
    }

    @Override
    public String toString() {
        return designacao;
    }

    public ModEstante getEstanteMod() {
        return estanteMod;
    }

    public void setEstanteMod(ModEstante estanteMod, String operacao) {
        if (estanteMod == null) {
            this.estanteMod = new ModEstante();
        } else {
            this.estanteMod = estanteMod;
        }
    }

    public ModEstante getEstanteAntiga() {
        return estanteAntiga;
    }

    public void setEstanteModAntiga(ModCategoria categoriaMod, String operacao) {
        if (categoriaMod.getEstanteAntiga() == null) {
            this.estanteAntiga = new ModEstante();
        } else {
            this.estanteAntiga = categoriaMod.getEstanteMod();
        }
    }

    public UtilControloDaData getUtilControloDaData() {
        return utilControloDaData;
    }

    public void equals(ModCategoria categoriaMod, String operacao) {
        if (categoriaMod.getEstanteMod().getIdEstante() == 0) {
            if (this.designacao.equalsIgnoreCase(categoriaMod.designacao)) {
                throw new UtilControloExcessao(operacao, "Já existe uma categoria com esta designação !", Alert.AlertType.WARNING);
            }
        } else {
            if (this.getEstanteMod().getIdEstante() == categoriaMod.getEstanteMod().getIdEstante()) {
                if (this.designacao.equalsIgnoreCase(categoriaMod.designacao)) {
                    throw new UtilControloExcessao(operacao, "Já existe uma Categoria com esta designação nesta Estante !", Alert.AlertType.WARNING);
                }
            }
        }
    }

}
