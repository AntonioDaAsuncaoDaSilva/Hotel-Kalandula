package repository;

import model.Quarto;
import model.TipoQuarto;
import java.util.*;
import java.util.stream.Collectors;

public class QuartoRepository {
    private final Map<Integer, Quarto> quartos = new LinkedHashMap<>();

    public QuartoRepository() {
        inicializarQuartos();
    }

    private void inicializarQuartos() {
        // Andar 1 - Solteiros
        for (int i = 101; i <= 105; i++) quartos.put(i, new Quarto(i, TipoQuarto.SOLTEIRO, "1º"));
        // Andar 2 - Duplos
        for (int i = 201; i <= 205; i++) quartos.put(i, new Quarto(i, TipoQuarto.DUPLO, "2º"));
        // Andar 3 - Suítes
        for (int i = 301; i <= 303; i++) quartos.put(i, new Quarto(i, TipoQuarto.SUITE, "3º"));
        // Andar 4 - Suítes Luxo
        for (int i = 401; i <= 402; i++) quartos.put(i, new Quarto(i, TipoQuarto.SUITE_LUXO, "4º"));
    }

    public Optional<Quarto> buscarPorNumero(int numero) {
        return Optional.ofNullable(quartos.get(numero));
    }

    public List<Quarto> listarTodos() {
        return new ArrayList<>(quartos.values());
    }

    public List<Quarto> listarDisponiveis() {
        return quartos.values().stream()
                .filter(Quarto::isDisponivel)
                .collect(Collectors.toList());
    }

    public List<Quarto> listarOcupados() {
        return quartos.values().stream()
                .filter(q -> !q.isDisponivel())
                .collect(Collectors.toList());
    }

    public List<Quarto> listarPorTipo(TipoQuarto tipo) {
        return quartos.values().stream()
                .filter(q -> q.getTipo() == tipo)
                .collect(Collectors.toList());
    }
}
