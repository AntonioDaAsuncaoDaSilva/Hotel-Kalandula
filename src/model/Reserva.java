package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Reserva {
    private String id;
    private Hospede hospede;
    private Quarto quarto;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int numeroDias;
    private double valorTotal;
    private StatusReserva status;

    public enum StatusReserva {
        ATIVA, CONCLUIDA, CANCELADA
    }

    public Reserva(String id, Hospede hospede, Quarto quarto,
                   LocalDate checkIn, int numeroDias) {
        this.id = id;
        this.hospede = hospede;
        this.quarto = quarto;
        this.checkIn = checkIn;
        this.numeroDias = numeroDias;
        this.checkOut = checkIn.plusDays(numeroDias);
        this.valorTotal = quarto.getTipo().getPrecoPorDia() * numeroDias;
        this.status = StatusReserva.ATIVA;
    }

    public String getId() { return id; }
    public Hospede getHospede() { return hospede; }
    public Quarto getQuarto() { return quarto; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
    public int getNumeroDias() { return numeroDias; }
    public double getValorTotal() { return valorTotal; }
    public StatusReserva getStatus() { return status; }
    public void setStatus(StatusReserva status) { this.status = status; }

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public String toString() {
        return String.format(
            "╔══════════════════════════════════════════╗\n" +
            "  Reserva ID : %s\n" +
            "  Hóspede    : %s\n" +
            "  Quarto     : %d (%s)\n" +
            "  Check-in   : %s\n" +
            "  Check-out  : %s\n" +
            "  Dias       : %d\n" +
            "  Valor/Dia  : Kz %.2f\n mil" +
            "  TOTAL      : Kz %.2f\n mil" +
            "  Status     : %s\n" +
            "╚══════════════════════════════════════════╝",
            id, hospede.getNome(), quarto.getNumero(), quarto.getTipo().getDescricao(),
            checkIn.format(FMT), checkOut.format(FMT), numeroDias,
            quarto.getTipo().getPrecoPorDia(), valorTotal, status
        );
    }
}
