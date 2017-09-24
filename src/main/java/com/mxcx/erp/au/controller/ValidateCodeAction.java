package com.mxcx.erp.au.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 生成验证码
 * @author Administrator
 *
 */
@Controller
public class ValidateCodeAction extends HttpServlet{
	private final static Logger log = Logger.getLogger(ValidateCodeAction.class);
	private static final long serialVersionUID = 3830489043573811734L;

	private static final int WIDTH = 60;
	private static final int HEIGHT = 22;

	private static final String CONTENT_TYPE = "image/jpeg";

	/**
	 * 获取验证码在登录页面显示
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/manager/getCode.do")
	public void getCode(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BufferedImage buffImg = new BufferedImage(WIDTH, HEIGHT,
				BufferedImage.TYPE_INT_RGB);

		Graphics2D g = buffImg.createGraphics();

		Font font = new Font("Times New Roman", Font.PLAIN, 18);

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);
		g.setColor(Color.GRAY);

		Random random = new Random();

		for (int i = 0; i < 40; i++) {
			int x1 = random.nextInt(WIDTH);
			int y1 = random.nextInt(HEIGHT);

			int x2 = random.nextInt(10);
			int y2 = random.nextInt(10);

			g.drawLine(x1, y1, x1 + x2, y1 + y2);
		}

		StringBuffer randomCode = new StringBuffer();

		for (int i = 0; i < 4; i++) {
			String strRand = String.valueOf(random.nextInt(10));

			g.setColor(Color.RED);
			g.drawString(strRand, 13 * i + 6, 16);

			randomCode.append(strRand);
		}

		HttpSession session = request.getSession();

		session.setAttribute("SESSION_RANDOM_CODE_KEY", randomCode.toString());

		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType(CONTENT_TYPE);

		OutputStream out = response.getOutputStream();

		ImageIO.write(buffImg, "jpeg", out);

		out.flush();
		out.close();
	}
}