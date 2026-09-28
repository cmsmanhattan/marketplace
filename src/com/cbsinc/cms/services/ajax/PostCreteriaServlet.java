package com.cbsinc.cms.services.ajax;

/**
 * <p>
 * Title: Content Manager System
 * </p>
 * <p>
 * Description: System building web application develop by Konstantin Grabko.
 * Konstantin Grabko is Owner and author this code.
 * Программный код написан Грабко Константином Владимировичем и является его интеллектуальной
 * собственностью.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Grabko Business (Предприниматель Грабко Константин Владимирович)
 * </p>
 *
 * @author Konstantin Grabko
 * @version 1.0
 */

import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.cbsinc.cms.PublisherBean;
import com.cbsinc.cms.controllers.ServiceLocator;
import com.cbsinc.cms.controllers.SiteType;
import com.cbsinc.cms.faceds.ProductPostAllFaced;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet Class
 *
 * @web.servlet name="postcreteria" display-name="Name for postcreteria"
 *              description="Description for postcreteria"
 * @web.servlet-mapping url-pattern="/postcreteria"
 * @web.servlet-init-param name="A parameter" value="A value"
 */
public class PostCreteriaServlet extends HttpServlet {

	transient ResourceBundle setupResources = null;

	public PostCreteriaServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		if (setupResources == null)
			setupResources = PropertyResourceBundle.getBundle("appconfig", response.getLocale());
		// HttpSession session = request.getSession(false);
		HttpSession session = request.getSession();

		ProductPostAllFaced productPostAllFaced = null;
		try {
			productPostAllFaced = ServiceLocator.getInstance().getProductPostAllFaced();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// if( session.getAttribute("publisherBeanId") == null ) return ;
		PublisherBean publisherBeanId = (PublisherBean) session.getAttribute("publisherBeanId");
		// if(publisherBeanId == null) publisherBeanId = new SoftPostBean();

		if (request.getParameter("softname") != null)
			publisherBeanId.setStrSoftName(request.getParameter("softname"));
		// if( request.getParameter("catalog_id") !=null ) publisherBeanId.set(
		// request.getParameter("catalog_id"));
		if (request.getParameter("softname2") != null)
			publisherBeanId.setStrSoftName2(request.getParameter("softname2"));
		if (request.getParameter("imagename") != null)
			publisherBeanId.setImgname(request.getParameter("imagename"));
		if (request.getParameter("softcost") != null)
			publisherBeanId.setStrSoftCost(request.getParameter("softcost"));
		if (request.getParameter("description") != null)
			publisherBeanId.setStrSoftDescription(request.getParameter("description"));
		if (request.getParameter("bigimagename") != null)
			publisherBeanId.setBigimgname(request.getParameter("bigimagename"));
		if (request.getParameter("fulldescription") != null)
			publisherBeanId.setProductFulldescription(request.getParameter("fulldescription"));
		if (request.getParameter("filename") != null)
			publisherBeanId.setFilename(request.getParameter("filename"));
		if (request.getParameter("show_rating1") != null) {
			publisherBeanId.setStrShowRatimg1Checked("CHECKED");
			publisherBeanId.setStrShowRatimg1("true");
		} else {
			publisherBeanId.setStrShowRatimg1Checked("");
			publisherBeanId.setStrShowRatimg1("false");
		}
		if (request.getParameter("show_blog") != null) {
			publisherBeanId.setShowForumChecked("CHECKED");
			publisherBeanId.setStrShowForum("true");
		} else {
			publisherBeanId.setShowForumChecked("");
			publisherBeanId.setStrShowForum("false");
		}
		if (request.getParameter("type_id") != null)
			publisherBeanId.setTypeId(request.getParameter("type_id"));
		if (request.getParameter("portlettype_id") != null)
			publisherBeanId.setPortlettypeId(request.getParameter("portlettype_id"));
		if (request.getParameter("image_id") != null)
			publisherBeanId.setImageId(request.getParameter("image_id"));
		if (request.getParameter("bigimage_id") != null)
			publisherBeanId.setBigimageId(request.getParameter("bigimage_id"));

		if (request.getParameter("creteria1_id") != null)
			publisherBeanId.setCreteria1Id(request.getParameter("creteria1_id"));
		if (request.getParameter("creteria2_id") != null)
			publisherBeanId.setCreteria2Id(request.getParameter("creteria2_id"));
		if (request.getParameter("creteria3_id") != null)
			publisherBeanId.setCreteria3Id(request.getParameter("creteria3_id"));
		if (request.getParameter("creteria4_id") != null)
			publisherBeanId.setCreteria4Id(request.getParameter("creteria4_id"));
		if (request.getParameter("creteria5_id") != null)
			publisherBeanId.setCreteria5Id(request.getParameter("creteria5_id"));
		if (request.getParameter("creteria6_id") != null)
			publisherBeanId.setCreteria6Id(request.getParameter("creteria6_id"));
		if (request.getParameter("creteria7_id") != null)
			publisherBeanId.setCreteria7Id(request.getParameter("creteria7_id"));
		if (request.getParameter("creteria8_id") != null)
			publisherBeanId.setCreteria8Id(request.getParameter("creteria8_id"));
		if (request.getParameter("creteria9_id") != null)
			publisherBeanId.setCreteria9Id(request.getParameter("creteria9_id"));
		if (request.getParameter("creteria10_id") != null)
			publisherBeanId.setCreteria10Id(request.getParameter("creteria10_id"));
		if (request.getParameter("dialog") != null)
			session.setAttribute("dialog", request.getParameter("dialog"));

		response.getWriter().write("<document>");
		response.getWriter()
				.write(productPostAllFaced.getAjaxDBList("Productlist.jsp?creteria1_id", "creteria1",
						publisherBeanId.getCreteria1Id(),
						"select creteria1_id , name   from creteria1   where  active = true "
								+ publisherBeanId.getPartCriteria(SiteType.MAIN_SITE,
										setupResources.getString("is_criteria_by_catalog").equals("true"))));
		response.getWriter()
				.write(productPostAllFaced.getAjaxDBList("Productlist.jsp?creteria2_id", "creteria2",
						publisherBeanId.getCreteria2Id(),
						"select creteria2_id , name   from creteria2   where  active = true "
								+ publisherBeanId.getPartCriteria(SiteType.MAIN_SITE,
										setupResources.getString("is_criteria_by_catalog").equals("true"))
								+ " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria1Id() + " ) "));
		response.getWriter()
				.write(productPostAllFaced.getAjaxDBList("Productlist.jsp?creteria3_id", "creteria3",
						publisherBeanId.getCreteria3Id(),
						"select creteria3_id , name   from creteria3   where  active = true "
								+ publisherBeanId.getPartCriteria(SiteType.MAIN_SITE,
										setupResources.getString("is_criteria_by_catalog").equals("true"))
								+ " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria2Id() + " ) "));
		response.getWriter()
				.write(productPostAllFaced.getAjaxDBList("Productlist.jsp?creteria4_id", "creteria4",
						publisherBeanId.getCreteria4Id(),
						"select creteria4_id , name   from creteria4   where  active = true "
								+ publisherBeanId.getPartCriteria(SiteType.MAIN_SITE,
										setupResources.getString("is_criteria_by_catalog").equals("true"))
								+ " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria3Id() + " ) "));
		response.getWriter()
				.write(productPostAllFaced.getAjaxDBList("Productlist.jsp?creteria5_id", "creteria5",
						publisherBeanId.getCreteria5Id(),
						"select creteria5_id , name   from creteria5   where  active = true "
								+ publisherBeanId.getPartCriteria(SiteType.MAIN_SITE,
										setupResources.getString("is_criteria_by_catalog").equals("true"))
								+ " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria4Id() + " ) "));
		response.getWriter()
				.write(productPostAllFaced.getAjaxDBList("Productlist.jsp?creteria6_id", "creteria6",
						publisherBeanId.getCreteria6Id(),
						"select creteria6_id , name   from creteria6   where  active = true "
								+ publisherBeanId.getPartCriteria(SiteType.MAIN_SITE,
										setupResources.getString("is_criteria_by_catalog").equals("true"))
								+ " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria5Id() + " ) "));
		response.getWriter()
				.write(productPostAllFaced.getAjaxDBList("Productlist.jsp?creteria7_id", "creteria7",
						publisherBeanId.getCreteria7Id(),
						"select creteria7_id , name   from creteria7   where  active = true "
								+ publisherBeanId.getPartCriteria(SiteType.MAIN_SITE,
										setupResources.getString("is_criteria_by_catalog").equals("true"))
								+ " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria6Id() + " ) "));
		response.getWriter()
				.write(productPostAllFaced.getAjaxDBList("Productlist.jsp?creteria8_id", "creteria8",
						publisherBeanId.getCreteria8Id(),
						"select creteria8_id , name   from creteria8   where  active = true "
								+ publisherBeanId.getPartCriteria(SiteType.MAIN_SITE,
										setupResources.getString("is_criteria_by_catalog").equals("true"))
								+ " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria7Id() + " ) "));
		response.getWriter()
				.write(productPostAllFaced.getAjaxDBList("Productlist.jsp?creteria9_id", "creteria9",
						publisherBeanId.getCreteria9Id(),
						"select creteria9_id , name   from creteria9   where  active = true "
								+ publisherBeanId.getPartCriteria(SiteType.MAIN_SITE,
										setupResources.getString("is_criteria_by_catalog").equals("true"))
								+ " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria8Id() + " ) "));
		response.getWriter()
				.write(productPostAllFaced.getAjaxDBList("Productlist.jsp?creteria10_id", "creteria10",
						publisherBeanId.getCreteria10Id(),
						"select creteria10_id , name   from creteria10   where  active = true "
								+ publisherBeanId.getPartCriteria(SiteType.MAIN_SITE,
										setupResources.getString("is_criteria_by_catalog").equals("true"))
								+ " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria9Id() + " ) "));

		response.getWriter().write("<dialog>");
		if (session.getAttribute("dialog") instanceof String)
			response.getWriter().write((String) session.getAttribute("dialog"));
		else
			response.getWriter().write("true");
		response.getWriter().write("</dialog>");

		response.getWriter()
				.write(productPostAllFaced.getAjaxDBList("Productlist.jsp?creteria1_id", "creteria_lable_1",
						publisherBeanId.getCreteria1Id(),
						"select creteria1_id , label   from creteria1   where  active = true "
								+ publisherBeanId.getPartCriteria(SiteType.MAIN_SITE,
										setupResources.getString("is_criteria_by_catalog").equals("true"))));
		response.getWriter()
				.write(productPostAllFaced.getAjaxDBList("Productlist.jsp?creteria2_id", "creteria_lable_2",
						publisherBeanId.getCreteria2Id(),
						"select creteria2_id , label   from creteria2   where  active = true "
								+ publisherBeanId.getPartCriteria(SiteType.MAIN_SITE,
										setupResources.getString("is_criteria_by_catalog").equals("true"))
								+ " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria1Id() + " ) "));
		response.getWriter()
				.write(productPostAllFaced.getAjaxDBList("Productlist.jsp?creteria3_id", "creteria_lable_3",
						publisherBeanId.getCreteria3Id(),
						"select creteria3_id , label   from creteria3   where  active = true "
								+ publisherBeanId.getPartCriteria(SiteType.MAIN_SITE,
										setupResources.getString("is_criteria_by_catalog").equals("true"))
								+ " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria2Id() + " ) "));
		response.getWriter()
				.write(productPostAllFaced.getAjaxDBList("Productlist.jsp?creteria4_id", "creteria_lable_4",
						publisherBeanId.getCreteria4Id(),
						"select creteria4_id , label   from creteria4   where  active = true "
								+ publisherBeanId.getPartCriteria(SiteType.MAIN_SITE,
										setupResources.getString("is_criteria_by_catalog").equals("true"))
								+ " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria3Id() + " ) "));
		response.getWriter()
				.write(productPostAllFaced.getAjaxDBList("Productlist.jsp?creteria5_id", "creteria_lable_5",
						publisherBeanId.getCreteria5Id(),
						"select creteria5_id , label   from creteria5   where  active = true "
								+ publisherBeanId.getPartCriteria(SiteType.MAIN_SITE,
										setupResources.getString("is_criteria_by_catalog").equals("true"))
								+ " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria4Id() + " ) "));
		response.getWriter()
				.write(productPostAllFaced.getAjaxDBList("Productlist.jsp?creteria6_id", "creteria_lable_6",
						publisherBeanId.getCreteria6Id(),
						"select creteria6_id , label   from creteria6   where  active = true "
								+ publisherBeanId.getPartCriteria(SiteType.MAIN_SITE,
										setupResources.getString("is_criteria_by_catalog").equals("true"))
								+ " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria5Id() + " ) "));
		response.getWriter()
				.write(productPostAllFaced.getAjaxDBList("Productlist.jsp?creteria7_id", "creteria_lable_7",
						publisherBeanId.getCreteria7Id(),
						"select creteria7_id , label   from creteria7   where  active = true "
								+ publisherBeanId.getPartCriteria(SiteType.MAIN_SITE,
										setupResources.getString("is_criteria_by_catalog").equals("true"))
								+ " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria6Id() + " ) "));
		response.getWriter()
				.write(productPostAllFaced.getAjaxDBList("Productlist.jsp?creteria8_id", "creteria_lable_8",
						publisherBeanId.getCreteria8Id(),
						"select creteria8_id , label   from creteria8   where  active = true "
								+ publisherBeanId.getPartCriteria(SiteType.MAIN_SITE,
										setupResources.getString("is_criteria_by_catalog").equals("true"))
								+ " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria7Id() + " ) "));
		response.getWriter()
				.write(productPostAllFaced.getAjaxDBList("Productlist.jsp?creteria9_id", "creteria_lable_9",
						publisherBeanId.getCreteria9Id(),
						"select creteria9_id , label   from creteria9   where  active = true "
								+ publisherBeanId.getPartCriteria(SiteType.MAIN_SITE,
										setupResources.getString("is_criteria_by_catalog").equals("true"))
								+ " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria8Id() + " ) "));
		response.getWriter()
				.write(productPostAllFaced.getAjaxDBList("Productlist.jsp?creteria10_id", "creteria_lable_10",
						publisherBeanId.getCreteria10Id(),
						"select creteria10_id , label   from creteria10   where  active = true "
								+ publisherBeanId.getPartCriteria(SiteType.MAIN_SITE,
										setupResources.getString("is_criteria_by_catalog").equals("true"))
								+ " and ( link_id = 0 or link_id = " + publisherBeanId.getCreteria9Id() + " ) "));
		// <%= publisherBeanId.getOneLabel("select label from creteria7 where active =
		// true " +
		// publisherBeanId.getPartCriteria(AuthorizationPageBeanId.getSite_id(),
		// setup_resources.getString("is_criteria_by_catalog").equals("true")) ) %>
		response.getWriter().write("</document>");
	}

}
