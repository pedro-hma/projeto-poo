import java.util.Scanner;

public class Jogo {
    private deck baralho;
    private jogador jogador;
    private Dealer dealer;
    private conta contaJogador;
    private Historico historico;

    public Jogo(double saldoInicial) {
        this.baralho = new deck();
        this.jogador = new jogador();
        this.dealer = new Dealer();
        this.contaJogador = new conta(saldoInicial);
        this.historico = new Historico();
    }

    public void iniciar() {
        System.out.println("Bem-vindo ao Blackjack! Seu saldo atual é: " + contaJogador.getSaldo());
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Quanto você gostaria de apostar? ");
        double aposta = scanner.nextDouble();

        if (!contaJogador.apostar(aposta)) {
            System.out.println("Aposta inválida. Saldo insuficiente.");
            return;
        }

        jogador.puxarCarta(baralho.puxarCarta());
        jogador.puxarCarta(baralho.puxarCarta());

        dealer.puxarCarta(baralho.puxarCarta());
        dealer.puxarCarta(baralho.puxarCarta());

        System.out.println("Mão do jogador: " + jogador.mostrarMao());
        System.out.println("Mão do dealer: [Carta Oculta, " + dealer.mao.get(1) + "]");

        while (jogador.calcularPontuacao() < 21) {
            System.out.println("Deseja 'Puxar' ou 'Parar'? ");
            String acao = scanner.next();

            if (acao.equalsIgnoreCase("Puxar")) {
                jogador.puxarCarta(baralho.puxarCarta());
                System.out.println("Mão do jogador: " + jogador.mostrarMao());
            } else {
                break;
            }
        }

        jogadaDealer();

        finalizarPartida(aposta);
    }

    private void jogadaDealer() {
        while (dealer.tomarDecisao(jogador.calcularPontuacao())) {
            dealer.puxarCarta(baralho.puxarCarta());
            System.out.println("Dealer puxou uma carta.");
        }
        System.out.println("Pontuação do Dealer: " + dealer.calcularPontuacao());
    }

    private void finalizarPartida(double aposta) {
        int pontuacaoJogador = jogador.calcularPontuacao();
        int pontuacaoDealer = dealer.calcularPontuacao();
        
        if (pontuacaoJogador > 21) {
            System.out.println("Você estourou! Dealer venceu.");
            historico.registrarResultado("Dealer venceu");
        } else if (pontuacaoDealer > 21 || pontuacaoJogador > pontuacaoDealer) {
            System.out.println("Você venceu!");
            contaJogador.ganharAposta(aposta * 2);
            historico.registrarResultado("Jogador venceu");
        } else if (pontuacaoJogador < pontuacaoDealer) {
            System.out.println("Dealer venceu.");
            historico.registrarResultado("Dealer venceu");
        } else {
            System.out.println("Empate! Aposta devolvida.");
            contaJogador.ganharAposta(aposta);
            historico.registrarResultado("Empate");
        }

        System.out.println(contaJogador);
        historico.exibirHistorico();
    }

    public static void main(String[] args) {
        Jogo jogo = new Jogo(100); // Saldo inicial de $100
        jogo.iniciar();
    }
}