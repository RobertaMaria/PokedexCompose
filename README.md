# 🔴 Pokédex Compose

Uma aplicação Android moderna e elegante para explorar informações sobre Pokémons, desenvolvida com as tecnologias mais recentes do ecossistema Android.

<div style="text-align: center;">

![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-purple?style=flat-square&logo=kotlin)
![Android](https://img.shields.io/badge/Android-API%2024+-green?style=flat-square&logo=android)
![Compose](https://img.shields.io/badge/Compose-Material%203-blue?style=flat-square&logo=android)
![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow?style=flat-square)

</div>

---

## 📱 Sobre o Projeto

Pokédex Compose é uma aplicação que permite aos usuários:
- 🔍 **Listar Pokémons** com paginação eficiente
- 🎯 **Buscar Pokémons** por ID ou nome completo
- 📊 **Visualizar detalhes** incluindo stats, tipos, habilidades e evoluções
- 💾 **Cache local** de dados para uso offline

A aplicação segue as melhores práticas de arquitetura, padrões de design e utiliza as bibliotecas mais modernas do Android.

---

## 🛠️ Tech Stack

### Core & Framework
- **Kotlin** - Linguagem principal
- **Android 24+** - API mínima suportada
- **Compose** - Interface declarativa com Material Design 3
- **Java 11** - Compilação com target moderno

### Arquitetura & Padrões
- **Clean Architecture** - Separação de responsabilidades em camadas
- **MVVM** - Model-View-ViewModel para gerenciamento de estado
- **Repository Pattern** - Abstração de dados
- **RemoteMediator + Paging 3** - Paginação eficiente com cache

### Networking
- **Retrofit 2** - Cliente HTTP type-safe
- **OkHttp** - Interceptação e logging de requisições
- **GSON** - Serialização/Desserialização JSON

### Persistência
- **Room Database** - ORM SQLite com Type Safety
- **KSP (Kotlin Symbol Processing)** - Compilação de anotações

### Injeção de Dependência
- **Koin** - Framework DI leve e intuitivo

### Testes
- **JUnit 4** - Testes unitários
- **Mockk** - Mocks para testes
- **Turbine** - Testes de Flows
- **Espresso** - Testes de UI
- **Architecture Core Testing** - Testes de LiveData/StateFlow

---

## 🏗️ Arquitetura

### Estrutura do Projeto

```
PokedexCompose/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/pokedex/
│   │   │   │   ├── common/          # Código compartilhado
│   │   │   │   │   ├── database/    # Banco de dados
│   │   │   │   │   ├── di/          # Injeção de dependência (Koin)
│   │   │   │   │   ├── retrofit/    # Configuração HTTP
│   │   │   │   │   └── model/       # Modelos comuns
│   │   │   │   │
│   │   │   │   ├── list/            # Feature: Lista de Pokémons
│   │   │   │   │   ├── data/        # Data Layer (DAO, API, Mappers)
│   │   │   │   │   ├── domain/      # Domain Layer (Use Cases, Repositories)
│   │   │   │   │   └── view/        # Presentation Layer (UI, ViewModel)
│   │   │   │   │
│   │   │   │   ├── details/         # Feature: Detalhes do Pokémon
│   │   │   │   │   ├── data/        # Data Layer
│   │   │   │   │   ├── domain/      # Domain Layer
│   │   │   │   │   └── view/        # Presentation Layer
│   │   │   │   │
│   │   │   │   ├── ui/              # Temas e estilos globais
│   │   │   │   └── utils/           # Funções utilitárias
│   │   │   │
│   │   │   └── AndroidManifest.xml
│   │   │
│   │   ├── test/                    # Testes unitários
│   │   └── androidTest/             # Testes de instrumentação
│   │
│   └── build.gradle.kts             # Build config com versões
│
├── gradle/
│   └── libs.versions.toml           # Gerenciamento de dependências
│
└── build.gradle.kts
```

### Fluxo de Dados

```
UI (Compose) 
    ↓
ViewModel (State Management)
    ↓
Use Case (Business Logic)
    ↓
Repository (Abstração de dados)
    ↓
Data Sources (Local DB + Remote API)
    ↓
Room Database / PokéAPI
```

---

## 📚 Funcionalidades Principais

### 1️⃣ Listagem de Pokémons
- Paginação com **Paging 3**
- RemoteMediator sincroniza dados da API com o banco local
- Cache inteligente com apenas os dados necessários

### 2️⃣ Busca de Pokémons
- **Busca por nome exato**: Digite "pikachu" e encontre-o
- **Busca por ID**: Use o identificador numérico do pokémon

### 3️⃣ Detalhes do Pokémon
Ao abrir um pokémon, você vê:
- 🖼️ Imagem oficial de alta qualidade
- 📊 **Stats**: HP, Ataque, Defesa, Sp. Atk, Sp. Def, Velocidade
- 🏷️ **Tipos**: Todos os tipos do pokémon
- 🎯 **Habilidades**: Listagem de habilidades especiais
- ⚡ **Relações de dano**: Tipos super efetivos e resistências
- 📈 **Evoluções**: Cadeia evolutiva completa
- 📝 **Descrição**: Informações da espécie

### 4️⃣ Design & UX
- Interface moderna com **Material Design 3**
- Paleta de cores adaptada por tipo de pokémon
- Animações suaves para melhor experiência
- Dark mode automático
- Responsivo para diferentes tamanhos de tela

---

## 🚀 Como Usar

### Pré-requisitos
- Android Studio Ladybug ou superior
- Android SDK 24 (API Level 24) ou superior
- Kotlin 1.9+
- Git

### Instalação

1. **Clone o repositório**
```bash
git clone https://github.com/seu-usuario/pokedex-compose.git
cd PokedexCompose
```

2. **Abra no Android Studio**
```bash
# Android Studio deve detectar automaticamente
open -a "Android Studio" .
```

3. **Sincronize as dependências**
- Android Studio fará isso automaticamente
- Ou execute: `./gradlew build`

4. **Execute a aplicação**
- Conecte um dispositivo/emulador
- Clique em "Run" (▶️) ou pressione Shift + F10

### Uso da Aplicação

#### Tela de Lista
1. Abra a aplicação
2. Role para explorar pokémons
3. Clique em qualquer pokémon para ver detalhes

#### Busca
- **Por Nome**: Digite "pikachu" na barra de busca
- **Por ID**: Digite "25" para encontrar Pikachu

#### Tela de Detalhes
- Deslize para cima para ver mais informações
- Toque em um pokémon da cadeia evolutiva para vê-lo

---

## 🔌 API Integrada

A aplicação utiliza a **PokéAPI v2** (https://pokeapi.co/)

### Endpoints Utilizados

| Funcionalidade | Endpoint | Método |
|---|---|---|
| Listar pokémons | `GET /pokemon?limit=20&offset=X` | Paginado |
| Detalhes por ID | `GET /pokemon/{id}` | Único |
| Detalhes por nome | `GET /pokemon/{name}` | Único |
| Espécie | `GET /pokemon-species/{id}` | Detalhes |
| Cadeia evolutiva | `GET /evolution-chain/{id}` | Detalhes |
| Relações de tipo | `GET /type/{name}` | Detalhes |

**Notas importantes:**
- ✅ Suporta busca por nome exato (pikachu ✓, pika ✗)
- ✅ Suporta busca por ID numérico
- ❌ Não suporta busca por substring (sem autocomplete)
- ✅ Sem autenticação necessária

---

## 💾 Banco de Dados Local

### Estratégia de Persistência

**RemoteMediator Pattern:**
1. Primeira carga: Busca por todos os pokémons da API
2. Insere no Room Database
5. Dados sempre disponíveis mesmo offline

**Tabelas:**
- `pokemon_table` - Armazena pokémons com stats e tipos
- `remote_keys_table` - Controla paginação (prev_key, next_key)
- `pokemon_specie_table` - Espécie e evoluções
- `pokemon_evolution_table` - Cadeia evolutiva

---

## 🧪 Testes

A aplicação inclui testes em múltiplas camadas:

### Testes Unitários
```bash
./gradlew test
```

### Testes de Integração
```bash
./gradlew connectedAndroidTest
```

### Cobertura de Testes
- ✅ Repository pattern
- ✅ Use cases
- ✅ ViewModel
- ✅ Mappers
- ✅ Remote Mediator
- ✅ Database operations

---

## 🎨 Paleta de Cores por Tipo

Cada pokémon é colorido de acordo com seu tipo:

| Tipo | Cor |
|---|---|
| Normal | #A8A878 |
| Fogo | #F08030 |
| Água | #6890F0 |
| Elétrico | #F8D030 |
| Grama | #78C850 |
| Gelo | #98D8D8 |
| Luta | #C03028 |
| Veneno | #A040A0 |
| Terra | #E0C068 |
| Voador | #A890F0 |
| Psíquico | #F85888 |
| Inseto | #A8B820 |
| Rocha | #B8A038 |
| Fantasma | #705898 |
| Dragão | #7038F8 |
| Sombrio | #705848 |
| Metálico | #B8B8D0 |
| Fada | #EE99AC |

---

## 📊 Performance

### Otimizações Implementadas

✅ **Paging 3 + RemoteMediator**
- Carrega em lotes conforme o `pageSize` configurado (por padrão 20)
- Cache inteligente no Room Database
- Evita recarregar dados já obtidos

✅ **Lazy Loading de Imagens**
- Coil para carregamento otimizado
- Cache de imagens em memória e disco
- Placeholder durante carregamento

✅ **Coroutines**
- Operações de banco em thread separada
- Network requests assíncronos
- Gerenciamento eficiente de memória

✅ **Database Queries**
- Índices nas tabelas principais
- Queries otimizadas com Room
- Sincronização em background

---

## 🐛 Solução de Problemas

### A aplicação não carrega pokémons
1. Verifique conexão com internet
2. Verifique se a PokéAPI está online
3. Limpe cache: Settings > Apps > Pokédex > Storage > Clear Cache

### Busca não encontra pokémons
- **Certifique-se de digitar o nome completo**: "pikachu" ✓, "pika" ✗
- Ou use o ID numérico: "25"
- Nomes são case-insensitive: "Pikachu", "PIKACHU", "pikachu" funcionam

### Imagens não carregam
1. Verifique conexão com internet
2. Verifique se há espaço suficiente no dispositivo
3. Limpe cache do Coil nas configurações do app

---

## 📝 Roadmap Futuro

- [ ] **Filtro por tipo** de pokémon
- [ ] **Favoritos** - Salvar pokémons favoritos
- [ ] **Notificações** quando novos pokémons são adicionados
- [ ] **Comparador** de dois pokémons lado a lado
- [ ] **Gerenciador de equipe** - Montar times de batalha
- [ ] **Modo offline melhorado** - Download seletivo de dados
- [ ] **Widget** para home screen
- [ ] **Temas customizáveis** além de light/dark

---

## 👨‍💻 Autor


- GitHub: [@RobertaMaria](https://github.com/RobertaMaria)
- LinkedIn: [Roberta-Ramos](https://www.linkedin.com/in/roberta-ramos-14b0a6199/)
- Email: robertaji26@gmail.com
