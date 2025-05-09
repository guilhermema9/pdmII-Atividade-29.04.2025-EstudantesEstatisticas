- Para executar o DiarioEscolar
  1. Abrir projeto no Intellij
  2. Maven > lifecycle > click duplo em package
  3. Apos criado o target
  4. Executar o arquivo .jar
  5. Abrir terminal no diretorio
  6. Executar: java -jar DiarioEscolar.jar
  7. https://localhost:8080/estudantes

- No codigo do APP no AndroidStudio, inserir as linhas onde é instanciado o Conection
- OBS: trocar http por https na chamada

- Usar RecyclerView !



----
Atividade 03 (continuação)

- Definir o envio do PUT por JSON
- Metodo PUT
- Cadastrar Novo Estudante -> Nova Activity
- Dentro do repository 
- Criar classe repository para organizar os metodos (opcional)
  - getEstudante
  - getEstudante por id
  - getEstudantes
  - setEstudante
    