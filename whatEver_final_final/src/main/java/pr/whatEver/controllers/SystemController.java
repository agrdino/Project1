package pr.whatEver.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import static pr.whatEver.Environment.*;

import java.sql.*;
import java.util.Random;

@RestController
public class SystemController {
    /**
     * Check login => user/admin
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @GetMapping("/login")
    public int Login() throws SQLException, ClassNotFoundException {
        CreateConnection();
        storeProcedure = con.prepareCall("{call LoginPermission(?,?,?,?)}");
        storeProcedure.setString(1,"admin");
        storeProcedure.setInt(2, 1);
        storeProcedure.registerOutParameter(3, Types.INTEGER);
        storeProcedure.registerOutParameter(4, Types.INTEGER);
        storeProcedure.execute();
        return storeProcedure.getInt(3) + storeProcedure.getInt(4);
    }
}
