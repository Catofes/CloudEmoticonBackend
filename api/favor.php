<?php
require("./function.php");

function Add($Link)
{
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

function GetListNum($Link)
{
	$UserId=$_SESSION['UserId'];
	$Result=mysqli_fetch_array(mysqli_query($Link,"select count(*) as `Num` from `Favor` where `UserId`='$UserId';"));
	if($Result===null)return 204;
	echo json_encode(Array('code'=>101,'UserId'=>$UserId,'Num'=>$Result['Num']));
	return 100;
}

function GetList($Link)
{
	$limit=0;
	if($_POST['s']){
		if($_POST['e']){
			$StartPoint=intval($_POST['s']);
			$EndPoint=intval($_POST['e']);
			if(($StartPoint>=0)&&($EndPoint>=$StartPoint)){
				$QueryLength=$EndPoint-$StartPoint+1;
				$limit=1;
			}
		}
	}
	$UserId=$_SESSION['UserId'];
	if($limit===0){
		$Result=mysqli_fetch_all(mysqli_query($Link,"select * from `Favor` where `UserId`='$UserId';"),MYSQLI_ASSOC);
		if($Result===null)return 204;
		echo json_encode(Array('code'=>101,'UserId'=>$UserId,'Num'=>count($Result),'Result'=>$Result));
		return 100;
	}
	if($limit===1){
		$Query=mysqli_prepare($Link,"select * from `Favor` where `UserId`=? limit ?,?;");
		mysqli_stmt_bind_param($Query,"iii",$UserId,$StartPoint,$QueryLength);
		mysqli_stmt_execute($Query);
		$Result=mysqli_fetch_all(mysqli_stmt_get_result($Query),MYSQLI_ASSOC);
		if($Result===null)return 204;
		echo json_encode(Array('code'=>101,'UserId'=>$UserId,'StartPoint'=>$StartPoint,'EndPoint'=>$EndPoint,'Result'=>$Result));
		return 100;
	}
	return 204;
}

if(IfLogin($link)===FALSE)
	EchoErrorCode(201);

switch($_GET['f'])
{
case 'add':
	EchoErrorCode(Add($link));
	break;
case 'getlistnum':
	EchoErrorCode(GetListNum($link));
	break;
case 'getlist':
	EchoErrorCode(GetList($link));
	break;
default :
	EchoErrorCode(404);
	break;
}

?>
