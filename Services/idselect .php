<?php 

error_reporting(0);
$con = mysql_connect("localhost","cre_jobportal","7792803988");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

mysql_select_db("cre_jobportal", $con);
 $fileid= $_POST['fileid'];

 $email = $_POST['email'];

 
$query="select *  from filestore where email='$email' ";

$result=mysql_query($query);

$data=mysql_fetch_array($result);
$u_email=$data['email'];
if($u_email==$email )
{
	echo 'success';	
$sql = "UPDATE filestore SET fileid='$fileid' WHERE email='$email'";

 if(mysql_query($sql)){
 echo "Successfully UpdateDrive";
 }else{
 echo "Could not Updated drive";
 
 }


	}
	else{
		echo 'failure';

$sql = "INSERT INTO filestore (fileid,email ) VALUES ('$fileid','$email')";
 
 
 if(mysql_query($sql)){
 echo "Successfully Registered Drive";
 }else{
 echo "Could not register drive";
 
 }


		}
 
?>