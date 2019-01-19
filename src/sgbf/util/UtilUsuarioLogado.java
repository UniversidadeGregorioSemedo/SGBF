/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.util;

import sgbf.modelo.ModVisitante;

/**
 *
 * @author Dell
 */
public class UtilUsuarioLogado {
    
    private static ModVisitante usuarioLogado = null;

    public static ModVisitante getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void setUsuarioLogado(ModVisitante usuarioLogado) {
        UtilUsuarioLogado.usuarioLogado = usuarioLogado;
    }

}
