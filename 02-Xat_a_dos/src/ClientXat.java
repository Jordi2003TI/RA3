import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientXat {
    private static final String HOST = "localhost";
    private static final int PORT = 9999;
    private static final String MSG_SORTIR = "sortir";

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public void connecta() throws IOException{
        socket = new Socket(HOST, PORT);
        System.out.println("Client conectat a " + HOST + ":" + PORT);

        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());
        System.out.println("Flux d'entrada i sortida creat.");
    }

    public void enviarMissatge(String missatge)throws IOException{
        out.writeObject(missatge);
        out.flush();
        System.out.println("Enviar missatge " + missatge);
    }

    public void tancarClient() throws IOException{
        System.out.println("Tancat client...");
        if(socket != null && !socket.isClosed()){
            socket.close();
        }
        System.out.println("Client tancat.");
    }

    public static void main(String[] args){
        ClientXat client = new ClientXat();
        Scanner scanner = new Scanner(System.in);

        try {
            client.connecta();

            FilLectorCX fil = new FilLectorCX(client.in);

            fil.start();
            System.out.println("Missatge ('sortir' per tancar): Fil de lectura iniciat");

            System.out.print("Rebut: Escriu el teu nom: \n");
            String nom = scanner.nextLine();
            client.enviarMissatge(nom);

            String missatge;

            do{
                System.out.println("Missatge ('sortir' per tancar): ");
                missatge = scanner.next();
                client.enviarMissatge(missatge);
            }while (!missatge.equals(MSG_SORTIR));

            scanner.close();

            fil.join();

            client.tancarClient();

        } catch (Exception e) {
            System.out.println("El servidor ha tancat la connexió.");
        }
    }
}
