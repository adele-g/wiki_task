package task.wiki.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import task.wiki.mongodb.ConnectMongoDB;

@RestController
@RequestMapping("/wiki")
public class WikiController {
	final private ConnectMongoDB mongo;

	@Autowired
	public WikiController(ConnectMongoDB mongo) {
		this.mongo = mongo;
	}

	@GetMapping("/{expression}")
	public String get(@PathVariable("expression") String expression,
						  @RequestParam(value = "format", required = false, defaultValue = "none") String format) throws Exception {
		if (format.equals("pretty")){
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree(mongo.getDataFromDB(expression).toString()));
		}
		return mongo.getDataFromDB(expression).toString();
	}
}
