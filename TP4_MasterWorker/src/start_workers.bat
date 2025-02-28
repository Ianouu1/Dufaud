@echo off
setlocal enabledelayedexpansion
REM Chemin vers l'exécutable Java, A modifier en fonction du poste (Java n'est pas déclaré dans mes variables d'environnement et ca peut arriver sur un autre poste donc whatever)set JAVA_EXE="C:\Users\IAN78\.jdks\openjdk-19.0.2-7\bin\java.exe"
set JAVA_EXE="C:\Users\IAN78\.jdks\openjdk-19.0.2-7\bin\java.exe"

REM Chemin vers le fichier .class de WorkerSocket
set CLASS_PATH="C:\Users\IAN78\Desktop\Dufaud\out\production\Dufaud"

REM Port de départ
set START_PORT=25545

REM Nombre de workers
set NUM_WORKERS=12

REM Lancer les workers dans une boucle
for /L %%i in (1,1,%NUM_WORKERS%) do (
    set /A PORT=%START_PORT% + %%i - 1
    start "Worker %%i" /B %JAVA_EXE% -cp %CLASS_PATH% WorkerSocket !PORT!
)

echo Tous les workers ont été lancés.
pause