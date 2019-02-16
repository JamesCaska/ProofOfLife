if (Test-Path .\ada.zip) 
{
  Remove-Item .\ada.zip
}
jvm-gnatcompile -c ProofOfLife.adb
Compress-Archive -Path *.adb,*.ads,*.class,*.java -DestinationPath ada.zip 