package service;

import model.Hospede;
import repository.HospedeRepository;
import java.util.List;
import java.util.Optional;

public class HospedeService {
    private final HospedeRepository repository;

    public HospedeService(HospedeRepository repository) {
        this.repository = repository;
    }

    public Hospede cadastrar(String nome, String bi, String telefone, String email) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome é obrigatório.");
        if (bi == null || !bi.matches("\\d{9}[A-Z]{2}\\d{3}"))
            throw new IllegalArgumentException("BI inválido (formato: 000000000LA000).");
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Email inválido.");
        if (telefone == null || !telefone.matches("9\\d{8}"))
            throw new IllegalArgumentException("Telefone inválido (deve começar com 9 e ter 9 dígitos).");
        
        Hospede h = repository.salvar(nome.trim(), bi, telefone, email.trim());
        if (h == null) throw new IllegalStateException("BI já cadastrado no sistema.");
        return h;
    }

    public Optional<Hospede> buscarPorId(String id) {
        return repository.buscarPorId(id);
    }

    public Optional<Hospede> buscarPorBI(String bi) {
        return repository.buscarPorBI(bi);
    }

    public List<Hospede> listarTodos() {
        return repository.listarTodos();
    }
}
