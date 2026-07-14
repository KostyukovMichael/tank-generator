@echo off
chcp 65001 > nul

where docker >nul 2>nul
if %errorlevel% neq 0 (
  echo Ошибка: Docker не найден в PATH или не запущен.
  echo Пожалуйста, запустите Docker Desktop и попробуйте снова.
  pause
  exit /b 1
)

echo  Запуск нагрузочного теста Yandex.Tank

docker compose up

pause