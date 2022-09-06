package com.movie2.model.vo;

import com.movie2.model.entity.FilmEvaluate;
import com.movie2.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmEvaluateVo {
	private User user;
	private FilmEvaluate filmEvaluate;
}
