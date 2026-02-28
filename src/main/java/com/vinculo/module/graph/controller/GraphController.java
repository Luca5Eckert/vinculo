package com.vinculo.module.graph.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/graphs")
public class GraphController {

    @GetMapping("/feed")
    public String getFeed(){
        return "feed";
    }

    @GetMapping("/{personId}")
    public String getGraphForPerson(String personId){
        return "graph for person " + personId;
    }


}
