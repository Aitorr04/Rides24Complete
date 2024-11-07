package gui;

import domain.Driver;
import domain.DriverAdapter;

import javax.swing.*;
import java.awt.*;

public class DriverTable extends JFrame {
    private Driver driver;
    private JTable tabla;

    public DriverTable(Driver driver) {
        super(driver.getUsername() + "’s rides");
        this.setBounds(100, 100, 700, 200);
        this.driver = driver;
        DriverAdapter adapt	= new DriverAdapter(driver);
        tabla =	new JTable(adapt);
        tabla.setPreferredScrollableViewportSize(new Dimension(500, 70));
        //Creamos un JscrollPane y le agregamos la JTable
        JScrollPane	scrollPane = new JScrollPane(tabla);
        //Agregamos el JScrollPane al contenedor
        getContentPane().add(scrollPane,BorderLayout.CENTER);
    }
}