//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.parsers.SAXParser;
//import javax.xml.parsers.SAXParserFactory;
//
//import org.xml.sax.Attributes;
//import org.xml.sax.SAXException;
//
//import org.xml.sax.helpers.DefaultHandler;
//
//public class SAXParserExample extends DefaultHandler {
//
//    List<Employee> myEmpls;
//
//    private String tempVal;
//
//    //to maintain context
//    private Employee tempEmp;
//
//    public SAXParserExample() {
//        myEmpls = new ArrayList<Employee>();
//    }
//
//    public void runExample() {
//        parseDocument();
//        printData();
//    }
//
//    private void parseDocument() {
//
//        //get a factory
//        SAXParserFactory spf = SAXParserFactory.newInstance();
//        try {
//
//            //get a new instance of parser
//            SAXParser sp = spf.newSAXParser();
//
//            //parse the file and also register this class for call backs
//            sp.parse("employees.xml", this);
//
//        } catch (SAXException se) {
//            se.printStackTrace();
//        } catch (ParserConfigurationException pce) {
//            pce.printStackTrace();
//        } catch (IOException ie) {
//            ie.printStackTrace();
//        }
//    }
//
//    /**
//     * Iterate through the list and print
//     * the contents
//     */
//    private void printData() {
//
//        System.out.println("No of Employees '" + myEmpls.size() + "'.");
//
//        Iterator<Employee> it = myEmpls.iterator();
//        while (it.hasNext()) {
//            System.out.println(it.next().toString());
//        }
//    }
//
//    //Event Handlers
//    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//        //reset
//        tempVal = "";
//        if (qName.equalsIgnoreCase("Employee")) {
//            //create a new instance of employee
//            tempEmp = new Employee();
//            tempEmp.setType(attributes.getValue("type"));
//        }
//    }
//
//    public void characters(char[] ch, int start, int length) throws SAXException {
//        tempVal = new String(ch, start, length);
//    }
//
//    public void endElement(String uri, String localName, String qName) throws SAXException {
//
//        if (qName.equalsIgnoreCase("Employee")) {
//            //add it to the list
//            myEmpls.add(tempEmp);
//
//        } else if (qName.equalsIgnoreCase("Name")) {
//            tempEmp.setName(tempVal);
//        } else if (qName.equalsIgnoreCase("Id")) {
//            tempEmp.setId(Integer.parseInt(tempVal));
//        } else if (qName.equalsIgnoreCase("Age")) {
//            tempEmp.setAge(Integer.parseInt(tempVal));
//        }
//
//    }
//
////    public static void main(String[] args) {
////        SAXParserExample spe = new SAXParserExample();
////        spe.runExample();
////    }
//
//}

import java.io.IOException;
import java.sql.*;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SAXParseMovie extends DefaultHandler {
    static String DBNAME = "moviedb";
    static List<Movie> myMovies;
    public static int counter = 1;
    private String tempVal;
    private String tempDir;
    //to maintain context
    private Movie tempAct;

    public SAXParseMovie() {
        myMovies = new ArrayList<Movie>();
    }

    public void runExample() throws Exception {
        parseDocument();
        printData();
        handleJDB();
    }

    private void parseDocument() {

        //get a factory
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try {

            //get a new instance of parser
            SAXParser sp = spf.newSAXParser();

            //parse the file and also register this class for call backs
            sp.parse("mains243.xml", this);

        } catch (SAXException se) {
            se.printStackTrace();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    /**
     * Iterate through the list and print
     * the contents
     */
    private void printData() {

        System.out.println("No of Movies '" + myMovies.size() + "'.");

        Iterator<Movie> it = myMovies.iterator();
        while (it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }

    //Event Handlers
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //reset
        tempVal = "";

//        if (qName.equalsIgnoreCase("director")) {
//            //create a new instance of employee
//            tempAct = new Movie();
//
//            //tempEmp.setType(attributes.getValue("type"));
//        }
        if (qName.equalsIgnoreCase("film")) {
            //create a new instance of employee
            tempAct = new Movie();

            //tempEmp.setType(attributes.getValue("type"));
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        tempVal = new String(ch, start, length);
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equalsIgnoreCase("dirn")) {
            //add it to the list
            tempAct.setDirector(tempVal);


        }
        else if (qName.equalsIgnoreCase("t")) {
            //add it to the list
            tempAct.setTitle(tempVal);


        }
        else if (qName.equalsIgnoreCase("cat")) {
            //add it to the list
            tempAct.setGenre(tempVal);


        }
        else if (qName.equalsIgnoreCase("fid")) {
            //add it to the list
            tempAct.setId(tempVal);


        }
        else if (qName.equalsIgnoreCase("year")) {
            //add it to the list
            tempVal = tempVal.replaceAll("\\D+","");
            if (!tempVal.equalsIgnoreCase(""))
            tempAct.setYear(Integer.parseInt(tempVal));


        }
        else if (qName.equalsIgnoreCase("film")) {
            //add it to the list
            myMovies.add(tempAct);


        }

    }

    public static void handleJDB() throws InstantiationException, IllegalAccessException, ClassNotFoundException, Exception{
        String dbtype = "mysql";
        String dbname = DBNAME;
        String username = "mytestuser";
        String password = "mypassword";
        Hashtable<String, String> oldMovie = new Hashtable<String, String>();
        Hashtable<String, Integer> oldGenre = new Hashtable<String, Integer>();
        Hashtable<String, String> dupMovie = new Hashtable<String, String>();
        Hashtable<String, String> notDupMovie = new Hashtable<String, String>();
        //List <String> oldGenre = new ArrayList<String>();


        // Incorporate mySQL driver
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            // Connect to the test database
            Connection connection = DriverManager.getConnection("jdbc:" + dbtype + ":///"
                            + dbname + "?autoReconnect=true&useSSL=false",
                    username, password);

            if (connection != null) {
                System.out.println("Connection established!!");
                System.out.println();
            }

            // Create an execute an SQL statement to select all of table"Stars" records
            String query = "select distinct (movies.title) , movies.id from movies ;";
            String query2 = "select distinct (genres.id), genres.name from genres ;";
            PreparedStatement select = connection.prepareStatement(query);
            ResultSet result = select.executeQuery(query);

            // print table's contents, field by field
            while (result.next()) {
                String title = result.getString("movies.title");
                String id = result.getString("movies.id");
                oldMovie.put(title, id);

            }

            select = connection.prepareStatement(query2);
            result = select.executeQuery(query2);
            while (result.next()) {
                String genName = result.getString("genres.name");
                int genID = result.getInt("genres.id");
                oldGenre.put(genName, genID);

                counter++;
            }
            result.close();
            select.close();


        /*    System.out.println(oldMovie);
            System.out.println(oldGenre);*/

            int[] iNoRows=null;
            int[] iNoRows2=null;
            int[] iNoRows3=null;
            int[] iNoRows4=null;
            connection.setAutoCommit(false);

            /*
             * INSERT INTO movies VALUES('tt0421974','Sky Fighters',2005,'Gérard Pirès');
             * INSERT INTO ratings VALUES('tt0421974',5.8,3920);  @ rating 9.9 0
             *
             * INSERT INTO genres VALUES(1,'Action');
             * INSERT INTO genres_in_movies VALUES(1,'tt0421974');
             */
            String sqlInsertToMovie = " INSERT INTO movies VALUES(?,?,?,?);";
            String sqlInsertToRating = " INSERT INTO ratings VALUES(?,9.9,0);";
            String sqlInsertToGenre = " INSERT INTO genres VALUES(?,?);";
            String sqlInsertToGenre_in_movie = " INSERT INTO genres_in_movies VALUES(?,?);";


            PreparedStatement psInsertRecord= null;
            PreparedStatement psInsertRating=null;
            PreparedStatement psGenre=null;
            PreparedStatement psGemreInMovie=null;


            psInsertRecord = connection.prepareStatement(sqlInsertToMovie);
            psInsertRating = connection.prepareStatement(sqlInsertToRating);
            psGenre  = connection.prepareStatement(sqlInsertToGenre);
            psGemreInMovie = connection.prepareStatement(sqlInsertToGenre_in_movie);


            // ------------------- Insertion
            long startTime = System.nanoTime();
            if(oldMovie.isEmpty())
            {
                oldMovie.put("unknow","unknown");
            }
            int limitPerBatch = 50;
            int iter = 0 ;
            System.out.println("******** size of myMovies:" + myMovies.size());
            //System.out.println(myMovies);
            for (int i = 0; i < myMovies.size() ; i++) {
                Movie mv = myMovies.get(i);
                String newMovie = mv.getTitle();
                System.out.println(i + " " + mv.getTitle() + " " + mv.getId() + " " + mv.getYear() + " " + mv.getDirector());
                if (mv.getTitle() == null || mv.getDirector() == null || mv.getYear() == 0 || mv.getId() == null) {
                    continue;
                }
                // if it is not a duplicate


                String movieID = mv.getId();
                if(!oldMovie.containsKey(newMovie) && !oldMovie.containsValue(mv.getId()))
                {
                    if (!notDupMovie.containsKey(mv.getId()) && !notDupMovie.contains(mv.getTitle()) && !movieID.equals("NA"))
                    {

                        notDupMovie.put(mv.getId(),mv.getTitle());
                    }
                    else
                    {

                        dupMovie.put(mv.getId(), mv.getTitle());
                        continue;
                    }

                    psInsertRecord.setString(1,myMovies.get(i).getId());
                    psInsertRecord.setString(2,myMovies.get(i).getTitle());
                    psInsertRecord.setInt(3,myMovies.get(i).getYear());
                    psInsertRecord.setString(4,myMovies.get(i).getDirector());
                    psInsertRecord.addBatch();
                    // insert rating
                    psInsertRating.setString(1,myMovies.get(i).getId());
                    psInsertRating.addBatch();
                    // validate the genre than add it to the branch

                    String newMvGen = mv.getGenre();
                    if(newMvGen == null)
                        newMvGen = "NA";
                    if (newMvGen.startsWith("Comd"))
                    {
                        newMvGen = "Comedy";
                    }
                    else if (newMvGen.startsWith("H**") )
                        newMvGen = "NA";
                    else if (newMvGen.startsWith("Act") )
                        newMvGen = "Action";
                    else if (newMvGen.startsWith("Ad") )
                        newMvGen = "Adult";
                    else if (newMvGen.startsWith("Porn") )
                        newMvGen = "Adult";
                    else if (newMvGen.startsWith("Adv") )
                        newMvGen = "Adventure";
                    else if (newMvGen.startsWith("Bio") )
                        newMvGen = "Biography";
                    else if (newMvGen.startsWith("Dra") )
                        newMvGen = "Drama";
                    else
                    {
                        boolean genreValidation = false;
                        Set<String> keys = oldGenre.keySet();
                        for(String key: keys){
                            boolean validateP = key.matches("^"+ newMvGen + ".*");
                            if( validateP) {
                                newMvGen = key;
                                genreValidation = true;
                                break;
                            }
                        }   // it matches
                        if (!genreValidation)
                        {
                            // insert it
                            psGenre.setInt(1,counter);
                            psGenre.setString(2,newMvGen);
                            psGenre.addBatch();
                            oldGenre.put(newMvGen, counter++);
                        }
                    }
                    //Insert into genre_in_movie
                    int getGenID = 0;
                    getGenID = oldGenre.get(newMvGen);
                    psGemreInMovie.setInt(1, getGenID);
                    String MovieID =  myMovies.get(i).getId();
                    psGemreInMovie.setString(2, MovieID);
                    psGemreInMovie.addBatch();
                }// End of IF

                if ( limitPerBatch == iter)
                {
                    iNoRows=psInsertRecord.executeBatch();
                    connection.commit();
                    iNoRows2 = psInsertRating.executeBatch();
                    connection.commit();
                    if(psGenre != null){
                        iNoRows3 = psGenre.executeBatch();connection.commit();
                    }
                    psGemreInMovie.executeBatch();
                    connection.commit();

                    iter = 0;
                }  iter++;
            }


            long stopTime = System.nanoTime();

            myMovies.clear();
            System.out.println("size of Inconsistent Data from Movies: " + dupMovie.size());
            System.out.println("size of Data from XML to DB: " + notDupMovie.size());
            System.out.println("size of original DB before insertion: " + oldMovie.size());
            System.out.println("The whole process took: " + (stopTime - startTime) + " nano seconds");
            System.out.println("Inconsistent Data from Movies: " + dupMovie);
            String sqlIndex = " CREATE INDEX rate_index ON ratings (rating);";
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sqlIndex);
            connection.commit();
            stmt.close();
            String sqlIndex1 = " CREATE INDEX id_index ON movies (id);";
            stmt = connection.createStatement();

            stmt.executeUpdate(sqlIndex1);
            connection.commit();
            try {
                if(psInsertRecord !=null)
                    psInsertRecord.close();
                if(psInsertRating !=null)
                    psInsertRating.close();
                if(psGenre !=null)
                    psGenre.close();
                if(psGemreInMovie !=null)
                    psGemreInMovie.close();
                if(stmt !=null)
                    stmt.close();

                if(connection!=null) connection.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getSQLState());

        } catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("Error MSG: " + e.getMessage() );
        }

    }


    public static void main(String[] args) throws Exception {
        SAXParseMovie spe = new SAXParseMovie();
        spe.runExample();
    }

}
