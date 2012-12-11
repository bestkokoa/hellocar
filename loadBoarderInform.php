<head>

<meta http-equiv = "content-Type" content = "text/html" charset = "utf-8">
</head> 
<!-- 한글 지원(DB는 utf-8_unicode_ci로 만들어주세요)을 위한 소스 -->

<?

$connect = mysql_connect("127.0.0.1", "root", "apmsetup");

mysql_selectdb("mydb");

mysql_query("set names utf8"); 
//위와 마찬가지로 utf8을 지원하기 위한 소스

$NO = $_REQUEST[NO];


$qry = "select * from board where no='$NO'";

$result = mysql_query($qry);


$xmlcode = "<?xml version = \"1.0\" encoding = \"utf-8\"?>\n";



$obj = mysql_fetch_object($result);
$goalLoc = $obj->goalLoc;

$startLoc = $obj->startLoc;
$routeLoc = $obj->routeLoc;


$no = $obj->no;


$title = $obj->title;


$startTime = $obj->startTime;

$regTime = $obj->regTime;

$Text = $obj->Text;
$owner = $obj->owner;

$MaxRide = $obj->maxRide;
$xmlcode .= "<node>\n";
$xmlcode .= "<goalLoc>$goalLoc</goalLoc>\n";

$xmlcode .= "<routeLoc>$routeLoc</routeLoc>\n";

$xmlcode .= "<no>$no</no>\n";

$xmlcode .= "<title>$title</title>\n";

$xmlcode .= "<startTime>$startTime</startTime>\n";

$xmlcode .= "<regTime>$regTime</regTime>\n";
$xmlcode .= "<owner>$owner</owner>\n";
$xmlcode .= "<startLoc>$startLoc</startLoc>\n";
$xmlcode .= "<MaxRide>$MaxRide</MaxRide>\n";
$xmlcode .= "<Text>$Text</Text>\n";


$qry = "select * from member where id='$owner' ";
$result = mysql_query($qry);

$obj = mysql_fetch_object($result);

$name = $obj->name;
$cellphone = $obj->cellphone;
$Univ = $obj->univ;
$Major = $obj->major;
$xmlcode .= "<name>$name</name>\n";
$xmlcode .= "<cellphone>$cellphone</cellphone>\n";
$xmlcode .= "<Univ>$Univ</Univ>\n";
$xmlcode .= "<Major>$Major</Major>\n";
$xmlcode .= "</node>\n";



$dir = "C:/APM_Setup/htdocs/hellocar";
	 //파일이 있을 디렉토리
$filename = $dir."/loadBoarderInformresult.xml";
	 //파일 이름

file_put_contents($filename, $xmlcode);

?>