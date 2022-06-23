#Task for finding articles by their title in wiki 

### Technology Stack
- Spring Boot
- REST API
- MongoDB

###Prepare database
```
use wiki_dump

mongoimport --db test --collection wiki --file "path_to_json"

mongod.exe
```

### Run the project

```
gradlew bootRun
```

###Usage 
```$xslt
localhost:8081/wiki/title -> for one string output
localhost:8081/wiki/title?format=pretty -> for json output
```