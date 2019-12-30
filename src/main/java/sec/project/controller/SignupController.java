package sec.project.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Account;
import sec.project.domain.Signup;
import sec.project.repository.AccountRepository;
import sec.project.repository.SignupRepository;

@Controller

public class SignupController {

    Logger logger = LoggerFactory.getLogger(SignupController.class);

    @Autowired
    private SignupRepository signupRepository;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("*")
    public String defaultMapping() {
        logger.info("REDIRECTED TO FORM");
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        logger.info("LOADED FORM");
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String address) {
        signupRepository.save(new Signup(name, address));
        logger.info("Created signup for " + name);
        return "done";
    }

    @RequestMapping(value = "/signups", method = RequestMethod.GET)
    public String listSignups(Model model) {
        List<Signup> signups = signupRepository.findAll();
        model.addAttribute("signups", signups);
        return "all";
    }

    @RequestMapping(value = "/signups/{id}", method = RequestMethod.GET)
    public String listSingleSignup(@PathVariable long id, Model model) {
        Signup s = signupRepository.findOne(id);
        if (s != null) {
            logger.info("FOUND SIGNUP WITH ID " + id);
            model.addAttribute("signup", s);
            return "signup";
        } else {
            logger.info("COULD NOT FIND SIGNUP WITH ID " + id);
            return "404";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String createUser(String username, String password) {
        if (username != null && password != null) {
            Account a = new Account();
            a.setUsername(username);
            a.setPassword(password);
            accountRepository.save(a);
            logger.info("NEW USER SAVED");
            return "redirect:/signups";
        }
        return "registeringerror";

    }

}
