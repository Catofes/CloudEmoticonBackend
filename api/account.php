<?php
require("./function.php");

/***
	function:

		Login($Link)
		AccessKeySet($Link,$User)
		Logout($Link)
		ChangePassword($Link)
	***/
function Login($Link)
{
	if(IfLogin($Link)===TRUE)return 202;//Check If Login

	$Username=$_POST['u'];
	$Password=substr(sha1($_POST['p']),0,32);//Password encrypt.
	$IfKeepLogin=$_POST['k'];//1 for a month login.

	$Query=mysqli_prepare($Link,"select * from User where Username = ? And Password = ?");
	mysqli_stmt_bind_param($Query,"ss",$Username,$Password);
	mysqli_stmt_execute($Query);
	$Result=mysqli_fetch_array(mysqli_stmt_get_result($Query));

	if($Result===NULL)return 203;//Login Failed. Username or Password incorrect.
	if($Result['Level']===0)return 205;//Access Deny. Account was baned.

	if($IfKeepLogin==='1'){//Set AccessKey for Long-Time-Login
		$AccessKey=AccessKeySet($Link,$Result);
		if($AccessKey===FALSE)return 204;//SQL Error.
		$_SESSION['AccessKey']=$AccessKey;
		SessionSet($Link,$Result['Id']);
		echo json_encode(Array('code'=>101,'UserId'=>$Result['Id'],'AccessKey'=>$AccessKey));
		return 100;
	}
	SessionSet($Link,$Result['Id']);
	echo json_encode(Array('code'=>101,'UserId'=>$Result['Id']));
	return 100;
}

function AccessKeySet($Link,$User)//Generate AccessKey and store it in SQL
{
	$Key=GenerateRandomCode(64);
	$UserId=$User['Id'];
	if(mysqli_query($Link,"insert into AccessKey VALUES('$Key','$UserId',now(),'');"))
		return $Key;
	return FALSE;
}

function Logout($Link)
{
	if(IfLogin($Link)===FALSE)return 201;
	if($_SESSION['AccessKey']){
		$AccessKey=$_SESSION['AccessKey'];
		mysqli_query($Link,"update AccessKey set `Expired`=1 where `Key`='$AccessKey';");
	}
	$_SESSION['Login']=FALSE;
	session_unset();
	session_destroy();
	return 101;
}

function ChangePassword($Link)
{
	if(IfLogin($Link)===FALSE)return 201;
	if(!$_POST['np'])return 207;
	$UserId=$_SESSION['UserId'];
	$OldPassword=substr(sha1($_POST['op']),0,32);
	$NewPassword=substr(sha1($_POST['np']),0,32);
	$Result=mysqli_fetch_array(mysqli_query($Link,"select * from `User` where `Id`='$UserId' ;"));//Get User Info
	if($Result==null)return 204;
	if($OldPassword!==$Result['Password'])
		return 206;
	mysqli_query($Link,"update `User` set `Password`='$NewPassword' where `Id`='$UserId';");//Update User Password
	return 101;
}

if($_GET['f']==='login')
	EchoErrorCode(Login($link));	
if($_GET['f']==='logout')
	EchoErrorCode(Logout($link));
if($_GET['f']==='changepassword')
	EchoErrorCode(ChangePassword($link));
?>
