package de.dataenv.game.frontend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("")
public class FrontendController {


    @GetMapping("match/**")
    public ModelAndView forwardRequestsToSinglePageApp() {
        return new ModelAndView("forward:/");
    }
}