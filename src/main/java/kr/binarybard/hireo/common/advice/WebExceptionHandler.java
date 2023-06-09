package kr.binarybard.hireo.common.advice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.binarybard.hireo.common.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice(annotations = Controller.class, basePackages = "kr.binarybard.hireo.web")
public class WebExceptionHandler {

	private ModelAndView createModelAndView(HttpServletRequest req, HttpServletResponse resp, HttpStatus status, Exception e, String viewName) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("url", req.getRequestURL());
		mav.addObject("exception", e);
		mav.setViewName(viewName);
		if (e instanceof BusinessException businessException) {
			mav.addObject("errorCode", businessException.getErrorCode());
		}
		resp.setStatus(status.value());
		return mav;
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ModelAndView handleMethodArgumentTypeMismatchException(HttpServletRequest req, HttpServletResponse resp, MethodArgumentTypeMismatchException e) {
		return createModelAndView(req, resp, HttpStatus.BAD_REQUEST, e, "error/400");
	}

	@ExceptionHandler(BusinessException.class)
	public ModelAndView handleBusinessException(HttpServletRequest req, HttpServletResponse resp, BusinessException e) {
		HttpStatus status = e.getErrorCode().getStatus();
		return createModelAndView(req, resp, status, e, "error/" + e.getErrorCode().getStatus().value());
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, HttpServletResponse resp, Exception e) {
		log.error("An unexpected error occurred: ", e);
		return createModelAndView(req, resp, HttpStatus.INTERNAL_SERVER_ERROR, e, "error/default");
	}
}
