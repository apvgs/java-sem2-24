package br.com.fiap.model;

import br.com.fiap.exception.CepInvalido;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

final class PegaEnderecoViaApi {

    public static Endereco buscarEndereco(String cep) throws CepInvalido {
        try {
            String uri = "http://viacep.com.br/ws/" + cep + "/json/";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, Endereco.class);
        } catch (IOException | InterruptedException e) {
            throw new CepInvalido("cep não encontrado");
        }
    }
}
