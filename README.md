# Recipes Manager with Play! Framework

It allows to create, update, get and delete recipes with its ingredients, its difficulty and ratings.


## Build and run the project locally

To build and run the project:

1. Enter sbt. Enter command: `./sbt`.
2. Build the project. Enter: `compile`.
3. Run the project. Enter: `run`.
4. After the message `Server started, ...` displays, enter the following URL in a browser: <http://localhost:9000>
5. To exit the console: `exit`

### To test routes
```sh 
curl --verbose "http://localhost:9000/..."
```

### or the use of an app (ex. Postman)