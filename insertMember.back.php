<head>
<meta http-equiv = "content-Type" content = "text/html" charset = "utf-8">
</head> <!-- �ѱ� ����(DB�� utf-8_unicode_ci�� ������ּ���)�� ���� �ҽ� -->
<?
$connect = mysql_connect("127.0.0.1", "root", "apmsetup"); //���� ���������� utf8�� �����ϱ� ���� �ҽ�
mysql_selectdb("mydb");
mysql_query("set names utf8");

$ID = $_REQUEST[ID];
$PW = $_REQUEST[PW];
$UNIV = $_REQUEST[UNIV];
$MAJOR = $_REQUEST[MAJOR];
$NAME = $_REQUEST[NAME];
$CELL = $_REQUEST[CELL];


$qry = "INSERT INTO `mydb`.`member` (
 `id` ,
 `password` ,
 `univ` ,
 `major` ,
 `sex` ,
 `name` ,
 `cellphone` ,
 `rely` ,
 `cnt` ,
 `carRely` ,
 `carCnt` ,
 `rate` ,
 `stdCert` ,
 `cellCert` ,
 `publicCert` ,
 `nameCert` ,
 `no` 
)
VALUES (
 '$ID','$PW','$UNIV','$MAJOR','','$NAME','$CELL','','','','','','0','0','0','0',''
); ";

$result = mysql_query($qry);

$xmlcode = "<?xml version = \"1.0\" encoding = \"utf-8\" ?>\n";
$xmlcode .= "<result>$result</result>\n";

$dir = "C:/APM_Setup/htdocs/Books"; //������ ���� ���丮
$filename = $dir."/insertresult.xml"; //���� �̸�
	
file_put_contents($filename, $xmlcode);


?>