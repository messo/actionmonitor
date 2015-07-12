package com.betvictor.exercises.actionmonitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The controller for the main page which listens for the actions triggered by the database changes
 */
@Controller
@RequestMapping("/")
public class ListenerController {

    @RequestMapping(method = RequestMethod.GET)
    public String listen() {
        return "listen";
    }
}
