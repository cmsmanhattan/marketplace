package com.cbsinc.cms.services.health;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;

import com.cbsinc.cms.QueryManagerSQL;
import com.cbsinc.cms.controllers.ServiceLocator;
import com.cbsinc.cms.faceds.AuthorizationPageFaced;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet Class
 *
 * @web.servlet name="Status" display-name="Check CMS Health status"
 *              description="By clicking on Eureka console then you will b see CMS Health status "
 * @web.servlet-mapping url-pattern="/Status"
 * @web.servlet-init-param name="A parameter" value="A value"
 */
public class StatusEurikaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	ResourceBundle setupResources = null;
	ResourceBundle localization = null;

	float beginClearMemory = 30;

	@Override
		public void init(ServletConfig config) throws ServletException {
	AuthorizationPageFaced authorizationPageFaced = null ;

		try {
			authorizationPageFaced = ServiceLocator.getInstance().getAuthorizationPageFaced();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!authorizationPageFaced.getSetupResources().getString("begin_clear_memory").equals(""))
			beginClearMemory = Float
					.parseFloat(authorizationPageFaced.getSetupResources().getString("begin_clear_memory").trim());
			super.init(config);
		}


	public StatusEurikaServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("<document>");

		CheckMemory( response.getWriter() );

		response.getWriter().write("</document>");

	}

	public boolean CheckMemory( PrintWriter printWriter  ) {

		long max;
		long total;
		long free;
		boolean result = false;

	    Runtime runtimec = Runtime.getRuntime();
		max = runtimec.maxMemory();
		total = runtimec.totalMemory();
		free = runtimec.freeMemory();

		float persent = (float) free / (float) total * 100;
		printWriter.write("<into>=== CMS Manhattan ====</into>");
		printWriter.write("<into>=== Health Check ====</into>");
		printWriter.write("<into>max: " + max + "</into>");
		printWriter.write("<into>total: " + total + "</into>");
		printWriter.write("<into>free: " + free + "</into>");
		printWriter.write("<into>free memory persent: " + persent+ "</into>");
		printWriter.write("<into>free sql connetion in pool : " + QueryManagerSQL.freeConnectionPool.size()+ "</into>");
		printWriter.write("<into>used sql connetion in pool : " + QueryManagerSQL.connectionPool.size()+ "</into>");
		printWriter.write("<into>========</into>");

		if (persent < beginClearMemory) {
			printWriter.write("<into>== clear ===</into>");
			System.gc();
			result = true;
		}

		return result;
	}


}
