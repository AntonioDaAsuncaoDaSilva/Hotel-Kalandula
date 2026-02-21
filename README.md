# 🏨 Sistema de Gestão Hoteleira — Java

## Como compilar e executar

```bash
# 1. Dentro da pasta hotel/src/
find . -name "*.java" > fontes.txt
javac -d ../out @fontes.txt

# 2. Executar
cd ../out
java ui.HotelApp
```

---

````cmd
cd ..\src
rmdir /s /q ..\out
mkdir ..\out
javac -d ..\out @fontes.txt
cd ..\out
java ui.HotelApp

```Power Shell
cd ..\src
Remove-Item -Recurse -Force ..\out
mkdir ..\out
javac -d ..\out @fontes.txt
cd ..\out
java ui.HotelApp

## Arquitetura (Separação de Responsabilidades)

````

src/
├── model/ ← Entidades de domínio (dados pures)
│ ├── TipoQuarto.java ← Enum com tipos e preços/dia
│ ├── Quarto.java ← Número, tipo, andar, disponibilidade
│ ├── Hospede.java ← Dados do hóspede
│ └── Reserva.java ← Vínculo hóspede↔quarto + cálculo de valor
│
├── repository/ ← Persistência em memória (in-memory "banco")
│ ├── HospedeRepository.java
│ ├── QuartoRepository.java ← Inicializa 15 quartos (4 tipos)
│ └── ReservaRepository.java
│
├── service/ ← Regras de negócio e validações
│ ├── HospedeService.java ← Validação CPF, email, duplicatas
│ ├── QuartoService.java ← Disponibilidade, cálculo de valor
│ └── ReservaService.java ← Criação, checkout, cancelamento
│
└── ui/
└── HotelApp.java ← Interface de console (menus interativos)

```

---

## Quartos disponíveis (pré-carregados)

| Andar | Quartos       | Tipo       | Preço/dia |
|-------|---------------|------------|-----------|
| 1º    | 101 – 105     | Solteiro   | R$ 80,00  |
| 2º    | 201 – 205     | Duplo      | R$ 150,00 |
| 3º    | 301 – 303     | Suíte      | R$ 250,00 |
| 4º    | 401 – 402     | Suíte Luxo | R$ 400,00 |

---

## Funcionalidades

### 👤 Hóspedes
- Cadastrar com validação de CPF e e-mail
- Buscar por ID ou CPF
- Listar todos

### 🛏️ Quartos
- Listar todos / disponíveis / ocupados
- Filtrar por tipo
- Simular valor (quarto + dias) sem reservar

### 📋 Reservas
- Nova reserva (com cálculo automático do valor total)
- Listar ativas ou todas
- Buscar por ID
- Ver reservas por hóspede
- Realizar checkout (libera o quarto)
- Cancelar reserva (libera o quarto)
```
