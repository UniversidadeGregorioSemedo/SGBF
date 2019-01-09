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
public class ModAcervosEscritos {
    private ModAcervo acervoMod;
    private ModAutor autorMod;
    
    public ModAcervosEscritos(){
        this.acervoMod = new ModAcervoFisico();
        this.autorMod = new ModAutor();
    }
    

    public ModAcervo getAcervoMod() {
        return acervoMod;
    }

    public void setAcervoMod(ModAcervo acervoMod, String operacao) {
        this.acervoMod = acervoMod;
    }

    public ModAutor getAutorMod() {
        return autorMod;
    }

    public void setAutorMod(ModAutor autorMod, String operacao) {
        this.autorMod = autorMod;
    }
    
}
