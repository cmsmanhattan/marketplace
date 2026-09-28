package com.cbsinc.cms.services.statistics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

/*import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder; */

public class GenNumberProducer {
	private static int ImageWidth = 90;
	private static int ImageHeight = 40;

	// String[] abs = { "a" , "A" , "b" , "B" , "c" , "C" , "d" , "D" , "e" , "E" ,
	// "f" , "F" , "g" , "G" , "h" , "H" , "i" , "I" , "j" , "J" , "k" , "K" , "l" ,
	// "L" , "m" , "M" , "n" , "N" , "o" , "O" , "p" , "P" , "q" , "Q" , "r" , "R" ,
	// "s" , "S" , "t" , "T" , "u" , "U" , "v" , "V" , "w" , "W" , "x" , "X" , "y" ,
	// "Y" , "z" , "Z" , "0" , "1" , "2" , "3" , "4" , "5" , "6" , "7" , "8" , "9" }
	// ;
	String[] abs = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	String[] fontNames = { "TimesRoman", "Courier", "Arial", "Roman", "Tahoma" };
	String code = "";
	int countChar = 1;

	/**
	 * Request the producer create an image
	 *
	 * @param stream stream to write image into
	 * @return image type
	 */
	public String createImage(OutputStream stream) throws IOException {
		// plottedPrices = new Point2D.Double[5];
		// int prices[] = {105, 100, 97, 93, 93};
		StringBuffer buff = new StringBuffer();

		BufferedImage bi = new BufferedImage(ImageWidth + 10, ImageHeight, BufferedImage.SCALE_DEFAULT);

		graphics = bi.createGraphics();
		graphics.setColor(Color.white);
		graphics.fillRect(0, 0, bi.getWidth(), bi.getHeight());
		graphics.setColor(Color.red);

		Random random = new java.util.Random();
		int index = 0;
		int fontIndex = 0;
		String mychar = "";
		Font font = null;

		for (int i = 0; i < 6; i++) {
			index = random.nextInt(abs.length);
			fontIndex = random.nextInt(fontNames.length);
			mychar = abs[index];
			buff.append(mychar);
			font = new Font(fontNames[fontIndex], Font.PLAIN, 18);
			decorateVerticalLine(graphics, font, mychar);
		}

		code = buff.toString();
		ImageIO.write(bi, "JPEG", stream);
		// encoder.encode(bi);
		return "image/jpg";
	}

	/**
	 * Adds decorating text to the enpoint of a vertical line
	 *
	 */
	void decorateVerticalLine(Graphics2D graphics, Font font, String text) {
		double endX = 14 * countChar;
		double endY = 10;
		double baseX = endX;
		double baseY = endY;

		// ***************************************************************
		// Center the text over the line
		// ***************************************************************
		FontMetrics metrics = graphics.getFontMetrics();
		baseX -= metrics.stringWidth(text) / 2;
		baseY += metrics.getAscent();
		graphics.setFont(font);
		graphics.drawString(text, new Float(baseX).floatValue(), new Float(baseY).floatValue());

		countChar = countChar + 1;
	}

	/**
	 * Test Entrypoint
	 *
	 */
	public static void main(String[] args) {

		try {
			FileOutputStream f = new FileOutputStream("C://stockgraph.jpg");
			GenNumberProducer producer = new GenNumberProducer();
			producer.createImage(f);
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Graphics2D graphics;

}
