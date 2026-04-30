import java.io.*;
import java.net.*;

public class ServidorXat {
    private static final int PORT = 9999;
    private static final String HOST="localHost";
    private static final String MSG_SORTIR = "sortir";

    private ServerSocket serverSocket;

    public void iniciarServidor()throws IOException{
        serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor iniciat a " + HOST + ":" + PORT);
    }

    public void pararServidor()throws IOException{
        if(serverSocket != null && !serverSocket.isClosed()){
            serverSocket.close();
        }
        System.out.println("Servidor aturat");
    }

    public String getName(ObjectInputStream in) throws IOException, ClassNotFoundException{
        System.out.println("Escreiu el teu nom: ");
        String nom = (String) in.readObject();
        System.out.println("Nom rebut: " + nom);
        return nom;
    }

    public static void main(String[] args){
        ServidorXat servidor = new ServidorXat();

        try {
            servidor.iniciarServidor();

            Socket clientSocket = servidor.serverSocket.accept();

            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.flush();
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            String nomClient = servidor.getName(in);

            FilServidorXat fil = new FilServidorXat(in, nomClient);
            fil.start();
            System.out.println("Fil de " + nomClient + " iniciat");
            
            BufferedReader consola = new BufferedReader(new InputStreamReader(System.in));
            String missatge;

            do{
                System.out.println("Missatge ('sortir' per tancar): ");
                missatge = consola.readLine();
                out.writeObject(missatge);
                out.flush();
            }while (!missatge.equals(MSG_SORTIR));
                fil.join();

                clientSocket.close();
                servidor.pararServidor();
            

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
