package com.hr.app.controllers;

import com.hr.app.models.database.CvsModel;
import com.hr.app.repositories.ICvsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class CvsController {

    @Autowired
    private ICvsRepository cvsRepository;

    @GetMapping("/cvs/byid/{id}")
    public CvsModel getCvId(@PathVariable long id) {
        return  cvsRepository.findById(id);
    }

    //TODO ogarnąć to po ogarnięciu Foreign keyów
//    @GetMapping("/cvs/byid/{id}")
//    public CvsModel getUserCvs(@PathVariable long id) {
//        return  cvsRepository.findByUser(id);
//    }

    @GetMapping("/cvs/setcurrent/{userid}/{cvid}")
    public List<CvsModel> setCurrentCv(@PathVariable long userid, @PathVariable long cvid){
        return cvsRepository.findAllByFKcvUserId(userid);
    }

}
