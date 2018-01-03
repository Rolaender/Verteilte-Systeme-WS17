package com.example.demo;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.ToDoRepository;
import com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.internal.filter.ValueNode.JsonNode;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import antlr.collections.List;

import org.json.*;

@RefreshScope
@EnableCircuitBreaker
@Controller
@RequestMapping("posts")
class ToDoController {
    
	//private static final Consumer<? super ToDo> ToDo = null;
	@Autowired
	private  ToDoRepository tdrep;
	
	@Value("${message:list}")
    private String message;
	
	@HystrixCommand(fallbackMethod = "fallbackListPosts")
    @RequestMapping(value="", method=RequestMethod.GET)
    public String listPosts(Model model) {
        model.addAttribute("posts", tdrep.findAll());
        System.out.println(this.message);
        return "posts/" + this.message;
        //return "posts/list";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable long id) {
    		tdrep.delete(id);
        return new ModelAndView("redirect:/posts");
    }

    @RequestMapping(value="/new", method = RequestMethod.GET)
    public String newProject() {
        return "posts/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(@RequestParam("message") String comment) {
    	tdrep.save(new ToDo(comment));
        return new ModelAndView("redirect:/posts");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@RequestParam("post_id") long id,
                               @RequestParam("todo") String message) {
        ToDo post = tdrep.findOne(id);
        post.setTodo(message);
        tdrep.save(post);
        return new ModelAndView("redirect:/posts");
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable long id,
                       Model model) {
        ToDo post = tdrep.findOne(id);
        model.addAttribute("post", post);
        return "posts/edit";
    }
    
    private String fallbackListPosts(Model model) {
    		System.out.println("erer");
        return "posts/fallback";
    }
}
