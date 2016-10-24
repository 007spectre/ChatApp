<?php 

error_reporting(0);
$con = mysql_connect("localhost","cre_jobportal","7792803988");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

mysql_select_db("cre_jobportal", $con);
 $email = $_POST['email'];
 $password = $_POST['password'];
 
$query="select *  from user where email='$email' AND password='$password'";

$result=mysql_query($query);

$data=mysql_fetch_array($result);
$u_email=$data['email'];
$u_pass=$data['password'];
if($u_email==$email )
{
	echo 'success';
	}
	else{
		echo 'failure';
		}
 
?>