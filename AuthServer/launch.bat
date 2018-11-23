@echo off
@title Auth/API Service
set CLASSPATH=.;dist\*
java server.Application
pause