import java.util.*;

public class deck {
    private List<carta> cartas;

    public deck() {
        this.cartas = new ArrayList<>();
        String[] naipes = {"Copas", "Espadas", "Ouros", "Paus"};

        for (String naipe : naipes) {
            for (int i = 2; i <= 14; i++) {
                int valor = Math.min(i, 10); // Valetes, Damas e Reis valem 10
                cartas.add(new carta(naipe, valor));
            }
        }
        Collections.shuffle(cartas);
    }

    public carta puxarCarta() {
        return cartas.remove(cartas.size() - 1);
    }
}
