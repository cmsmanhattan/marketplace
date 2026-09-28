package com.cbsinc.cms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.activation.MimetypesFileTypeMap;

import org.apache.log4j.Logger;

import com.cbsinc.cms.faceds.RedisUtils;
import com.cbsinc.cms.utils.FileStorage;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServlet;

/**
 * Filter class
 *
 * @web.filter name="ImagesCache" display-name="Name for ImagesCache"
 *             description="Description for image files if those exist in local
 *             web module cache directory "
 * @web.filter-mapping url-pattern="*images/*"
 *
 */

public class CatalogImagesCache extends HttpServlet {

	static private Logger log = Logger.getLogger(FilesCache.class);

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		OutputStream outStream = null ;
		FileInputStream inStream = null ;
		try {
			String path = FileStorage.getInstance().getPath();
	        String urlAfterWebDomain = request.getRequestURI();
	         String relativeImagePath = urlAfterWebDomain.substring(urlAfterWebDomain.indexOf("/imgcatalog/"));
	        log.debug("\nFetching imgcatalog from "+path+relativeImagePath);
	        String contentType = request.getContentType();
	        if( contentType == null)  contentType = getContentType(path+relativeImagePath);
	        response.setContentType(contentType);
	         outStream = response.getOutputStream();
	         inStream  = new FileInputStream(path+relativeImagePath);

	        byte[] buffer = new byte[4096];
	        int bytesRead;

	        while ((bytesRead = inStream .read(buffer)) != -1) {
	            outStream.write(buffer, 0, bytesRead);
	        }
		}catch (Exception e) {
			log.error(e);
		}finally {
		 if(inStream != null)  inStream.close();
		 if(outStream != null)  outStream.close();
		}
    }


	public  String getContentType(String filePath) {
        try {
            File file = new File(filePath);
            MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
            return mimeTypesMap.getContentType(file.getName());
        } catch (Throwable e) {
        	log.error(e);
            return null;
        }
    }

	public CatalogImagesCache() {

	}

	/*
	public void init(FilterConfig filterConfig) {

		checkFilesDir();
	}
	*/

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {
		String fileName = "";
		try {
			HttpSession hsession = ((HttpServletRequest) request).getSession(false);
			if (hsession == null)
				hsession = ((HttpServletRequest) request).getSession(true);

			// ++++++++++++++++++++++ start +++++++++++++++++++++++++++++++++++
			String path = ((HttpServletRequest) request).getRequestURI();
			int index = path.lastIndexOf("/") + 1;
			fileName = path.substring(index);
			checkFileInCacheRepo(fileName);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	void checkFilesDir() {
		try {
			//String pageStorePath = this.getClass().getResource("").getPath();
			//pageStorePath = pageStorePath.substring(1, pageStorePath.indexOf("/WEB-INF/"));
			String path = FileStorage.getInstance().getPath() ;

			File file = new File(path + File.separatorChar + "imgcatalog");
			if (!file.exists()) {
				file.mkdirs();
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	void checkFileInCacheRepo(String fileName) {
		try {
			//String pageStorePath = this.getClass().getResource("").getPath();
			//pageStorePath = pageStorePath.substring(1, pageStorePath.indexOf("/WEB-INF/"));
			String path = FileStorage.getInstance().getPath() ;
			File file = new File(path + File.separatorChar + "imgcatalog" + File.separatorChar + fileName);
			if (!file.exists()) {
				byte[] key = RedisUtils.getInstance().getKey(file.getName());
				RedisUtils.getInstance().writeFileInFS(file.getAbsoluteFile().getPath(), key);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
