package com.subtitlor.servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.subtitlor.connection.SingletonConnetion;

@WebServlet("/Upload")
@MultipartConfig
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Upload() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// processRequest(request, response);
		response.setContentType("text/html;charset=UTF-8");

		final PrintWriter writer = response.getWriter();

		// Create path components to save the file
		final String path = request.getParameter("destination");
		final Part filePart = request.getPart("file");
		String fileName = getFileName(filePart);

		OutputStream out = null;
		InputStream filecontent = null;

		try {
			out = new FileOutputStream(new File(path + File.separator + "myfile.srt"));
			filecontent = filePart.getInputStream();

			int read = 0;
			final byte[] bytes = new byte[1024];

			while ((read = filecontent.read(bytes)) != -1) {
				out.write(bytes, 0, read);

			}

			writer.println("New file " + fileName + " created at " + path);
			BufferedReader br = null;
			Connection connection = SingletonConnetion.getConnecxion();
			Statement st = connection.createStatement();
			String req = "";
			int id = 0;
			String periode = "";
			String message = "";
			try {
				br = new BufferedReader(new FileReader(fileName));
				String line;
				while ((line = br.readLine()) != null) {
					//

					if (isInteger(line, 10)) {
						if (id > 0) {
							message=message.replaceAll("'", " ");
							req = "INSERT INTO traduction VALUES (" + id + ",'" + periode+ "','" + message + "');";
							int res = st.executeUpdate(req);

							System.out.println("le resulat" + res);
						}
						System.out.println("id= " + id);
						periode = "";
						message = "";
						id = Integer.parseInt(line);

					} else if (periode != "") {
						message += line + " ";
						if (line.equalsIgnoreCase("")) {
							System.out.println("Message : " + message);
						}
					} else if (id != 0) {
						periode = line;
						System.out.println("Periode : " + periode);
					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (br != null) {
						br.close();
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}

		} catch (FileNotFoundException fne) {
			writer.println("You either did not specify a file to upload or are "
					+ "trying to upload a file to a protected or nonexistent " + "location.");
			writer.println("<br/> ERROR: " + fne.getMessage());
		} catch (SQLException e1) {

			e1.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
			if (filecontent != null) {
				filecontent.close();
			}
			if (writer != null) {
				writer.close();
			}
		}
	}

	public static boolean isInteger(String s, int radix) {
		if (s.isEmpty())
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1)
					return false;
				else
					continue;
			}
			if (Character.digit(s.charAt(i), radix) < 0)
				return false;
		}
		return true;
	}

	private String getFileName(final Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;

	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		// Create path components to save the file
		final String path = request.getParameter("destination");
		final Part filePart = request.getPart("file");
		final String fileName = getFileName(filePart);

		OutputStream out = null;
		InputStream filecontent = null;
		final PrintWriter writer = response.getWriter();

		try {
			out = new FileOutputStream(new File(path + File.separator + fileName));
			filecontent = filePart.getInputStream();

			int read = 0;
			final byte[] bytes = new byte[1024];

			while ((read = filecontent.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			writer.println("New file " + fileName + " created at " + path);

		} catch (FileNotFoundException fne) {
			writer.println("You either did not specify a file to upload or are "
					+ "trying to upload a file to a protected or nonexistent " + "location.");
			writer.println("<br/> ERROR: " + fne.getMessage());

		} finally {
			if (out != null) {
				out.close();
			}
			if (filecontent != null) {
				filecontent.close();
			}
			if (writer != null) {
				writer.close();
			}
		}
	}

	/*
	 * @SuppressWarnings("unused") private String getFileName(final Part part) {
	 * final String partHeader = part.getHeader("content-disposition");
	 * 
	 * for (String content : part.getHeader("content-disposition").split(";")) {
	 * if (content.trim().startsWith("filename")) { return
	 * content.substring(content.indexOf('=') + 1).trim().replace("\"", ""); } }
	 * return null; }
	 */

}
