
package sgbf.util;

import sgbf.modelo.ModFuncionario;

/**
 *
 * @author Dell
 */
public class UtilUsuarioLogado {
    
    private static ModFuncionario usuarioLogado = null;

    public static ModFuncionario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void setUsuarioLogado(ModFuncionario usuarioLogado) {
        UtilUsuarioLogado.usuarioLogado = usuarioLogado;
    }

}
