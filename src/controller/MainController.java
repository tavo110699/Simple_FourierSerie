/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
    private   double var1 = 0;

    private double amplitud[];
    private double frecuencia[];
    private int noTerminos;
    private double datos[];
    private double numInter;
    public Double datos1[][] = {};

//inicializa las vistas 
    public MainController(MainView mainView) {

        this.mainView = mainView;
        this.mainView.setVisible(true);
        initEvents();
        configViews();

    }

    //desabilita el boton generar
    public void configViews() {
        mainView.btnGenerate.setEnabled(false);
    }

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

    }

    public void getData() {

        T = Integer.parseInt(mainView.tField.getText().toString());
        intervalo = Double.parseDouble(mainView.intervalosField.getText().toString());
        tiempo = Integer.parseInt(mainView.tiempoField.getText().toString());
        initVectors(mainView.terms.getSelectedIndex() + 1);
    }

    public void diableFields() {

        mainView.intervalosField.setEditable(false);
        mainView.tField.setEditable(false);
        mainView.tiempoField.setEditable(false);
        mainView.terms.setEnabled(false);

    }

    public void initVectors(int numTerms) {

        amplitud = new double[numTerms];
        frecuencia = new double[numTerms];
    }

    public void getDataAmFrec(int numTerms) {

        for (int i = 0; i < numTerms; i++) {

            String value = JOptionPane.showInputDialog(mainView, "Ingresa la frecuencia " + (i + 1));
            frecuencia[i] = Float.parseFloat(value);
            String valueAmplitud = JOptionPane.showInputDialog(mainView, "Ingresa la amplitud " + (i + 1));
            amplitud[i] = Float.parseFloat(value);
        }
    }

    public void table(int numTerminos) {

        XYSeries series = new XYSeries("Termino 1 ");

        double res;
        double seno = 0;
        double ft;
        float aux = (float) intervalo;

        DefaultTableModel modelo;
        String info[] = {"intervalo de tiempo", "Termino 1", "Termino 2", "Termino 3", "Termino 4", "Termino 5"};
        Double datos1[][] = {};
        modelo = new DefaultTableModel(datos1, info);
        mainView.tableDataResult.setModel(modelo);
        numInter = tiempo / intervalo;

        do {
            if (numTerminos == 1) {

                for (int i = 0; i < numInter; i++) {

                    var1 += amplitud[0] * Math.sin((frecuencia[0] * Math.PI * intervalo) / T);

                    System.out.println(var1);

                    Object datos[] = {intervalo, var1};
                    modelo.addRow(datos);
                    intervalo = (float) (intervalo + aux);

                    series.add(intervalo, var1);

                }
                break;
            } else if (numTerminos == 2) {

                double var1 = 0;
                double var2 = 0;
                for (int i = 0; i < numInter; i++) {

                    var1 += amplitud[0] * Math.sin((frecuencia[0] * Math.PI * intervalo) / T);
                    var2 += amplitud[1] * Math.sin((frecuencia[1] * Math.PI * intervalo) / T);

                    Object datos[] = {intervalo, var1};
                    modelo.addRow(datos);
                    Object datos2[] = {intervalo, var2};
                    modelo.addRow(datos2);
                    intervalo = (float) (intervalo + aux);

                }
                break;

            }

        } while (intervalo > tiempo);
           //grafica
                 
                    XYSeriesCollection dataset = new XYSeriesCollection();
                    dataset.addSeries(series);
                    
                    
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Serie de fourier simplificada", // Título
                "Intervalo (x)", // Etiqueta Coordenada X
                "Termino", // Etiqueta Coordenada Y
                dataset, // Datos
                PlotOrientation.VERTICAL,
                true, // Muestra la leyenda de los productos (Producto A)
                false,
                false
        );

        // Mostramos la grafica en pantalla
        ChartFrame frame = new ChartFrame("Serie fourierl", chart);
        frame.pack();
        frame.setVisible(true);
    }



}
