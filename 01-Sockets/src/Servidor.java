import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor{
    private static final int PORT = 7777;
    private static final String HOST = "localhost";

    private ServerSocket serverSocket;
    private Socket clienSocket;

    public void connecta() throws IOException{
        serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor en marxa a " + HOST + ":" + PORT);
        System.out.println("Esperant connexions a " + HOST + ":" + PORT);
        clienSocket = serverSocket.accept();
        System.out.println("Client connectat: " + clienSocket.getInetAddress());
    }

    public void repDades() throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clienSocket.getInputStream()));

        String linea;
        while((linea = bufferedReader.readLine())!=null){
            System.out.println("Rebut " + linea);

            if(linea.equals("Adéu!")) {
            System.out.println("Client ha dit adéu, tancant...");
            break;
        }
        }

        bufferedReader.close();
    }


    public void tanca()throws IOException{
        if(clienSocket != null) clienSocket.close();
        if(serverSocket != null) serverSocket.close();
        System.out.println("Servidor tancat."); 
    }

    public static void main(String[] args){
        Servidor servidor = new Servidor();
        try {
            servidor.connecta();
            servidor.repDades();
            servidor.tanca();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}