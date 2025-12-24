# Demetrius App

Este projeto é um exemplo de aplicação Android moderna utilizando **Jetpack Compose**, **Retrofit**, e arquitetura **MVVM**.

## Estrutura do Projeto

O código está organizado em pacotes seguindo a Clean Architecture simplificada:

- **`data`**: Camada de dados.
  - **`model`**: Contém as classes de dados (ex: `Post`).
  - **`remote`**: Configuração da API (Retrofit) e interfaces de serviço (`ApiService`, `RetrofitClient`).
  - **`repository`**: Padrão Repository (`PostRepository`) que abstrai a fonte de dados para o restante do app.

- **`ui`**: Camada de interface do usuário.
  - **`viewmodel`**: Gerenciamento de estado e lógica de negócios (`PostViewModel`, `DetailsViewModel`). Utiliza `StateFlow` para emitir estados para a UI.
  - **`state`**: Classes que representam o estado da tela (ex: `UiState` com `Loading`, `Success`, `Error`).
  - **`screens`**: As telas do aplicativo (`PostListScreen`, `DetailsScreen`, `SettingsScreen`).
  - **`navigation`**: Configuração da navegação entre telas (`NavGraph`).
  - **`theme`**: Configuração de tema, cores e tipografia.

## Funcionalidades

### Tela 1: Lista de Posts (`PostListScreen`)
- Consome a API pública `https://jsonplaceholder.typicode.com/posts`.
- Utiliza `LazyColumn` para exibir a lista de forma eficiente.
- Trata estados de Carregamento (Loading), Sucesso (Success) e Erro (Error) usando a sealed class `UiState`.
- Permite navegar para os detalhes ao clicar em um item.
- Possui botão na barra superior para acessar as Configurações.

### Tela 2: Detalhes do Post (`DetailsScreen`)
- Recebe o ID do post via navegação.
- Busca os detalhes do post específico na API (ou repositório).
- Exibe Título e Corpo do post.

### Tela 3: Configurações (`SettingsScreen`)
- Tela simples com informações sobre o aplicativo.

## Principais Tecnologias

- **Jetpack Compose**: Kit de ferramentas moderno para UI nativa.
- **Retrofit**: Cliente HTTP para Android.
- **ViewModel & StateFlow**: Para gerenciamento de estado reativo.
- **Navigation Compose**: Para navegação entre Composables.
- **Coroutines**: Para operações assíncronas.

## Como Executar

1. Abra o projeto no Android Studio.
2. Aguarde a sincronização do Gradle.
3. Execute em um emulador ou dispositivo físico.
