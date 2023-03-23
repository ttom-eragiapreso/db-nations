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

            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, "%" + userSearch + "%");

            ResultSet rs = ps.executeQuery();

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

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}