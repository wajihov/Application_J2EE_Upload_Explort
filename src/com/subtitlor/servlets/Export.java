package com.subtitlor.servlets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.subtitlor.connection.SingletonConnetion;
import com.subtitlor.model.Subliter;

@WebServlet("/Export")
public class Export extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Export() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		File file = new File("C:\\AMD\\write.srt");

		try (Writer writer = new BufferedWriter(new FileWriter(file))) {
			Connection connection = SingletonConnetion.getConnecxion();
			Statement st = connection.createStatement();
			String req = "Select * from Traduction";
			ResultSet rs = st.executeQuery(req);
			while (rs.next()) {
				Subliter subliter = new Subliter(rs.getInt(1), rs.getString(2), rs.getString(3));

				String contents = subliter.getId() + System.getProperty("line.separator") + subliter.getPeriode()
						+ System.getProperty("line.separator") + subliter.getMessage()
						+ System.getProperty("line.separator");
				writer.write(contents);

			}

			response.setContentType("text/plain");
			response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
			FileInputStream inputStream = null;
			try {
				inputStream = new FileInputStream("C:\\AMD\\write.srt");

				byte[] buffer = new byte[1024];
				int bytesRead = 0;
				do {
					bytesRead = inputStream.read(buffer, 0, buffer.length);
					response.getOutputStream().write(buffer);
				} while (bytesRead == buffer.length);
				System.out.println("END OF WHILE");
				response.getOutputStream().flush();
				System.out.println("FLUSHING DONE");
			} catch (Exception e) {
				System.out.println("Exception in DownloadFile.java =" + e);
			} finally {
				if (inputStream != null)
					inputStream.close();
				System.out.println("INPUT STREAM CLOSED");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
