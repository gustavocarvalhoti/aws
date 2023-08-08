package service.lambda;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import service.lambda.request.ItemQuestionarioRequest;
import service.lambda.request.QuestionarioRequest;
import service.lambda.response.QuestionarioResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SaveQuestionarioHandler implements RequestHandler<QuestionarioRequest, QuestionarioResponse> {

    private AmazonDynamoDB amazonDynamoDB;

    private final String DYNAMODB_TABLE_NAME = "Questionario";
    private final Regions REGION = Regions.SA_EAST_1;

    public QuestionarioResponse handleRequest(QuestionarioRequest questionarioRequest, Context context) {
        this.initDynamoDbClient();

        persistData(questionarioRequest);

        QuestionarioResponse questionarioResponse = new QuestionarioResponse();
        questionarioResponse.setMessage("Saved Successfully!!! ");

        return questionarioResponse;
    }

    private void persistData(QuestionarioRequest questionarioRequest) throws ConditionalCheckFailedException {

        Map<String, AttributeValue> questionarioMap = new HashMap<>();
        questionarioMap.put("categoria_produto", new AttributeValue(String.valueOf(questionarioRequest.getCategoria())));

        List<ItemQuestionarioRequest> itens = questionarioRequest.getItens();
        if (Objects.nonNull(itens) && itens.size() > 0) {
            questionarioRequest.getItens().forEach(item -> {
                Map<String, AttributeValue> itemMap = new HashMap<>();
                itemMap.put("ref_questao", new AttributeValue(String.valueOf(item.getRefQuestao())));
                itemMap.put("ativo", new AttributeValue(String.valueOf(item.getAtivo())));
                itemMap.put("ordem", new AttributeValue(String.valueOf(item.getOrdem())));

                questionarioMap.put("itens", new AttributeValue(String.valueOf(itemMap)));
            });
        }

        amazonDynamoDB.putItem(DYNAMODB_TABLE_NAME, questionarioMap);
    }

    private void initDynamoDbClient() {
        this.amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
                .withRegion(REGION)
                .build();
    }
}
