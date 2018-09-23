# :beer: Desafio

Nossos devs gostam muito de cerveja, e por isso queremos criar nossa pr�pria cervejeira :smirk::beer:. 

O desafio � servir a cerveja sempre gelada! Voc� sabia que existem v�rios estilos de cerveja (IPA, Weizenbier, Pilsens, etc)? 

Sabia que cada estilo tem uma temperatura ideal de consumo? Isso mesmo, em uma temperatura ideal sua breja fica mais saborosa :open_mouth:! 

**Exemplo:**


|Estilo|Temperatura Ideal para consumo|
|:---:|:---:|
|Weissbier|-1� a 3�|
|Pilsens |-2� a 4�|
|Weizenbier |-4� a 6�|
|Red ale|-5� a 5�|
|India pale ale|-6� a 7�|
|IPA|-7� a 10�|
|Dunkel|-8� a 2�|
|Imperial Stouts|-10� a 13�|
|Brown ale|0� a 14�|


## Tarefas

### 1. Crie um microservi�o para os estilos de cerveja

Precisamos que crie uma api que possamos listar, cadastrar, deletar e atualizar nossos estilos de cerveja e suas temperaturas(C.R.U.D).


### 2. Criar um endpoint

Para nos ajudar a criar nossa m�quina cervejeira, desenvolva uma **api Restful** na qual, dada uma temperatura, ela nos devolva o estilo de cerveja mais adequado para aquela temperatura e uma playlist que contenha o nome desse estilo(use a api do [spotify](https://developer.spotify.com/documentation/web-api/) para buscar as playlist). 

**Regras de neg�cio** 

* Todo estilo de cerveja tem uma temperatura m�nima e uma temperatura m�xima.
* O c�lculo para selecionar o estilo de cerveja adequado: � qual estilo cont�m a m�dia das suas temperaturas mais pr�xima do input dado pela api.(Se o input foi -2 e temos as cervejas Dunkel e Weissbier o estilo selecionado � o Dunkel).
* Caso o resultado seja mais de um estilo de cerveja, devolver o estilo por ordem alfab�tica(entre Pilsens e IPA volt�ra IPA) e caso de empate na primeira letra, ordernar pela segunda e assim por diante.
* Caso n�o tenha uma playlist que contenha o nome do estilo, retornar um HTTP Status que achar mais adequado.
* A lista dada foi um exemplo, a api tem que estar pronta para receber mais estilos e mais temperaturas.


Exemplo:

**Entrada:**
```json
{
"temperature" : -7
}
```
**Sa�da**
```json
{
  "beerStyle": "IPA",
  "playlist":{	
  "name":"IPARTY",
  "tracks": [{
			"name": "Lua de Cristal",
			"artist": "Xuxa",
			"link": "https: //open.spotify.com/artist/21451j1KhjAiaYKflxBjr1"
		},
		{
			"name": "Vogue",
			"artist": "Madonna",
			"link": "https: //open.spotify.com/artist/21451j1Khj123YKflxBjr1"
		}
	]
  }
}
```



### 3. O que esperamos:

* Seu projeto deve ser **multi-plataforma**.
* Crie uma documenta��o e explique como como rodar localmente e tamb�m como realizar o deploy.
* Quanto mais simples for para realizar o deploy, melhor!
* Sinta-se a vontade para usar qualquer framework e ferramentas que se sentir a vontade a �nica restri��o � a linguagem de programa��o que deve ser **Java**
