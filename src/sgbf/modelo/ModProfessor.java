/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.modelo;

import sgbf.util.UtilControloDaData;

/**
 *
 * @author Look
 */
public class ModProfessor extends ModUtente {
    private ModUsuario usuarioMod;

    public ModProfessor() {
        this.idUtente = 0;
        this.primeiro_nome = null;
        this.segundo_nome = null;
        this.genero = null;
        this.tipo_identificacao = null;
        this.numero = null;
        this.endereco = null;
        this.data_registo = String.valueOf(UtilControloDaData.dataActual());
        this.data_modificacao = String.valueOf(UtilControloDaData.dataActual());
        this.usuarioMod = new ModUsuario();
    }

    public ModUsuario getUsuarioMod() {
        return usuarioMod;
    }

    public void setUsuarioMod(ModUsuario usuarioMod) {
        this.usuarioMod = usuarioMod;
    }
    
}
