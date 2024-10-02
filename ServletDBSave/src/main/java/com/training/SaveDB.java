package com.training;

import jakarta.servlet.ServletConfig;
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

//	String url = "jdbc:postgresql://localhost:5432/demo";
//	String uname = "postgres";
//	String pass = "257257";
	Connection con;
	
	@Override
	public void init(ServletConfig config) throws ServletException {

		try {
			Class.forName("org.postgresql.Driver");
			String url = config.getServletContext().getInitParameter("url");
			String uname = config.getServletContext().getInitParameter("uname");
			String pass = config.getServletContext().getInitParameter("pass");
					
			con = DriverManager.getConnection(url, uname, pass);
			
		} catch (ClassNotFoundException e) {	
			System.out.println("First catch block init...");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Second catch block init...");
			e.printStackTrace();
		}
	}
	
//	@Override
//	public void init() throws ServletException {
//		try {
//			Class.forName("org.postgresql.Driver");
//			con = DriverManager.getConnection(url, uname, pass);
//		} catch (ClassNotFoundException e) {	
//			System.out.println("First catch block init...");
//			e.printStackTrace();
//		} catch (SQLException e) {
//			System.out.println("Second catch block init...");
//			e.printStackTrace();
//		}
//		
//	}
		
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String uid = request.getParameter("uid");

		try {
//			Class.forName("org.postgresql.Driver"); // optional - for loading and registering
			
//			Connection con = DriverManager.getConnection(url, uname, pass);
//			System.out.println("After getting con object...");
			PreparedStatement pstmt = con.prepareStatement("insert into servlet values (?,?,?)");
			pstmt.setString(1, fname);
			pstmt.setString(2, lname);
			pstmt.setString(3, uid);
//			System.out.println("Before executing query");
			int result = pstmt.executeUpdate();
			pstmt.close();			
		
			if(result > 0) 
				out.println("User Inserted successfully");
			else
				out.println("Error inserting user data...");

		} catch (SQLException e) {
			System.out.println("Catch block doGet method...");
			e.printStackTrace();
		}

	}	

	public void destroy() {
		
		if(con != null)	{
			try {
				con.close();
				System.out.println("Connection Closed...");
			} catch (SQLException e) {
				System.out.println("Inside destroy catch block...");
				e.printStackTrace();
			}			
		}
	}
	
}
