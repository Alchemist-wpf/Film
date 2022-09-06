package com.movie2.controller;

import com.movie2.config.MyConstants;
import com.movie2.model.entity.FilmEvaluate;
import com.movie2.model.entity.User;
import com.movie2.model.vo.FilmEvaluateVo;
import com.movie2.service.FilmEvaluateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fe")
@CrossOrigin
@Slf4j
public class FilmEvaluateController {

	@Autowired
	FilmEvaluateService filmEvaluateService;

	@PostMapping("")
	public Map<String,Object> save(@RequestBody FilmEvaluate fe){
		log.info("FilmEvaluateController--------->save(@RequestBody FilmEvaluate fe)");

		Map<String,Object> map = new HashMap<>();
		int count = filmEvaluateService.insert(fe);

		if(count==1){
			map.put("success","ok");
		}else{
			map.put("success","error");
		}

		return map;
	}

	@GetMapping("")
	public List<FilmEvaluateVo> getAllFe(@RequestParam("id") int id){
		log.info("FilmEvaluateController--------->getAllFe()");
		List<FilmEvaluateVo> voList = new ArrayList<>();
		List<FilmEvaluate> filmEvaluates = filmEvaluateService.selectAllFilmEvaluatesAndUser(id);
		for(FilmEvaluate filmEvaluate : filmEvaluates){
			FilmEvaluateVo vo = new FilmEvaluateVo();
			User user = filmEvaluate.getUser();
			user.setAvatar(MyConstants.MY_URL+user.getAvatar());
			vo.setUser(user);
			vo.setFilmEvaluate(filmEvaluate);
			voList.add(vo);
		}
		return voList;
	}

}
