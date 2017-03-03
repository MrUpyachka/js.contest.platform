package qq.upyachka.js.challenge.auth.api.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import qq.upyachka.js.challenge.auth.service.AuthenticationService;
import qq.upyachka.js.challenge.auth.service.RegistrationService;
import qq.upyachka.js.challenge.auth.validation.UserRegistrationValidator;
import qq.upyachka.js.challenge.core.model.User;

import static qq.upyachka.js.challenge.auth.constants.AuthUrlConst.REGISTRATION_URL;
import static qq.upyachka.js.challenge.core.constants.UrlConst.REDIRECT_URL_PREFIX;
import static qq.upyachka.js.challenge.core.model.constants.ParamConst.USER_KEY;
import static qq.upyachka.js.challenge.core.model.constants.ViewConst.REGISTRATION_VIEW;

/**
 *
 * Created on 24.02.17.
 * @author upyachka.
 */
@RestController
@RequestMapping(REGISTRATION_URL)
public class RegistrationRestController {

    /** Internal logger. */
    private static final Logger LOG = LogManager.getLogger(RegistrationRestController.class);

    /** Service to access user authentication. */
    @Autowired
    private AuthenticationService authentication;

    /** Service to access user registration. */
    @Autowired
    private RegistrationService service;

    /** Validator of user-data. */
    @Autowired
    private UserRegistrationValidator validator;

    /**
     * End-point to start user registration.
     * @param model actual model.
     * @return registration view.
     */
    @GetMapping
    public ModelAndView registration(Model model) {
        model.addAttribute(USER_KEY, new User());
        LOG.debug("Show registration view");
        return new ModelAndView(REGISTRATION_VIEW, model.asMap());
    }

    /**
     * End-point to start user registration.
     * @param user user data from client.
     * @param bindingResult result of validation.
     * @return registration view or home view.
     */
    @PostMapping
    public ModelAndView tryRegister(@ModelAttribute(USER_KEY) User user, BindingResult bindingResult) {
        final String username = user.getUsername();
        LOG.debug("Try register user with name {}.", username);
        validator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            LOG.debug("Validation failed, stay on view.");
            return new ModelAndView(REGISTRATION_VIEW);
        }
        final String password = user.getPassword();
        service.register(user);
        authentication.login(username, password);
        LOG.debug("User {} registered and automatically logged in.", username);
        // FIXME clear redirect required
        return new ModelAndView(REDIRECT_URL_PREFIX);
    }
}