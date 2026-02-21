package service;

import model.Quarto;
import model.TipoQuarto;
import repository.QuartoRepository;
import java.util.List;
import java.util.Optional;

public class QuartoService {
    private final QuartoRepository repository;

    public QuartoService(QuartoRepository repository) {
        this.repository = repository;
    }

    public List<Quarto> listarTodos() {
        return repository.listarTodos();
    }

    public List<Quarto> listarDisponiveis() {
        return repository.listarDisponiveis();
    }

    public List<Quarto> listarOcupados() {
        return repository.listarOcupados();
    }

    public List<Quarto> listarPorTipo(TipoQuarto tipo) {
        return repository.listarPorTipo(tipo);
    }

    public Optional<Quarto> buscarPorNumero(int numero) {
        return repository.buscarPorNumero(numero);
    }

    public double calcularValor(int numeroQuarto, int dias) {
        Quarto q = repository.buscarPorNumero(numeroQuarto)
                .orElseThrow(() -> new IllegalArgumentException("Quarto não encontrado."));
        if (dias <= 0) throw new IllegalArgumentException("Número de dias deve ser maior que zero.");
        return q.getTipo().getPrecoPorDia() * dias;
    }
}
