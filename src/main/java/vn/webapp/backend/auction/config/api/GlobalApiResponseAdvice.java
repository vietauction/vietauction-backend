package vn.webapp.backend.auction.config.api;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import vn.webapp.backend.auction.model.ApiResponse;
import vn.webapp.backend.auction.service.vnpay.ResponseObject;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice(basePackages = "vn.webapp.backend.auction.controller")
public class GlobalApiResponseAdvice implements ResponseBodyAdvice<Object> {

    private static final String CONTROLLER_BASE = "vn.webapp.backend.auction.controller";

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (!MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType)) {
            return false;
        }
        Class<?> containing = returnType.getContainingClass();
        return containing != null && containing.getPackageName().startsWith(CONTROLLER_BASE);
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {

        if (body instanceof ApiResponse) {
            return body;
        }
        if (body instanceof ResponseObject.Payload) {
            return body;
        }
        if (body instanceof org.springframework.core.io.Resource) {
            return body;
        }

        int status = resolveHttpStatus(response);
        if (status == HttpStatus.NO_CONTENT.value()) {
            return body;
        }

        String message = status >= 400 ? "Error" : "Success";

        if (body instanceof Page<?> page) {
            Map<String, Object> meta = pageMeta(page);
            return ApiResponse.success(message, page.getContent(), meta);
        }

        return ApiResponse.success(message, body);
    }

    private static int resolveHttpStatus(ServerHttpResponse response) {
        if (response instanceof ServletServerHttpResponse servletResponse) {
            int code = servletResponse.getServletResponse().getStatus();
            if (code > 0) {
                return code;
            }
        }
        return HttpStatus.OK.value();
    }

    private static Map<String, Object> pageMeta(Page<?> page) {
        Map<String, Object> meta = new LinkedHashMap<>();
        meta.put("page", page.getNumber() + 1);
        meta.put("limit", page.getSize());
        meta.put("total", page.getTotalElements());
        meta.put("totalPages", page.getTotalPages());
        return meta;
    }
}
