@echo off
echo ========================================
echo    Thiet lap co so du lieu - He thong Quan ly Khuyen mai Rap Chieu Phim
echo ========================================
echo.

echo Hay dam bao dich vu MySQL dang chay...
echo.

echo Nhap ten nguoi dung MySQL (mac dinh: root):
set /p mysql_user=root

echo Nhap mat khau MySQL:
set /p mysql_password=

echo.
echo Dang tao co so du lieu va bang...
echo.

REM Su dung script thiet lap nhanh
mysql -u%mysql_user% -p%mysql_password% < quick_setup.sql

if %errorlevel% equ 0 (
    echo.
    echo ========================================
    echo    Thiet lap co so du lieu thanh cong!
    echo ========================================
    echo.
    echo Ten co so du lieu: movie_theater
    echo Ten bang: promotions
    echo Cong: 3306
    echo.
    echo Bay gio co the chay start.bat de khoi dong ung dung
    echo.
) else (
    echo.
    echo ========================================
    echo    Thiet lap co so du lieu that bai!
    echo ========================================
    echo.
    echo Hay kiem tra:
    echo 1. Dich vu MySQL co dang chay khong
    echo 2. Ten nguoi dung va mat khau co chinh xac khong
    echo 3. MySQL co duoc cai dat khong
    echo.
)

echo Nhan phim bat ky de thoat...
pause > nul
