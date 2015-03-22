package org.cl.common.register;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.cl.common.register.MyAuthenticator;

public class SequenceRegister extends HttpServlet {

	// 发送邮件
	@SuppressWarnings("static-access")
	public void init() throws ServletException {
		// 判断是否需要身份认证
		Properties p = new Properties();
		p.put("mail.smtp.host", "smtp.sohu.com");
		p.put("mail.smtp.port", "pop3.sohu.com");
		p.put("mail.smtp.auth", 1);
		Authenticator authenticator = new MyAuthenticator("ss", "19990909");
		Session sendMailSession = Session.getInstance(p, authenticator);
		// 根据session创建一个邮件消息
		Message mailMessage = new MimeMessage(sendMailSession);
		try {
			mailMessage.setFrom(new InternetAddress("ss@sohu.com"));
			mailMessage.setRecipients(Message.RecipientType.TO, InternetAddress
					.parse("ss@sohu.com"));
			// 设置邮件消息的主题
			mailMessage.setSubject("软件启动");
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			Multipart mainPart = new MimeMultipart();
			// 创建一个包含HTML内容的MimeBodyPart
			BodyPart html = new MimeBodyPart();
			// 设置HTML内容
			html.setContent(this.getServletContext().getServerInfo(),
					"text/html; charset=utf-8");
			mainPart.addBodyPart(html);
			// 将MiniMultipart对象设置为邮件内容
			mailMessage.setContent(mainPart);
			Transport transport = sendMailSession.getTransport("smtp");
			transport.connect();
			// 发送邮件
			transport.send(mailMessage);
			transport.close();
		} catch (Exception e) {

		}
	}
}
