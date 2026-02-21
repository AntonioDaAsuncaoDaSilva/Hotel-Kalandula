package service;

import model.*;
import repository.QuartoRepository;
import repository.ReservaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ReservaService {
    private final ReservaRepository reservaRepository;
    private final QuartoRepository quartoRepository;

    public ReservaService(ReservaRepository reservaRepository, QuartoRepository quartoRepository) {
        this.reservaRepository = reservaRepository;
        this.quartoRepository = quartoRepository;
    }

    public Reserva criarReserva(Hospede hospede, int numeroQuarto, int dias) {
        Quarto quarto = quartoRepository.buscarPorNumero(numeroQuarto)
                .orElseThrow(() -> new IllegalArgumentException("Quarto " + numeroQuarto + " não existe."));

        if (!quarto.isDisponivel())
            throw new IllegalStateException("Quarto " + numeroQuarto + " não está disponível.");
        if (dias <= 0)
            throw new IllegalArgumentException("Número de dias deve ser maior que zero.");

        String id = reservaRepository.gerarId();
        Reserva reserva = new Reserva(id, hospede, quarto, LocalDate.now(), dias);
        quarto.setDisponivel(false);
        reservaRepository.salvar(reserva);
        return reserva;
    }

    public Reserva cancelarReserva(String reservaId) {
        Reserva reserva = reservaRepository.buscarPorId(reservaId)
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada."));
        if (reserva.getStatus() != Reserva.StatusReserva.ATIVA)
            throw new IllegalStateException("Reserva não está ativa.");

        reserva.setStatus(Reserva.StatusReserva.CANCELADA);
        reserva.getQuarto().setDisponivel(true);
        return reserva;
    }

    public Reserva realizarCheckout(String reservaId) {
        Reserva reserva = reservaRepository.buscarPorId(reservaId)
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada."));
        if (reserva.getStatus() != Reserva.StatusReserva.ATIVA)
            throw new IllegalStateException("Reserva não está ativa.");

        reserva.setStatus(Reserva.StatusReserva.CONCLUIDA);
        reserva.getQuarto().setDisponivel(true);
        return reserva;
    }

    public List<Reserva> listarTodas() {
        return reservaRepository.listarTodas();
    }

    public List<Reserva> listarAtivas() {
        return reservaRepository.listarAtivas();
    }

    public List<Reserva> listarPorHospede(String hospedeId) {
        return reservaRepository.listarPorHospede(hospedeId);
    }

    public Optional<Reserva> buscarPorId(String id) {
        return reservaRepository.buscarPorId(id);
    }
}
