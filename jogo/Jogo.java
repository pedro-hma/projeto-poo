import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

// Classe Carta
class Carta {
    private int valor;
    private String naipe;

    public Carta(int valor, String naipe) {
        this.valor = valor > 10 ? 10 : valor; // Limita o valor máximo a 10 (valetes, damas e reis)
        this.naipe = naipe;
    }

    public int getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return valor + " de " + naipe;
    }
}

// Classe Baralho
class Baralho {
    private List<Carta> cartas;

    public Baralho() {
        cartas = new ArrayList<>();
        String[] naipes = {"Copas", "Espadas", "Ouros", "Paus"};
        for (String naipe : naipes) {
            for (int valor = 1; valor <= 13; valor++) {
                cartas.add(new Carta(valor, naipe));
            }
        }
        Collections.shuffle(cartas);
    }

    public Carta puxarCarta() {
        return cartas.remove(0);
    }
}

// Classe Conta para gerenciar o saldo e apostas
class Conta {
    private double saldo;

    public Conta(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    public boolean apostar(double valor) {
        if (valor <= saldo) {
            saldo -= valor;
            return true;
        }
        System.out.println("Saldo insuficiente!");
        return false;
    }

    public void ganharAposta(double valor) {
        saldo += valor;
    }

    public double getSaldo() {
        return saldo;
    }

    @Override
    public String toString() {
        return "Saldo atual: $" + saldo;
    }
}

// Classe Jogador para gerenciar a mão e pontuação
class Jogador {
    protected List<Carta> mao;

    public Jogador() {
        mao = new ArrayList<>();
    }

    public void puxarCarta(Carta carta) {
        mao.add(carta);
    }

    public int calcularPontuacao() {
        int soma = 0;
        int ases = 0;

        for (Carta carta : mao) {
            soma += carta.getValor();
            if (carta.getValor() == 1) ases++;  // Conta Ases
        }

        while (ases > 0 && soma + 10 <= 21) {
            soma += 10;
            ases--;
        }

        return soma;
    }

    public String mostrarMao() {
        return mao.toString();
    }
}

// Classe Dealer, com IA para decisões
class Dealer extends Jogador {

    public boolean tomarDecisao(int pontuacaoJogador) {
        int pontuacaoDealer = this.calcularPontuacao();

        if (pontuacaoDealer < 17) {
            return true; // Dealer puxa carta com pontuação menor que 17
        } else if (pontuacaoDealer < pontuacaoJogador && pontuacaoJogador <= 21) {
            return true; // Dealer puxa se o jogador tem pontuação mais alta e ainda não estourou
        }
        return false; // Dealer para de puxar cartas
    }
}

// Classe Historico para armazenar os resultados
class Historico {
    private List<String> resultados;

    public Historico() {
        this.resultados = new ArrayList<>();
    }

    public void registrarResultado(String resultado) {
        resultados.add(resultado);
        salvarNoArquivo(resultado);
    }

    private void salvarNoArquivo(String resultado) {
        try (FileWriter writer = new FileWriter("historico.txt", true)) {
            writer.write(resultado + "\n");
        } catch (IOException e) {
            System.out.println("Erro ao salvar o histórico: " + e.getMessage());
        }
    }

    public void exibirHistorico() {
        System.out.println("Histórico de Partidas:");
        for (String resultado : resultados) {
            System.out.println(resultado);
        }
    }
}

// Classe principal do Jogo
public class Jogo {
    private Baralho baralho;
    private Jogador jogador;
    private Dealer dealer;
    private Conta contaJogador;
    private Historico historico;

    public Jogo(double saldoInicial) {
        this.baralho = new Baralho();
        this.jogador = new Jogador();
        this.dealer = new Dealer();
        this.contaJogador = new Conta(saldoInicial);
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
