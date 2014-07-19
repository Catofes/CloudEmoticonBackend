<?php
require("./function.php");
function CheckDeviceId($Link)
{
	if(!isset($_POST['d']))return 218;
	$Query=mysqli_prepare($Link,"select * from `Device` where `DeviceId`=? and `UserId`=?;");
	mysqli_stmt_bind_param($Query,"ss",$_POST['d'],$_SESSION['UserId']);
	mysqli_stmt_execute($Query);
	$Result=mysqli_fetch_array(mysqli_stmt_get_result($Query),MYSQLI_ASSOC);
	if($Result===null)return FALSE;
	$ID=$Result['DeviceId'];
	$Result=mysqli_fetch_array(mysqli_query($Link,"select * from `Device` where `DeviceId`='$ID';"),MYSQLI_ASSOC);
	return $Result;
}

function GenerateDeviceId($Link)
{
	$DeviceCode=GenerateRandomCode(64);
	$UserId=$_SESSION['UserId'];
	mysqli_query($Link,"insert into `Device` value ('$DeviceCode','$UserId','0000-00-00',0);");
	echo json_encode(Array('code'=>101,'DeviceCode'=>$DeviceCode));
	return 100;
}

function Now($Link)
{
	$Now=mysqli_fetch_array(mysqli_query($Link,"select now(6);"))[0];
	echo json_encode(Array('code'=>101,'Now'=>$Now));
	return 100;
}

function UpdateMainKey($Link)
{
	if(CheckDeviceId($Link)===FALSE)return 220;
	if(!isset($_POST['d']))return 218;
	if(!isset($_POST['k']))return 219;
	$Query=mysqli_prepare($Link,"update	`Device` set `MainKey`=? where `DeviceId`=? and `UserId`=?");
	mysqli_stmt_bind_param($Query,"isi",$_POST['k'],$_POST['d'],$_SESSION['UserId']);
	mysqli_stmt_execute($Query);
	echo json_encode(Array('code'=>101));
	return 100;
}

function GetDeviceInfo($Link)
{
	$Device=CheckDeviceId($Link);
	if($Device===FALSE)return 220;
	$ID=$Device['DeviceId'];
	$Result=mysqli_fetch_array(mysqli_query($Link,"select * from `Device` where `DeviceId`='$ID';"),MYSQLI_ASSOC);
	echo json_encode(Array('code'=>101,'Result'=>$Result));
	return 100;
}

function Pull($Link)
{
	$Device=CheckDeviceId($Link);
	if($Device===FALSE)return 220;
	$UserId=$Device['UserId'];
	$Time=$Device['LastSync'];
	$Result=mysqli_fetch_all(mysqli_query($Link,"select *,now(6) as `PullTime` from `Favor` Where `UserId`='$UserId' and `LastModified` > '$Time'"),MYSQLI_ASSOC);
	echo json_encode(Array('code'=>101,'Result'=>$Result),MYSQLI_ASSOC);
	return 100;
}

function PullOk($Link)
{
	$Device=CheckDeviceId($Link);
	if($Device===FALSE)return 220;
	$Query=mysqli_prepare($Link,"update `Device` set `LastSync`=? Where `DeviceId`=?;");
	mysqli_stmt_bind_param($Query,"ss",$_POST['t'],$Device['DeviceId']);
	mysqli_stmt_execute($Query);
	echo json_encode(Array('code'=>101));
	return 100;
}

function Check($Link)
{
	$UserId=$_SESSION['UserId'];
	if(isset($_POST['t'])){
		$Query=mysqli_prepare($Link,"select group_concat(CheckCode SEPARATOR \"\") `CheckCode`  FROM  `Favor` where `UserId` = '$UserId' and `LastModified` < ?;");
		mysqli_stmt_bind_param($Query,"s",$_POST['t']);
		mysqli_stmt_execute($Query);
		$Result=mysqli_fetch_array(mysqli_stmt_get_result($Query));
		echo json_encode(Array('code'=>101,'CheckCode'=>SHA1($Result[0])));
		return 100;
	}
	$Result=mysqli_fetch_array(mysqli_query($Link,"select group_concat(CheckCode SEPARATOR \"\") `CheckCode`  FROM  `Favor` where `UserId` = '$UserId';"));
	echo json_encode(Array('code'=>101,'CheckCode'=>SHA1($Result[0])));
	return 100;
}

function CheckList($Link)
{
    $UserId=$_SESSION['UserId'];
    if(isset($_POST['t'])){
        $Query=mysqli_prepare($Link,"select `Id`,`CheckCode`  FROM  `Favor` where `UserId` = '$UserId' and `LastModified` < ?;");
        mysqli_stmt_bind_param($Query,"s",$_POST['t']);
        mysqli_stmt_execute($Query);
        $Result=mysqli_fetch_all(mysqli_stmt_get_result($Query),MYSQLI_ASSOC);
        echo json_encode(Array('code'=>101,'Result'=>$Result));
        return 100;
    }
    $Result=mysqli_fetch_all(mysqli_query($Link,"select `Id`,`CheckCode`  FROM  `Favor` where `UserId` = '$UserId';"),MYSQLI_ASSOC);
    echo json_encode(Array('code'=>101,'CheckCode'=>$Result));
    return 100;
}

if(IfLogin($link)===FALSE)
	EchoErrorCode(201);

switch($_GET['f'])
{
case 'generatedeviceid':
	EchoErrorCode(GenerateDeviceId($link));
	break;
case 'now':
	EchoErrorCode(Now($link));
	break;
case 'updatemainkey':
	EchoErrorCode(UpdateMainKey($link));
	break;
case 'pull':
	EchoErrorCode(Pull($link));
	break;
case 'getdeviceinfo':
	EchoErrorCode(GetDeviceInfo($link));
	break;
case 'pullok':
	EchoErrorCode(PullOk($link));
	break;
case 'check':
	EchoErrorCode(Check($link));
	break;
case 'checklist':
	EchoErrorCode(CheckList($link));
	break;
default :
	EchoErrorCode(404);
	break;
}
?>
