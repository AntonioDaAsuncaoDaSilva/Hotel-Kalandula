package repository;

import model.Hospede;
import java.util.*;

public class HospedeRepository {
    private final Map<String, Hospede> hospedes = new LinkedHashMap<>();
    private int contador = 1;

    public Hospede salvar(String nome, String bi, String telefone, String email) {
        // Verifica BI duplicado
        for (Hospede h : hospedes.values()) {
            if (h.getBI().equals(bi)) return null;
        }
        for (Hospede h : hospedes.values()) {
            if (h.getEmail().equals(email)) return null;
        }
        for (Hospede h : hospedes.values()) {
            if (h.getTelefone().equals(telefone)) return null;
        }
        String id = String.format("H%03d", contador++);
        Hospede hospede = new Hospede(id, nome, bi, telefone, email);
        hospedes.put(id, hospede);
        return hospede;
    }

    public Optional<Hospede> buscarPorId(String id) {
        return Optional.ofNullable(hospedes.get(id));
    }

    public Optional<Hospede> buscarPorBI(String bi) {
        return hospedes.values().stream()
                .filter(h -> h.getBI().equals(bi))
                .findFirst();
    }

    public List<Hospede> listarTodos() {
        return new ArrayList<>(hospedes.values());
    }
}
