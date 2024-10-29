public class carta {
    private String naipe;
    private int valor;

    public carta(String naipe, int valor) {
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