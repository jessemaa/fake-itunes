package com.example.fakeitunes.fakeitunes.dataaccess;

        import com.example.fakeitunes.fakeitunes.logging.ConsoleLogger;
        import com.example.fakeitunes.fakeitunes.models.*;
        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.util.ArrayList;
        import java.util.HashMap;

// This class serves as the encapsulation of all database interactions,
public class CustomerRepository {

    private ConsoleLogger logger = new ConsoleLogger();

    // Setting up the connection object we need.
    private String URL = ConnectionHelper.CONNECTION_URL;
    private Connection conn = null;

    // Makes a query to get all customers in the database
    public ArrayList<Customer> getAllCustomers(){
        ArrayList<Customer> customers = new ArrayList<>();
        try{
            // Connect to DB
            conn = DriverManager.getConnection(URL);
            logger.log("Connection to SQLite has been established.");

            // Make SQL query
            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT CustomerId,FirstName,LastName,Country,PostalCode,Phone,Email FROM Customer");

            // Execute Query
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                customers.add(
                        new Customer(
                                resultSet.getString("CustomerId"),
                                resultSet.getString("FirstName"),
                                resultSet.getString("LastName"),
                                resultSet.getString("Country"),
                                resultSet.getString("PostalCode"),
                                resultSet.getString("Phone"),
                                resultSet.getString("Email")
                        ));
            }
            logger.log("Selected all customers successfully");
        }
        catch (Exception exception){
            logger.log(exception.toString());
        }
        finally {
            try {
                conn.close();
            }
            catch (Exception exception){
                logger.log(exception.toString());
            }
        }
        return customers;
    }

    // Adds a customer to database
    public Boolean addCustomer(Customer customer){
        Boolean success = false;
        try{
            conn = DriverManager.getConnection(URL);
            logger.log("Connection to SQLite has been established.");

            PreparedStatement preparedStatement =
                    conn.prepareStatement("INSERT INTO customer(CustomerId,FirstName,LastName,Country,PostalCode,Phone,Email) VALUES(?,?,?,?,?,?,?)");
            preparedStatement.setString(1,customer.getCustomerId());
            preparedStatement.setString(2,customer.getFirstName());
            preparedStatement.setString(3,customer.getLastName());
            preparedStatement.setString(4,customer.getCountry());
            preparedStatement.setString(5,customer.getPostalCode());
            preparedStatement.setString(6,customer.getPhone());
            preparedStatement.setString(7,customer.getEmail());

            int result = preparedStatement.executeUpdate();
            success = (result != 0);
            logger.log("Added customer successfully");
        }
        catch (Exception exception){
            logger.log(exception.toString());
        }
        finally {
            try {
                conn.close();
            }
            catch (Exception exception){
                logger.log(exception.toString());
            }
        }
        return success;
    }

    // Updates an existing customer in database
    public Boolean updateCustomer(Customer customer){
        Boolean success = false;
        try{
            conn = DriverManager.getConnection(URL);
            logger.log("Connection to SQLite has been established.");

            PreparedStatement preparedStatement =
                    conn.prepareStatement("UPDATE customer SET CustomerId=?,FirstName=?,LastName=?,Country=?,PostalCode=?,Phone=?,Email=? WHERE CustomerId=?");
            preparedStatement.setString(1,customer.getCustomerId());
            preparedStatement.setString(2,customer.getFirstName());
            preparedStatement.setString(3,customer.getLastName());
            preparedStatement.setString(4,customer.getCountry());
            preparedStatement.setString(5,customer.getPostalCode());
            preparedStatement.setString(6,customer.getPhone());
            preparedStatement.setString(7, customer.getEmail());
            preparedStatement.setString(8,customer.getCustomerId());

            int result = preparedStatement.executeUpdate();
            success = (result != 0);
            logger.log("Updated customer successfully");
        }
        catch (Exception exception){
            logger.log(exception.toString());
        }
        finally {
            try {
                conn.close();
            }
            catch (Exception exception){
                logger.log(exception.toString());
            }
        }
        return success;
    }

    // Gets all the customers by countries, descending order
    public ArrayList<Country> getCustomersByCountries(){
        ArrayList<Country> countries = new ArrayList<>();
        try{
            conn = DriverManager.getConnection(URL);
            logger.log("Connection to SQLite has been established.");

            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT Country, count(Country) FROM Customer GROUP BY Country ORDER BY count(Country) DESC");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                countries.add(
                        new Country(
                                resultSet.getString("Country"),
                                resultSet.getString("count(Country)")
                        ));
            }
            logger.log("Selected all customers by countries successfully");
        }
        catch (Exception exception){
            logger.log(exception.toString());
        }
        finally {
            try {
                conn.close();
            }
            catch (Exception exception){
                logger.log(exception.toString());
            }
        }
        return countries;
    }

    // Gets the highest spenders (most money spent in invoices)
    public ArrayList<HighestSpender> getHighestSpenders(){
        ArrayList<HighestSpender> highestSpenders = new ArrayList<>();
        try{
            conn = DriverManager.getConnection(URL);
            logger.log("Connection to SQLite has been established.");

            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT Invoice.Total, Customer.FirstName, Customer.LastName FROM Invoice INNER JOIN Customer ON Invoice.CustomerId=Customer.CustomerId ORDER BY Total DESC");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                highestSpenders.add(
                        new HighestSpender(
                                resultSet.getString("FirstName"),
                                resultSet.getString("LastName"),
                                resultSet.getString("Total")
                        ));
            }
            logger.log("Selected the highest spenders successfully");
        }
        catch (Exception exception){
            logger.log(exception.toString());
        }
        finally {
            try {
                conn.close();
            }
            catch (Exception exception){
                logger.log(exception.toString());
            }
        }
        return highestSpenders;
    }

    // Gets the most popular genre by given user ID - if theres multiple, its shows them all
    public HashMap<String, String> getPopularGenre(String id){
        ArrayList<PopularGenre> genre = new ArrayList<>();
        try{
            conn = DriverManager.getConnection(URL);
            logger.log("Connection to SQLite has been established.");

            String sqlQuery = """
                    SELECT Customer.FirstName, Customer.LastName, Genre.Name, COUNT(Genre.GenreId) AS InvoiceTotal FROM Genre
                    INNER JOIN Track ON Track.GenreId = Genre.GenreId
                    INNER JOIN InvoiceLine ON InvoiceLine.TrackId = Track.TrackId
                    INNER JOIN Invoice ON InvoiceLine.InvoiceId = Invoice.InvoiceId
                    INNER JOIN Customer ON Invoice.CustomerId = Customer.CustomerId
                    WHERE Customer.CustomerId = ? GROUP BY Genre.GenreId ORDER BY InvoiceTotal DESC;""";
            PreparedStatement preparedStatement =
                    conn.prepareStatement(sqlQuery);

            preparedStatement.setString(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                genre.add(
                        new PopularGenre(
                                resultSet.getString("FirstName"),
                                resultSet.getString("LastName"),
                                resultSet.getString("Name"),
                                resultSet.getString("InvoiceTotal")
                        ));
            }
            logger.log("Selected the most popular genre(s) successfully");
        }
        catch (Exception exception){
            logger.log(exception.toString());
        }
        finally {
            try {
                conn.close();
            }
            catch (Exception exception){
                logger.log(exception.toString());
            }
        }

        int popular = 0;
        HashMap<String, String> popularList = new HashMap<String, String>();

        // Loop through the list of genres and determine which are the most popular ones
        for (int i = 0; i < genre.size(); i++) {
            if (Integer.valueOf(genre.get(i).getTotal()) > popular) {
                popularList.clear();
                popularList.put("firstName", genre.get(i).getFirstName());
                popularList.put("lastName", genre.get(i).getLastName());
                popularList.put("favGenre", genre.get(i).getGenreName());
                popular = Integer.valueOf(genre.get(i).getTotal());

            // If there is something that is as popular as the most popular, add it to the list
            } else if (Integer.valueOf(genre.get(i).getTotal()) == popular) {
                popularList.put("anotherFavGenre", genre.get(i).getGenreName());
            }
        }
        return popularList;
    }

    // Returns five random artists
    public ArrayList<Artist> getRandomArtists(){
        ArrayList<Artist> artists = new ArrayList<>();

        try{
            conn = DriverManager.getConnection(URL);
            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT Name FROM Artist ORDER BY RANDOM() LIMIT 5;");
            ResultSet set = preparedStatement.executeQuery();
            while(set.next()){
                artists.add( new Artist(
                        set.getString("Name")
                ));
            }
            System.out.println("Got five random artists");

        }catch(Exception exception){
            System.out.println(exception.toString());
        }
        finally {
            try{
                conn.close();
            } catch (Exception exception){
                System.out.println(exception.toString());
            }
        }
        return artists;
    }

    // Returns five random songs
    public ArrayList<Song> getRandomSongs(){
        ArrayList<Song> songs = new ArrayList<>();

        try{
            conn = DriverManager.getConnection(URL);
            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT Name FROM Track ORDER BY RANDOM() LIMIT 5;");
            ResultSet set = preparedStatement.executeQuery();
            while(set.next()){
                songs.add( new Song(
                        set.getString("Name")
                ));
            }
            System.out.println("Got five random songs");

        }catch(Exception exception){
            System.out.println(exception.toString());
        }
        finally {
            try{
                conn.close();
            } catch (Exception exception){
                System.out.println(exception.toString());
            }
        }
        return songs;
    }

    // Returns five random genres
    public ArrayList<Genre> getRandomGenres(){
        ArrayList<Genre> genres = new ArrayList<>();

        try{
            conn = DriverManager.getConnection(URL);
            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT Name FROM Genre ORDER BY RANDOM() LIMIT 5;");
            ResultSet set = preparedStatement.executeQuery();
            while(set.next()){
                genres.add( new Genre(
                        set.getString("Name")
                ));
            }
            System.out.println("Got five random genres");

        }catch(Exception exception){
            System.out.println(exception.toString());
        }
        finally {
            try{
                conn.close();
            } catch (Exception exception){
                System.out.println(exception.toString());
            }
        }
        return genres;
    }

    // Search for specific song by searchword (does not work)
    public String searchForSong(String songs) {
        ArrayList<Song> searchedSongs = new ArrayList<>();
        try{
            conn = DriverManager.getConnection(URL);
            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT * FROM Track WHERE Name LIKE ?;");
            preparedStatement.setString(1, "'%"+songs+"%'");
            ResultSet set = preparedStatement.executeQuery();
            while(set.next()){
                searchedSongs.add( new Song(
                        set.getString("Name")
                ));
            }
            System.out.println("Get all went well!");

        }catch(Exception exception){
            System.out.println(exception.toString());
        }
        finally {
            try{
                conn.close();
            } catch (Exception exception){
                System.out.println(exception.toString());
            }
        }
        return searchedSongs.get(0).getName();
    }
}

