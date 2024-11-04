import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Historico {
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