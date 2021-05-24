/*
"use strict" //строгий режим

document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('form'); // перехватывает id всей формы
    form.addEventListener('submit', formSend); //вешаем событие 

    async function formSend(e) {
        e.preventDefault();

        let error = formValidate(form); // переменная для ошибок из form


        // отправка формы
        let formData = new FormData(form);




        if (error === 0) {
            form.classList.add('_sending');
            let response = await fetch('sendmail.php', {  // ждём выполнения отправки методом post
                method: 'POST',
                body: formData  // тело что перекидываем
            });
            if (response.ok) {  // проверяем отправилась ли форма
                let result = await response.json(); // ждем ответа json 
                alert(result.message); //выводим ошибку если такова есть
                form.reset(); // обнуляем фомру
            } else {
                alert("Error!");
            }
        } else {
            alert('Please, fill in the required fields!');
        }
    }
    // отправили форму


    function formValidate(e) {
        let error = 0;
        let formReq = document.querySelectorAll('._req'); //для все обязательных полей

        for (let index = 0; index < formReq.length; index++) { // прогнать все классы на _req
            const input = formReq[index];
            formRemoveError(input);

            if (input.classList.contains('_email')) {
                if (emailTest(input)) {
                    formAddError(input);
                    error++;
                }
            }
            else {
                if (input.value === '') { // если строка пустая то вешаем ошибку
                    formAddError(input);
                    error++;
                }
            }



        }

    }
    function formAddError(input) {  // добавляет клас с ошибкой 
        input.parentElement.classList.add('_error');
        input.classList.add('_error');
    }
    function formRemoveError(input) { // удаляет класс с ошибкой
        input.parentElement.classList.remove('_error');
        input.classList.remove('_error');
    }
    function emailTest(input) {
        return !/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,8})+$/.test(input.value); //регулярное выражение на проверку мыла
    }
});*/
