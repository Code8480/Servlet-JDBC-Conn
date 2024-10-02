package com.training;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//@WebServlet("/save")
public class SaveDB extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String url = "jdbc:postgresql://localhost:5432/demo";
	String uname = "postgres";
	String pass = "257257";
		
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String uid = request.getParameter("uid");

		try {
			Class.forName("org.postgresql.Driver"); // optional - for loading and registering
			
			Connection con = DriverManager.getConnection(url, uname, pass);
//			System.out.println("After getting con object...");
			PreparedStatement pstmt = con.prepareStatement("insert into servlet values (?,?,?)");
			pstmt.setString(1, fname);
			pstmt.setString(2, lname);
			pstmt.setString(3, uid);
//			System.out.println("Before executing query");
			int result = pstmt.executeUpdate();
			pstmt.close();
			
		
			if(result > 0) {
				out.println("User Inserted successfully");
			}else {
				out.println("Error inserting user data...");
			}
			
						
			con.close();
			System.out.println("Connection Closed...");

		} catch (ClassNotFoundException e) {
			System.out.println("Catch first block...");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Catch second block...");
			e.printStackTrace();
		}

	}

}
