@echo off
@title LoginServer
set CLASSPATH=.;dist\*;
java -server -Xmx2048m -Xrunjdwp:transport=dt_socket,address=9003,server=y,suspend=n -Dwz="wz/" server.Server
pause