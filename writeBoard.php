<head>
<meta http-equiv = "content-Type" content = "text/html" charset = "utf-8">
</head> <!-- �ѱ� ����(DB�� utf-8_unicode_ci�� ������ּ���)�� ���� �ҽ� -->
<?
$connect = mysql_connect("127.0.0.1", "root", "apmsetup"); //���� ���������� utf8�� �����ϱ� ���� �ҽ�
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

$dir = "C:/APM_Setup/htdocs/hellocar"; //������ ���� ���丮
$filename = $dir."/writeresult.xml"; //���� �̸�
	
file_put_contents($filename, $xmlcode);


?>