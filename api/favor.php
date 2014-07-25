<?php
require("./function.php");

function Add($Link)
{
	if(!isset($_POST['v']))return 213;
	$Value=$_POST['v'];
	$AddOn=(!isset($_POST['a']))?'':$_POST['a'];
	$UserId=$_SESSION['UserId'];
	$IfLove=(!isset($_POST['l']))?1:$_POST['l'];
	$CheckCode=GenerateRandomCode(4);
	if(strlen($Value)>65535)return 212;
	//Insert
	if(isset($_POST['t'])){
		$Query=mysqli_prepare($Link,"insert into `Favor` values ('',?,?,?,?,?,?);");
		mysqli_stmt_bind_param($Query,"isisss",$UserId,$Value,$IfLove,$AddOn,$_POST['t'],$CheckCode);
	}else{
		$Query=mysqli_prepare($Link,"insert into `Favor` values ('',?,?,?,?,now(6),?);");
		mysqli_stmt_bind_param($Query,"isiss",$UserId,$Value,$IfLove,$AddOn,$CheckCode);
	}
	mysqli_stmt_execute($Query);
	//Query
	$Query=mysqli_prepare($Link,"select * from `Favor` where `Value`=? and `UserId`=? and `CheckCode`=?;");
	mysqli_stmt_bind_param($Query,"sis",$Value,$UserId,$CheckCode);
	mysqli_stmt_execute($Query);
	$Result=mysqli_fetch_array(mysqli_stmt_get_result($Query),MYSQLI_ASSOC);
	if($Result===null)return 204;//Insert Error
	echo json_encode(Array('code'=>101,'UserId'=>$UserId,'Result'=>$Result));
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
	if(isset($_POST['s'])){
		if(isset($_POST['e'])){
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
		$Result=mysqli_fetch_all(mysqli_query($Link,"select * from `Favor` where `UserId`='$UserId' limit $StartPoint,$QueryLength;"),MYSQLI_ASSOC);
		if($Result===null)return 204;
		echo json_encode(Array('code'=>101,'UserId'=>$UserId,'StartPoint'=>$StartPoint,'EndPoint'=>$EndPoint,'Result'=>$Result));
		return 100;
	}
	return 204;
}

function Get($Link)
{
	if(!isset($_POST['i']))return 214;
	if(!is_numeric($_POST['i']))return 215;
	$Id=$_POST['i'];
	$Result=mysqli_fetch_array(mysqli_query($Link,"select * from `Favor` where `Id`='$Id';"),MYSQLI_ASSOC);
	if($Result===null)return 217;
	if($Result['UserId']!=$_SESSION['UserId']) return 216;
	echo json_encode(Array('code'=>101,'Result'=>$Result));
	return 100;
}

function Modify($Link)
{
	if(!isset($_POST['i']))return 214;
	if(!is_numeric($_POST['i']))return 215;
	$Id=$_POST['i'];
	$Result=mysqli_fetch_array(mysqli_query($Link,"select * from `Favor` where `Id`='$Id';"),MYSQLI_ASSOC);
	if($Result===null)return 217;
	if($Result['UserId']!=$_SESSION['UserId']) return 216;
	$Value=$_POST['v'];
	if(!isset($_POST['v']))return 213;
	$AddOn=(!isset($_POST['a']))?$Result['AddOn']:$_POST['a'];
	$IfLove=(!isset($_POST['l']))?$Result['IfLove']:$_POST['l'];
	$CheckCode=GenerateRandomCode(4);
	if(strlen($Value)>65535)return 212;
	if(isset($_POST['t'])){
		$Query=mysqli_prepare($Link,"update `Favor` set `Value`=? ,`AddOn`=? ,`IfLove`=? ,`CheckCode`=?, `LastModified`=? where `Id` = ?;");
		mysqli_stmt_bind_param($Query,"ssissi",$Value,$AddOn,$IfLove,$CheckCode,$_POST['t'],$Id);
	}else{
		$Query=mysqli_prepare($Link,"update `Favor` set `Value`=? ,`AddOn`=? ,`IfLove`=? ,`CheckCode`=?, `LastModified`=now(6) where `Id` = ?;");
		mysqli_stmt_bind_param($Query,"ssisi",$Value,$AddOn,$IfLove,$CheckCode,$Id);
	}
	mysqli_stmt_execute($Query);
	$Result=mysqli_fetch_array(mysqli_query($Link,"select * from `Favor` where `Id`='$Id';"),MYSQLI_ASSOC);
	if($Result===null)return 204;
	if(count($Result)===0)return 204;
	echo json_encode(Array('code'=>101,'Result'=>$Result));
	return 100;
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
case 'get':
	EchoErrorCode(Get($link));
	break;
case 'modify':
	EchoErrorCode(Modify($link));
	break;
default :
	EchoErrorCode(404);
	break;
}

?>
