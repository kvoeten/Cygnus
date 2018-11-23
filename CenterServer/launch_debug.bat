@echo off
@title CenterServer
set CLASSPATH=.;dist\*;
java -server -Xmx8048m -Xrunjdwp:transport=dt_socket,address=9004,server=y,suspend=n -Dwz="wz/" server.Start
pause