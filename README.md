
# Compilador Codigo C3



 Compilador de codigo C3 en java
 
- **Preview**
  
  Ejercicio 1

  ![preview img](/preview/compilador_codc3_factorial.jpg)
  
  Script 
 ~~~	
  0 E1:
  1 WriteS("Introduzca N")
  2 READ(N)
  3 t1 = 0
  4 t2 = (N >= t1)
  5 IF (t2=0) => GOTO E1
  6 RET
  7 CALL Lectura
  8 I = 1
  9 F = 1
 10 E2:
 11 t1 = (I <= N)
 12 IF (t1=0) => GOTO E3
 13 F = F * I
 14 INC I
 15 GOTO E2
 16 E3:
 17 WriteS("Factorial = ")
 18 WRITE(F)
 19 RET
 ~~~
  
  Ejercicio 2

  ![preview img](/preview/compilador_codc3.jpg)
  
  Script
  ~~~
    0 t1 = 0
  1 E1:
  2 WriteS("Introdusca N: ")
  3 READ(N)
  4 t2 = (N <= t1)
  5 IF (t2=1) => GOTO E1
  6 RET
  7 J = 1
  8 E2:
  9 t1 = (J <= K)
 10 IF (t1=0) => GOTO E3
 11 WriteS("*")
 12 INC J
 13 GOTO E2
 14 E3:
 15 NL
 16 RET
 17 CALL Lectura
 18 I = 1
 19 E4:
 20 t1 = (I <= N)
 21 IF (t1=0) => GOTO E5
 22 K = I
 23 CALL Linea
 24 INC I
 25 GOTO E4
 26 E5:
 27 RET

  ~~~
  
  