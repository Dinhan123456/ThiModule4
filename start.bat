@echo off
echo ========================================
echo    Quan ly Khuyen mai Rap Chieu Phim
echo ========================================
echo.
echo Dang khoi dong ung dung...
echo.
echo Luu y: Can co MySQL dang chay va co so du lieu 'movie_theater'
echo.
echo Nhan phim bat ky de tiep tuc...
pause > nul

echo.
echo Dang chay ung dung Spring Boot...
echo.
echo Truy cap: http://localhost:8080
echo.
echo Nhan Ctrl+C de dung ung dung
echo.

gradlew bootRun

echo.
echo Ung dung da dung.
pause
