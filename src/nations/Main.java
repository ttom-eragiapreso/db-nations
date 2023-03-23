package nations;

import java.math.BigInteger;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:8889/db_nations";
        String user = "root";
        String password = "root";

        try(Connection conn = DriverManager.getConnection(url,user,password)) {

            String query = """
                    select c.country_id  as country_id,\s
                    c.name as country_name,\s
                    r.name as region_name,
                    c2.name as name_of_continent
                    from countries c
                    join regions r on r.region_id = c.region_id\s
                    join continents c2 on c2.continent_id = r.continent_id\s
                    where c.name like ?
                    order by country_name;
                    """;

            Scanner scan = new Scanner(System.in);
            System.out.println("What country would you like to search?");
            String userSearch = scan.nextLine();

            PreparedStatement ps = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);


            ps.setString(1, "%" + userSearch + "%");

            ResultSet rs = ps.executeQuery();


            if(!rs.next()){
                System.out.println("Sorry, we couldn't find any country containing " + userSearch + " in its name.");
                return;
            }else {
                rs.beforeFirst();
            }

            while (rs.next()){
                int countryId = rs.getInt(1);
                String countryName = rs.getString(2);
                String regionName = rs.getString(3);
                String continentName = rs.getString(4);

                System.out.println("Country ID: " + countryId);
                System.out.println("Country Name: " + countryName);
                System.out.println("Region Name: " + regionName);
                System.out.println("Continent Name: " + continentName);
            }

            System.out.println("Select a country ID to get additional information: ");
            int searchedId = Integer.parseInt(scan.nextLine());

            String detailsQuery = """
                    select c.name as country_name,
                    l.`language` as country_language,
                    cs.population as country_population,
                    cs.gdp as country_gdp
                    from countries c\s
                    join country_languages cl on c.country_id = cl.country_id\s
                    join languages l on l.language_id = cl.language_id\s
                    join country_stats cs on cs.country_id = c.country_id\s
                    where c.country_id = ?
                    and cs.`year` >= 2018
                    ;
                    """;

            PreparedStatement ps2 = conn.prepareStatement(detailsQuery, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            ps2.setInt(1, searchedId);

            ResultSet rs2 = ps2.executeQuery();

            Country c;

            if(!rs2.next()){
                System.out.println("Sorry, we couldn't find any country with ID " + searchedId + ".");
                return;
            }else {
                String countryName = rs2.getString("country_name");
                int countryPopulation = rs2.getInt("country_population");
                long countryGDP = rs2.getLong("country_gdp");

                c = new Country(countryName,countryPopulation, countryGDP);
                rs2.beforeFirst();
            }

            while(rs2.next()){
                String countryLanguage = rs2.getString("country_language");
                c.addLanguage(countryLanguage);
            }
            System.out.println(c);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}