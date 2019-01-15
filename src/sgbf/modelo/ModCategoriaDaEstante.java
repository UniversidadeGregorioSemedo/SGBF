/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.modelo;

import sgbf.controlo.ConCategoria;

/**
 *
 * @author Look
 */
public class ModCategoriaDaEstante {

    private ModCategoria categoriaMod;
    private ModEstante estanteMod;

    public ModCategoriaDaEstante() {
        this.categoriaMod = new ModCategoria();
        this.estanteMod = new ModEstante();
    }

    public ModCategoria getCategoriaMod() {
        return categoriaMod;
    }

    public void setCategoriaMod(ModCategoria categoriaMod, String operacao) {
        ConCategoria categoriaCon = new ConCategoria();
        this.categoriaMod = categoriaMod;
        for (Object todosRegistos : categoriaCon.listarTodos(operacao)) {
            ModCategoria categoriaRegistada = (ModCategoria) todosRegistos;
            if (categoriaMod.getDesignacao().equalsIgnoreCase(categoriaRegistada.getDesignacao())) {
                this.categoriaMod = categoriaRegistada;
                break;
            }
        }
    }

    public ModEstante getEstanteMod() {
        return estanteMod;
    }

    public void setEstanteMod(ModEstante estanteMod, String operacao) {
        this.estanteMod = estanteMod;
    }

}
