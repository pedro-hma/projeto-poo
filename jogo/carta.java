public class Carta {
    private String naipe;
    private int valor;

    public Carta(String naipe, int valor) {
        this.naipe = naipe;
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public String toString() {
        return valor + " de " + naipe;
    }
}