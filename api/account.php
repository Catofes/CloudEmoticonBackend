<?php
require("./function.php");

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

	if($IfKeepLogin==='1'){
		$AccessKey=AccessKeySet($Link,$Result);
		if($AccessKey===FALSE)return 204;//SQL Error.
		$_SESSION['AccessKey']=$AccessKey;
		SessionSet($Link,$Result['Id']);
		echo json_encode(Array('code'=>101,'UserId'=>$Result['Id'],'AccessKey'=>$AccessKey));
		return 100;
	}
	SessionSet($Link,$Result['Id']);
	return 101;
}

function AccessKeySet($Link,$User)
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

if($_GET['f']==='login')
	EchoErrorCode(Login($link));	
if($_GET['f']==='logout')
	EchoErrorCode(Logout($link));

?>
