# books-service

This Spring project uses MySQL as db,  Redis as a cache and RabbitMQ as message broker

To run this project please type "docker-compose up" in project folder 

You can add books through POST http://localhost:8081
{
    "name":"Alan2",
    "author":"Lily",
    "price":1
}

And find all books: GET http://localhost:8081
Find one book: GET http://localhost:8081/{id}

 
