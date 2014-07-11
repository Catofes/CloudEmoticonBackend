<?php
require("./config.php");
session_start();
date_default_timezone_set('Asia/Shanghai');

function EchoErrorCode($code)
{
	if($code==100)exit(0);
	$info=Array(
		'101'=>'OK.',
		'201'=>'Please login.',
		'202'=>'Already login. Please logout first.',
		'203'=>'Username or password incorrect.',
		'204'=>'SQL Error. We are sorry about that and please contact administrator to solve the issue.',
		'205'=>'Access denied. Your account was baned.',	
	);
	$info_cn=Array(
		'101'=>'请求完成。',
		'201'=>'请登录。',
		'202'=>'您已登录。请先登出。',
		'203'=>'帐户名密码不正确。',
		'204'=>'数据库错误，请速与管理猿联系并且Pia!<(=ｏ ‵-′)ノ☆程序猿。',
		'205'=>'拒绝登陆，您的帐号已经被禁用。',
	);
	echo json_encode(Array('code'=>$code,'info'=>$info[$code],'info_cn'=>$info_cn[$code]));
	exit(0);
}

function GenerateRandomCode($length)
{
	$Chars='abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
	$Result='';
	for($i=0; $i<$length; $i++)
		$Result .= $Chars[mt_rand(0,strlen($Chars)-1)];
	return $Result;	
}

function IfLogin($Link)
{
	if($_SESSION['Login']===TRUE)return TRUE;
	if($_POST['ak']){
		$Query=mysqli_prepare($Link,"select * from `AccessKey` where `Key`=?");
		mysqli_stmt_bind_param($Query,"s",$_POST['ak']);
		mysqli_stmt_execute($Query);
		$Result=mysqli_fetch_array(mysqli_stmt_get_result($Query));
		if($Result===NULL)return FALSE;
		if($Result['Expired']=='1')return FALSE;
		$Key=$Result['Key'];
		if(strtotime('-30 days')<strtotime($Result['GenerateTime'])){
			SessionSet($Link,$Result['UserId']);
			$_SESSION['AccessKey']=$Result['Key'];
			return TRUE;
		}
		else
			mysqli_query($Link,"update `AccessKey` set `expired`=1 where `Key`= '$Key';");
	}
	return FALSE;
}

function SessionSet($link,$UserId)
{
	$_SESSION['Login']=TRUE;
	$_SESSION['UserId']=$UserId;
}

?>
