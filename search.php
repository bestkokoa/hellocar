<head>

<meta http-equiv = "content-Type" content = "text/html" charset = "utf-8">
</head> 
<!-- �ѱ� ����(DB�� utf-8_unicode_ci�� ������ּ���)�� ���� �ҽ� -->

<?

$connect = mysql_connect("127.0.0.1", "root", "apmsetup");

mysql_selectdb("mydb");

mysql_query("set names utf8"); 
//���� ���������� utf8�� �����ϱ� ���� �ҽ�


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
	 //�������� ���� ��� xml���� �����ϱ� ���� �ϱ� ���ؼ� node�� ����
	
	$xmlcode .= "<goalLoc>$goalLoc</goalLoc>\n";

	$xmlcode .= "<routeLoc>$routeLoc</routeLoc>\n";

	$xmlcode .= "<no>$no</no>\n";

	$xmlcode .= "<title>$title</title>\n";

	$xmlcode .= "<startTime>$startTime</startTime>\n";

	$xmlcode .= "<regTime>$regTime</regTime>\n";

	$xmlcode .= "</node>\n";

}



$dir = "C:/APM_Setup/htdocs/hellocar";
	 //������ ���� ���丮
$filename = $dir."/searchresult.xml";
	 //���� �̸�

file_put_contents($filename, $xmlcode);

?>