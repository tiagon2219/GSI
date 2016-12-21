/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pqm.client;

import Documents.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.xwpf.usermodel.*;

/**
 *
 * @author Tiago
 */
public class DocGenerator {
    
    public static Documento pqm = new Documento();
    
    static ArrayList <String> proc_ti = new ArrayList<String>();
    static ArrayList <String> proc_criticos = new ArrayList<String>();
    static ArrayList <String> proc_gastador = new ArrayList<String>();
    
    public DocGenerator(Documento in){
        teste t1 = new teste();
        t1.doc_teste = in;
        pqm = t1.escreve();
    }
    
    public void work(){
        FileOutputStream out= null;
        try {
            XWPFDocument document= new XWPFDocument();
            out = new FileOutputStream(
                    new File("C:\\Users\\Tiago\\Desktop\\gsiteste.docx"));
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText("PQM");
            paragraph = document.createParagraph();
            run = paragraph.createRun();
            run.setText("2015-2016");
            document.createParagraph();
            gerar_introducao(document);
            document.createParagraph();
            gerar_missao(document);
            document.createParagraph();
            //gerar_influencias(document);
            document.createParagraph();
            gerar_fcs(document);
            document.createParagraph();
            gerar_pn(document);
            document.createParagraph();
            tabela_fcs_pn(document); /*tabela fcs vs pcn*/
            document.createParagraph();
            gerar_matriz1(document);
            document.createParagraph();
            tabela_importancia(document); /*tabela analise */
            document.createParagraph();
            gerar_matriz2(document);
            document.createParagraph();
            gerar_conclusao(document);
            document.write(out);
            out.close();
            System.out.println("createdocument.docx written successully");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DocGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DocGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(DocGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void gerar_introducao(XWPFDocument document){
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("INTRODUCAO:");
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.setText(pqm.introducao.texto);
    }
    
    
    
    public static void tabela_fcs_pn(XWPFDocument document){
        
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("MATRIZES DA METODOLOGIA PQM:");
        
        XWPFTable table = document.createTable();
        XWPFTableRow tableRowOne = table.getRow(0);
        tableRowOne.getCell(0).setText("");
        
        teste t1 = new teste();
        pqm = t1.escreve();
        
        /* ler fcs e colocar na tabela*/
        int num_fcs = pqm.fcs.size();
        for(int i=0; i<num_fcs; i++){
            tableRowOne.addNewTableCell().setText(pqm.fcs.get(i).texto);
        }
        
        /*criar analise */
        for(int i=0; i<3;i++){
            tableRowOne.addNewTableCell();
        }
        tableRowOne.getCell(num_fcs+1).setText("Tot. Imp");
        tableRowOne.getCell(num_fcs+2).setText("Qual. Proc.");
        tableRowOne.getCell(num_fcs+3).setText("Proc. Gast");
        
        /*processos*/
        for(int j=0; j<pqm.processosNegocio.processos.size(); j++){
            int contador = 0;
            XWPFTableRow tableRow = table.createRow();
            tableRow.getCell(0).setText(""+pqm.processosNegocio.processos.get(j).id+" - "+pqm.processosNegocio.processos.get(j).texto);
            int cont_cell = pqm.processosNegocio.processos.get(j).relacoes.size();
            for(int x=0; x< cont_cell; x++){
                if(pqm.processosNegocio.processos.get(j).relacoes.get(x).estado == true){
                    tableRow.getCell(x+1).setText("x");
                    contador++;
                }
            }
            cont_cell++;
            tableRow.getCell(cont_cell).setText(Integer.toString(contador));
            cont_cell++;
            tableRow.getCell(cont_cell).setText(pqm.processosNegocio.processos.get(j).qualidadep);
            cont_cell++;
            if(pqm.processosNegocio.processos.get(j).gastador == true){
               tableRow.getCell(cont_cell).setText("x"); 
            }
        }
        
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.setText("Tabela 1 - Analise dos FCS nos PN");
        
        
        
    }
    
    public static void gerar_matriz1(XWPFDocument document){
        
        /*criar matrizes*/
        XWPFTable table1 = document.createTable();
        

        
        XWPFTableRow tableRow = table1.getRow(0);
        tableRow.getCell(0).setText("   4   ");
       
        for(int i=3; i>0; i--){
            table1.createRow().getCell(0).setText("   "+Integer.toString(i)+"   ");         
        }
        
        table1.createRow().getCell(0).setText("");
        
        for(int i=0; i<=4; i++){
            for(int j=1; j<=5; j++){
                table1.getRow(i).addNewTableCell();
            }
        }
        
        
        table1.getRow(4).getCell(1).setText("   E   ");
        table1.getRow(4).getCell(2).setText("   D   ");
        table1.getRow(4).getCell(3).setText("   C   ");
        table1.getRow(4).getCell(4).setText("   B   ");
        table1.getRow(4).getCell(5).setText("   A   ");
        
                   
        
        /*preencher matriz*/
        
        teste t1 = new teste();
        pqm = t1.escreve();
        
        
        int tot_imp = 0;
        String qual;
        int qual_int=0;
        String id_p;
        for(int p=0; p<pqm.processosNegocio.processos.size(); p++){
            for(int t=0; t<pqm.processosNegocio.processos.get(p).relacoes.size(); t++){
                if(pqm.processosNegocio.processos.get(p).relacoes.get(t).estado == true)
                    tot_imp++;
            }
            qual = pqm.processosNegocio.processos.get(p).qualidadep;
            
            if(qual.equals("E") || qual.equals("e") )
                qual_int = 1;
            else if(qual.equals("D") || qual.equals("d") )
                qual_int = 2;
            if(qual.equals("C") || qual.equals("c") )
                qual_int = 3;
            if(qual.equals("B") || qual.equals("b") )
                qual_int = 4;
            if(qual.equals("A") || qual.equals("a") )
                qual_int = 5;
            
            
            id_p = pqm.processosNegocio.processos.get(p).id;
            System.out.println("tot_imp: "+tot_imp);
            table1.getRow(4-tot_imp).getCell(qual_int).setText(id_p+" ");
            
            if(pqm.processosNegocio.processos.get(p).gastador == true){
                proc_gastador.add(pqm.processosNegocio.processos.get(p).id);
            }
            
            if(4-tot_imp == 4 && qual_int == 1 || qual_int == 2 || qual_int == 3){
                proc_criticos.add(id_p);
                proc_criticos.add(pqm.processosNegocio.processos.get(p).texto);
            }
            else if(4-tot_imp == 3 && qual_int == 1 || qual_int == 2){
                proc_criticos.add(id_p);
                proc_criticos.add(pqm.processosNegocio.processos.get(p).texto);
            }
            else if(4-tot_imp == 2 && qual_int == 1){
                proc_criticos.add(id_p);
                proc_criticos.add(pqm.processosNegocio.processos.get(p).texto);
            }
            
            tot_imp = 0;
        } 
        
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("Tabela 2 - Divisao dos Processos por nivel de importancia e criticidade");
    }
  
    
    public static void tabela_importancia(XWPFDocument document){
        /*criar tabela pn vs fcs */
        XWPFTable table = document.createTable();
        XWPFTableRow tableRowOne = table.getRow(0);
        tableRowOne.getCell(0).setText("");
        /*tableRowOne.addNewTableCell().setText("FC1");
        tableRowOne.addNewTableCell().setText("FC2");*/
       
        teste t1 = new teste();
        pqm = t1.escreve();
        
        /* ler fcs e colocar na tabela*/
        int num_fcs = pqm.fcs.size();
        for(int i=0; i<num_fcs; i++){
            tableRowOne.addNewTableCell().setText(pqm.fcs.get(i).texto);
        }
        
        /*criar analise */
        for(int i=0; i<5;i++){
            tableRowOne.addNewTableCell();
        }
        
        /*escrever analise*/
        tableRowOne.getCell(num_fcs+1).setText("Tot. Imp");
        tableRowOne.getCell(num_fcs+2).setText("Qual. Proc.");
        tableRowOne.getCell(num_fcs+3).setText("Proc. Gast");
        tableRowOne.getCell(num_fcs+4).setText("Qual. Neg.");
        tableRowOne.getCell(num_fcs+5).setText("Qual. Tecn.");


        /*processos*/
        for(int j=0; j<pqm.processosNegocio.processos.size(); j++){
            int contador = 0;
            XWPFTableRow tableRow = table.createRow();
            tableRow.getCell(0).setText(""+pqm.processosNegocio.processos.get(j).id+" - "+pqm.processosNegocio.processos.get(j).texto);
            int cont_cell = pqm.processosNegocio.processos.get(j).relacoes.size();
            for(int x=0; x< cont_cell; x++){
                if(pqm.processosNegocio.processos.get(j).relacoes.get(x).estado == true){
                    tableRow.getCell(x+1).setText("x");
                    contador++;
                }
            }
            cont_cell++;
            tableRow.getCell(cont_cell).setText(Integer.toString(contador));
            cont_cell++;
            tableRow.getCell(cont_cell).setText(pqm.processosNegocio.processos.get(j).qualidadep);
            cont_cell++;
            if(pqm.processosNegocio.processos.get(j).gastador == true){
               tableRow.getCell(cont_cell).setText("x"); 
            }
            cont_cell++;
            tableRow.getCell(cont_cell).setText(pqm.processosNegocio.processos.get(j).qualidaden);
            cont_cell++;
            tableRow.getCell(cont_cell).setText(pqm.processosNegocio.processos.get(j).qualidadet);
        }
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("Tabela 3 - Analise da importancia dos FCS nos PN e nas IT associadas");
    }
    
      public static void gerar_matriz2(XWPFDocument document){
        
        /*criar matrizes*/
        XWPFTable table1 = document.createTable();
        XWPFTableRow tableRow = table1.getRow(0);
        tableRow.getCell(0).setText("   A   ");
       
        table1.createRow().getCell(0).setText("   B   ");         
        table1.createRow().getCell(0).setText("   C   ");
        table1.createRow().getCell(0).setText("   D   ");
        table1.createRow().getCell(0).setText("");
        
        for(int i=0; i<=4; i++){
            for(int j=1; j<=4; j++){
                table1.getRow(i).addNewTableCell();
            }
        }
        
        table1.getRow(4).getCell(1).setText("    D    ");
        table1.getRow(4).getCell(2).setText("    C    ");
        table1.getRow(4).getCell(3).setText("    B    ");
        table1.getRow(4).getCell(4).setText("    A    ");
        
                   
        
        /*preencher matriz*/
        
        teste t1 = new teste();
        pqm = t1.escreve();
        
        int qual_neg = 0;
        String qual_n;
        String qual_t;
        int qual_int=0;
        String id_p;
        for(int p=0; p<pqm.processosNegocio.processos.size(); p++){
            
            qual_n = pqm.processosNegocio.processos.get(p).qualidaden;
            if(qual_n.equals("D") || qual_n.equals("d") )
                qual_neg = 3;
            else if(qual_n.equals("C") || qual_n.equals("c") )
                qual_neg = 2;
            if(qual_n.equals("B") || qual_n.equals("b") )
                qual_neg = 1;
            if(qual_n.equals("A") || qual_n.equals("a") )
                qual_neg = 0;
           
            
            
            qual_t = pqm.processosNegocio.processos.get(p).qualidadet;
            
            if(qual_t.equals("D") || qual_t.equals("d") )
                qual_int = 1;
            else if(qual_t.equals("C") || qual_t.equals("c") )
                qual_int = 2;
            if(qual_t.equals("B") || qual_t.equals("b") )
                qual_int = 3;
            if(qual_t.equals("A") || qual_t.equals("a") )
                qual_int = 4;
           
            
            
            
            id_p = pqm.processosNegocio.processos.get(p).id;
            table1.getRow(qual_neg).getCell(qual_int).setText(id_p+" ");
            
            if(qual_neg == 1 || qual_neg == 0 && qual_int == 1 || qual_int == 2){
                proc_ti.add(id_p);
                proc_ti.add(pqm.processosNegocio.processos.get(p).texto);
            } 
            
            qual_neg = 0;
            qual_int = 0;
            
        }
        
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("Tabela 4 - Divisao dos processos por nivel de criticidade");
    }
    
      public static void gerar_conclusao(XWPFDocument document){
          
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("CONCLUSAO:");
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.setText("Apos uma analise das tabelas geradas podemos concluir que os processos mais criticos sao: ");
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        for(int i=0; i<proc_criticos.size(); i=i+2){
            run.setText(proc_criticos.get(i)+" - "+proc_criticos.get(i+1));
            paragraph = document.createParagraph();
            run = paragraph.createRun();
        }
        run.setText("Tambem podemos verificar quais os processos gastadores, que sao: ");
        for(int i=0; i<proc_gastador.size(); i++){
            if(i<proc_gastador.size()-1)
                run.setText(proc_gastador.get(i)+", ");
            else run.setText(proc_gastador.get(i)+".");
        }
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.setText("Por fim podemos verificar que os processos com as ti inadequadas sao: ");
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        for(int i=0; i<proc_ti.size(); i=i+2){
            run.setText(proc_ti.get(i)+" - "+proc_ti.get(i+1));
            paragraph = document.createParagraph();
            run = paragraph.createRun();
        }
        
        
          
      }


    public static void gerar_missao(XWPFDocument document) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("MISSAO:");
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.setText(pqm.missao.texto);
    }

    public static void gerar_influencias(XWPFDocument document) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("INFLUENCIAS DOMINANTES:");
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        run.setText("Relacoes Ascendentes: ");
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        for(int i=0; i<pqm.influenciasDominantes.relacoesAscendentes.size(); i++){
            run.setText(pqm.influenciasDominantes.relacoesAscendentes.get(i).texto);
             paragraph = document.createParagraph();
            run = paragraph.createRun();
        }
        
        run.setText("Relacoes Equivalentes:");
        for(int i=0; i<pqm.influenciasDominantes.relacoesEquivalentes.size(); i++){
            run.setText(pqm.influenciasDominantes.relacoesEquivalentes.get(i).texto);
            paragraph = document.createParagraph();
            run = paragraph.createRun();
        }
        
        run.setText("Relacoes Externas");
        for(int i=0; i<pqm.influenciasDominantes.relacoesExternas.size(); i++){
            run.setText(pqm.influenciasDominantes.relacoesExternas.get(i).texto);
             paragraph = document.createParagraph();
            run = paragraph.createRun();
        }
        
        run.setText("Controlo Funcional: ");
        for(int i=0; i<pqm.influenciasDominantes.controloFuncional.size(); i++){
            run.setText(pqm.influenciasDominantes.controloFuncional.get(i).texto);
             paragraph = document.createParagraph();
            run = paragraph.createRun();
        }
    }

    public static void gerar_fcs(XWPFDocument document) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("FACTORES CRITICOS DE SUCESSO:");
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        for(int i=0; i<pqm.fcs.size(); i++){
            run.setText(pqm.fcs.get(i).texto);
            paragraph = document.createParagraph();
            run = paragraph.createRun();
        }
    }

    public static void gerar_pn(XWPFDocument document) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("PROCESSOS DE NEGOCIO:");
        paragraph = document.createParagraph();
        run = paragraph.createRun();
        for(int i=0; i<pqm.processosNegocio.processos.size(); i++){
            run.setText(pqm.processosNegocio.processos.get(i).id+" - "+pqm.processosNegocio.processos.get(i).texto);
            paragraph = document.createParagraph();
            run = paragraph.createRun();
        }
    }
    
}
