@echo off
REM Chemin vers l'exécutable Java, A modifier en fonction du poste (Java n'est pas déclaré dans mes variables d'environnement et ca peut arriver sur un autre poste donc whatever)
set JAVA_EXE="C:\Users\IAN78\.jdks\openjdk-19.0.2-7\bin\java.exe"

REM Chemin vers le fichier .class de WorkerSocket
set CLASS_PATH="C:\Users\IAN78\Desktop\Dufaud\out\production\Dufaud"

REM Ports sur lesquels les workers vont écouter
set PORT_1=25545
set PORT_2=25546
set PORT_3=25547
set PORT_4=25548
set PORT_5=25549
set PORT_6=25550
set PORT_7=25551
set PORT_8=25552
set PORT_9=25553
set PORT_10=25554
set PORT_11=25555
set PORT_12=25556

REM Lancer les workers sur des ports différents
start "Worker 1" %JAVA_EXE% -cp %CLASS_PATH% WorkerSocket %PORT_1%
start "Worker 2" %JAVA_EXE% -cp %CLASS_PATH% WorkerSocket %PORT_2%
start "Worker 3" %JAVA_EXE% -cp %CLASS_PATH% WorkerSocket %PORT_3%
start "Worker 4" %JAVA_EXE% -cp %CLASS_PATH% WorkerSocket %PORT_4%
start "Worker 5" %JAVA_EXE% -cp %CLASS_PATH% WorkerSocket %PORT_5%
start "Worker 6" %JAVA_EXE% -cp %CLASS_PATH% WorkerSocket %PORT_6%
start "Worker 7" %JAVA_EXE% -cp %CLASS_PATH% WorkerSocket %PORT_7%
start "Worker 8" %JAVA_EXE% -cp %CLASS_PATH% WorkerSocket %PORT_8%
start "Worker 9" %JAVA_EXE% -cp %CLASS_PATH% WorkerSocket %PORT_9%
start "Worker 10" %JAVA_EXE% -cp %CLASS_PATH% WorkerSocket %PORT_10%
start "Worker 11" %JAVA_EXE% -cp %CLASS_PATH% WorkerSocket %PORT_11%
start "Worker 12" %JAVA_EXE% -cp %CLASS_PATH% WorkerSocket %PORT_12%

echo Tous les workers ont été lancés.
pause