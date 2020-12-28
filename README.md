# Short Documentation
Develop a telegram bot that access to a database getting a random quote of a group of tables populated with responses, depending on the command it will change it response, returning it to the user.

If it's 10 AM or PM (CET) the bot will send a message wishing a good morning or a good night.

Client side will be a connection between server and telegram which translates the command given by the user into a readable order for the server.

Server side will be in charge to make a call to the database or third party APIs taking the response sending it to the client.

Bot will take commands and will show the response to the user:

    /horoscope      ----
    /PC                |
    /politics          | → will return a silly quote being able to rate it
    /random            |
    /relationship   ----
    /music {name|genre} → will send a YouTube link of the requested song
    /weather {Location} → will return the weather depending on user location
    /worldclock         → will provide a list of most common time zones
    /sales              → will send a link with the Amazon sales
