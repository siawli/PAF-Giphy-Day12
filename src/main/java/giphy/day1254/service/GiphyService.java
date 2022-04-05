package giphy.day1254.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

@Service
public class GiphyService {
    
    @Value("${giphy.api.key}")
    private String giphyKey;

    public List<String> getGiphs(String q) {
        return getGiphs(q, "pg", 10);
    }

    public List<String> getGiphs(String q, String rating) {
        return getGiphs(q, rating, 10);
    }

    public List<String> getGiphs(String q, Integer limit) {
        return getGiphs(q, "pg", limit);
    }

    public List<String> getGiphs(String q, String rating, Integer limit) {
        /* 
            https://api.giphy.com/v1/gifs/search?
            api_key=d079u6CDxbHAYxSZjHeIRgGd5a5ioIUM
            &q=pokemon
            &limit=25
            &offset=0
            &rating=g
            &lang=en
            want the fixed_width: url
        */
        List<String> result = new LinkedList<>();

        String url = "https://api.giphy.com/v1/gifs/search";
        //System.out.println(">>>>>>> giphykey: " + giphyKey);
        url = UriComponentsBuilder
                    .fromUriString(url)
                    .queryParam("api_key", giphyKey)
                    .queryParam("q", q)
                    .queryParam("limit", limit)
                    .queryParam("rating", rating)
                    .toUriString();

        RequestEntity<Void> req = RequestEntity.get(url).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);

        JsonObject jsonObj = null;
        try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
            JsonReader reader = Json.createReader(is);
            jsonObj = reader.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }

        JsonArray dataValArr = jsonObj.getJsonArray("data");
        for (JsonValue gif : dataValArr) {
            JsonObject imgValObj = gif.asJsonObject().getJsonObject("images");
            JsonObject fixedWidthValues = imgValObj.getJsonObject("fixed_width");
            String urlFromFixedWidth = fixedWidthValues.getString("url");
            //System.out.println(">>>>>>>>> urlFromFixedWidth: " + urlFromFixedWidth);
            /* Strig image = gif.asJsonObject()
                            .getJsonObject("images")
                            .getJsonObject("fixed_width")
                            .getString("url");            
            */
            result.add(urlFromFixedWidth);
        }

        return result;
    }
}
