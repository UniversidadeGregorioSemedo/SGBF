/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.modelo;

/**
 *
 * @author Look
 */
public class ModCategoriaDaEstante {
    
    private ModCategoria categoriaMod;
    private ModEstante estanteMod;

    public ModCategoria getCategoriaMod() {
        return categoriaMod;
    }

    public void setCategoriaMod(ModCategoria categoriaMod, String operacao) {
        this.categoriaMod = categoriaMod;
    }

    public ModEstante getEstanteMod() {
        return estanteMod;
    }

    public void setEstanteMod(ModEstante estanteMod, String operacao) {
        this.estanteMod = estanteMod;
    }
    
}
