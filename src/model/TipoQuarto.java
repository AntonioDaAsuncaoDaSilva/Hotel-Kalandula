package model;

public enum TipoQuarto {
    SOLTEIRO("Solteiro", 80.000),
    DUPLO("Duplo", 150.000),
    SUITE("Suíte", 250.000),
    SUITE_LUXO("Suíte Luxo", 400.000);

    private final String descricao;
    private final double precoPorDia;

    TipoQuarto(String descricao, double precoPorDia) {
        this.descricao = descricao;
        this.precoPorDia = precoPorDia;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPrecoPorDia() {
        return precoPorDia;
    }

    @Override
    public String toString() {
        return (
            descricao +
            " - Kz " +
            String.format("%.2f", precoPorDia) +
            "mil" +
            "/dia"
        );
    }
}
