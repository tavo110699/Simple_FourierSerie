/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Color;
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
public class Graphic {
        
        XYSeries series = new XYSeries("Termino 1 ");
        XYSeries series2 = new XYSeries("Termino 2 ");
        XYSeries series3 = new XYSeries("Termino 3 ");
        XYSeries series4 = new XYSeries("Termino 4 ");
        XYSeries series5 = new XYSeries("Termino 5 ");
        
    public void generateGraph(){
    MainController mc = new MainController();
        XYSeriesCollection dataset = new XYSeriesCollection();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.ORANGE);
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesPaint(2, Color.GREEN);
        renderer.setSeriesPaint(3, Color.YELLOW);
        renderer.setSeriesPaint(4, Color.PINK);

        dataset.addSeries(series);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        dataset.addSeries(series4);
        dataset.addSeries(series5);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Serie de fourier simplificada", // Título
                " Tiempo (x)", // Etiqueta Coordenada X
                "Amplitud (Y)", // Etiqueta Coordenada Y
                dataset, // Datos
                PlotOrientation.VERTICAL,
                true, // Muestra la leyenda de los terminos
                false,
                false
        );
        
        // Mostramos la grafica en pantalla
        ChartFrame frame = new ChartFrame("Serie fourierl", chart);
        frame.pack();
        frame.setVisible(true);
    }
}
