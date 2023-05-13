# Bibliotecas usadas

Room, Retrofit, Glide, Hilt, OkHttp, DataStore, LiveData, Coroutines

# Arquitetura
MVVM com Clean Architecture

# Presentation
Camada de apresentaçao trabalha com Activities que fazem uso da ViewModel trabalhando em conjunto com LiveDatas que disponibilizam os dados mapeados para um objeto de view que separa a lógica da entidade de domínio. Faço uso de UseCases que trabalham em conjunto com mappers dividindo essa lógica.

# Domain
Contem a representação lógica/feature do negócio. Essa representação acordado através de casos de uso ie: UseCases fazem uso de uma camada de Repository, que em conjunto com mappers mapeiam a entidade do domínio.

# Data
Contém a camada de dados do app, aqui essa parte só se comunica com a parte de domínio, fazem todo tratamento de lógica de network assim como persistência dos dados.

# Vídeo
[device-2023-05-13-153302.webm](https://github.com/marks5/github-list/assets/6106197/b14bf09c-2c59-49e5-bf95-ffa959b1e321)
