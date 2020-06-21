package pr.whatEver.controllers;

import org.springframework.web.bind.annotation.*;
import pr.whatEver.models.*;

import java.sql.*;

@RestController
public class UserController {
    /**
     * Create suggestion by random foodID
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @GetMapping("/create-suggestion")
    public FoodRepository CreateSuggestion() throws ClassNotFoundException, SQLException {
        return (new Suggestion()).ReturnFood();
    }

    /**
     * Create review by using @postMapping
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @PostMapping(value = "/create-review")
    public @ResponseBody void CreateReview(@RequestBody ReviewRepository rw) throws SQLException, ClassNotFoundException {
        Review review = new Review(rw.getSgID(), rw.getRate(), System.currentTimeMillis(), rw.getRecommend(), rw.getReview());
    }
}
