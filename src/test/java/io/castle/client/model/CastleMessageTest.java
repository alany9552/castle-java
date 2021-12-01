package io.castle.client.model;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.castle.client.internal.json.CastleGsonModel;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class CastleMessageTest {

    private CastleGsonModel model = new CastleGsonModel();

    @Test
    public void jsonSerialized() {
        // Given
        CastleMessage message = new CastleMessage("event");
        message.setCreatedAt("2018-01-01");
        message.setTimestamp("2018-01-01");
        message.setDeviceToken("1234");
        message.setReviewId("2345");
        message.setProperties(ImmutableMap.builder()
                .put("key", "val")
                .build());
        message.setUserId("3456");
        message.setUserTraits(ImmutableMap.builder()
                .put("key", "val")
                .build());

        // When
        String payloadJson = model.getGson().toJson(message);
        JsonObject convertedObject = new Gson().fromJson(payloadJson, JsonObject.class);

        // Then
        Assertions.assertThat(convertedObject.get("created_at").getAsString()).isEqualTo("2018-01-01");
        Assertions.assertThat(convertedObject.get("timestamp").getAsString()).isEqualTo("2018-01-01");
        Assertions.assertThat(convertedObject.get("device_token").getAsString()).isEqualTo("1234");
        Assertions.assertThat(convertedObject.get("event").getAsString()).isEqualTo("event");
        Assertions.assertThat(convertedObject.get("properties").toString()).isEqualTo("{\"key\":\"val\"}");
        Assertions.assertThat(convertedObject.get("review_id").getAsString()).isEqualTo("2345");
        Assertions.assertThat(convertedObject.get("user_id").getAsString()).isEqualTo("3456");
        Assertions.assertThat(convertedObject.get("user_traits").toString()).isEqualTo("{\"key\":\"val\"}");
    }

    @Test
    public void fullBuilderJson() {
        // Given
        CastleMessage message = CastleMessage.builder("event")
            .createdAt("2018-01-01")
            .timestamp("2018-01-01")
            .deviceToken("1234")
            .reviewId("2345")
            .properties(ImmutableMap.builder()
                .put("key", "val")
                .build())
            .userId("3456")
            .userTraits(ImmutableMap.builder()
                .put("key", "val")
                .build())
            .build();

        Assert.assertEquals(message.getCreatedAt(), "2018-01-01");
        Assert.assertEquals(message.getDeviceToken(), "1234");
        Assert.assertEquals(message.getReviewId(), "2345");
        Assert.assertEquals(message.getUserId(), "3456");
        Assert.assertEquals(message.getEvent(), "event");
        Assert.assertEquals(message.getUserTraits(), ImmutableMap.builder()
                .put("key", "val")
                .build());
        Assert.assertEquals(message.getProperties(), ImmutableMap.builder()
                .put("key", "val")
                .build());

        // When
        String payloadJson = model.getGson().toJson(message);
        JsonObject convertedObject = new Gson().fromJson(payloadJson, JsonObject.class);

        // Then
        Assertions.assertThat(convertedObject.get("created_at").getAsString()).isEqualTo("2018-01-01");
        Assertions.assertThat(convertedObject.get("timestamp").getAsString()).isEqualTo("2018-01-01");
        Assertions.assertThat(convertedObject.get("device_token").getAsString()).isEqualTo("1234");
        Assertions.assertThat(convertedObject.get("event").getAsString()).isEqualTo("event");
        Assertions.assertThat(convertedObject.get("properties").toString()).isEqualTo("{\"key\":\"val\"}");
        Assertions.assertThat(convertedObject.get("review_id").getAsString()).isEqualTo("2345");
        Assertions.assertThat(convertedObject.get("user_id").getAsString()).isEqualTo("3456");
        Assertions.assertThat(convertedObject.get("user_traits").toString()).isEqualTo("{\"key\":\"val\"}");
    }

    @Test
    public void properties() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("key", "value");

        CastleMessage message = CastleMessage.builder("event")
                .properties(jsonObject)
                .build();

        String payloadJson = model.getGson().toJson(message);
        JsonObject convertedObject = new Gson().fromJson(payloadJson, JsonObject.class);

        Assertions.assertThat(convertedObject.get("event").getAsString()).isEqualTo("event");
        Assertions.assertThat(convertedObject.get("properties").toString()).isEqualTo("{\"key\":\"value\"}");
    }

    @Test
    public void otherProperties() {
        CastleMessage message = CastleMessage.builder("event")
                .put("key", "value")
                .build();

        String payloadJson = model.getGson().toJson(message);

        Assertions.assertThat(payloadJson).isEqualTo("{\"event\":\"event\",\"key\":\"value\"}");

        HashMap other = new HashMap();
        other.put("key", "value");

        message = CastleMessage.builder("event")
                .other(other)
                .build();

        payloadJson = model.getGson().toJson(message);

        Assertions.assertThat(payloadJson).isEqualTo("{\"event\":\"event\",\"key\":\"value\"}");
    }

    @Test(expected = NullPointerException.class)
    public void propertiesNull() {
        CastleMessage message = CastleMessage.builder("event")
                .properties(null)
                .build();
    }

    @Test(expected = NullPointerException.class)
    public void userTraitsNull() {
        CastleMessage message = CastleMessage.builder("event")
                .userTraits(null)
                .build();
    }
}
