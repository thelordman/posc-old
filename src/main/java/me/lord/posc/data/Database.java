package me.lord.posc.data;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import me.lord.posc.Posc;
import org.bson.Document;

public class Database {
    public static void init() {
        ConnectionString connectionString = new ConnectionString(Posc.DB_URI);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        MongoDatabase database = MongoClients.create(settings).getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("test");
        System.out.println(collection.find(Filters.eq("63b89b556a9c6e5ae7e558be")).first().getString("test"));
    }
}
