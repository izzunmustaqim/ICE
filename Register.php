<?php
    //$con=mysqli_connect("localhost","admin","my_password","my_db");
	$con=mysqli_connect("mysql7.000webhost.com","a7002543_admin","admin123","a7002543_ice");
    
    $name = $_POST["name"];
    $age = $_POST["age"];
    $username = $_POST["username"];
    $password = $_POST["password"];
	
	if (mysqli_connect_errno($con)) 
	{ 
		echo "Failed to connect to MySQL: " . mysqli_connect_error(); 
    } 
    
    $statement = mysqli_prepare($con, "INSERT INTO User (name, age, username, password) VALUES (?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "siss", $name, $age, $username, $password);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_close($statement);
    
    mysqli_close($con);
?>
