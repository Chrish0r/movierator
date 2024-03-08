# Run project

1. Copy `config/application.properties.template` to `src/main/resources/application.properties`
2. Fill in all required values
3. Start the application and let it build the database schema.
3. Execute `src/main/resources/data.sql` in your database. This is the seeding script and inserts all required data.

## Database
You can either use your database or use the provided `docker-compose.yml` file to quickly spin up a local database.

To spin up the database service run:
```bash
docker-compose up -d database
```

Add this to your `application.properties`:
```
spring.datasource.url=jdbc:mysql://localhost/movie-rator
spring.datasource.username=root
spring.datasource.password=password
```

## TDMB API Token
Because the TMDB Token is a bit hard to obtain it will be include it here. But please don't use it for anything else than this project to adhere to the general terms of TMDB.

```
tmdb.api_token=eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0NjNiMGQyNTc2MzFmNzUyZTA0ODJhY2NlMWFlZGJlNCIsInN1YiI6IjYzODkxMTRkYTQxMGM4MDBhNDM1ODMyZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.VgqC42m-GyOPSepGVT3rbHSLIJDpwFZ7y9EakC_daoc
```

# Dokumentation
You can find various class diagrams in the `docs` folder.
