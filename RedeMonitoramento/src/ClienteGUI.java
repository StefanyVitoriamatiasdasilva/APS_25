import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ClienteGUI extends JFrame {

    private JTextArea areaMensagens;
    private JTextField campoMensagem;
    private JTextField campoNome;
    private JTextField campoLoocalizacao;
    private JButton botaoEnviar;

    private PrintWriter out;

    public ClienteGUI() {
        super("Inspetor Ambiental - Rio Tietê");

        setLayout(new BorderLayout());

        //Painel superior com nome e localização
        JPanel painelTopo = new JPanel(new GridLayout(2, 2));
        painelTopo.add(new JLabel("Nome: "));
        campoNome = new JTextField();
        painelTopo.add(campoNome);
        painelTopo.add(new JLabel("Localização: "));
        campoLoocalizacao = new JTextField();
        painelTopo.add(campoLoocalizacao);
        add(painelTopo, BorderLayout.NORTH);

        //Área de mensagens
        areaMensagens = new JTextArea();
        areaMensagens.setEditable(false);
        add(new JScrollPane(areaMensagens), BorderLayout.CENTER);

        //Painel inferiror com campo de mensagem e botão
        JPanel painelInferior = new JPanel(new BorderLayout());
        campoMensagem = new JTextField();
        botaoEnviar = new JButton("Enviar");
        painelInferior.add(campoMensagem, BorderLayout.CENTER);
        painelInferior.add(botaoEnviar, BorderLayout.EAST);
        add(painelInferior, BorderLayout.SOUTH);

        //ação do botão
        botaoEnviar.addActionListener(e -> enviarMensagem());

        //Configurações da janela
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        conectarServidor();
    }


    private void conectarServidor() {
        try {
            Socket socket = new Socket("localhost", 12345);
            out = new PrintWriter(socket.getOutputStream(), true);

            //Thread para ouvir respostas do servidor (opicional)
            new Thread(() -> {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String linha;
                    while ((linha = in.readLine()) != null) {
                        areaMensagens.append(">> " + linha + "\n");
                    }
                } catch (IOException ex) {
                    areaMensagens.append("Conexão perdida com o servidor.\n");
                }
            }).start();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao Servidor.");
            System.exit(1);
        }
    }

    private void enviarMensagem() {
        String nome = campoNome.getText().trim();
        String local = campoLoocalizacao.getText().trim();
        String texto = campoMensagem.getText().trim();

        if (nome.isEmpty() || local.isEmpty() || texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }

        String mensagem = nome + "|" + local + "|" + texto;
        out.println(mensagem);
        areaMensagens.append(mensagem + "\n");
        campoMensagem.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClienteGUI::new);
    }
}