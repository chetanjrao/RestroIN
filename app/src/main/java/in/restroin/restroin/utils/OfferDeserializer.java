package in.restroin.restroin.utils;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import in.restroin.restroin.models.Offers;

public class OfferDeserializer implements JsonDeserializer<Offers> {

    @Override
    public Offers deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement offers = json.getAsJsonObject().get("offers");
        return new Gson().fromJson(offers, Offers.class);
    }
}
