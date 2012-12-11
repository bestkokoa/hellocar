<head>
<meta http-equiv = "content-Type" content = "text/html" charset = "utf-8">
</head> <!-- 한글 지원(DB는 utf-8_unicode_ci로 만들어주세요)을 위한 소스 -->
<?
$connect = mysql_connect("127.0.0.1", "root", "apmsetup"); //위와 마찬가지로 utf8을 지원하기 위한 소스
mysql_selectdb("mydb");
mysql_query("set names utf8");

$ID = $_REQUEST[ID];
$TITLE = $_REQUEST[TITLE];
$STARTTIME = $_REQUEST[STARTTIME];
$STARTLOC = $_REQUEST[STARTLOC];
$ROUTELOC = $_REQUEST[ROUTELOC];
$GOALLOC = $_REQUEST[GOALLOC];
$TEXT = $_REQUEST[TEXT];

$qry = "Select max(no) from board";
$result = mysql_query($qry);
$row = mysql_fetch_array($result);
$temp = $row[0] + 1;

$qry = "INSERT INTO `mydb`.`board` (
 `owner` ,
 `no` ,
 `title` ,
 `text` ,
 `regTime` ,
 `startTime` ,
 `startLoc` ,
 `routeLoc` ,
 `goalLoc` ,
 `busy` ,
 `maxRide` ,
 `luggage` ,
 `cigar` ,
 `pet` ,
 `food` ,
 `empty` 
)
VALUES (
 '$ID','$temp','$TITLE','$TEXT', NOW() ,'$STARTTIME','$STARTLOC','$ROUTELOC','$GOALLOC','$DEFAULT','0','0','0','0','0',NULL); ";

$result = mysql_query($qry);

$xmlcode = "<?xml version = \"1.0\" encoding = \"utf-8\" ?>\n";
$xmlcode .= "<result>$result</result>\n";

$dir = "C:/APM_Setup/htdocs/hellocar"; //파일이 있을 디렉토리
$filename = $dir."/writeresult.xml"; //파일 이름
	
file_put_contents($filename, $xmlcode);


?>