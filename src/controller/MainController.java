/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import mdlaf.MaterialLookAndFeel;
import view.MainView;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author gustavo
 */
public class MainController {

    private MainView mainView;

    private double intervalo;
    private double tiempo;
    private int T;
    private double var1 = 0;
    private double var2 = 0;
    private double var3 = 0;
    private double var4 = 0;
    private double var5 = 0;

    private double amplitud[];
    private double frecuencia[];
    private int noTerminos;
    private double datos[];
    private double numInter;

    /**
     * Los datos generados en el metodo @table se guardan en la matriz datos1
     */
    DefaultTableModel modelo;
    public Double datos1[][] = {};

    /**
     * inicializa las vistas
     *
     * @param mainView
     */
    public MainController(MainView mainView) {

        this.mainView = mainView;
        this.mainView.setVisible(true);
        initEvents();
        configViews();

    }

    public MainController() {
    }

    /**
     * desabilita el boton generar
     */
    public void configViews() {
        mainView.btnGenerate.setEnabled(false);
    }

    /**
     * Inicializa los eventos de la interfaz
     */
    public void initEvents() {
        mainView.btnAddAF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                getData();

                JOptionPane.showMessageDialog(mainView, "Ingresa datos en los siguientes campos desbloqueados");

                diableFields();
                getDataAmFrec(mainView.terms.getSelectedIndex() + 1);

            }
        });

        mainView.btnGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table(mainView.terms.getSelectedIndex() + 1);
            }
        });

        mainView.btnClean.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clean();
            }
        });

    }

    /**
     * Obtiene datos Ingresados por el usuario en la GUI
     */
    public void getData() {
        try {
            T = Integer.parseInt(mainView.tField.getText().toString());
            intervalo = Double.parseDouble(mainView.intervalosField.getText().toString());
            tiempo = Integer.parseInt(mainView.tiempoField.getText().toString());
            initVectors(mainView.terms.getSelectedIndex() + 1);
        } catch (Exception error) {
            JOptionPane.showMessageDialog(mainView, "Ocurrio un error en la captura de datos");
        }
    }

    /**
     * Desabilita los campos para futuros fallos en caso de que quiera ingresar
     * datos una segunda vez
     */
    public void diableFields() {

        mainView.intervalosField.setEditable(false);
        mainView.tField.setEditable(false);
        mainView.tiempoField.setEditable(false);
        mainView.terms.setEnabled(false);

    }

    /**
     * Inicializa los vectores
     *
     * @param numTerms
     */
    public void initVectors(int numTerms) {

        amplitud = new double[numTerms];
        frecuencia = new double[numTerms];
    }

    /**
     * Este metodo se pide los datos en la tabla de la formula de fourier
     *
     * @param numTerms
     */
    public void getDataAmFrec(int numTerms) {
        try {
            for (int i = 0; i < numTerms; i++) {

                String value = JOptionPane.showInputDialog(mainView, "Ingresa la frecuencia " + (i + 1));
                frecuencia[i] = Float.parseFloat(value);
                String valueAmplitud = JOptionPane.showInputDialog(mainView, "Ingresa la amplitud " + (i + 1));
                amplitud[i] = Float.parseFloat(value);
            }
        } catch (Exception error) {
            JOptionPane.showMessageDialog(mainView, "Ocurrio un error en la captura de datos");
        }
    }

    /**
     * Este metodo se encarga de pasar los datos obtenidos en la grafica
     *
     * @param numTerminos
     */
    public void table(int numTerminos) {
        Graphic graph = new Graphic();

        double res;
        double seno = 0;
        double ft;
        float aux = (float) intervalo;

        String info[] = {"intervalo de tiempo", "Termino 1", "Termino 2", "Termino 3", "Termino 4", "Termino 5"};

        modelo = new DefaultTableModel(datos1, info);
        mainView.tableDataResult.setModel(modelo);
        numInter = tiempo / intervalo;

        do {
            // la primera condicion entra en accion cuando le indicamos que solo queremos un termino
            if (numTerminos == 1) {

                for (int i = 0; i < numInter; i++) {

                    var1 = amplitud[0] * Math.sin((frecuencia[0] * Math.PI * intervalo) / T);
                    System.out.println(var1);
                    Object datos[] = {intervalo, var1};
                    modelo.addRow(datos);
                    intervalo = (float) (intervalo + aux);
                    //agrega datos a la grafica
                    graph.series.add(intervalo, var1);

                }
                break;
                // la primera condicion entra en accion cuando le indicamos que solo queremos un termino
            } else if (numTerminos == 2) {

                for (int i = 0; i < numInter; i++) {

                    var1 = amplitud[0] * Math.sin((frecuencia[0] * Math.PI * intervalo) / T);
                    var2 = var1 + (amplitud[1] * Math.sin((frecuencia[1] * Math.PI * intervalo) / T));
                    System.out.println(var1);
                    Object datos[] = {intervalo, var1, var2};
                    modelo.addRow(datos);
                    intervalo = (float) (intervalo + aux);
                    //agrega datos a la grafica
                    graph.series.add(intervalo, var1);
                    graph.series2.add(intervalo, var2);

                }
                break;

            } else if (numTerminos == 3) {

                for (int i = 0; i < numInter; i++) {

                    var1 = amplitud[0] * Math.sin((frecuencia[0] * Math.PI * intervalo) / T);
                    var2 = var1 + (amplitud[1] * Math.sin((frecuencia[1] * Math.PI * intervalo) / T));
                    var3 = var1 + var2 + (amplitud[2] * Math.sin((frecuencia[2] * Math.PI * intervalo) / T));
                    System.out.println(var1);
                    Object datos[] = {intervalo, var1, var2, var3};
                    modelo.addRow(datos);
                    intervalo = (float) (intervalo + aux);
                    //agrega datos a la grafica
                    graph.series.add(intervalo, var1);
                   graph.series2.add(intervalo, var2);
                    graph.series3.add(intervalo, var3);

                }
                break;

            } else if (numTerminos == 4) {
                for (int i = 0; i < numInter; i++) {

                    var1 = amplitud[0] * Math.sin((frecuencia[0] * Math.PI * intervalo) / T);
                    var2 = var1 + (amplitud[1] * Math.sin((frecuencia[1] * Math.PI * intervalo) / T));
                    var3 = var1 + var2 + (amplitud[2] * Math.sin((frecuencia[2] * Math.PI * intervalo) / T));
                    var4 = var1 + var2 + var3 + (amplitud[3] * Math.sin((frecuencia[3] * Math.PI * intervalo) / T));

                    Object datos[] = {intervalo, var1, var2, var3, var4};
                    modelo.addRow(datos);
                    intervalo = (float) (intervalo + aux);
                    //agrega datos a la grafica
                    graph.series.add(intervalo, var1);
                    graph.series2.add(intervalo, var2);
                    graph.series3.add(intervalo, var3);
                    graph.series4.add(intervalo, var4);

                }
                break;

            } else if (numTerminos == 5) {
                for (int i = 0; i < numInter; i++) {

                    var1 = amplitud[0] * Math.sin((frecuencia[0] * Math.PI * intervalo) / T);
                    var2 = var1 + (amplitud[1] * Math.sin((frecuencia[1] * Math.PI * intervalo) / T));
                    var3 = var1 + var2 + (amplitud[2] * Math.sin((frecuencia[2] * Math.PI * intervalo) / T));
                    var4 = var1 + var2 + var3 + (amplitud[3] * Math.sin((frecuencia[3] * Math.PI * intervalo) / T));
                    var5 = var1 + var2 + var3 + var4 + (amplitud[4] * Math.sin((frecuencia[4] * Math.PI * intervalo) / T));

                    Object datos[] = {intervalo, var1, var2, var3, var4, var5};
                    modelo.addRow(datos);
                    intervalo = (float) (intervalo + aux);
                    //agrega datos a la grafica
                    graph.series.add(intervalo, var1);
                    graph.series2.add(intervalo, var2);
                    graph.series3.add(intervalo, var3);
                    graph.series4.add(intervalo, var4);
                    graph.series5.add(intervalo, var5);

                }
                break;

            }

        } while (intervalo > tiempo);
        //grafica0 

        graph.generateGraph();

    }

    public void clean() {
        int a = modelo.getRowCount() - 1;

        mainView.intervalosField.setText(null);
        mainView.tiempoField.setText(null);
        mainView.tField.setText(null);
        mainView.btnGenerate.setEnabled(false);

        mainView.intervalosField.setEditable(true);
        mainView.tiempoField.setEditable(true);
        mainView.tField.setEditable(true);
        mainView.terms.setEnabled(true);

        T=0;
        intervalo=0;
        noTerminos=0;
        tiempo=0;
        
      
        
        for (int i = a; i >= 0; i--) {
            modelo.removeRow(modelo.getRowCount() - 1);

        }
        for (int i = 0; i < noTerminos; i++) {
            amplitud[i] = 0;
            frecuencia[i] = 0;
            datos[i] = 0;
        }

        var1 = 0;
        var2 = 0;
        var3 = 0;
        var4 = 0;
        var5 = 0;
    }

}
