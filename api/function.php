<?php
require("./config.php");
/***
 
	function:
			
		EchoErrorCode($code)
		GenerateRandomCode($length)
		IfLogin($Link)
		SessionSet($Link,$User

 
***/

session_start();
date_default_timezone_set('Asia/Shanghai');
header('Content-type: text/json');


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
		'206'=>'Wrong Password.',
		'207'=>'Please Input Password.',
		'208'=>'Username Illegal.',
		'209'=>'Username existed.',
		'210'=>'Email Address Illegal.',
		'211'=>'Email Address Already Existed.',
		'212'=>'Data too long.',	
		'213'=>'Please input value.',
		'404'=>'Page Not Found.',
	);
	$info_cn=Array(
		'101'=>'请求完成。',
		'201'=>'请登录。',
		'202'=>'您已登录。请先登出。',
		'203'=>'帐户名密码不正确。',
		'204'=>'数据库错误，请速与管理猿联系并且Pia!<(=ｏ ‵-′)ノ☆程序猿。',
		'205'=>'拒绝登陆，您的帐号已经被禁用。',
		'206'=>'密码错误。',
		'207'=>'密码不能为空。',
		'208'=>'用户名不合法。',
		'209'=>'用户名已存在。',
		'210'=>'邮箱地址不合法。',
		'211'=>'邮箱地址已经注册。',
		'212'=>'数据过长。',
		'213'=>'请输入数据。',
		'404'=>'请求不存在。',
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
			SessionSet($Result['UserId'],$Result['Key']);
			return TRUE;
		}
		else
			mysqli_query($Link,"update `AccessKey` set `expired`=1 where `Key`= '$Key';");
	}
	return FALSE;
}

function SessionSet($UserId,$AccessKey=0)
{
	$_SESSION['Login']=TRUE;
	$_SESSION['UserId']=$UserId;
	if($AccessKey!==0)
		$_SESSION['AccessKey']=$AccessKey;
}

?>
