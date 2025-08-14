# ========================================
# Script PowerShell - Thiet lap co so du lieu
# He thong Quan ly Khuyen mai Rap Chieu Phim
# ========================================

Write-Host "========================================" -ForegroundColor Green
Write-Host "    Thiet lap co so du lieu - He thong Quan ly Khuyen mai Rap Chieu Phim" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""

# Kiem tra MySQL co duoc cai dat khong
try {
    $mysqlVersion = mysql --version 2>$null
    if ($mysqlVersion) {
        Write-Host "✓ MySQL da duoc cai dat: $mysqlVersion" -ForegroundColor Green
    }
} catch {
    Write-Host "✗ MySQL chua duoc cai dat hoac khong co trong PATH" -ForegroundColor Red
    Write-Host "Hay cai dat MySQL truoc va dam bao lenh mysql co the su dung" -ForegroundColor Yellow
    Read-Host "Nhan Enter de thoat"
    exit 1
}

Write-Host ""
Write-Host "Hay dam bao dich vu MySQL dang chay..." -ForegroundColor Yellow
Write-Host ""

# Lay thong tin nhap vao tu nguoi dung
$mysqlUser = Read-Host "Nhap ten nguoi dung MySQL (mac dinh: root)"
if ([string]::IsNullOrEmpty($mysqlUser)) {
    $mysqlUser = "root"
}

$mysqlPassword = Read-Host "Nhap mat khau MySQL" -AsSecureString
$mysqlPasswordPlain = [Runtime.InteropServices.Marshal]::PtrToStringAuto([Runtime.InteropServices.Marshal]::SecureStringToBSTR($mysqlPassword))

Write-Host ""
Write-Host "Dang tao co so du lieu va bang..." -ForegroundColor Yellow
Write-Host ""

# Tao file mat khau tam thoi
$tempPasswordFile = [System.IO.Path]::GetTempFileName()
try {
    # Ghi mat khau vao file tam thoi
    $mysqlPasswordPlain | Out-File -FilePath $tempPasswordFile -Encoding ASCII
    
    # Thuc thi script SQL
    $result = mysql -u$mysqlUser -p$mysqlPasswordPlain < quick_setup.sql 2>&1
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host ""
        Write-Host "========================================" -ForegroundColor Green
        Write-Host "    Thiet lap co so du lieu thanh cong!" -ForegroundColor Green
        Write-Host "========================================" -ForegroundColor Green
        Write-Host ""
        Write-Host "Ten co so du lieu: movie_theater" -ForegroundColor Cyan
        Write-Host "Ten bang: promotions" -ForegroundColor Cyan
        Write-Host "Cong: 3306" -ForegroundColor Cyan
        Write-Host ""
        Write-Host "Bay gio co the chay start.bat de khoi dong ung dung" -ForegroundColor Green
        Write-Host ""
    } else {
        Write-Host ""
        Write-Host "========================================" -ForegroundColor Red
        Write-Host "    Thiet lap co so du lieu that bai!" -ForegroundColor Red
        Write-Host "========================================" -ForegroundColor Red
        Write-Host ""
        Write-Host "Thong tin loi:" -ForegroundColor Red
        Write-Host $result -ForegroundColor Red
        Write-Host ""
        Write-Host "Hay kiem tra:" -ForegroundColor Yellow
        Write-Host "1. Dich vu MySQL co dang chay khong" -ForegroundColor Yellow
        Write-Host "2. Ten nguoi dung va mat khau co chinh xac khong" -ForegroundColor Yellow
        Write-Host "3. MySQL co duoc cai dat khong" -ForegroundColor Yellow
        Write-Host ""
    }
} catch {
    Write-Host "Xay ra loi trong qua trinh thuc thi: $($_.Exception.Message)" -ForegroundColor Red
} finally {
    # Xoa file tam thoi
    if (Test-Path $tempPasswordFile) {
        Remove-Item $tempPasswordFile -Force
    }
}

Write-Host "Nhan Enter de thoat..."
Read-Host
