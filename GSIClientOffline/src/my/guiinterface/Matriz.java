/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.guiinterface;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.*;
import javax.swing.table.*;

/**
 *
 * @author ritajoao
 */
public class Matriz extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable table;

    public Matriz(ArrayList <String> fcs_array,ArrayList <String> processos_array) {
        
        
        int dim2= processos_array.size();
        int dim1= fcs_array.size();
        int i;
        String []fcs_array2 = new String[fcs_array.size()];
        fcs_array.toArray(fcs_array2);
        String []processos_array2 = new String[processos_array.size()];
        processos_array.toArray(processos_array2);
        
        Object[] columnNames = new Object[fcs_array.size()+1+5];//3 colunas finais
        columnNames[0] = "Processo";
        columnNames[1] = "Total de impactos";
        columnNames[2] = "Qualidade do Processo";
        columnNames[3] = "Processos gastadores";
        columnNames[4] = "Qualidade de Negócio";
        columnNames[5] = "Qualidade Tecnica";
        
        for (i=0; i<fcs_array.size();i++)
        {
            columnNames[i+1+5]=fcs_array2[i];
        }
        
        Object[][] data= new Object[processos_array.size()][fcs_array.size()+1+5];
        
        for (i=0; i<processos_array.size();i++)
        {
            data[i][0]=processos_array2[i];
        }
        
        for (i=0; i<dim2;i++)
        {
            for(int j=0; i<dim1;i++)
            {
                data[i][j+1+5]=false;
            }
        }
        
        
        
        
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table = new JTable(model) {

            private static final long serialVersionUID = 1L;

            /*@Override
            public Class getColumnClass(int column) {
            return getValueAt(0, column).getClass();
            }*/
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return Integer.class;
                    case 2:
                        return String.class;
                    case 4:
                        return String.class;
                    case 5:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
        };
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 344, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            ArrayList <String> fcs_array;
            ArrayList <String> processos_array;
            @Override
            public void run() {
                
                Matriz frame = new Matriz(fcs_array, processos_array);
                
                
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
