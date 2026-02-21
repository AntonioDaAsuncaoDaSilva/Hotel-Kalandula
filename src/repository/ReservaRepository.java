package repository;

import model.Reserva;
import java.util.*;
import java.util.stream.Collectors;

public class ReservaRepository {
    private final Map<String, Reserva> reservas = new LinkedHashMap<>();
    private int contador = 1;

    public String gerarId() {
        return String.format("R%04d", contador++);
    }

    public void salvar(Reserva reserva) {
        reservas.put(reserva.getId(), reserva);
    }

    public Optional<Reserva> buscarPorId(String id) {
        return Optional.ofNullable(reservas.get(id));
    }

    public List<Reserva> listarTodas() {
        return new ArrayList<>(reservas.values());
    }

    public List<Reserva> listarPorHospede(String hospedeId) {
        return reservas.values().stream()
                .filter(r -> r.getHospede().getId().equals(hospedeId))
                .collect(Collectors.toList());
    }

    public List<Reserva> listarAtivas() {
        return reservas.values().stream()
                .filter(r -> r.getStatus() == Reserva.StatusReserva.ATIVA)
                .collect(Collectors.toList());
    }
}
