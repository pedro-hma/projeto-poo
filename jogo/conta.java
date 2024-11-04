public class conta {
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

    public void perderAposta(double valor) {
        // Nesse caso, o valor já foi descontado na aposta inicial
    }

    public double getSaldo() {
        return saldo;
    }

    @Override
    public String toString() {
        return "Saldo atual: $" + saldo;
    }
}
