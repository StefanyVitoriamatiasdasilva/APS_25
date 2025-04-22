public class Mensagem {
    private String nomeInspetor;
    private String localizacao;
    private String mensagem;

    public Mensagem(String nomeInspetor, String localizacao, String mensagem) {
        this.nomeInspetor = nomeInspetor;
        this.localizacao = localizacao;
        this.mensagem = mensagem;
    }

    public String formatarMensagem() {
        return "[" + localizacao + "] " + nomeInspetor + ": " + mensagem;
    }
}

