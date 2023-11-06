/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo_3;

import java.util.Calendar;
import java.util.Scanner;
import java.util.Stack;
import javax.swing.JOptionPane; 
import javax.swing.JTextArea; 
import Presentacion.FrameInterprete;

/**
 *
 * @author FRANKLIN SALAS
 */
public class Interprete {
    public Codigo3 c3;
    private  TSS tss;
    private  TSVAR tsVar;
    
    
   
private  int  IP;
private  int  BP;
private  int  GB;

private Stack <Integer> pila;
    public Interprete( String filename) {
        open(filename);
    }

   public Interprete() {
   c3= null;
   pila= new Stack<Integer>();
   }
    
   public  void  open (String filename){
   
    c3 = new Codigo3();
    boolean exito =c3.Open(filename);
   
    if(exito){
     tss = c3.getTSS();
    tsVar = c3.getTSVar();
    }else{
    
    c3=null;
    }
   }
   public  boolean  HayCodigoCargado(){
   iniciar();
   return  c3!=null && IP!=-1;
   }
   public  void run(){
   

       if (!HayCodigoCargado()) {
          return;
       }
      
       while (IP<c3.lenght()) {           
           interpretarCuadrupla(  c3.getCuadrupla(IP)); 
       }
            
       
   }
   public  void stop(){ IP = c3.lenght();}
   public void iniciar(){
       for (int i = 0; i < tsVar.lenght(); i++) {
          if("$_MAIN".equals(tsVar.getTupla(i).getNombreID())||"$MAIN".equals(tsVar.getTupla(i).getNombreID())){
          IP= tsVar.getTupla(i).getValorI();
         int temp = tsVar.getTupla(i).getCantTmp();
          while (temp>0) {                
            pila.add(Integer.MAX_VALUE);
            temp--;
            }
          BP=-1;
          GB=-2;
          return;
          }
       }
   IP=-1;
   }
   private  void  interpretarCuadrupla(Cuadrupla c){
   switch(c.getOpCode()){
       
        case Cuadrupla.NOP:  { 
//            System.out.println("No-Opetration");
//            IP++;
            OPMAYNOP(c);
        }// traduccion  de NOP
               break; 
        case Cuadrupla.OPNL:  { FrameInterprete.jTextArea1.append("\n"); IP++;}// traduccion  de OPNL
               break; 
        case Cuadrupla.OPRET: { while (pila.size()-1>BP){pila.pop();} BP=GB; GB= pila.isEmpty()? -2 : pila.pop(); IP =pila.isEmpty() ? Integer.MAX_VALUE :pila.pop();}// traduccion  de OPRET
               break; 
        case Cuadrupla.OPCALL: {int p=-c.getDir(1); pila.push(IP+1); 
        IP=tsVar.getTupla(p).getValorI();pila.push(GB); GB= BP;BP= pila.size()-1;
        int temp = tsVar.getTupla(p).getCantTmp();
       
        while (temp>0) {                
            pila.add(Integer.MAX_VALUE);
            temp--;
            }
        }// traduccion  de OPCALL

                   break; 
         case Cuadrupla.OPGOTO: {  for (int i = 0; i < c3.lenght(); i++) {
          if(Cuadrupla.ETIQUETA == c3.getCuadrupla(i).getOpCode() &&c3.getCuadrupla(i).getDir(1)==c.getDir(1)){
          IP= i;
          }
       }}; break;
         case Cuadrupla.OPINC: {if(
                 c.getDir(1)<=0){     int p =  - c.getDir(1);
           int s = tsVar.getTupla(p).getValorI();
           s++; tsVar.getTupla(p).setValorI(s);}
         else{ int p =pila.get(BP+c.getDir(1));
         pila.set(BP+c.getDir(1), p+1);}
            IP++;
           } // traduccion  de OPINC
               break;   
               
             case Cuadrupla.OPDEC: {if(- c.getDir(1)<=0){     int p =  - c.getDir(1);
           int s = tsVar.getTupla(p).getValorI();
           s--; tsVar.setValorI(p, s);}
             else{int p =pila.get(BP+c.getDir(1));pila.set(BP+c.getDir(1), p-1);}
            IP++;
           } // traduccion  de OPDEC
               break; 
        case Cuadrupla.OPWRITE: {int s=0; if(c.getDir(1)<=0)
        {int p =  - c.getDir(1);s = tsVar.getTupla(p).getValorI();}
        else{ s =pila.get(BP+c.getDir(1));  }
           IP++;
       
         //   FrameInterprete.jTextArea1.list();
         FrameInterprete.jTextArea1.append(""+s+"\n");
      FrameInterprete.jTextArea1.setCaretPosition(FrameInterprete.jTextArea1.getDocument().getLength());
        } // traduccion  de OPWRITE
               break;  
         case Cuadrupla.OPREAD: {int p =  -c.getDir(1);
         int v = Integer.valueOf(JOptionPane.showInputDialog(null,"valor","introdusca", 1));
          FrameInterprete.jTextArea1.append(v +"\n");
           tsVar.getTupla(p).setValorI(v);
           
           IP++; 
           }   // traduccion  de OPWRITES
               break;
             
        case Cuadrupla.OPWRITES: {int p =  -c.getDir(1);
           String s = tss.getStr(p);   IP++; 
     
         FrameInterprete.jTextArea1.append(s );
        FrameInterprete.jTextArea1.setCaretPosition(FrameInterprete.jTextArea1.getDocument().getLength());
        }   // traduccion  de OPWRITES
               break;
        case Cuadrupla.ETIQUETA: IP++; break;  //////
            
        case Cuadrupla.OPNOT:{if(
                 c.getDir(1)<=0){     int p =  - c.getDir(1);
           int s = tsVar.getTupla(p).getValorI();
           if(s==0){s=1;}else{s=0;}
            tsVar.getTupla(p).setValorI(s);}
         else{ int p =pila.get(BP+c.getDir(1));
          if(p==0){p=1;}else{p=0;}
         pila.set(BP+c.getDir(1), p);}
            IP++;}; break;
        case Cuadrupla.OPAND:{OPAND(c); IP++;} break;
        case Cuadrupla.OPOR:{OPOR(c); IP++;} break;
        case Cuadrupla.OPSUMA: {OPSUMA(c); IP++;} break;
        case Cuadrupla.OPMENOS:{OPREST(c); IP++;} break;
        case Cuadrupla.OPPOR:{  OPPOR(c); IP++;} break;
        case Cuadrupla.OPDIV:{OPDIV(c); IP++;} break;
        case Cuadrupla.OPMOD:{OPMOD(c); IP++;} break;
        case Cuadrupla.OPMEN:{ OPMEN(c); IP++;} break;
        case Cuadrupla.OPMAY:{OPMAY(c);  IP++;} break;
        case Cuadrupla.OPIGUAL:{ OPIGUAL(c); IP++;} break;
        case Cuadrupla.OPDIS:{ OPDIST(c); IP++;} break;
        case Cuadrupla.OPMEI:{ OPMEI(c);IP++;} break; 
        case Cuadrupla.OPMAI:{ OPMAI(c); IP++;} break;
        case Cuadrupla.OPIF0:{ int t= pila.get(c.getDir(1)+BP);if(t!=0){ IP++;}else{
             Cuadrupla n = new Cuadrupla(Cuadrupla.OPGOTO,c.getDir(2),0, 0);
             interpretarCuadrupla(n);
         }} break;
        case Cuadrupla.OPIF1: {
           int t = pila.get(c.getDir(1) + BP);
           if (t != 1) {
               IP++;
           } else {
               Cuadrupla n = new Cuadrupla(Cuadrupla.OPGOTO, c.getDir(2), 0, 0);
               interpretarCuadrupla(n);
           }
              } break;
         case Cuadrupla.OPASIGNID:{
            int b= tsVar.getValorI(-c.getDir(2));
            tsVar.setValorI(-c.getDir(1), b);
             
             IP++;} break;
         case Cuadrupla.OPASIGNNUM:{  
             if(c.getDir(1)<=0)
         {tsVar.getTupla(-c.getDir(1)).setValorI(c.getDir(2));}
             else{pila.set(c.getDir(1)+BP,c.getDir(2));} IP++;} break;
               }
   
   }
 public void OPAND(Cuadrupla c){ 
int y=0;int z=0; if(c.getDir(2)<=0){y =tsVar.getTupla(-c.getDir(2)).getValorI();}
else{y=pila.get(c.getDir(2)+BP);} 
if(c.getDir(3)<=0){z =tsVar.getTupla(-c.getDir(3)).getValorI();}
else{z=pila.get(c.getDir(3)+BP);}
int p = (y>0&&z>0)? 1:0;
if(c.getDir(1)<=0){tsVar.getTupla(-c.getDir(1)).setValorI(p);}
else{pila.set(c.getDir(1)+BP,p);}


}
  public void OPOR(Cuadrupla c){ 
int y=0;int z=0; if(c.getDir(2)<=0){y =tsVar.getTupla(-c.getDir(2)).getValorI();}
else{y=pila.get(c.getDir(2)+BP);} 
if(c.getDir(3)<=0){z =tsVar.getTupla(-c.getDir(3)).getValorI();}
else{z=pila.get(c.getDir(3)+BP);}
int p = (y>0||z>0)? 1:0;
if(c.getDir(1)<=0){tsVar.getTupla(-c.getDir(1)).setValorI(p);}
else{pila.set(c.getDir(1)+BP,p);}


}
public void OPSUMA(Cuadrupla c){ 
int y=0;int z=0; if(c.getDir(2)<=0){y =tsVar.getTupla(-c.getDir(2)).getValorI();}
else{y=pila.get(c.getDir(2)+BP);} 
if(c.getDir(3)<=0){z =tsVar.getTupla(-c.getDir(3)).getValorI();}
else{z=pila.get(c.getDir(3)+BP);}
int p = y+z;
if(c.getDir(1)<=0){tsVar.getTupla(-c.getDir(1)).setValorI(p);}
else{pila.set(c.getDir(1)+BP,p);}


}
public void OPREST(Cuadrupla c){ 
int y=0;int z=0; if(c.getDir(2)<=0){y =tsVar.getTupla(-c.getDir(2)).getValorI();}
else{y=pila.get(c.getDir(2)+BP);} 
if(c.getDir(3)<=0){z =tsVar.getTupla(-c.getDir(3)).getValorI();}
else{z=pila.get(c.getDir(3)+BP);}
int p = y-z;
if(c.getDir(1)<=0){tsVar.getTupla(-c.getDir(1)).setValorI(p);}
else{pila.set(c.getDir(1)+BP,p);}
}
public void OPDIV(Cuadrupla c){ 
int y=0;int z=0; if(c.getDir(2)<=0){y =tsVar.getTupla(-c.getDir(2)).getValorI();}
else{y=pila.get(c.getDir(2)+BP);} 
if(c.getDir(3)<=0){z =tsVar.getTupla(-c.getDir(3)).getValorI();}
else{z=pila.get(c.getDir(3)+BP);}
int p = y/z;
if(c.getDir(1)<=0){tsVar.getTupla(-c.getDir(1)).setValorI(p);}
else{pila.set(c.getDir(1)+BP,p);}


}
public void OPMOD(Cuadrupla c){ 
int y=0;int z=0; if(c.getDir(2)<=0){y =tsVar.getTupla(-c.getDir(2)).getValorI();}
else{y=pila.get(c.getDir(2)+BP);} 
if(c.getDir(3)<=0){z =tsVar.getTupla(-c.getDir(3)).getValorI();}
else{z=pila.get(c.getDir(3)+BP);}
int p = y%z;
if(c.getDir(1)<=0){tsVar.getTupla(-c.getDir(1)).setValorI(p);}
else{pila.set(c.getDir(1)+BP,p);}


}
public void OPPOR(Cuadrupla c){ int y=0;int z=0; if(c.getDir(2)<=0){y =tsVar.getTupla(-c.getDir(2)).getValorI();}
else{y=pila.get(c.getDir(2)+BP);} 
if(c.getDir(3)<=0){z =tsVar.getTupla(-c.getDir(3)).getValorI();}
else{z=pila.get(c.getDir(3)+BP);}
int p = y*z;
if(c.getDir(1)<=0){tsVar.getTupla(-c.getDir(1)).setValorI(p);}
else{pila.set(c.getDir(1)+BP,p);}

}
public void OPMEN(Cuadrupla c){ int y=0;int z=0; if(c.getDir(2)<=0){y =tsVar.getTupla(-c.getDir(2)).getValorI();}
else{y=pila.get(c.getDir(2)+BP);} 
if(c.getDir(3)<=0){z =tsVar.getTupla(-c.getDir(3)).getValorI();}
else{z=pila.get(c.getDir(3)+BP);}
int p = y<z?1:0;
pila.set(c.getDir(1)+BP,p);
}
public void OPMAY(Cuadrupla c){ int y=0;int z=0; if(c.getDir(2)<=0){y =tsVar.getTupla(-c.getDir(2)).getValorI();}
else{y=pila.get(c.getDir(2)+BP);} 
if(c.getDir(3)<=0){z =tsVar.getTupla(-c.getDir(3)).getValorI();}
else{z=pila.get(c.getDir(3)+BP);}
int p = y>z?1:0;
pila.set(c.getDir(1)+BP,p);
}

public void OPMAYNOP(Cuadrupla c){ 
        int y = 0;
        int z = 0;
        if (c.getDir(1) <= 0) {
            y = tsVar.getTupla(-c.getDir(1)).getValorI();
        } else {
            y = pila.get(c.getDir(1) + BP);
        }
        if (c.getDir(2) <= 0) {
            z = tsVar.getTupla(-c.getDir(2)).getValorI();
        } else {
            z = pila.get(c.getDir(2) + BP);
        }
        int p = y > z ? 1 : 0;
        //pila.set(c.getDir(1) + BP, p);
        if (p == 0) {
            IP++;
        } else {
            Cuadrupla n = new Cuadrupla(Cuadrupla.OPGOTO, c.getDir(3), 0, 0);
            interpretarCuadrupla(n);
        }
    }

public void OPIGUAL(Cuadrupla c){ int y=0;int z=0; if(c.getDir(2)<=0){y =tsVar.getTupla(-c.getDir(2)).getValorI();}
else{y=pila.get(c.getDir(2)+BP);} 
if(c.getDir(3)<=0){z =tsVar.getTupla(-c.getDir(3)).getValorI();}
else{z=pila.get(c.getDir(3)+BP);}
int p = y==z?1:0;
pila.set(c.getDir(1)+BP,p);
}
public void OPDIST(Cuadrupla c){ int y=0;int z=0; if(c.getDir(2)<=0){y =tsVar.getTupla(-c.getDir(2)).getValorI();}
else{y=pila.get(c.getDir(2)+BP);} 
if(c.getDir(3)<=0){z =tsVar.getTupla(-c.getDir(3)).getValorI();}
else{z=pila.get(c.getDir(3)+BP);}
int p = y!=z?1:0;
pila.set(c.getDir(1)+BP,p);
}

public void OPMEI(Cuadrupla c){ int y=0;int z=0; if(c.getDir(2)<=0){y =tsVar.getTupla(-c.getDir(2)).getValorI();}
else{y=pila.get(c.getDir(2)+BP);} 
if(c.getDir(3)<=0){z =tsVar.getTupla(-c.getDir(3)).getValorI();}
else{z=pila.get(c.getDir(3)+BP);}
int p = y<=z?1:0;
pila.set(c.getDir(1)+BP,p);
}
public void OPMAI(Cuadrupla c){ int y=0;int z=0; if(c.getDir(2)<=0){y =tsVar.getTupla(-c.getDir(2)).getValorI();}
else{y=pila.get(c.getDir(2)+BP);} 
if(c.getDir(3)<=0){z =tsVar.getTupla(-c.getDir(3)).getValorI();}
else{z=pila.get(c.getDir(3)+BP);}
int p = y>=z?1:0;
pila.set(c.getDir(1)+BP,p);
}
    @Override
    public String toString() {
        return c3.toString();
    }
   
   
}
