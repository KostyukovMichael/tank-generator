#!/bin/bash

if ! command -v docker &> /dev/null; then
    echo "Ошибка: Docker не найден. Установите Docker для запуска теста."
    exit 1
fi

echo " Запуск нагрузочного теста Yandex.Tank"

docker compose up