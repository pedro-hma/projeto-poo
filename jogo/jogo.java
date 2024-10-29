import java.util.Scanner;
public class jogo {
    private deck baralho;
    private jogador jogador;
    private jogador dealer;

    public jogo() {
        this.baralho = new deck();
        this.jogador = new jogador();
        this.dealer = new jogador();
    }

    public void iniciar() {
        // Distribuir duas cartas para cada jogador
        jogador.puxarCarta(baralho.puxarCarta());
        jogador.puxarCarta(baralho.puxarCarta());

        dealer.puxarCarta(baralho.puxarCarta());
        dealer.puxarCarta(baralho.puxarCarta());

        System.out.println("Mão do jogador: " + jogador.mostrarMao());
        System.out.println("Mão do dealer: [Carta Oculta, " + dealer.mao.get(1) + "]");

        while (jogador.calcularPontuacao() < 21) {
            System.out.println("Deseja 'Puxar' ou 'Parar'? ");
            Scanner scanner = new Scanner(System.in);
            String acao = scanner.nextLine();

            if (acao.equalsIgnoreCase("Puxar")) {
                jogador.puxarCarta(baralho.puxarCarta());
                System.out.println("Mão do jogador: " + jogador.mostrarMao());
            } else {
                break;
            }
        }

        verificarVencedor();
    }

    private void verificarVencedor() {
        int pontuacaoJogador = jogador.calcularPontuacao();
        int pontuacaoDealer = dealer.calcularPontuacao();

        System.out.println("Mão do dealer: " + dealer.mostrarMao());

        if (pontuacaoJogador > 21) {
            System.out.println("Você estourou! Dealer venceu.");
        } else if (pontuacaoDealer > 21 || pontuacaoJogador > pontuacaoDealer) {
            System.out.println("Você venceu!");
        } else if (pontuacaoJogador < pontuacaoDealer) {
            System.out.println("Dealer venceu.");
        } else {
            System.out.println("Empate!");
        }
    }

    public static void main(String[] args) {
        jogo jogo = new jogo();
        jogo.iniciar();
    }
}