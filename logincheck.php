<head>
<meta http-equiv = "content-Type" content = "text/html" charset = "utf-8">
</head> <!-- �ѱ� ����(DB�� utf-8_unicode_ci�� ������ּ���)�� ���� �ҽ� -->
<?
$connect = mysql_connect("127.0.0.1", "root", "apmsetup"); //���� ���������� utf8�� �����ϱ� ���� �ҽ�
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

$dir = "C:/APM_Setup/htdocs/hellocar"; //������ ���� ���丮
$filename = $dir."/loginresult.xml"; //���� �̸�
	
file_put_contents($filename, $xmlcode);


?>