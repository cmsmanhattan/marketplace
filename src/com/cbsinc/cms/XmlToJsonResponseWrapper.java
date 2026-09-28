package com.cbsinc.cms;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

//import org.dom4j.io.JSONFormat;
import org.json.XML;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

public class XmlToJsonResponseWrapper extends HttpServletResponseWrapper {

	private ByteArrayServletOutputStream _servletOutputStream;
	private PrintWriter _printWriter;

	public XmlToJsonResponseWrapper(HttpServletResponse response) {
		super(response);
		response.setContentType("text/x-json");
	}

	@Override
	public void setContentLength(int len) {
		// ignore
	}

	@Override
	public void setContentType(String type) {
		// ignore
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (_printWriter != null) {
			throw new IllegalStateException("Servlet already accessed print writer.");
		}

		if (_servletOutputStream == null) {
			_servletOutputStream = new ByteArrayServletOutputStream();
		}
		return _servletOutputStream;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (_printWriter == null && _servletOutputStream != null) {
			throw new IllegalStateException("Servlet already accessed output stream.");
		}

		if (_printWriter == null) {
			_servletOutputStream = new ByteArrayServletOutputStream();
			Writer writer = new OutputStreamWriter(_servletOutputStream, getResponse().getCharacterEncoding());
			_printWriter = new PrintWriter(writer);
		}
		return _printWriter;
	}

	public void finishResponse() throws IOException {
		if (_servletOutputStream != null) {
			if (_printWriter != null) {
				_printWriter.flush();
			}
			try {
				String xmlText = new String(_servletOutputStream.getBytes(), getResponse().getCharacterEncoding());
				String jsonText = XML.toJSONObject(xmlText).toString();
				getResponse().getWriter().write(jsonText);
				getResponse().getWriter().write("\n");

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void resetBuffer() {
		_servletOutputStream = null;
		_printWriter = null;
		super.resetBuffer();
	}

	@Override
	public void reset() {
		_servletOutputStream = null;
		_printWriter = null;
		super.reset();
	}
}
