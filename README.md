# Trade
This application was written as part of a coding assessment assignment.

In my approach I decided to write a simple Java Spring Boot app with an http REST endpoint as well as take advantage of the snakeYaml parser.
This allowed me to create an easily readable and editable .yaml configuration file storing all the signal specifications. The file is loaded by the app at its start and is then loaded into its internal objects. On receiving an integer signal on the http endpoint the app simply reads the corresponding set of instructions and executes the proper methods from the Algo library.

The test suite consists of unit tests testing the business logic as well as MockMvc tests testing the behaviour of the http endpoint controller.
