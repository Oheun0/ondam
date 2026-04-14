package com.ondam.common.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/** GET /guide → 이용 가이드 */
public class GuideController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "home/guide";
	}
}
