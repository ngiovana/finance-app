# Finance App 📱💰

Aplicativo de **finanças pessoais** que permite o gerenciamento de transações de um usuário, oferecendo as funcionalidades de um **CRUD** (criar, ler, atualizar e deletar).

Desenvolvido por: **Giovana Niehues** e **Gisele de Avila**.

---

## 🎥 Tutorial em vídeo

Acesse o tutorial em vídeo completo no link abaixo:

🔗 [Ver vídeo no Youtube]([https://moodle.joinville.udesc.br/mod/assign/view.php?id=290340](https://www.youtube.com/watch?v=VOBXw717U80))

---

## 🚀 Como rodar o projeto

### 1. Clone o repositório

```bash
git clone https://github.com/ngiovana/finance-app.git
```

### 2. Rodando o backend (Spring Boot)
Abra a pasta finance-app/FinanceBackend no IntelliJ.

Verifique se possui Java 17+ e Maven instalados.

No terminal, dentro da pasta do backend, execute:

```bash
./mvnw spring-boot:run
```
A API estará disponível em http://localhost:8081

### 3. Configurando a URL da API no app Android
No Android, edite a constante BASE_URL no arquivo Constantes.java com o IP da máquina que está executando o backend:

```bash
public static final String BASE_URL = "http://<IP-DA-SUA-MÁQUINA>:8081/";
```

### 4. Rodando o app Android
Abra o Android Studio e selecione a pasta: finance-app/android-app

Aguarde a sincronização do Gradle e a instalação das dependências.

Conecte um dispositivo físico ou inicie um emulador.

Pressione Shift + F10 ou clique em Run para executar o app.

### ✅ Pronto! O aplicativo estará rodando com backend conectado e pronto para uso!
