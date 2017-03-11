package com.mju.web.controller;

import com.mju.model.dto.ExceptionMsg;
import com.mju.model.dto.PageBean;
import com.mju.model.dto.RepMessage;
import com.mju.model.entity.Movie;
import com.mju.service.MovieService;
import com.mju.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hua on 2017/2/20.
 */
@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @PostMapping("/query")
    public RepMessage queryMovie(@RequestBody(required = false) Map<String, Object> fields) {
        RepMessage rep = new RepMessage();
        Map<String, Object> content = new HashMap<>();
        try {
            PageBean<Movie> list = movieService.queryMovie(fields);

            if (list != null && list.getPageData().size() > 0) {
                content.put("movieInfo", list);
                rep.setContent(content);
                rep.setStatus(ExceptionMsg.SUCCESS);
            }else {
                rep.setStatus(ExceptionMsg.FAILED);
            }


        } catch (Exception e) {
            rep.setStatus(ExceptionMsg.EXCEPTION);
        }
        return rep;

    }

    @GetMapping("/{movieId}")
    public RepMessage getMovieById(@PathVariable Integer movieId) {
        RepMessage rep = new RepMessage();
        Map<String, Object> content = new HashMap<>();
        try {
            Movie movie = movieService.getMovieById(movieId);
            if (movie != null) {
                content.put("movieInfo", movie);
                rep.setContent(content);
                rep.setStatus(ExceptionMsg.SUCCESS);
            }else {
                rep.setStatus(ExceptionMsg.FAILED);
            }

        } catch (Exception e) {
            rep.setStatus(ExceptionMsg.EXCEPTION);
        }
        return rep;
    }

    @PutMapping("/{movieId}")
    public RepMessage putMovie(@Valid @RequestBody Movie movie, BindingResult result,@PathVariable Integer movieId) {
        RepMessage rep = new RepMessage();
        try {
            if (result.hasErrors()) {
                StringBuffer sb = new StringBuffer();
                for (ObjectError err : result.getAllErrors()) {
                    sb.append(err.getDefaultMessage() +";");
                }
                rep.setRepCode(Const.FAILED);
                rep.setRepMsg(sb.toString());
            }else {
                movie.setMovieId(movieId);
                if (movieService.modifyMovie(movie)) {
                    rep.setStatus(ExceptionMsg.SUCCESS);
                }else {
                    rep.setStatus(ExceptionMsg.FAILED);
                }
            }
        } catch (Exception e) {
            rep.setStatus(ExceptionMsg.EXCEPTION);
        }
        return rep;

    }

    @PostMapping
    public RepMessage postMovie(@Valid @RequestBody Movie movie, BindingResult result) {
        RepMessage rep = new RepMessage();
        try {
            if (result.hasErrors()) {
                StringBuffer sb = new StringBuffer();
                for (ObjectError err : result.getAllErrors()) {
                    sb.append(err.getDefaultMessage() +";");
                }
                rep.setRepCode(Const.FAILED);
                rep.setRepMsg(sb.toString());
            }else {
                if (movieService.saveMovie(movie)) {
                    rep.setStatus(ExceptionMsg.SUCCESS);
                }else {
                    rep.setStatus(ExceptionMsg.FAILED);
                }
            }
        } catch (Exception e) {
            rep.setStatus(ExceptionMsg.EXCEPTION);
        }
        return rep;
    }

    @DeleteMapping("/{movieId}")
    public RepMessage deleteMovie(@PathVariable Integer movieId) {
        RepMessage rep = new RepMessage();
        try {
            if (movieService.deleteMovieById(movieId)) {
                rep.setStatus(ExceptionMsg.SUCCESS);
            }else {
                rep.setStatus(ExceptionMsg.FAILED);
            }
        } catch (Exception e) {
            rep.setStatus(ExceptionMsg.EXCEPTION);
        }
        return rep;
    }

}