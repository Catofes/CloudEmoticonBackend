<?php
require("./function.php");

function GET($Link)
{
	if(!isset($_POST['v']))return 213;
	$Value	=	$_POST['v'];
	$Query=mysqli_prepare($Link,"select * from `Favor` where Value = ?;");
	mysqli_stmt_bind_param($Query,"s",$Value);
	mysqli_stmt_execute($Query);
	$Result	=	mysqli_fetch_array(mysqli_stmt_get_result($Query),MYSQLI_ASSOC);
	if($Result===null) return 404;
	if($Result['AddOn']==0) return 404;
	$Result=mysqli_fetch_array(mysqli_query($Link,"select * from `Favor` where `AddOn`='$Value';"),MYSQLI_ASSOC);
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
