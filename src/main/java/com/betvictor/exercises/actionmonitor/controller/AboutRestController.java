package com.betvictor.exercises.actionmonitor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A simple REST controller exposing two methods:
 * <ul>
 * <li>{@link #ping()}: returns {@literal OK}</li>
 * <li>{@link #version()}: returns the version number of the application using {@link Package#getImplementationVersion()} --
 * this is only available if the application has been built by Maven</li>
 * </ul>
 */
@RestController
@RequestMapping("/rest")
public class AboutRestController {

    private static final String PONG = "OK";


    @RequestMapping("/ping")
    public String ping() {
        return PONG;
    }

    @RequestMapping("/version")
    public String version() throws Exception {
        return getClass().getPackage().getImplementationVersion();
    }
}
