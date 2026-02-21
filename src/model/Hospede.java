package model;

public class Hospede {
    private String id;
    private String nome;
    private String bi;
    private String telefone;
    private String email;

    public Hospede(String id, String nome, String bi, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.bi = bi;
        this.telefone = telefone;
        this.email = email;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getBI() { return bi; }
    public String getTelefone() { return telefone; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return String.format("ID: %s | Nome: %s | BI: %s | Tel: %s | Email: %s",
                id, nome, bi, telefone, email);
    }
}
