<?php
require("./function.php");

function Add($Link)
{
	If(IfLogin($Link)===FALSE)return 201;
	if(!$_POST['v'])return 213;
	$Value=$_POST['v'];
	$Addon=(!$_POST['a'])?'':$_POST['a'];
	$UserId=$_SESSION['UserId'];
	$CheckCode=GenerateRandomCode(4);
	if(strlen($Value)>65535)return 212;
	//Insert
	$Query=mysqli_prepare($Link,"insert into `Favor` values ('',?,?,1,?,now(6),?);");
	mysqli_stmt_bind_param($Query,"isss",$UserId,$Value,$Addon,$CheckCode);
	mysqli_stmt_execute($Query);
	//Query
	$Query=mysqli_prepare($Link,"select * from `Favor` where `Value`=? and `UserId`=? and `CheckCode`=?;");
	mysqli_stmt_bind_param($Query,"sis",$Value,$UserId,$CheckCode);
	mysqli_stmt_execute($Query);
	$Result=mysqli_fetch_array(mysqli_stmt_get_result($Query));
	if($Result==null)return 204;//Insert Error
	echo json_encode(Array('code'=>101,'Id'=>$Result['Id'],'Value'=>$Result['Value'],'UserId'=>$Result['UserId'],'CheckCode'=>$Result['CheckCode']));
	return 100;
}

switch($_GET['f'])
{
case 'add':
	EchoErrorCode(Add($link));
default :
	EchoErrorCode(404);
}

?>
