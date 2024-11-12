package ru.mirea.ulemdzhievob.web.http;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import com.fasterxml.jackson.databind.JsonNode;
import retrofit2.Response;
import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;

public class PkmnHttpClient {
    PokemonTcgAPI tcgAPI;
    private Retrofit client;

    public PkmnHttpClient() {
        client = new Retrofit.Builder()
                .baseUrl("https://api.pokemontcg.io")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        tcgAPI = client.create(PokemonTcgAPI.class);
    }
    public JsonNode getPokemonCard(String name, String number) throws IOException {
        String requestQuery = "name:\""+name+"\"" + " " + "number:"+number;

        Response<JsonNode> response = tcgAPI.getPokemon(requestQuery).execute();

        return response.body();
    }
}

