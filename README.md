# Prakem

Este projeto tem como objetivo implementar um backend completo utilizando Java com Spring Boot para o projeto Prakem.

# Tecnologias Utilizadas

- Java 21
- MongoDB Atlas
- Maven
- Lombook
- Jakarta Validation
- JJWT
- Spring:
  * Boot
  * Data
  * Security
  * Session

# Estrutura do Projeto

O projeto segue uma estrutura organizada para facilitar a manutenção e expansão do código. Segue a estrutura detalhada dos diretórios e classes:

## Diretórios e Arquivos

```
src/main/java/com/prakem/prakem
├── config
│   ├── AsyncConfig.java            # Configuração para métodos assíncronos
│   ├── CorsConfig.java             # Configuração de permissões CORS
│   ├── SecurityConfiguration.java  # Configurações de segurança (JWT, autenticação)
│   └── JwtConfig.java              # Configuração do JWT
├── controller
│   └── UserController.java        # Endpoints para gerenciar usuários
├── dto
│   └── UserDTO.java               # Objeto de transferência de dados de usuário
├── entity
│   └── User.java                  # Entidade de usuário persistida no banco
├── logging
│   ├── config
│   │   └── WebConfig.java           # Configuração do interceptor de logging
│   ├── entity
│   │   └── RequestLog.java         # Entidade para armazenar logs
│   ├── interceptor
│   │   └── LoggingInterceptor.java  # Intercepta requisições HTTP para logging
│   ├── repository
│   │   └── RequestLogRepository.java # Repositório MongoDB para logs
│   └── service
│       └── RequestLogService.java    # Lógica para salvar logs
├── mapper
│   └── UserMapper.java            # Mapeamento entre entidade e DTO de usuário
├── repository
│   └── UserRepository.java        # Repositório MongoDB para usuários
├── service
│   └── UserService.java           # Regras de negócio relacionadas a usuários
├── util
│   ├── Encrypter.java            # Utilitário para criptografar dados
│   └── PasswordValidator.java              # Utilitário para manipulação de tokens JWT
└── MainFile                       # Classe principal para inicializar o projeto
```

# Como Executar o Projeto

- Execute o comando no powershell como administrador, substituindo MONGO_CUSTOM_URL com sua URL de conexão com o banco: [System.Environment]::SetEnvironmentVariable("MONGODB_URI", "mongodb+srv://usuario:senha@urlbanco/nomecluster?retryWrites=true&w=majority", "Machine");
- Execute o comando no powershell como administrador, substituindo YOUR_CUSTOM_SECRET com sua chave para criptografar os JWTs: [System.Environment]::SetEnvironmentVariable("PRAKEM_KEY", "YOUR_CUSTOM_SECRET", "Machine");
- Clone o repositório:
- git clone https://github.com/LHMasson/prakem.git
- Instale as dependências:
- mvn clean install
- Execute o arquivo PrakemApplication no caminho src/main/java/com.prakem.prakem/PrakemApplication

# Melhorias Futuras

* Implementar log para autenticação e erros de negócio. 
* Adicionar suporte a outros bancos de dados, como PostgreSQL. 
* Criar uma API para consulta de logs diretamente.

# Contribuições
- Fork este repositório 
- Crie uma branch com sua feature: git checkout -b minha-feature 
- Envie suas modificações: git push origin minha-feature 
- Abra um Pull Request

# Licença

Este projeto está licenciado sob a Licença MIT. Consulte o arquivo LICENSE para mais informações.
