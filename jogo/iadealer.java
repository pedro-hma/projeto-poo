// Classe Dealer, com IA para decisões
class Dealer extends jogador {

    public boolean tomarDecisao(int pontuacaoJogador) {
        int pontuacaoDealer = calcularPontuacao();  // Corrigido: Chamando diretamente o método calcularPontuacao()

        if (pontuacaoDealer < 17) {
            return true; // Dealer puxa carta com pontuação menor que 17
        } else if (pontuacaoDealer < pontuacaoJogador && pontuacaoJogador <= 21) {
            return true; // Dealer puxa se o jogador tem pontuação mais alta e ainda não estourou
        }
        return false; // Dealer para de puxar cartas
    }
}