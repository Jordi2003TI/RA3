import java.io.ObjectInputStream;
import java.util.TreeMap;

public class FilLectorCX extends Thread{

     private ObjectInputStream in;
 
    public FilLectorCX(ObjectInputStream in) {
        this.in = in;
    }

    @Override
    public void run(){
        try {
            String missatge;

        do {
            missatge = (String) in.readObject();
            System.out.println("Rebut: " + missatge);
        } while (!missatge.equals("sortir"));
        } catch (Exception e) {
            System.out.println("El servidor ha tancat la connexió.");
        }
    }
}
