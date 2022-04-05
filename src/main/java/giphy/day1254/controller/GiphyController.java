package giphy.day1254.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import giphy.day1254.service.GiphyService;

@Controller
public class GiphyController {

    @Autowired
    private GiphyService giphySvc;
    
    @GetMapping("/search")
    public String searchGiphy(
            @RequestParam(name="phrase") String phrase, 
            @RequestParam(name="limit") Integer limit,
            @RequestParam(name="rating") String rating, 
            Model model) {
        // System.out.println(">>> phrase: " + phrase);
        // System.out.println(">>> limit: " + limit);
        // System.out.println(">>> rating: " + rating);
        
        List<String> results = giphySvc.getGiphs(phrase, rating, limit);

        model.addAttribute("results", results);
        model.addAttribute("phrase", phrase);

        return "results";
    }
}
