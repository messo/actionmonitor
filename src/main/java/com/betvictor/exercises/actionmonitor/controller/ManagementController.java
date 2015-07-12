package com.betvictor.exercises.actionmonitor.controller;

import com.betvictor.exercises.actionmonitor.model.Magic;
import com.betvictor.exercises.actionmonitor.service.MagicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The controller for the management interface.
 */
@Controller
@RequestMapping("/management")
public class ManagementController {

    @Autowired
    MagicService service;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        Iterable<Magic> magics = service.findAll();
        model.addAttribute("magics", magics);

        return "management";
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String remove(@PathVariable("id") Long id) {
        service.deleteById(id);

        return "redirect:/management";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {
        Magic magic = service.findById(id);
        model.addAttribute("magic", magic);

        return "edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@RequestParam("id") Long id, @RequestParam("description") String description) {
        service.update(id, description);

        return "redirect:/management";
    }

    @RequestMapping(value = "/createNew", method = RequestMethod.POST)
    public String createNew(@RequestParam("description") String description) {
        service.createNew(description);

        return "redirect:/management";
    }
}
