<head>
<meta http-equiv = "content-Type" content = "text/html" charset = "utf-8">
</head> <!-- 한글 지원(DB는 utf-8_unicode_ci로 만들어주세요)을 위한 소스 -->
<?
$connect = mysql_connect("127.0.0.1", "root", "apmsetup"); //위와 마찬가지로 utf8을 지원하기 위한 소스
mysql_selectdb("mydb");
mysql_query("set names utf8");

$ID = $_REQUEST[ID];
$PW = $_REQUEST[PW];


$qry = "select count(*) as count from member where id='$ID' and password='$PW'";
$result = mysql_query($qry);
$row = mysql_fetch_array($result);
$cnt = $row['count'];

$xmlcode = "<?xml version = \"1.0\" encoding = \"utf-8\" ?>\n";
$xmlcode .= "<result>$cnt</result>\n";

$dir = "C:/APM_Setup/htdocs/hellocar"; //파일이 있을 디렉토리
$filename = $dir."/loginresult.xml"; //파일 이름
	
file_put_contents($filename, $xmlcode);


?>