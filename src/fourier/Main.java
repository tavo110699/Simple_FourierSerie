/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fourier;

import controller.MainController;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import mdlaf.MaterialLookAndFeel;
import view.MainView;

/**
 *
 * @author hugoluna
 */
public class Main {
    
    //este metodo es para inicializarlo a la GUI
    public static void main(String[] args) {
        initMaterialDesign();
        new MainController(new MainView());
        
    }
    
    //Metodo de la libreria encargada del diseño de la GUI
    public static void initMaterialDesign() {
        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
