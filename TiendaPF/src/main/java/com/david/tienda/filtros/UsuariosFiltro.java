package com.david.tienda.filtros;

import java.io.IOException;

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

@WebFilter("/resources/paginas/usuarios/usuarios.xhtml")
public class UsuariosFiltro implements Filter {
	public static final String WELCOME_PAGE = "/resources/paginas/home.xhtml";
	public static final String INDEX = "/index.xhtml";

	public void init(FilterConfig fConfig) throws ServletException {

	}

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {
			HttpServletRequest reqt = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			Integer nivel = SessionUtils.getNivel((HttpServletRequest) request);
			Boolean bandera = SessionUtils.getBandera((HttpServletRequest) request);

			if (bandera && nivel != 1)
				chain.doFilter(request, response);
			else
				resp.sendRedirect(reqt.getContextPath() + WELCOME_PAGE);

		} catch (Exception e) {
			System.out.println("Error en filtro Usuarios: " + e.getMessage());
		}

	}

}
