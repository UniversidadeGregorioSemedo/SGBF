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
public class ModFuncionario extends ModUtente  {
    private String cargo;
    private Integer codigo_funcionario;
    private ModUsuario usuarioMod;

    public ModFuncionario() {
        this.idUtente = 0;
        this.primeiro_nome = null;
        this.segundo_nome = null;
        this.genero = null;
        this.tipo_identificacao = null;
        this.numero = null;
        this.endereco = null;
        this.data_registo = String.valueOf(UtilControloDaData.dataActual());
        this.data_modificacao = String.valueOf(UtilControloDaData.dataActual());
        this.cargo = null;
        this.codigo_funcionario = 0;
        this.usuarioMod = new ModUsuario();
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo, String operacao) {
        this.cargo = cargo;
    }

    public Integer getCodigo_funcionario() {
        return codigo_funcionario;
    }

    public void setCodigo_funcionario(Integer codigo_funcionario, String operacao) {
        this.codigo_funcionario = codigo_funcionario;
    }

    public ModUsuario getUsuarioMod() {
        return usuarioMod;
    }

    public void setUsuarioMod(ModUsuario usuarioMod, String operacao) {
        this.usuarioMod = usuarioMod;
    }
    
}
