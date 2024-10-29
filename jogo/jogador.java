import java.util.*;

public class jogador {
    protected List<Carta> mao;

    public Jogador() {
        this.mao = new ArrayList<>();
    }

    public void puxarCarta(Carta carta) {
        mao.add(carta);
    }

    public int calcularPontuacao() {
        int soma = 0;
        int ases = 0;

        for (Carta carta : mao) {
            soma += carta.getValor();
            if (carta.getValor() == 1) ases++;
        }

        // Ajusta o valor do Ás (1 ou 11)
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
