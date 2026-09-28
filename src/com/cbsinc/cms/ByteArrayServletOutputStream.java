package com.cbsinc.cms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;

public class ByteArrayServletOutputStream extends ServletOutputStream {

	private ByteArrayOutputStream _outputStream;

	public ByteArrayServletOutputStream() {
		_outputStream = new ByteArrayOutputStream();
	}

	@Override
	public void write(int b) throws IOException {
		_outputStream.write(b);
	}

	@Override
	public void write(byte[] b) throws IOException {
		_outputStream.write(b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		_outputStream.write(b, off, len);
	}

	@Override
	public void close() throws IOException {
		_outputStream.close();
	}

	public byte[] getBytes() {
		return _outputStream.toByteArray();
	}

	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setWriteListener(WriteListener writeListener) {
		// TODO Auto-generated method stub

	}
}
