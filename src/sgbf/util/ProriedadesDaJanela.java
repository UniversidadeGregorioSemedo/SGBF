/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgbf.util;

import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author franc
 */
public class ProriedadesDaJanela {

    public static void barraDeTitulo(Stage proriedadeDaJanela) {
        proriedadeDaJanela.setTitle("Sistema de Gestão de Biblioteca");
        proriedadeDaJanela.getIcons().add(new Image("sgbf\\icones\\igregoriosemedo.jpg"));
    }

    public static void exibirUsuarioLogado(Stage proriedadeDaJanela) {
        proriedadeDaJanela.setTitle(proriedadeDaJanela.getTitle() + "(" + UtilUsuarioLogado.getUsuarioLogado().getNome() + ")");
    }
}
