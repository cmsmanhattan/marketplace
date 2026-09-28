package com.cbsinc.cms;

import com.cbsinc.cms.utils.FileNames;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.cbsinc.cms.controllers.ServiceLocator;
import com.cbsinc.cms.faceds.ProductPostAllFaced;
import com.cbsinc.cms.faceds.RedisUtils;
import com.cbsinc.cms.utils.FileStorage;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet Class
 *
 * @web.servlet name="imageservletupload" display-name="Name for
 *              ImageServletUpload" description="Description for
 *              ImageServletUpload"
 * @web.servlet-mapping url-pattern="/imageservletupload"
 * @web.servlet-init-param name="A parameter" value="A value"
 */
public class ImageServletUpload extends HttpServlet {
	/**
	 * <p>
	 * Title: Content Manager System
	 * </p>
	 * <p>
	 * Description: System building web application develop by Konstantin Grabko.
	 * Konstantin Grabko is Owner and author this code. You can not use it and you
	 * cannot change it without written permission from Konstantin Grabko Email:
	 * konstantin.grabko@yahoo.com or konstantin.grabko@gmail.com
	 * </p>
	 * <p>
	 * Copyright: Copyright (c) 2002-2025
	 * </p>
	 * <p>
	 * Company: CENTER BUSINESS SOLUTIONS INC
	 * </p>
	 *
	 * @author Konstantin Grabko
	 * @version 1.0
	 */

	private static final long serialVersionUID = 1L;

	static private Logger log = Logger.getLogger(ImageServletUpload.class);

	// SoftPostBean publisherBeanId = null ;
	AuthorizationPageBean AuthorizationPageBeanId = null;
	ProductPostAllFaced productPostAllFaced = null;
	transient ResourceBundle resources = null;
	ServletContext servletContext = null;

	int scaleImageHeigth = 0;
	int scaleImageWidth = 0;
	// long file_size = 0 ;
	// 1byte = 7
	// long limmit_file_size = 11000000 ;
	float limmitFileSize = 1570000;

	public String filename = "";

	public long intID = 0;

	// java.io.PipedOutputStream bs = null ;
	// java.io.PipedInputStream pin = null ;

	FileOutputStream bs = null;

	// java.io.ByteArrayInputStream dd = new java.io.ByteArrayInputStream( ;
	// java.io.

	// java.io.PipedOutputStream outs = null ;

	private final static int BUFFER_SIZE = 2048;

	/**
	 * Initializes the servlet.
	 */

	private ResourceBundle sequencesRs = null;
	private ResourceBundle setupResources = null;
	private ResourceBundle localization = null;

	private File createdFile = null;

	public ImageServletUpload() {

	}

	public void init(ServletConfig config) throws ServletException {
		if (sequencesRs == null)
			sequencesRs = PropertyResourceBundle.getBundle("sequence");
		if (setupResources == null)
			setupResources = PropertyResourceBundle.getBundle("appconfig");

		if (!setupResources.getString("scale_image_heigth").equals(""))
			scaleImageHeigth = Integer.parseInt(setupResources.getString("scale_image_heigth"));
		if (!setupResources.getString("scale_image_width").equals(""))
			scaleImageWidth = Integer.parseInt(setupResources.getString("scale_image_width"));
		if (!setupResources.getString("limmit_image_size").equals(""))
			limmitFileSize = Integer.parseInt(setupResources.getString("limmit_image_size"));
		servletContext = config.getServletContext();
		super.init(config);
	}

	/**
	 * Flushes and closes the upload stream, then verifies the stored bytes.
	 *
	 * <p>The extension whitelist in cretareFileStream runs before any bytes have
	 * arrived, so it can only check the declared name. The signature check has to
	 * happen here, once the file is complete; a file whose contents do not match
	 * its extension is deleted rather than left in the storage directory.</p>
	 */
	public void saveFile() {
		try {
			if (bs == null)
				return;
			bs.flush();
			bs.close();
			verifyStoredImage();
			return;
		} catch (java.lang.Exception e) {
			log.error(e);
			System.out.println(e.toString());
		}
	}

	public FileOutputStream cretareFileStream(String FileName) {
		FileOutputStream fis = null;
		// File file = null ;
		try {
			if (FileName == null)
				return null;
			if (FileName.length() == 0)
				return null;
			if (!FileNames.isAllowedImage(FileName)) {
				log.warn("Rejected upload with disallowed extension: " + FileName);
				return null;
			}
			intID = saveImgURL(FileName, AuthorizationPageBeanId.getIntUserID());
			FileName = "" + intID + FileNames.extensionWithDot(FileName);

			String path = FileStorage.getInstance().getPath() ;
			//path = this.getClass().getResource("").getPath();
			//path = path.substring(0, path.indexOf("/WEB-INF/"));

			createdFile = new File(path + "//imgpositions", FileName);
			fis = new FileOutputStream(createdFile);
			// createdFile = file ;
		} catch (java.lang.Exception e) {
			log.error(e);
			System.out.println(e.toString());
		}
		return fis;
	}

//	void scaleImage1()
//	{
//
//		File f = new File("C:"+ File.separatorChar +"serega.jpg");
//		BufferedImage image = ImageIO.read(f);
//		BufferedImage bdest = new BufferedImage(120, 80, BufferedImage.TYPE_INT_RGB);
//		Graphics2D g = bdest.createGraphics();
//		AffineTransform at = AffineTransform.getScaleInstance((double)120/image.getWidth(),(double)80/image.getHeight());
//		g.drawRenderedImage(image,at);
//		ImageIO.write( bdest,"jpg",response.getOutputStream());
//		response.getOutputStream().flush();
//
//	}

	void scaleImage(int width, int height, File inf) {

		// File inf = new File("C:"+ File.separatorChar +"serega.jpg");
		// File outf = new File("C:"+ File.separatorChar +"serega.jpg");
		File outf = new File(inf.getPath());
		File backup = new File(inf.getPath() + "_bak");
		FileOutputStream foutstream = null;
		try {
			inf.renameTo(backup);
			outf.createNewFile();
			foutstream = new FileOutputStream(outf);
			BufferedImage image = ImageIO.read(backup);
			BufferedImage bdest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bdest.createGraphics();
			AffineTransform at = AffineTransform.getScaleInstance((double) width / image.getWidth(),
					(double) height / image.getHeight());
			g.drawRenderedImage(image, at);
			ImageIO.write(bdest, "jpg", foutstream);
			backup.delete();
		} catch (Exception e) {
			backup.renameTo(outf);
			log.error(e);

		} finally {
			try {
				foutstream.close();
			} catch (IOException e) {
				log.error(e);
			}
		}

	}

	public long saveImgURL(String FileName, long userId) {
		QueryManager Adp = new QueryManager();
		Adp.beginTransaction();
		String strID = null;
		// String query = "SELECT NEXT VALUE FOR images_image_id_seq AS ID FROM
		// ONE_SEQUENCES";
		String query = sequencesRs.getString("images");
		try {

			Adp.executeQuery(query);
			strID = Adp.getValueAt(0, 0);
			query = "insert into images ( image_id ,  imgname , img_url ,  user_id ) VALUES " + "( ? ,  ? , ? ,  ? )";
			Map args = new HashMap();
			args.put("image_id", Long.valueOf(strID));
			args.put("imgname", FileName);
			args.put("img_url", "imgpositions/" + strID + FileNames.extensionWithDot(FileName));
			args.put("user_id", Long.valueOf(userId));
			Adp.executeInsertWithArgs(query, args);
			Adp.commit();
		} catch (SQLException ex) {
			log.error(query, ex);
			Adp.rollback();
		} catch (Exception ex) {
			log.error(ex);
			Adp.rollback();
		} finally {
			Adp.close();
		}

		return Long.parseLong(strID);
	}

	/**
	 * Destroys the servlet.
	 */
	public void destroy() {

	}

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 */
	// FIX (shared state between concurrent requests): a servlet is a single
	// instance shared by every request thread, but this class keeps the
	// current request's user bean, file id, filename, output stream and
	// similar values in instance fields that processRequest() overwrites. Two
	// requests at the same time therefore read each other's values: one user
	// receives another user's file, or two uploads write into one
	// FileOutputStream and both files are corrupt.
	// The correct fix is to turn those fields into locals passed to the helper
	// methods; that is a large edit across all upload/download servlets. As a
	// safe interim measure, requests to this servlet are serialised. Uploads
	// are short and infrequent so this costs nothing; for downloads it caps
	// throughput at one transfer at a time and should be replaced by the
	// proper refactor if download volume matters.
	protected synchronized void processRequest(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, java.io.IOException {

		if (localization == null)
			localization = PropertyResourceBundle.getBundle("localization", req.getLocale());
		else if (!localization.getLocale().getLanguage().equals(req.getLocale().getLanguage()))
			localization = PropertyResourceBundle.getBundle("localization", req.getLocale());

		AuthorizationPageBeanId = (AuthorizationPageBean) req.getSession().getAttribute("authorizationPageBeanId");

		if (resources == null)
			resources = PropertyResourceBundle.getBundle("localization", res.getLocale());
		try {
			productPostAllFaced = ServiceLocator.getInstance().getProductPostAllFaced();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long start = System.currentTimeMillis();
		// RFC 1867
		String contentType = req.getContentType();
		res.setContentType("text/html;charset=UTF8");
		PrintWriter out = res.getWriter();

		if (AuthorizationPageBeanId == null) {
			printResult(out, "User not autorization");
			return;
		}
		if (AuthorizationPageBeanId.getIntUserID() == 0) {
			printResult(out, "User not autorization");
			out.close();
			return;
		}

		if (productPostAllFaced.isLimmitPostedMessages(AuthorizationPageBeanId, true)) {
			printResult(out, resources.getString("global_has_limmit_forsite"));
			out.close();
			return;
		}
		/*
		 * if (AuthorizationPageBeanId.getRoleId() != 2) { printResult(out,
		 * "User have low  access"); out.close(); return; }
		 */

		if (contentType == null) {
			printResult(out, "content type is null");
			log.error(new Exception("content type is null"));
			return;
		}

		int ind = contentType.indexOf("boundary=");
		if (ind == -1) {
			printResult(out, "IND is less than 0");
			log.error(new Exception("IND is less than 0"));
			return;
		}
		String boundary = contentType.substring(ind + 9);

		if (boundary == null) {
			printResult(out, "boundary is null");
			log.error(new Exception("boundary is null"));
			return;
		}

		String boundaryString = "--" + boundary;
		ServletInputStream in = req.getInputStream();
		byte[] buffer = new byte[BUFFER_SIZE];
		HashMap map = new HashMap();
		int result = in.readLine(buffer, 0, BUFFER_SIZE);
		int size = 0;
		outer: while (true) {

			if (result <= 0) {
				printResult(out, "Error. Stream truncated");
				log.error(new Exception("Error. Stream truncated"));
				break;
			}

			String line = new String(buffer, 0, result);

			if (!line.startsWith(boundaryString)) {
				printResult(out, "Error. multipart boundary missing.");
				log.error(new Exception("Error. multipart boundary missing."));
				break;
			}

			// boundary end tag
			if (line.substring(boundaryString.length()).startsWith("--")) {
				break;
			}

			result = in.readLine(buffer, 0, BUFFER_SIZE);
			if (result <= 0) {
				printResult(out, "Upload : may be end boundary which has no contents");
				log.error(new Exception("Upload : may be end boundary which has no contents"));
				break;
			}

			line = new String(buffer, 0, result);
			StringTokenizer tokenizer = new StringTokenizer(line, ";\r\n");
			String token = tokenizer.nextToken();
			String upperToken = token.toUpperCase();
			if (!upperToken.startsWith("CONTENT-DISPOSITION")) {
				printResult(out, "Format error. Content-Disposition expected.");
				log.error(new Exception("Format error. Content-Disposition expected."));
				break;
			}
			String disposition = upperToken.substring(21);
			if (!disposition.equals("FORM-DATA")) {
				printResult(out, "Sorry, I don't know how to handle [" + disposition + "] disposition.");
				log.error(new Exception("Sorry, I don't know how to handle [" + disposition + "] disposition."));
				break;
			}
			if (tokenizer.hasMoreElements()) {
				token = tokenizer.nextToken();
			} else {
				printResult(out, "Format error. NAME expected.");
				log.error(new Exception("Format error. NAME expected."));
				break;
			}
			int nameStart = token.indexOf("name=\"");
			int nameEnd = token.indexOf("\"", nameStart + 7);
			if (nameStart < 0 || nameEnd < 0) {
				printResult(out, "Format error. NAME expected.");
				log.error(new Exception("Format error. NAME expected."));
				break;
			}
			String name = token.substring(nameStart + 6, nameEnd);

			if (tokenizer.hasMoreElements()) {

				filename = null;
				int fnStart, fnEnd;
				String fileContentType = null;

				fnStart = line.indexOf("filename=\"");
				if (fnStart < 0) { // filename term missing
					printResult(out, "NO FILENAME given.");
					log.error(new Exception("NO FILENAME given."));
					result = in.readLine(buffer, 0, BUFFER_SIZE);
					continue;
				}

				fnEnd = line.indexOf("\"", fnStart + 11);
				if (fnEnd < 0) {
					printResult(out, "FILENAME is null.");
					log.error(new Exception("FILENAME is null."));
				} else {
					filename = line.substring(fnStart + 10, fnEnd);
					int lastindex = -1;
					if ((lastindex = filename.lastIndexOf('/')) < 0) {
						lastindex = filename.lastIndexOf('\\');
					}
					if (lastindex >= 0) {
						filename = filename.substring(lastindex + 1);
					}
					filename = processEscape(filename);
				}

				bs = cretareFileStream(filename);
				if (bs == null) {
					printResult(out, "Error. FileOutputStream = null");
					log.error(new Exception("Error. FileOutputStream = null"));
					break;
				}
				result = in.readLine(buffer, 0, BUFFER_SIZE);
				if (result <= 0) {
					printResult(out, "Error. Stream truncated");
					log.error(new Exception("Error. Stream truncated"));
					break;
				}
				fileContentType = new String(buffer, 0, result);
				if (fileContentType.toUpperCase().startsWith("CONTENT-TYPE:")) {
					fileContentType = fileContentType.substring(13).trim();
				} else {
					printResult(out, "what should I read here ??? - result = " + result + ", and read ["
							+ new String(buffer, 0, result) + "]");
					log.error(new Exception("what should I read here ??? - result = " + result + ", and read ["
							+ new String(buffer, 0, result) + "]"));
				}

				try {
					byte[] tmpbuffer1 = buffer;
					byte[] tmpbuffer2 = new byte[BUFFER_SIZE];
					byte[] tmpbuffer = tmpbuffer2;
					int tmpbufferlen = 0;
					boolean isFirst = true;
					boolean odd = true;
					inner: while ((result = in.readLine(buffer, 0, BUFFER_SIZE)) > 0) {
						if (limmitFileSize < size) {
							printResult(out, localization.getString("has_reached_limmit_file_size") + " "
									+ (limmitFileSize - 70000) / 1000000 + " Mb.");
							out.close();
							return;
						}

						if (isFirst) { // ignore all proceeding \r\n
							if (result == 2 && buffer[0] == '\r' && buffer[1] == '\n') {
								continue;
							}

						}

						if (bytesStartsWith(buffer, 0, result, boundaryString)) {
							if (!isFirst) {
								size += tmpbufferlen - 2;
								bs.write(tmpbuffer, 0, tmpbufferlen - 2);
								// fw.
							}
							continue outer;
						} else {
							if (!isFirst) {
								size += tmpbufferlen;
								bs.write(tmpbuffer, 0, tmpbufferlen);
							}
						}

						if (odd) {
							buffer = tmpbuffer2;
							tmpbuffer = tmpbuffer1;
						} else {
							buffer = tmpbuffer1;
							tmpbuffer = tmpbuffer2;
						}
						odd = !odd;

						tmpbufferlen = result;
						isFirst = false;
					}
				} catch (IOException ie) {
					log.error(ie);
					System.out.println("IO Error while write to file : " + ie.toString());
				} finally {
					System.out.println("finally Upload : size = " + size);
					appendValue(map, name, filename, fileContentType, size);
					saveFile();
					String lowerfile = createdFile.getPath().toLowerCase();
					if (lowerfile.endsWith(".gif") || lowerfile.endsWith(".jpg") || lowerfile.endsWith(".png")
							|| lowerfile.endsWith(".bmp")) {
						if (scaleImageWidth > 0 && scaleImageHeigth > 0)
							scaleImage(scaleImageWidth, scaleImageHeigth, createdFile);
					}
					RedisUtils.getInstance().writeFileInRedis(createdFile.getAbsoluteFile().getPath(),
							createdFile.getName());
				}
				result = in.readLine(buffer, 0, BUFFER_SIZE);
				System.out.println("what should I read here? - result = " + result + ", and read ["
						+ new String(buffer, 0, result) + "]");
			} else { // no more elements
				result = in.readLine(buffer, 0, BUFFER_SIZE);
				if (result <= 0) {
					System.out.println("Error. Stream truncated");
					log.error("Error. Stream truncated");
					break;
				}
				result = in.readLine(buffer, 0, BUFFER_SIZE);
				if (result <= 0) {
					System.out.println("Error. Stream truncated");
					log.error("Error. Stream truncated");
					break;
				}
				String value = new String(buffer, 0, result - 2); // exclude
																	// \r\n
				appendValue(map, name, value);
			}

			result = in.readLine(buffer, 0, BUFFER_SIZE);

		} // end of while

		long end = System.currentTimeMillis();
		System.out.println("Good! it took " + (end - start) + " (ms)");
		// saveLObj(filename.trim() , bs) ;
		// saveLObj(filename.trim() , size ) ;
		saveFile();
		String lowerfile = createdFile.getPath().toLowerCase();
		if (lowerfile.endsWith(".gif") || lowerfile.endsWith(".jpg") || lowerfile.endsWith(".png")
				|| lowerfile.endsWith(".bmp")) {
			if (scaleImageWidth > 0 && scaleImageHeigth > 0)
				scaleImage(scaleImageWidth, scaleImageHeigth, createdFile);
		}
		// saveLObj(filename.trim() , size ) ;
		printResult(out, map);

		out.close();
		RedisUtils.getInstance().writeFileInRedis(createdFile.getAbsoluteFile().getPath(), createdFile.getName());

	}

	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 */
	public String getServletInfo() {
		return "Short description";
	}

	boolean bytesStartsWith(byte[] bytes, int offset, int length, String toCompare) {
		boolean result = true;
		if (toCompare.length() > length) {
			return false;
		}

		for (int i = toCompare.length() - 1; i >= 0; i--) {
			if (toCompare.charAt(i) != bytes[offset + i]) {
				result = false;
				break;
			}
		}

		return result;
	}

	void appendValue(HashMap map, String name, String value, String contentType, int size) {
		ItemData data = new ItemData(name, value, contentType, size, true);
		map.put(name, data);
	}

	void appendValue(HashMap map, String name, String value) {
		ItemData data = new ItemData(name, value, null, 0, false);
		map.put(name, data);
	}

	String getValue(HashMap map, String name) {
		ItemData data = (ItemData) map.get(name);
		if (data == null) {
			return null;
		}
		return data.value;
	}

	final static int NORMAL = 0;

	final static int AMPERSAND = 1;

	final static int AMPERSHARP = 2;

	/**
	 * process html escape characters (&#NNNN;)
	 */
	String processEscape(String string) {
		StringBuffer buffer = new StringBuffer(string.length());
		char[] chars = string.toCharArray();
		StringBuffer escaped = new StringBuffer(6);
		int status = NORMAL;

		for (int i = 0; i < string.length(); i++) {
			switch (status) {
			case NORMAL:
				if (chars[i] == '&') {
					status = AMPERSAND;
				} else {
					buffer.append(chars[i]);
				}
				break;

			case AMPERSAND:
				if (chars[i] == '#') {
					status = AMPERSHARP;
				} else {
					status = NORMAL;
					buffer.append('&');
				}
				break;

			case AMPERSHARP:
				if (chars[i] == ';') {
					try {
						buffer.append((char) Integer.parseInt(escaped.toString()));
					} catch (NumberFormatException nfe) {
						// I don't handle other Entities
						buffer.append(escaped);
						buffer.append(';');
					}
					escaped.setLength(0);
					status = NORMAL;
				} else {
					escaped.append(chars[i]);
				}
				break;
			}
		}

		if (escaped.length() > 0) {
			buffer.append(escaped);
		}

		return buffer.toString();
	}

	void printResult(PrintWriter out, Map map) throws IOException {
		// file_size = 0 ;
		Iterator itr = map.values().iterator();
		out.println("<HTML><HEAD>");
		out.println("<META http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
		out.println("<TITLE>" + localization.getString("new_small_image") + "</TITLE>");
		out.println("<script language=\"JavaScript\">");
		out.println("<!--");
		out.println("function setData(){");
		out.println("top.postsoftform.imagename.value = '" + filename + "' ;");
		// out.println("opener.parent.postsoftform.imagename.value =
		// 'imgpositions/" + filename + "' ;" );
		out.println("top.postsoftform.image_id.value = '" + intID + "' ;");
		// out.println("top.close();");
		out.println("return true ;");
		out.println("}");
		out.println("function setClose(){");
		out.println("top.dwindow('SelectImage.jsp');");
		out.println("return true ;");
		out.println("}");
		out.println("//-->");
		out.println("</script>");
		out.println("</HEAD><BODY onLoad=\"return  setData()\" >");
		out.println("<H3>" + localization.getString("result_upload") + "</H3>");
		out.println("<TABLE>");
		out.println(localization.getString("header_result_loading"));
		while (itr.hasNext()) {
			ItemData data = (ItemData) itr.next();
			out.println("<TR>");
			// out.println("<TD>" + (data.name == null ? "" : data.name) + "</TD>");
			out.println("<TD>" + (data.value == null ? "" : data.value) + "</TD>");
			out.println("<TD>" + (data.contentType == null ? "" : data.contentType) + "</TD>");
			out.println("<TD>" + (data.isFile ? String.valueOf(data.size) : "") + "</TD>");
			// out.println("<TD>" + (data.isFile ? "file" : "") + "</TD>");
			out.println("</TR>");
		}
		out.println("</TABLE>");
		out.println("<br/><b> " + localization.getString("file_uploaded") + " </b><br/><br/>");
		out.println("<FORM><input type=\"button\" value=\"" + localization.getString("close_window")
				+ "\" onClick=\"return setClose()\" ></FORM>");
		out.println("</BODY></HTML>");
	}

	void printResult(PrintWriter out, String mess) throws IOException {
		// file_size = 0 ;
		out.println("<HTML><HEAD>");
		out.println("<META http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
		out.println("<TITLE>" + localization.getString("new_small_image") + "</TITLE>");
		out.println("<script language=\"JavaScript\">");
		out.println("<!--");
		out.println("function setData(){");
		out.println("top.postsoftform.imagename.value = '" + localization.getString("file_not_uploaded") + "' ;");
		// out.println("opener.parent.postsoftform.filename.value = 'file not
		// uploaded' ;" );
		out.println("top.postsoftform.image_id.value = -1 ;");
		// out.println("top.close();");
		out.println("return true ;");
		out.println("}");
		out.println("function setClose(){");
		out.println("top.dwindow('SelectImage.jsp');");
		out.println("return true ;");
		out.println("}");
		out.println("//-->");
		out.println("</script>");
		out.println("</HEAD><BODY onLoad=\"return  setData()\" >");
		out.println("<H3>" + localization.getString("result_upload") + "</H3>");
		out.println("<font size='3' >" + mess + " </font>");
		out.println("<br/><b> " + localization.getString("file_not_uploaded") + " </b><br/><br/>");
		out.println("<FORM><input type=\"button\" value=\"" + localization.getString("close_window")
				+ "\" onClick=\"return setClose()\" ></FORM>");
		out.println("</BODY></HTML>");
	}

	/*
	 * public String getServletInfo() { return "A servlet that uploads files"; }
	 */

	public int string2Integer(String s) {
		int i;
		try {
			i = Integer.parseInt(s);
		} catch (NumberFormatException ex) {
			log.error(ex);
			i = -1;
		}
		return i;
	}


	/**
	 * Deletes the just-written upload when its leading bytes do not match the
	 * image type its extension claims.
	 */
	private void verifyStoredImage() {
		if (createdFile == null || !createdFile.exists()) {
			return;
		}
		byte[] head = new byte[12];
		int read = 0;
		try (java.io.InputStream in = new java.io.FileInputStream(createdFile)) {
			int n;
			while (read < head.length && (n = in.read(head, read, head.length - read)) > 0) {
				read += n;
			}
		} catch (java.io.IOException ex) {
			log.error("could not re-read upload for signature check", ex);
			return;
		}
		if (read < head.length
				|| !FileNames.hasMatchingImageSignature(createdFile.getName(), head)) {
			log.warn("Rejected upload whose content does not match its extension: "
					+ createdFile.getName());
			if (!createdFile.delete()) {
				log.error("could not delete rejected upload: " + createdFile.getAbsolutePath());
			}
			createdFile = null;
		}
	}
}
