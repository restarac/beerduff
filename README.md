# beerduff

## how to run

Requirements:
- Java
- Gradle (Opcional, se já tive instalado utilize `gradle` no lugar de `./gradlew`)

```
./gradlew bootRun
```

Nos logs do console há informando qual host e porta o SpringBoot iniciou.

#### Acessando a API

A API usa o banco em memoria H2 (http://www.h2database.com/html/main.html).
Toda vez quando o servidor inicializa ele executa a classe `BeerSetup` e preenche com algumas cervejas pre-selecionadas.

`GET /api` --redirect--> `GET /api/browser/index.html#/api`

Acesse o path: `/api/beers/search/findBestPlaylistBeer?temperature=0` e veja qual a cerveja é a mais indicada para essa temperatura.

Todas as rotas devolvem um atributo profile, que é parte da especificação JSON para o HAL (https://tools.ietf.org/html/draft-kelly-json-hal-08#section-5.6). O spring adiciona isso como padrão, e é uma forma complementar de documentação da sua rota restfull sem a necessidade de outra ferramenta como o `swagger`

##### CRUD Beer

A api é restful logo as ações de `Create`, `Read`, `Update` e `Delete` são baseados no verbo HTTP padrão.

`GET /api/beers` - List
`GET /api/beers/<beer_id>` - Read
`PUT /api/beers/<beer_id>` - Update all atributes
`PATCH /api/beers/<beer_id>` - Update only specified atributes
`DELETE /api/beers/<beer_id>` - Delete
`POST /api/beers` - Create

#### Browser Hal

Para acessar o browser HAL e ter uma visualização um pouco melhor de JSON

## Tests

Para executar os testes:

```
./gradlew test
```

## How to import on eclipse

Execute:

```
./gradlew eclipse
```

Depois o seu projeto com as configurações já está pronto, é necessário apenas importa-lo para o seu workspace.

Duvidas sobre como fazer o import: https://www.codejava.net/ides/eclipse/import-existing-projects-into-eclipse-workspace
