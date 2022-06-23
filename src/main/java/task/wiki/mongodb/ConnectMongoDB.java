package task.wiki.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

@Component
public class ConnectMongoDB {
	private MongoCollection<Document> mongoCollection;
	private MongoDatabase mongoDatabase;

	ConnectMongoDB() {
		getConnection();
	}
	private void getConnection() {
		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

		mongoDatabase = mongoClient.getDatabase("test");
		mongoCollection = mongoDatabase.getCollection("wiki");
	}

	public JSONObject getDataFromDB(String expression) throws Exception {
		Optional<Document> doc = Optional.ofNullable(mongoCollection.find(eq("title", expression)).first());
		return convertResult(doc.orElseThrow());
	}
	public JSONObject convertResult(Document doc) throws Exception {

		JSONObject ob = new JSONObject();
		Optional<String> text = Optional.ofNullable(doc.getString("auxiliary_text"));
		ob.put("auxiliary_text", text.orElse("[]"));

		Instant tmp1 = Instant.parse(doc.getString("create_timestamp"));
		ob.put("create_timestamp", (Date.from(tmp1).getTime())/1000);

		Instant tmp2 = Instant.parse(doc.getString("timestamp"));
		ob.put("timestamp", (Date.from(tmp2).getTime())/1000);
		return ob;
	}
}