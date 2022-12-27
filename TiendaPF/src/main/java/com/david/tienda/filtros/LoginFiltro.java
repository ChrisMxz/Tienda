package com.david.tienda.filtros;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.david.tienda.util.SessionUtils;

@WebFilter({ "/resources/paginas/productos/*", "/resources/paginas/usuarios/*" })
public class LoginFiltro implements Filter {
	public static final String LOGIN_PAGE = "/resources/paginas/login.xhtml";
	public static final String WELCOME_PAGE = "/home.xhtml";
	public static final String INDEX = "/index.xhtml";

	public void init(FilterConfig fConfig) throws ServletException {

	}

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// System.out.println("\n--LoginFilter.doFilter()--");

		try {
			HttpServletRequest reqt = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			Optional<String> username = SessionUtils.getUsername((HttpServletRequest) request);

			if (username.isPresent()) {
				// System.out.println(" !Sesion: "+username.get());

				if (reqt.getRequestURI().endsWith(LOGIN_PAGE)) {
					resp.sendRedirect(reqt.getContextPath() + INDEX);
				} else {
					chain.doFilter(request, response);
				}

			} else {
				//System.out.println("  !Sin iniciar sesion");
				if (!reqt.getRequestURI().endsWith(LOGIN_PAGE)) {
					resp.sendRedirect(reqt.getContextPath() + INDEX);
				} else {
					chain.doFilter(request, response);
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
