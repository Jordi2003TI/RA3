import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    private static final int PORT = 7777;
    private static final String HOST = "localhost";

    private Socket socket;
    private PrintWriter out;

    public void connecta()throws IOException{
        socket = new Socket(HOST, PORT); // aqui le pasamos el puerto y host para mandarlo por la red
        out = new PrintWriter(socket.getOutputStream(), true);
        System.out.println("Connectat a servidor en " + HOST + ":" + PORT);
    }

    public void envia(String message){
        out.println(message);
        System.out.println("Enviat al servidor: " + message);
    }

    public void tanca() throws IOException {
        if (out != null) out.close(); // si devuelve nulo cerramos el out 
        if (socket != null) socket.close(); // si el socket devuelve nulo cerramos tambien 
        System.out.println("Client tancat");
    }

    public static void main(String[] args){
        Cliente cliente = new Cliente();
        try {
            cliente.connecta();
            cliente.envia("Prova d'enviament 1");
            cliente.envia("Prova d'enviament 2");
            cliente.envia("Adéu!");

            System.out.println("Prem Enter per tancar el client...");
            new Scanner(System.in).nextLine();
            
            cliente.tanca();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
