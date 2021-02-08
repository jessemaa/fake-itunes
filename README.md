# Fake iTunes (Don't sue me Apple!)

This is an web application with REST API's that return data from the database, made with
Spring, Thymeleaf and SQLite. It has also been published to Heroku in a Docker container.
## Main specifications

- See the working application in browser
    * Front page will display five random artists, songs and genres from DB
    * Also has a search bar from where the user can search for songs (does not work currently)
- Also has REST API and GET, POST and PUT calls can be made:
    * Get all customers
    * Add a new customer
    * Update existing customer
    * Return the number of customers by countries in descending order
    * Return the highest spenders in descending order
    * Return the favorite genre of a customer by their ID

### API calls

There is a Postman JSON file included in which contains the API calls for testing purposes.