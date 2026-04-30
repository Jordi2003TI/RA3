import java.io.*;

public class FilServidorXat extends Thread {
     private ObjectInputStream in;
     private String nom;

     public FilServidorXat(ObjectInputStream in, String nom){
          this.in = in;
          this.nom = nom;
     }

     @Override
     public void run(){
          try {
               String missatge;
               do {
                    missatge = (String) in.readObject();
                    if (!missatge.equals("sortir")) {
                         System.out.println(nom + ": " + missatge);
                    }
          } while (!missatge.equals("sortir"));

          } catch (Exception e) {
               System.out.println("Fil finalitzat: " + e.getMessage());
          }
     }
}
