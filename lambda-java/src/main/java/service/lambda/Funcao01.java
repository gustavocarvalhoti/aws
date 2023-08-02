package service.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Funcao01 implements RequestStreamHandler {

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        //API para manipulacao de JSONs
        JsonObject responseJson = new JsonObject();
        JsonObject responseBody = new JsonObject();
        JsonObject headerJson = new JsonObject();

        //Propriedade mensagem na resposta
        responseBody.addProperty("message", "It's everything OK here!");

        //Header aleatório na resposta para API Gateway
        headerJson.addProperty("x-custom-header", "My custom header value");

        //Http status code 200 OK
        responseJson.addProperty("statusCode", 200);
        responseJson.add("headers", headerJson);
        responseJson.addProperty("body", responseBody.toString());

        //Serializa o JSON para um OutputStream
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
        writer.write(responseJson.toString());
        writer.close();
    }

}