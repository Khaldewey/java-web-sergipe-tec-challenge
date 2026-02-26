@echo off
echo Iniciando Empresa Web...

cd apache-tomcat-10.1.52\bin
call .\startup.bat

timeout /t 5

start http://localhost:8080/empresa-web/pagina-principal

echo Sistema iniciado!
pause