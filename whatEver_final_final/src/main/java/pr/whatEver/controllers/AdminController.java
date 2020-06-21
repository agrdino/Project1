package pr.whatEver.controllers;

import org.springframework.web.bind.annotation.*;
import pr.whatEver.models.Food;
import java.sql.*;

@RestController
public class AdminController{
    /**
     * Test API
     * @return
     */
    @GetMapping("/hello-world")
    public String getHelloMessage(){
        return "Hello World";
    }

    @PostMapping("/add-food")
    public @ResponseBody void AddFood(@RequestBody Food food) throws SQLException, ClassNotFoundException {
        new Food(food.getName(), food.getPrice());
    }
}
