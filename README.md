CloudEmoticonPHP
===============

##API

###Define

All returns will be json type. Enclude `code` and other info.They may have `info` and  `info_cn`  to show the code's meaning.

Function name with `*` means you must login first. Args with `*` means the parameter is required.

Return Code Define:

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
		'214'=>'Please input Id.',
		'215'=>'Wrong input.',
		'216'=>'Permission denied.',
		'217'=>'No record.',
		'218'=>'Device id is needed.',
		'219'=>'Please input MainKey',
		'220'=>'Wrong Device Id',
		'404'=>'Page Not Found.',


###Account Part

Login Function:

    URL:        ./api/account.php?f=login
    Method:     POST
    Args:       u:*		Username        (Char, length < 32. )
                p:*		Password        (Char. Server will do substr(SHA1(Password),0,32). )
                k:      KeepLogin       (1 for on. Server will return an AccessKey if it is on. )
    Return:     code  
				UserId					
                AccessKey               (return when KeepLogin is On. A 64-long Char.)
    Others:     Server use Session and AccessKey to keep login. Session livetime depends on PHP settings. AccessKey 
                can be use for a month. You can post request with AccessKey so the server will automatic relogin
                and set Session for you.
                
Logout Function: *

    URL:        ./api/account.php?f=logout
    Method:     POST or GET
    Args:       ak:     AccessKey       
    Return:     code
    Others:     If session is timeout you must carry AccessKey to make the Long-Time-Login logout.

ChangePassworde Function: *

	URL:		./api/account.php?f=changepassword
	Method:		POST
	Args:		op:*	OldPassword		
				np:*	NewPassword		(Char. Don't be empty.)
				ak:		AccessKey		
	Return:		code

CheckLogin Function:
	
	URL:		./api/account.php?f=checklogin
	Method:		POST
	Args:		ak:		AccessKey
	Return:		code

CheckUsername Function:

	URL:		./api/account.php?f=checkusername
	Method:		POST
	Args:		u:*		Username		
	Return:		code

CheckEmailAddress Function:

	URL:		./api/account.php?f=checkemailaddress
	Method:		POST
	Args:		e:*		EmailAddress	(Char, length<128.)
	Return:		code

Register Function:

	URL:		./api/account.php?f=register
	Method:		POST
	Args:		u:*		Username		
				p:*		Password		
				e:*		EmailAddress	
	Return:		code
				UserId
				Username
				EmailAddress

###Favor Function

Add Function: *
	
	URL:		./api/favor.php?f=add
	Method:		POST
	Args:		v:*		Value			(The main data, text, length<65535.)	
				a:		Addon			(The addon info, text, length<2^16-1.)
				t:		LastModified	(The Time you add this record.)
				l:		IfLove			(Int.)
	Return:		code
				UserId
				Result					(The Record Data. Include Id, UserId, Value, Addon, LastModified, CheckCode.)

GetListNum Function: *

	URL:		./api/favor.php?f=getlistnum
	Method:		POST
	Return:		code
				UserId
				Num						(The count of favor list.)

GetList Function: *

	URL:		./api/favor.php?f=getlist
	Method:		POST
	Agrs:		s:		StartPoint		(The first's record's position is 0.This number must equal or bigger than 0)
				e:		EndPoint		(This number must bigger than StartPoint.)
	Return:		code
				UserId
				Num		countNum		(Records Number. This will not return if you set s&e.)
				StartPoint
				EndPoint				(This two only return when you set s&e)
				Result					(All Records Data. Each element is an object include record id, user id, value, iflove, addon, lastmodified and checkcode.)

Get Function: *

	URL:		./api/favor.php?f=get
	Method:		POST
	Args:		i:*		Record Id				
	Return:		code
				Result

Modify Function: *

	URL:		./api/favor.php?f=modify
	Method:		POST
	Args:		i:*		Record Id
				v:*		Value
				a:		Addon
				l:		IfLove			(You can modify this to 0 to dislike it.)
				t:		LastModified	(The Time you modify the record.)
	Return:		code
				Result

####Device Part

Now Function:
	
	URL:		./api/device.php?f=now
	Method:		GET or POST
	Return:		code
				Now						(The server's time now.)

GenerateDeviceId Function: *

	URL:		./api/device.php?f=generatedeviceid
	Method:		Get or POST
	Return:		code
				DeviceCode				(64-length char.)

GetDeviceInfo Function: *

	URL:		./api/device.php?f=getdeviceinfo
	Method:		POST
	Args:		d:*		DeviceCode		
	Return:		code
				Result					(All info include DeviceCode, UserId, LastSync, MainKey.)

UpdateMainKey Function:	*

	URL:		./api/device.php?f=updatemainkey
	Method:		POST
	Args:		d:*		DeviceCode
				k:*		MainKey			(A int,stores whatever you want.)
	Return:		code

Pull Function: *

	URL:		./api/device.php?f=pull
	Method:		POST
	Args:		d:*		DeviceCode
	Return:		code
				Result					(This contains all you favor record since you lastsync. Also includ now time.)

PullOk Function: *

	URL:		./api/device.php?f=pullok
	Method:		POST
	Args:		d:*		DeviceCode
				t:*						(This time will store and replace this device's lastsync time.)
	Return:		code

Check Funtion: *

	URL:		./api/device.php?f=check
	Method:		POST
	Args:		t:					
	Return:		code
				CheckCode				(This Code equals combine your records' checkcode (All Or before time t) and sha1 it.)
				
CheckList Function: *

	URL:		./api/device.php?f=checklist
	Method:		POST
	Args:		t:
	Return:		code
				Result					(This will return all(or before time t) your records' id and checkcode.)

####使用说明

这里大概的介绍一下如何使用。

所有的返回数据格式都为json，所有的时间的格式返回格式都精确到微秒，为形如`2014-07-16 22:12:54.424814`的字符串。

首先account.php 部分是有关帐号的。包括`Login Logout ChangePassword CheckUsername CheckEmailAddress Register `这些函数。需要说明的是注册和改密码的API不会检测你的密码强度。这一点应该由客户端来完成。密码传输过程为明文，这一点可以靠使用HTTPS解决。

然后favor.php 部分是有关收藏的。其实这里设计是可以储存任何数据的。 包括`Add GetListNum GetList Get Modify` 五个函数。分别是增加记录，获取记录数目，获取部分记录，获取一个记录，修改记录。一条记录会包涵以下部分： 记录的ID，记录所属用户的ID，记录的值(65535个字符以内)，记录的附加值(2^24-1字符以内)，记录的属性值（名字叫做IfLove，设计时想法是区分是否收藏），记录最后的修改时间，以及记录的校验码（4个char字符）。记录只可增加，不可删除，但是可以通过修改属性值来更改其具体的意义。比方说可以定义当属性值为0的时候这条记录就是无效记录，当属性值为2的时候此记录记录的是程序设置参数等。

最后devive.php 部分是有关于同步的。有`GenerateDeviceId Now UpdateMainKey Pull GetDeviceInfo Pull PullOk Check`这些函数。其储存信息包涵设备识别码，设备所属用户，设备最后同步时间和设备的主要属性（MainKey，一个Int，完全可以自定义，设计时考虑的是指向Favor中的一个具体记录用于储存设备的设置信息等等）。具体的使用方法在下面介绍。

####同步说明：

对于一个程序，他的同步部分应该遵循下述步骤。

第一次同步：

1. 检查登录状态，未登录则进行登录操作。
2. 使用GenerateDeviceId函数获取设备识别码。为64位char字符串。以下的所有操作都需要提交此识别码。
3. 使用Pull函数获取所有记录。这时候应该记录获取记录的时间（即Result中的Now），假设为T。
4. 与本地的数据进行合并，并处理争议，并整理出需要提交给服务器的修改。
5. 使用Add和Modify函数对服务器的记录进行修改。此时需要提交获取记录的时间T。
6. 使用PullOk告诉服务器同步完成，此时也需提交获取记录的时间T。

增量同步：

1. 检查登录状态并登陆。
2. 使用Pull函数获取自上回同步之后有过修改的记录。提交设备识别码，并储存返回的时间T。
3. 数据合并，处理争议，整理出需要的更改。
4. 使用Add和Modify函数提交更改，同样的需要同时提交时间T。
5. 使用PullOk告诉服务器同步完成，同上。

程序可以对同步的数据进行校验。使用Check函数，服务器会获取用户的所有记录，然后将所有记录的CheckCode按照记录Id增序连接为一个字符串。返回此字符串的SHA1值。客户端可以以此进行校验。不过不建议频繁的使用此操作。

####CloudEmoticion 项目如何使用此程序

可以说这个服务器是一个通用的记录，同步服务器。所以具体的数据格式需要 我们的ლ(K◡Tლ)进行定义。

我在此做出以下定义：

1. Device的MainKey值为Favor表中的一条属于该用户的记录的ID值。
2. 记录的IfLove值 有以下意义： 
	
	0. 用户取消了对此颜文字的收藏
	1. 用户收藏了此颜文字
	2. 此条记录为Android应用的设置值。


####客户端本地处理异常举例

这里假设客户端储存数据结构是和服务器一致，即如下：

	| Id | UserId | Value | Addon | IfLove | LastModified | CheckCode | 
	
的数据表。

现在有以下记录：
	
	| 5 | 2 | KT | ... | 1 | 2000-00-00 00:00:00.000000 | An8s |
	| 6 | 2 | LT | ... | 1 | 2000-00-00 00:00:00.000000 | K3Ni |
	| 7 | 2 | MT | ... | 1 | 2000-00-00 00:00:00.000000 | K12i |
	| 9 | 2 | NT | ... | 1 | 2000-00-00 00:00:00.000000 | As8t |
	
用户进行了一些操作。

	如果用户修改了一条记录（比方说将其标记为取消收藏），则将此记录的CheckCode修改为 `*`.
	如果用户增加了一条记录，则在记录最后新增加记录，并将CheckCode修改为 `+`。
	
例如，用户取消了Id为5,6的记录的收藏，增加了一条新的记录，数据表变为：

	| 5 | 2 | KT | ... | 0 | 2000-00-00 00:00:00.000000 | *    |
	| 6 | 2 | LT | ... | 0 | 2000-00-00 00:00:00.000000 | *    |
	| 7 | 2 | MT | ... | 1 | 2000-00-00 00:00:00.000000 | K12i |
	| 9 | 2 | NT | ... | 1 | 2000-00-00 00:00:00.000000 | As8t |
	| 10| 2 | OT | ... | 1 | 2001-00-00 00:00:00.000000 | +    |
	
然后客户端请求同步，Pull获得的数据如下：

	| 6 | 2 | LT | ... | 1 | 2000-00-00 00:00:00.000000 | 8Km0 |
	| 7 | 2 | MT | ... | 0 | 2000-00-00 00:00:00.000000 | 08Jm |
	| 10| 2 | PT | ... | 1 | 2001-00-00 00:00:00.000000 | 5kO4 |
	| 13| 2 | QT | ... | 1 | 2001-00-00 00:00:00.000000 | se3V |
	
逐条处理Pull获取的数据。

	处理记录6,发现其在本地有过修改（CheckCode为 `*`), 则表明这里有冲突，按照保留数据的原则，将本地记录6复制一份，认为是新增加的记录，同时将pull得到的6的记录覆写入本地。
	处理记录7,发现起在本地没有修改，则直接覆写。
	处理记录10,发现其在本地对应的记录标记为`+`，表明是新加记录，将本地记录移动到最后，同时将pull获得的记录10覆写。
	处理记录13,没有对应记录，则新增加记录。

处理完成结果为：

	| 5 | 2 | KT | ... | 1 | 2000-00-00 00:00:00.000000 | *    |
	| 6 | 2 | LT | ... | 1 | 2000-00-00 00:00:00.000000 | 8Km0 |
	| 7 | 2 | MT | ... | 0 | 2000-00-00 00:00:00.000000 | 08Jm |
	| 9 | 2 | NT | ... | 1 | 2000-00-00 00:00:00.000000 | As8t |
	| 10| 2 | PT | ... | 1 | 2001-00-00 00:00:00.000000 | 5kO4 |
	| 11| 2 | LT | ... | 0 | 2000-00-00 00:00:00.000000 | +    |
	| 12| 2 | OT | ... | 1 | 2001-00-00 00:00:00.000000 | +    |
	| 13| 2 | QT | ... | 1 | 2001-00-00 00:00:00.000000 | se3V |
	
然后向服务器推送修改。遍历本地记录，处理CheckCode为`+`或者`*`的记录，向服务器提交更改，并根据返回数据更新本地数据库。处理完结果为：

	| 5 | 2 | KT | ... | 1 | 2002-00-00 00:00:00.000000 | 9kuE |
	| 6 | 2 | LT | ... | 1 | 2000-00-00 00:00:00.000000 | 8Km0 |
	| 7 | 2 | MT | ... | 0 | 2000-00-00 00:00:00.000000 | 08Jm |
	| 9 | 2 | NT | ... | 1 | 2000-00-00 00:00:00.000000 | As8t |
	| 10| 2 | PT | ... | 1 | 2001-00-00 00:00:00.000000 | 5kO4 |
	| 13| 2 | QT | ... | 1 | 2001-00-00 00:00:00.000000 | se3V |
	| 18| 2 | LT | ... | 0 | 2002-00-00 00:00:00.000000 | tk80 |
	| 19| 2 | OT | ... | 1 | 2002-00-00 00:00:00.000000 | KlIp |

不出意外的话同步就完毕了。

这里可能出现的问题为：

	设备A于 00：00 发起pull请求
	设备B于 00：01 发起pull请求
	设备A于 00：02 本地处理完，并增加两条记录。发送PullOk请求。
	设备B于 00：03 本地处理完。发送PullOk请求。

这时候会出现 设备B不能得到设备A新增加或者修改的记录。所以客户端应该考虑使用Check和CheckList不定期的校验数据库.













