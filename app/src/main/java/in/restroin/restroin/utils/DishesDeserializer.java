package in.restroin.restroin.utils;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import in.restroin.restroin.models.Offers;
import in.restroin.restroin.models.RestaurantDishesModel;

public class DishesDeserializer implements JsonDeserializer<RestaurantDishesModel> {

    @Override
    public RestaurantDishesModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement dishes = json.getAsJsonObject().get("popular_dishes");
        return new Gson().fromJson(dishes, RestaurantDishesModel.class);
    }
}
