<?php
require("./function.php");

function GET($Link)
{
	if(!isset($_POST['g']))return 213;
	if(!isset($_POST['i']))return 213;
	$Group	=	$_POST['g'];
	$UserId	=	$_POST['i'];
	$Query=mysqli_prepare($Link,"select * from `Favor` where Value = ? and UserId=? and Type=3 ;");
	mysqli_stmt_bind_param($Query,"si",$Group,$UserId);
	mysqli_stmt_execute($Query);
	$Result	=	mysqli_fetch_array(mysqli_stmt_get_result($Query),MYSQLI_ASSOC);
	if($Result===null) return 404;
	$Result=mysqli_fetch_array(mysqli_query($Link,"select * from `Favor` where `Group`='$Group' and `UserId`='$UserId' and Type = 1;"),MYSQLI_ASSOC);
	return $Result;
}

switch($_GET['f'])
{
case 'get':
	EchoErrorCode(GET($link));
	break;
default:
	EchoErrorCode(404);
	break;
}
