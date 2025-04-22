import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        int porta = 12345;

        try (ServerSocket servidor = new ServerSocket(porta)) {
            System.out.println("servidor pronto. Aguardando conexões... ");

            while (true) {
                Socket Cliente = servidor.accept();
                new Thread(new ClienteHandler(Cliente)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class ClienteHandler implements Runnable {
    private Socket socket;

    public ClienteHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            String entrada;
            while ((entrada = in.readLine()) != null) {
                System.out.println("Mensagem recebida: " + entrada);
            }
        } catch (IOException e) {
            System.out.println("Conexão encerrada.");
        }
    }

    //Gravar todas as mensagens em um único arquivo
    private synchronized void gravarMensagem(String mensagem) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("mensagens.txt", true))) {
            writer.write(mensagem);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao gravar a mensagem no arquivo.");
        }
    }

    //Gravar mensagens em arquivos por inspertor (ex: Ana_Santana.txt)
    private synchronized void gravarMensagemPorInspertor(String mensagem) {
       // Esperar o formato : Nome|Localização|Mensagem
       String[] partes = mensagem.split("\\|");
       if (partes.length !=3) {
          System.out.println("Formato inválido para gravação por inspetor: " + mensagem);
          return;
        }

        String nome = partes[0].trim();
        String local = partes[1].trim();
        String texto = partes[2].trim();

        //Cria a pasta "historico" se não for existir
        File pasta = new File("historico");
        if (!pasta.exists()) {
            pasta.mkdir();
        }

        //Nome do arquivo : Nome_local.txt (sem espaço)
        String nomeArquivo = "Historico/" + nome + "_" + local.replaceAll("\\s+", "") + ".txt";

        try (BufferedWriter writer = new BufferedWriter(null))
    }
}