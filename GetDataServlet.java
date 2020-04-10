package com.angulatpac;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/GetDataServlet")
public class GetDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public GetDataServlet() { super();}
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/report?useSSL=false","root","password");   
			PreparedStatement stmt=con.prepareStatement("select * from storePercentData");   
			ResultSet rs=stmt.executeQuery();  

			JSONArray jsonArray=new JSONArray();
			JSONObject jsonDataObject;  

			while(rs.next()){
				jsonDataObject= new JSONObject();
				jsonDataObject.put("ramUsed",rs.getDouble(1));
				jsonDataObject.put("diskUsed",rs.getDouble(2));
				jsonDataObject.put("cpuUsed",rs.getDouble(3));
				jsonDataObject.put("readDateTime",rs.getString(4));
				jsonArray.put(jsonDataObject);  
			}

//			System.out.println(jsonArray);
			response.setContentType("application/json"); 
			response.setCharacterEncoding("utf-8"); 
			String jsons=jsonArray.toString();
			PrintWriter out=response.getWriter();
			out.println(jsons);
			con.close();  
			stmt.close();
			rs.close();

		 }catch(Exception e){ 
				System.out.println(e);
		 }  
	}

}
