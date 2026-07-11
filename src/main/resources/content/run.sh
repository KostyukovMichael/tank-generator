#!/bin/bash

if ! command -v docker &> /dev/null; then
    echo "Ошибка: Docker не найден. Установите Docker для запуска теста."
    exit 1
fi

echo " Запуск нагрузочного теста Yandex.Tank"

docker run --rm -v "$(pwd)":/var/loadtest -it yandex/yandex-tank