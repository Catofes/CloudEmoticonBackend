<?php
/******************************* *
 * This is Config and Mysql File *
 * Also include log function     *
 ********************************/

//Global params
$mysql_host     ="127.0.0.1";
$mysql_username ="CloudEmoticon";
$mysql_passwd   ="G5h6EXqNVMLxJtK4";
$mysql_datebase ="CloudEmoticon";
//Mysql
$link= mysqli_connect(
	$mysql_host,
	$mysql_username,
	$mysql_passwd, 
	$mysql_datebase)
	or die("Error " . mysqli_error($link));
if(mysqli_connect_errno()){
	if($GLOBALS['debugmode']) echo "Can't connect to MySQL Server Error code:".mysqli_connect_error();
	die("MySQL Error");
}
if(!mysqli_set_charset($link,"utf8")){
	if($GLOBALS['debugmode']) echo "Can't Set MySQL UTF8.".mysqli_error($link);
	die("MySQL Charset Error");
}
?>

