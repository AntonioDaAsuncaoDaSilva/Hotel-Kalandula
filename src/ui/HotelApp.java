package ui;

import model.*;
import repository.*;
import service.*;
import java.util.*;

public class HotelApp {

    private static final Scanner scanner = new Scanner(System.in);

    private static final HospedeRepository hospedeRepository = new HospedeRepository();
    private static final QuartoRepository quartoRepository   = new QuartoRepository();
    private static final ReservaRepository reservaRepository = new ReservaRepository();

    private static final HospedeService hospedeService = new HospedeService(hospedeRepository);
    private static final QuartoService  quartoService  = new QuartoService(quartoRepository);
    private static final ReservaService reservaService = new ReservaService(reservaRepository, quartoRepository);

    public static void main(String[] args) {
        System.out.println(banner());
        boolean rodando = true;
        while (rodando) {
            menuPrincipal();
            int opcao = lerInt("Escolha uma opção: ");
            System.out.println();
            switch (opcao) {
                case 1 -> menuHospedes();
                case 2 -> menuQuartos();
                case 3 -> menuReservas();
                case 0 -> { System.out.println("Até logo!"); rodando = false; }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    // ─────────────────────────── MENUS PRINCIPAIS ───────────────────────────

    static void menuPrincipal() {
        System.out.println("""
            ══════════════════════════════
               🏨  MENU PRINCIPAL
            ══════════════════════════════
             [1] Gestão de Hóspedes
             [2] Gestão de Quartos
             [3] Gestão de Reservas
             [0] Sair
            ══════════════════════════════""");
    }

    static void menuHospedes() {
        System.out.println("""
            ── Gestão de Hóspedes ──
            [1] Cadastrar hóspede
            [2] Buscar por ID
            [3] Buscar por BI
            [4] Listar todos
            [0] Voltar""");
        int op = lerInt("Opção: ");
        switch (op) {
            case 1 -> cadastrarHospede();
            case 2 -> buscarHospedePorId();
            case 3 -> buscarHospedePorBI();
            case 4 -> listarHospedes();
            case 0 -> {}
            default -> System.out.println("Opção inválida.");
        }
    }

    static void menuQuartos() {
        System.out.println("""
            ── Gestão de Quartos ──
            [1] Listar todos os quartos
            [2] Listar quartos disponíveis
            [3] Listar quartos ocupados
            [4] Listar por tipo
            [5] Simular valor (sem reservar)
            [0] Voltar""");
        int op = lerInt("Opção: ");
        switch (op) {
            case 1 -> listarQuartos(quartoService.listarTodos(), "Todos os Quartos");
            case 2 -> listarQuartos(quartoService.listarDisponiveis(), "Quartos Disponíveis");
            case 3 -> listarQuartos(quartoService.listarOcupados(), "Quartos Ocupados");
            case 4 -> listarPorTipo();
            case 5 -> simularValor();
            case 0 -> {}
            default -> System.out.println("Opção inválida.");
        }
    }

    static void menuReservas() {
        System.out.println("""
            ── Gestão de Reservas ──
            [1] Nova reserva
            [2] Listar reservas ativas
            [3] Listar todas as reservas
            [4] Buscar reserva por ID
            [5] Reservas de um hóspede
            [6] Realizar checkout
            [7] Cancelar reserva
            [0] Voltar""");
        int op = lerInt("Opção: ");
        switch (op) {
            case 1 -> criarReserva();
            case 2 -> reservaService.listarAtivas().forEach(r -> System.out.println(r + "\n"));
            case 3 -> reservaService.listarTodas().forEach(r -> System.out.println(r + "\n"));
            case 4 -> buscarReservaPorId();
            case 5 -> reservasPorHospede();
            case 6 -> checkout();
            case 7 -> cancelarReserva();
            case 0 -> {}
            default -> System.out.println("Opção inválida.");
        }
    }

    // ─────────────────────────── AÇÕES DE HÓSPEDE ───────────────────────────

    static void cadastrarHospede() {
        System.out.println("\n── Cadastrar Hóspede ──");
        String nome     = lerString("Nome completo : ");
        String bi     = lerString("BI : ");
        String telefone = lerString("Telefone (Só números para o sistema Angolano 9xx)     : ");
        String email    = lerString("Email         : ");
        try {
            Hospede h = hospedeService.cadastrar(nome, bi, telefone, email);
            System.out.println("\n✔ Hóspede cadastrado com sucesso!");
            System.out.println(h);
        } catch (Exception e) {
            System.out.println("✘ Erro: " + e.getMessage());
        }
    }

    static void buscarHospedePorId() {
        String id = lerString("ID do hóspede: ");
        hospedeService.buscarPorId(id).ifPresentOrElse(
            System.out::println,
            () -> System.out.println("Hóspede não encontrado.")
        );
    }

    static void buscarHospedePorBI() {
        String bi = lerString("BI (só números): ");
        hospedeService.buscarPorBI(bi).ifPresentOrElse(
            System.out::println,
            () -> System.out.println("Hóspede não encontrado.")
        );
    }

    static void listarHospedes() {
        List<Hospede> lista = hospedeService.listarTodos();
        if (lista.isEmpty()) { System.out.println("Nenhum hóspede cadastrado."); return; }
        System.out.println("\n─── Hóspedes Cadastrados ───");
        lista.forEach(System.out::println);
    }

    // ─────────────────────────── AÇÕES DE QUARTO ────────────────────────────

    static void listarQuartos(List<Quarto> quartos, String titulo) {
        System.out.println("\n─── " + titulo + " ───");
        if (quartos.isEmpty()) { System.out.println("Nenhum quarto encontrado."); return; }
        quartos.forEach(System.out::println);
    }

    static void listarPorTipo() {
        System.out.println("Tipos: [1] Solteiro  [2] Duplo  [3] Suíte  [4] Suíte Luxo");
        int op = lerInt("Escolha: ");
        TipoQuarto tipo = switch (op) {
            case 1 -> TipoQuarto.SOLTEIRO;
            case 2 -> TipoQuarto.DUPLO;
            case 3 -> TipoQuarto.SUITE;
            case 4 -> TipoQuarto.SUITE_LUXO;
            default -> null;
        };
        if (tipo == null) { System.out.println("Tipo inválido."); return; }
        listarQuartos(quartoService.listarPorTipo(tipo), tipo.getDescricao());
    }

    static void simularValor() {
        System.out.println("\nTabela de preços por dia:");
        for (TipoQuarto t : TipoQuarto.values())
            System.out.println("  " + t);
        int numero = lerInt("Número do quarto: ");
        int dias   = lerInt("Número de dias  : ");
        try {
            double valor = quartoService.calcularValor(numero, dias);
            quartoService.buscarPorNumero(numero).ifPresent(q ->
                System.out.printf("%n💰 Quarto %d (%s) por %d dia(s) = Kz %.2f%n mil",
                        numero, q.getTipo().getDescricao(), dias, valor));
        } catch (Exception e) {
            System.out.println("✘ " + e.getMessage());
        }
    }

    // ─────────────────────────── AÇÕES DE RESERVA ───────────────────────────

    static void criarReserva() {
        System.out.println("\n── Nova Reserva ──");

        // Mostra quartos disponíveis
        List<Quarto> disponiveis = quartoService.listarDisponiveis();
        if (disponiveis.isEmpty()) {
            System.out.println("Não há quartos disponíveis no momento.");
            return;
        }
        System.out.println("\nQuartos disponíveis:");
        disponiveis.forEach(System.out::println);

        String hospedeId = lerString("\nID do hóspede (ou 'novo' para cadastrar): ");
        Hospede hospede;
        if (hospedeId.equalsIgnoreCase("novo")) {
            cadastrarHospede();
            hospedeId = lerString("Agora informe o ID do hóspede cadastrado: ");
        }
        hospede = hospedeService.buscarPorId(hospedeId).orElse(null);
        if (hospede == null) { System.out.println("Hóspede não encontrado."); return; }

        int numeroQuarto = lerInt("Número do quarto: ");
        int dias         = lerInt("Número de dias  : ");

        try {
            Reserva reserva = reservaService.criarReserva(hospede, numeroQuarto, dias);
            System.out.println("\n✔ Reserva criada com sucesso!\n");
            System.out.println(reserva);
        } catch (Exception e) {
            System.out.println("✘ Erro: " + e.getMessage());
        }
    }

    static void buscarReservaPorId() {
        String id = lerString("ID da reserva: ");
        reservaService.buscarPorId(id).ifPresentOrElse(
            r -> System.out.println(r),
            () -> System.out.println("Reserva não encontrada.")
        );
    }

    static void reservasPorHospede() {
        String id = lerString("ID do hóspede: ");
        List<Reserva> lista = reservaService.listarPorHospede(id);
        if (lista.isEmpty()) { System.out.println("Nenhuma reserva para este hóspede."); return; }
        lista.forEach(r -> System.out.println(r + "\n"));
    }

    static void checkout() {
        String id = lerString("ID da reserva: ");
        try {
            Reserva r = reservaService.realizarCheckout(id);
            System.out.printf("%n✔ Checkout realizado! Quarto %d liberado. Total pago: Kz %.2f%n mil",
                    r.getQuarto().getNumero(), r.getValorTotal());
        } catch (Exception e) {
            System.out.println("✘ " + e.getMessage());
        }
    }

    static void cancelarReserva() {
        String id = lerString("ID da reserva: ");
        try {
            Reserva r = reservaService.cancelarReserva(id);
            System.out.printf("%n✔ Reserva %s cancelada. Quarto %d disponível novamente.%n",
                    r.getId(), r.getQuarto().getNumero());
        } catch (Exception e) {
            System.out.println("✘ " + e.getMessage());
        }
    }

    // ─────────────────────────── UTILITÁRIOS ────────────────────────────────

    static String lerString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    static int lerInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, informe um número válido.");
            }
        }
    }

    static String banner() {
        return """
            ╔══════════════════════════════════════════╗
            ║       🏨  SISTEMA DE GESTÃO HOTELEIRA    ║
            ║            Hotel Kalandula               ║
            ╚══════════════════════════════════════════╝
            """;
    }
}
