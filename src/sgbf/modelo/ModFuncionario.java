/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.modelo;

import javafx.scene.control.Alert;
import sgbf.util.UtilControloExcessao;

/**
 *
 * @author Look
 */
public class ModFuncionario extends ModUtente  {
    private Integer idFuncionario;
    private String cargo;
    private String codigoFuncionario;
    private ModUsuario usuarioMod;

    public ModFuncionario() {
        this.idUtente = 0;
        this.idFuncionario = 0;
        this.primeiro_nome = null;
        this.segundo_nome = null;
        this.genero = null;
        this.tipo_identificacao = null;
        this.numero = null;
        this.endereco = null;
        this.cargo = null;
        this.codigoFuncionario = null;
        this.usuarioMod = new ModUsuario();
    }

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario, String operacao) {
        this.idFuncionario = idFuncionario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo, String operacao) {
        this.cargo = cargo;
    }

    public String getCodigoFuncionario() {
        return codigoFuncionario;
    }

    public void setCodigoFuncionario(String codigoFuncionario, String operacao) {
        this.codigoFuncionario = codigoFuncionario;
    }

    public ModUsuario getUsuarioMod() {
        return usuarioMod;
    }

    public void setUsuarioMod(ModUsuario usuarioMod, String operacao) {
        this.usuarioMod = usuarioMod;
    }
    
    public boolean equals(ModFuncionario funcionarioMod, String operacao){
        if(this.idFuncionario != funcionarioMod.idFuncionario){
            if(this.getIdUtente() == funcionarioMod.getIdUtente()){
                throw new UtilControloExcessao(operacao, "Já existe registo deste funcionário", Alert.AlertType.WARNING);
            }else{
                if(this.getCodigoFuncionario().equalsIgnoreCase(funcionarioMod.codigoFuncionario)){
                    throw new UtilControloExcessao(operacao, "Já existe um Funcionário com este código", Alert.AlertType.WARNING);
                }else{
                    return false;
                }
            }
        }else{
            return false;
        }
    }
    
}
