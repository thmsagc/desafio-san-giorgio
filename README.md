# Desafio San Giorgio
### Compass.UOL
### Desenvolvedor: Thomás Chaves

#### Problema proposto: [Desafio San Giorgio](https://github.com/dtdtcamara/desafio-san-giorgio/blob/master/Desafio-San-Giorgio.md)

## Interpretação
O problema proposto solicitaa criação de uma funcionalidade para processar pagamentos. Ela deve receber como entrada
um objeto contendo o identificador do vendedor, entidade  a qual nomeei internamente como "Client" e uma lista de
pagamentos (Payment).
Cada pagamento deve conter o identificador da cobrança, entidade a qual nomeei internamente como "Charge" e o valor pago.
O retorno deve ser muito semelhante a entrada, porém deve informar a situação de pagamento.

### Inferências e Validações
1. Na descrição do problema, não consegui chegar a uma interpretação assertiva do que o propositor entendeu como
   "situação de pagamento".  Entendo que é possível entender como o status do pagamento em si, e também a situação de
   pagamento da cobrança (valor total pago).
   No desafio, assumi que a "situação de pagamento" se refere ao status da cobrança, e não do pagamento individual.
   Portanto, se há uma cobrança de valor 100 e o usuário insere dois pagamentos de 50 reais, o primeiro entendo que é
   parcial e o segundo total, pois o saldo devedor da cobrança, após o primeiro pagamento, era de 50 reais. Na resposta,
   a situação do pagamento foi propositalmente colocada em cada pagamento individual, de acordo com o que compreendi
   no item que solicita a escrita do pagamento em diferentes filas SQS a depender do seu status.

        Exemplo
        Cobrança: valor 100
        Pagamento 1: valor 50, status parcial.
        Pagamento 2: valor 50, status total.

2. Na entrada do endpoint para processamento de pagamentos, o identificador do cliente e a lista de pagamentos foram
   definidos como campos obrigatórios. Para os pagamentos inseridos na lista, o identificador da cobrança e o valor também
   foram definidos como obrigatórios.

3. O processamento de pagamentos aceita pagamentos para diferentes cobranças na mesma requisição e ser totalmente
   capaz de processar a requisição.

## Aplicação
### Recursos
- Framework: Spring Framework v3.3.5.
- Banco de Dados: H2.
- Recursos utilizados: Spring Web, Spring Data Jpa, Spring Validation, AWS SDK SQS, Springdoc Web MVC UI, JUnit 5, Mockito, Lombok, Mapstruct.

### Documentação
- Os endpoints da aplicação foram documentados com Swagger.
    - URL (localhost): http://localhost:8080/swagger-ui/index.html#
- Status de pagamento: Parcial (Partial), Total (Paid), Excedente (Surplus).
- Entidades: Pagamento (Payment), Vendedor (Client), Cobrança (Charge).


### Endpoints
- Endpoint para processamento de pagamentos.
    -  Request
          ```json
          {
            "client_id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            "payment_items": [
              {
                "charge_id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                "payment_value": 0.01
              }
            ]
          }
          ```
    - Response
      ```json
      {
        "client_id": "4f70f8df-517a-4f3d-af26-d53cbbaba775",
        "payment_items": [
          {
            "id": "e8ef4ba1-22f4-4679-a65c-a93bc9bf0a57",
            "charge_id": "cf7a7743-8a5a-474b-a1fd-c82affb83e10",
            "payment_value": 25,
            "status": "SURPLUS",
            "created_at": "2024-11-02T00:32:10.9061377",
            "updated_at": null
          }
        ]
      }
      ```

- Para facilitar o uso da API, foram criados endpoints para cadastrar, consultar e listar Clients (Vendedores)
- e Charges (Cobranças). Consultar documentação Swagger para mais detalhes.

## Como iniciar a aplicação corretamente
- Iniciar a aplicação com a IDE de sua preferência.
- Para uso com filas SQS reais, configurar o application.yml com as credenciais. Caso deseje executar em ambiente com filas SQS emuladas com 
Local Stack, inicializar o docker-compose e utilizar o profile "dev" na aplicação.

## Como usar as API
1. Cadastrar Client com o nome desejado com o endpoint POST /api/clients.
2. Cadastrar Charge com o valor desejado com o endpoint POST /api/charges.
3. Inserir pagamentos com o endpoint POST /api/payments, informando os identificadores dos objetos Client e Charge
cadastrados.

## Conclusão
O desafio foi excelente! Os propositores deixaram o projeto bem cru, para que o desenvolvedor pudesse pensar na solução
do que foi proposto proposto praticamente do zero, desde a compreensão do problema até a escolha da metodologia de
desenvolvimento.

Como sugestão, para evitar interpretações diferentes sobre o contexto do problema proposto, os propositores podem
acrescentar ao desafio a especificação exata do request e do response esperado. Essas informações podem ser muito
úteis para o desenvolvedor compreender com mais clareza as necessidades do problema.