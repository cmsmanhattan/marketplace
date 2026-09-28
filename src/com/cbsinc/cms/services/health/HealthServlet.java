package com.cbsinc.cms.services.health;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;

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
public class HealthServlet extends HttpServlet {

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


	public HealthServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		CheckMemory( response.getWriter() );

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


		if (persent < beginClearMemory) {
			printWriter.write("{\"status\":\"DOWN\"}");
			System.gc();
			result = true;
		}
		else
		{
			printWriter.write("{\"status\":\"UP\"}");
		}

		return result;
	}

}
