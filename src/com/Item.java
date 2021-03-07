package com;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Item {
	
	//Create connection
	public Connection connect() {
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
		 	con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/items","root", "");
		 	//For testing
		 	System.out.print("Successfully connected");
	
		} catch(Exception e){
			e.printStackTrace();
		}

		
	 return con;
	}
	
	//Insert items
	public String insertItem(String code, String name, String price, String desc) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null){
				return "Error while connecting to the database";
			}
			// create a prepared statement
			String query = " insert into items(`itemID`,`itemCode`,`itemName`,`itemPrice`,`itemDesc`)"
				+ " values (?, ?, ?, ?, ?)";
		
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, code);
			preparedStmt.setString(3, name);
			preparedStmt.setDouble(4, Double.parseDouble(price));
			preparedStmt.setString(5, desc); 

			//execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		}catch (Exception e) {
			output = "Error while inserting";
			System.err.println(e.getMessage());
	 	 }
	 	
		return output;
	 }

	//Read all items
	public String readItems() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
		// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Item Code</th>"
					+"<th>Item Name</th><th>Item Price</th>"
					+ "<th>Item Description</th>"
					+ "<th>Update</th><th>Remove</th></tr>";
			String query = "select * from items";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next()) {
				String itemID = Integer.toString(rs.getInt("itemID"));
				String itemCode = rs.getString("itemCode");
				String itemName = rs.getString("itemName");
				String itemPrice = Double.toString(rs.getDouble("itemPrice"));
				String itemDesc = rs.getString("itemDesc");
			
				// Add a row into the html table
				output += "<tr><td>" + itemCode + "</td>";
				output += "<td>" + itemName + "</td>";
				output += "<td>" + itemPrice + "</td>"; 
				output += "<td>" + itemDesc + "</td>";
	 
				// buttons
				output += "<td><form method='post' action='items.jsp'>  "
											
						+ "<input name='btnUpdate' type='submit' value='Update' class=\"btn btn-success\" >"
						+ "<input name='itemID' type='hidden' value='" + itemID + "'>"
						
						+ "</form></td>"
						+ "<td><form method='post' action='items.jsp'>"
						+ "<input name='btnRemove' "
						+ " type='submit' value='Remove' move' type='submit' value='Remove'\r\n"
						+ " class='btn btn-danger'>"
						+ "<input name='itemID' type='hidden' "
						+ " value='" + itemID + "'>" + "</form></td></tr>";
			}
			con.close();
			// Complete the html table
			
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	//select one item
	public String getItem(int itemID) {
		String output = "";
		
		try {
			Connection con = this.connect();
			if (con == null) {
				return "Error while connecting to get item for update";
			}		
			
			String query = "select * from items where itemID = ?";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, itemID);
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {				
				String itemCode = rs.getString("itemCode");
				String itemName = rs.getString("itemName");
				String itemPrice = rs.getString("itemPrice");
				String itemDesc = rs.getString("itemDesc");
				
				
				output = "<form method='post' action='items.jsp'>";	
				output += "<h2>Item ID : " + itemID + "</h2>";
				output += "Item code: <input name=\"itemCode\" type=\"text\" value=\""+ itemCode + "\" class=\"form-control\"><br>";
				output += "Item name: <input name=\"itemName\" type=\"text\" value=\""+ itemName + "\" class=\"form-control\"><br>";
				output += "Item price: <input name=\"itemPrice\" type=\"text\" value=\""+ itemPrice + "\" class=\"form-control\"><br>";
				output += "Item description: <input name=\"itemDesc\" type=\"text\" value=\""+ itemDesc + "\" class=\"form-control\"><br>";
				output += "<input name='itemID' type='hidden' value='" + itemID + "'>";

				output += "<input type=\"submit\" name=\"Update\" value=\"update\" class=\"btn btn-primary\"></form>";

			}
			
			con.close();			
			
		} catch (Exception e) {
			output = "Error while getting item ";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	//Update item details
	public String updateItems(String id,String code, String name, String price, String desc) {
		String output = "";
		
		
		
		try {
			Connection con = connect();
			if (con == null){
				return "Error while connecting to the database";
			}
			// create a prepared statement
			String query = "update items set itemCode=?,itemName =?,itemPrice=?,itemDesc=? where itemID = ?";		
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			
			preparedStmt.setString(1, code);
			preparedStmt.setString(2, name);
			preparedStmt.setDouble(3, Double.parseDouble(price));
			preparedStmt.setString(4, desc); 
			preparedStmt.setInt(5, Integer.parseInt(id));

			//execute the statement
			preparedStmt.executeUpdate();
			con.close();
			output = "Updated successfully";
		}catch (Exception e) {
			output = "Error while updating";
			System.err.println(e.getMessage());
	 	}
	 	
		return output;
		
		
	}
	
	//Delete an item
	public String deleteItem (String itemId) {
		
		int itemID = Integer.parseInt(itemId);
		String output = "";
		
		try {
			
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database";
			}
	
			// create a prepared statement		
			String query = "delete from items where itemID = ?" ;							
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding the value of itemID
			preparedStmt.setInt(1, itemID);
	
			//execute the statement
			preparedStmt.execute();
			con.close();
			output = "Item id = " + itemID + " deleted successfully";
		} catch (Exception e)
		{
			output = "Error while inserting";
			System.out.println(itemID);
			System.err.println(e.getMessage());
		}
		return output;

	}		
			
	
	
	
	


}
