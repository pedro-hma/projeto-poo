import java.util.*;

public class deck {
    private List<Carta> cartas;

    public Baralho() {
        this.cartas = new ArrayList<>();
        String[] naipes = {"Copas", "Espadas", "Ouros", "Paus"};

        for (String naipe : naipes) {
            for (int i = 2; i <= 14; i++) {
                int valor = Math.min(i, 10); // Valetes, Damas e Reis valem 10
                cartas.add(new Carta(naipe, valor));
            }
        }
        Collections.shuffle(cartas);
    }

    public Carta puxarCarta() {
        return cartas.remove(cartas.size() - 1);
    }
}
