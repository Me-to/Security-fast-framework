package com.example.qian.security.Image;

import javax.servlet.http.HttpServlet;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 验证码
 */

public class CheckImage extends HttpServlet {

	public static ImageCode createImageCode() {
		String number = "23456789ABCDEFGHKLMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz";
		int width = 100; // 验证码图片宽度
		int height = 36; // 验证码图片长度
		int length = 4; // 验证码位数
		int expireIn = 60; // 验证码有效时间 60s

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();

		Random random = new Random();

		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

//		StringBuilder sRand = new StringBuilder();
//		for (int i = 0; i < length; i++) {
//			String rand = String.valueOf(random.nextInt(10));
//			sRand.append(rand);
//			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
//			g.drawString(rand, 13 * i + 6, 16);
//		}


		StringBuilder sRand = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(number.length()-1);
			//在base字符串中获取下标为index的字符
			String rand = String.valueOf(number.charAt(index));
			sRand.append(rand);
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(rand, 13 * i + 6, 16);
		}
		g.dispose();
		return new ImageCode(image, sRand.toString(), expireIn);
	}

	private static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
}



