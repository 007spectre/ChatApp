<?php
	define('HOST','localhost');
	define('USER','cre_jobportal');
	define('PASS','7792803988');
	define('DB','cre_jobportal');
	
	$con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
	
if($_SERVER['REQUEST_METHOD']=='POST'){
 $username = $_POST['username'];
 $email = $_POST['email'];
 $password = $_POST['password'];
$token = $_POST['token'];
 

 
 $sql = "INSERT INTO user (username,password,email,token) VALUES ('$username','$password','$email','$token')";
 
 
 if(mysqli_query($con,$sql)){
 echo "Successfully Registered";
 }else{
 echo "Could not register";
 
 }
 }else{
echo 'error';
}
	
	?>