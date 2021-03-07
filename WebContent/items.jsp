<%@page import="com.Item"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%

	
	if (request.getParameter("Update") != null) {	
		
	 
		Item itemObj = new Item();
    	String stsMsg = itemObj.updateItems(
    			request.getParameter("itemID"),
    			request.getParameter("itemCode"),
    			request.getParameter("itemName"),
    			request.getParameter("itemPrice"),
    			request.getParameter("itemDesc"));
		session.setAttribute("statusMsg", stsMsg);
	}

	else if	(request.getParameter("itemCode") != null ){
  	 	Item itemObj = new Item();
  	 	
  	 	
  	
	 		String stsMsg = itemObj.insertItem(request.getParameter("itemCode"),
	 		request.getParameter("itemName"),
	 		request.getParameter("itemPrice"),
			request.getParameter("itemDesc"));
	 		session.setAttribute("statusMsg", stsMsg);
	}	
  
 	
 	//Delete item----------------------------------	
 	else if (request.getParameter("btnRemove") != null) {
 			
 		Item itemObj = new Item();
 		
 		
 		//System.out.print(request.getParameter("itemID"));	
 		String stsMsg = itemObj.deleteItem(request.getParameter("itemID"));
 					
 		session.setAttribute("statusMsg", stsMsg);			
 	}	
 	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="Views/bootstrap.min.css">

<title>Items Management</title>
</head>
<body>

<div class="container">
 	<div class="row">
 		<div class="col">
 		<h1>Items Management</h1>
 		<% 
 if (request.getParameter("btnUpdate") != null){
int id = Integer.parseInt(request.getParameter("itemID"));
Item itemObj = new Item();
String formUpdate = itemObj.getItem(id);
out.print(formUpdate);

 %>


 
  
 <% 
 }  else{
 %>
 <form method="post" action="items.jsp">
 Item code: <input name="itemCode" type="text" class="form-control"><br>
 Item name: <input name="itemName" type="text" class="form-control"><br>
 Item price: <input name="itemPrice" type="text" class="form-control"><br>
 Item description: <input name="itemDesc" type="text" class="form-control"><br>
 <input name="btnSubmit" type="submit" value="Save" class="btn btn-primary">
  
 <% 
 }  
 %>
 
 
 </form>
 <br/>
<div class="alert alert-success">
<%

 	out.print(session.getAttribute("statusMsg"));
%>
</div>
<br>
<%
 Item itemObj = new Item();
 //itemObj.connect();
 out.print(itemObj.readItems());
%>

 		</div>
 	</div>
</div>

</body>
</html>