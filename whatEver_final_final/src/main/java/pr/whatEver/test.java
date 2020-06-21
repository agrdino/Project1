package pr.whatEver;

import pr.whatEver.models.Suggestion;

import java.sql.SQLException;

public class test {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        System.out.println((new Suggestion()).ReturnFood().getName());
    }
}
