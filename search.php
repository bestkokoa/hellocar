<head>

<meta http-equiv = "content-Type" content = "text/html" charset = "utf-8">
</head> 
<!-- 한글 지원(DB는 utf-8_unicode_ci로 만들어주세요)을 위한 소스 -->

<?

$connect = mysql_connect("127.0.0.1", "root", "apmsetup");

mysql_selectdb("mydb");

mysql_query("set names utf8"); 
//위와 마찬가지로 utf8을 지원하기 위한 소스


$qry = "select * from board order by startTime DESC LIMIT 0, 30";

$result = mysql_query($qry);


$xmlcode = "<?xml version = \"1.0\" encoding = \"utf-8\"?>\n";


while($obj = mysql_fetch_object($result))

{
	
	$goalLoc = $obj->goalLoc;

	$routeLoc = $obj->routeLoc;


	$no = $obj->no;


	$title = $obj->title;


	$startTime = $obj->startTime;

	$regTime = $obj->regTime;


	$xmlcode .= "<node>\n";
	 //여러개가 나올 경우 xml에서 구분하기 쉽게 하기 위해서 node로 구분
	
	$xmlcode .= "<goalLoc>$goalLoc</goalLoc>\n";

	$xmlcode .= "<routeLoc>$routeLoc</routeLoc>\n";

	$xmlcode .= "<no>$no</no>\n";

	$xmlcode .= "<title>$title</title>\n";

	$xmlcode .= "<startTime>$startTime</startTime>\n";

	$xmlcode .= "<regTime>$regTime</regTime>\n";

	$xmlcode .= "</node>\n";

}



$dir = "C:/APM_Setup/htdocs/hellocar";
	 //파일이 있을 디렉토리
$filename = $dir."/searchresult.xml";
	 //파일 이름

file_put_contents($filename, $xmlcode);

?>