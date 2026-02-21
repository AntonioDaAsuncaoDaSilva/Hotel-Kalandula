package model;

public class Quarto {
    private int numero;
    private TipoQuarto tipo;
    private boolean disponivel;
    private String andar;

    public Quarto(int numero, TipoQuarto tipo, String andar) {
        this.numero = numero;
        this.tipo = tipo;
        this.andar = andar;
        this.disponivel = true;
    }

    public int getNumero() { return numero; }
    public TipoQuarto getTipo() { return tipo; }
    public boolean isDisponivel() { return disponivel; }
    public String getAndar() { return andar; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }

    @Override
    public String toString() {
        return String.format("Quarto %d | %s | Andar: %s | Status: %s",
                numero, tipo.getDescricao(), andar,
                disponivel ? "✔ Disponível" : "✘ Ocupado");
    }
}
