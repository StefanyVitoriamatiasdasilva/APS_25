import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        String enderecoServidor = "localhost";
        int porta = 12345;

        try (
            Socket socket = new Socket(enderecoServidor, porta);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.print("Nome do Inspertor: ");
            String nome = scanner.nextLine();

            System.out.print("Localização: ");
            String local = scanner.nextLine();

            while (true) {
                System.out.print("Digite uma mensagem (ou 'sair'): ");

                String texto = scanner.nextLine();
                if (texto.equalsIgnoreCase("sair")) break;

                Mensagem msg = new Mensagem(nome, local, texto);
                out.println(msg.formatarMensagem());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
