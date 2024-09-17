# tech-restaurante
Nosso serviço de reservas online conta com 3 fluxos
1. Cadastro de restaurante
2. Reservas
3. Avaliações

Nesse arquivo estará contido o fluxo para utiliza-los.

## Cadastro de restaurantes

### Cadastrar novo restaurante

Para iniciar a utilização do sistema, será necessário cadastrar o restaurante. 
Esse cadastro será efetuado pela rota POST /restaurant, a documentação deste endpoint pode ser acessada [aqui](http://localhost:8080/swagger-ui/index.html#/restaurant-controller/createRestaurant)

### Atualizar restaurante existente

Após o cadastro, caso necessário poderemos atualizar o registro utilizando o endpoint PUT /restaurant/edit/{id}, a documetanção deste endpoint pode ser acessada [aqui](http://localhost:8080/swagger-ui/index.html#/restaurant-controller/editRestaurant)

### Consultar restaurante existente por id

Para obter os dados de cadastro do restaurante, pode-se utilizar o endpoint GET /restaurant/get/{id}. Sua documentação está [aqui](http://localhost:8080/swagger-ui/index.html#/restaurant-controller/getRestaurant)

### Consultar todos os restaurantes cadastrados

Para obter uma lista com todos os restaurantes cadastrados, utiliza-se o endpoint GET /restaurant/get. Cuja documentação está [aqui](http://localhost:8080/swagger-ui/index.html#/restaurant-controller/findRestaurants).
**Atenção, essa consulta é case sensitive**.

### Deletar restaurante por id

Caso o restaurante pare de utilizar o sistema, é possível deletar o seu registro através do endpoint DELETE /restaurant/{id}, acesse [aqui](http://localhost:8080/swagger-ui/index.html#/restaurant-controller/deleteRestaurant) para ver a sua documentação.

## Reservas

### Processamento das disponibilidades

Nossa aplicação possui um scheduler que roda todos os dias durante a madrugada para realizar o processamento das disponibilidades dos restaurantes para o dia seguinte. Caso seja necessário, é possível reealizar esse processamento manualmente através do endpoint POST /availability que possui a documentação [aqui](http://localhost:8080/swagger-ui/index.html#/available-controller/process).

### Consultar disponibilidade do restaurante

Para consultar a disponibilidade do restaurante, é necessário utilizar o endpoint GET /availability, sua documentação está disponível [aqui](http://localhost:8080/swagger-ui/index.html#/available-controller/findAvailability).