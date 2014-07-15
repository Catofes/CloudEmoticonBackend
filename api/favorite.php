<?php
require("./function.php");

function Add($Link)
{
	If(Login($Link)===FALSE)return 201;
	$Value=$_POST['v'];
	$Addon=$_POST['a'];
	$UserId=$_SESSION['UserId'];
	$CheckCode=GenerateRandomCode(4);
	if(strlen($Value)>65535)return 212;
	//Insert
	$Query=mysqli_prepare($Link,"insert into `Favor` values ('',?,?,'',?,now(6),?);");
	mysqli_stmt_bind_param($Query,"isss",$UserId,$Value,$Addon,$CheckCode);
	mysqli_stmt_excute($Query);
	//Query
	$Query=mysqli_prepare($Link,"select * from `Favor` where `Value`=? and `UserId`=? and `CheckCode`=?;");
	mysqli_stmt_bind_param($Query,"sis",$Value,$UserId,$CheckCode);
	mysqli_stmt_excute($Query);
	$Result=mysqli_fetch_array(mysqli_stmt_get_result($Query));
	if($Result==null)return 204;//Insert Error
	echo json_encode(Array('code'=>101,'Id'=>$Result['Id'],'Value'=>$Result['Value'],'UserId'=>$Result['UserId'],'CheckCode'=>$Result['CheckCode']));
	return 100;
}

switch($_GET['f'])
{
case 'a':
	EchoErrorCode(Add($link));
default :
	EchoErrorCode(404);
}

?>
