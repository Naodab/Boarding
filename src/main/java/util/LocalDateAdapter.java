package util;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	@Override
	public JsonElement serialize(LocalDate localDate, Type type, JsonSerializationContext context) {
		return new JsonPrimitive(localDate.format(formatter));
	}

	@Override
	public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext context)
			throws JsonParseException {
		return LocalDate.parse(json.getAsString(), formatter);
	}
}
