
# API
Para acessar API de teste use os dados:
- Base url: `https://game-checkout.herokuapp.com`
- Para validação envie um header `Token` com a o valor `QceNFo1gHd09MJDzyswNqzStlxYGBzUG`

### Serviços
##### [GET]/game
Retorna a lista de jogos da loja
```
curl -X GET https://game-checkout.herokuapp.com/game  -H 'TOKEN: QceNFo1gHd09MJDzyswNqzStlxYGBzUG' 
```

##### [GET]/game/{id}
Retorna o jogo baseado no {id}

```
curl -X GET https://game-checkout.herokuapp.com/game/312 -H 'TOKEN: QceNFo1gHd09MJDzyswNqzStlxYGBzUG'
```

##### [POST]/checkout
Finaliza a compra. Para fins de teste, esse serviço não precisa receber nenhum valor, entretanto, existe uma pequena chance do serviço retornar `ERROR 400`

```
curl -X POST https://game-checkout.herokuapp.com/checkout -H 'TOKEN: QceNFo1gHd09MJDzyswNqzStlxYGBzUG'
```
