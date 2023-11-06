package codigo_3;
 
import Presentacion.FrameInterprete;
import java.util.Calendar;

/**
 *
 * @author Gino Barroso Viruez
 */
public class Codigo_3 {
   
    public static void Mostrar(Codigo3 c3){ //Para mostrar las tablas y el C3.
        System.out.println(c3.getTSVar());
        System.out.println(c3.getTSS());
        System.out.println(c3);
    }
    
    
    public static void main(String[] args) {
    //interprete es diferente q maquina irual
     // long t1,t2, dif;
      // Calendar ahora1 = Calendar.getInstance();
        //      t1 = ahora1.getTimeInMillis();
   //  Codigo3 c = new Codigo3();
  ///   c.Open("factorial2.c3");
   //     Mostrar(c);
     // Interprete VM =  new Interprete();
  ///  VM.open("factorial2.c3");
   
  //   VM.run();
/*     
     
      Calendar ahora2 = Calendar.getInstance();
              t2 = ahora2.getTimeInMillis();
      //Se calcula la diferencia de tiempo
              dif = t2 - t1;

              //Se muestra en pantalla la diferencia de tiempo obtenido
              System.out.println("Has tardado: " + dif + " milisegundos");
              System.out.printf("Equivale a: %.3f segundos", (double)dif/1000);  */
     FrameInterprete n = new FrameInterprete();
      n.setVisible(true);
       //------------------------------------------------------
     /*      TSVAR tsvar = new TSVAR();
        TSS tss = new TSS();
       Codigo3 c3 = new Codigo3(tsvar, tss);
        tsvar.addProc("Lectura");
        tsvar.addVar("N", TSVAR.TIPOINT);
        tss.add("Introduzca N");
        c3.add(Cuadrupla.ETIQUETA,1);
        c3.add(Cuadrupla.OPWRITES,0);
        c3.add(Cuadrupla.OPREAD,-1);
        c3.add(Cuadrupla.OPASIGNNUM, 1, 0);
       c3.add(Cuadrupla.OPMAI, 2, -1, 1);
       c3.add(Cuadrupla.OPIF0, 2, 1);
       c3.add(Cuadrupla.OPRET);
       tsvar.addProc("$ Main");
       tsvar.addVar("I", TSVAR.TIPOINT);
       tsvar.addVar("F", TSVAR.TIPOINT);
       tss.add("Factorial = ");
       c3.add(Cuadrupla.OPCALL,0);
       c3.add(Cuadrupla.OPASIGNNUM,-3,1);
       c3.add(Cuadrupla.OPASIGNNUM,-4,1);
       c3.add(Cuadrupla.ETIQUETA, 2);
       c3.add(Cuadrupla.OPMEI,1,-3,-1);
       c3.add(Cuadrupla.OPIF0,1,3);
       c3.add(Cuadrupla.OPPOR,-4,-4,-3);
       c3.add(Cuadrupla.OPINC,-3);
       c3.add(Cuadrupla.OPGOTO,2);
       c3.add(Cuadrupla.ETIQUETA,3);
       c3.add(Cuadrupla.OPWRITES,-1);
       c3.add(Cuadrupla.OPWRITE,-4);
       c3.add(Cuadrupla.OPRET);
       tsvar.setValorI(0, 0);
       tsvar.setValorF(0, 6);
       tsvar.setCantTmp(0,2);
       tsvar.setValorI(2, 7);
       tsvar.setValorF(2, 19);
       tsvar.setCantTmp(2,1);
       c3.Save("factorial2.c3");*/
   
    }
}
