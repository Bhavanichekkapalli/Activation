package testDemo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import demo.hackathon.client.LogMonitorClient;

public class PatterenSelectServlet extends HttpServlet {
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// Set the response message's MIME type
		response.setContentType("text/html;charset=UTF-8");
		// Allocate a output writer to write the response message into the
		// network socket
		PrintWriter out = response.getWriter();

		try {
			String patternListVal = request.getParameter("patternListVal");
			String fileListVal = request.getParameter("fileListVal");
			String notificationType = request.getParameter("alertNotify");
			String emailListVal = request.getParameter("mailListVal");
			String directoryName = request.getParameter("fileDir");
			String isDirSearchReq = request.getParameter("fileDirChk");

			ArrayList<String> mailList = new ArrayList<String>();
			ArrayList<String> fileList = new ArrayList<String>();
			ArrayList<String> patternList = new ArrayList<String>();

			String pattValArr[] = patternListVal.split(",");
			for (int i = 0; i < pattValArr.length; i++) {
				patternList.add(pattValArr[0]);

			}
			String fileListArr[] = fileListVal.split(",");
			for (int i = 0; i < fileListArr.length; i++) {
				fileList.add(fileListArr[0]);

			}

			String emailListArr[] = emailListVal.split(",");
			for (int i = 0; i < emailListArr.length; i++) {
				mailList.add(emailListArr[0]);

			}
			Runnable thread = new LogMonitorClient(directoryName, fileList,
					patternList, notificationType, mailList);
			thread.run();

			System.out.println(mailList.size() + " ::" + fileList.size()
					+ " ::" + notificationType + "::" + patternList.size() + "::"
					+ directoryName + "::" + isDirSearchReq);

			RequestDispatcher rd = request.getRequestDispatcher("/test.jsp");
			rd.forward(request, response);// method may be include or forward

		} finally {
			out.close(); // Always close the output writer
		}
	}
}