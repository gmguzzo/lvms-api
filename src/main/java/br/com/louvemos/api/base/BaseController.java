package br.com.louvemos.api.base;

import br.com.louvemos.api.exception.LvmsCodesEnum;
import br.com.louvemos.api.exception.LvmsException;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

public class BaseController {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object genericErrorInterceptor(Exception exception, HttpServletResponse response) throws IOException {
        exception.printStackTrace();

        response.setContentType("application/json");

        if (exception instanceof LvmsException) {
            LvmsException ce = (LvmsException) exception;
            response.setStatus(ce.getHttpStatus().value());
            return ce.getResponseBody();
        } else {
            LvmsException ce = new LvmsException(LvmsCodesEnum.GENERIC_INTERNAL_SERVER_ERROR);
            response.setStatus(ce.getHttpStatus().value());
            return ce.getResponseBody();
        }
    }

}
