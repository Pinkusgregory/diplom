$(document).ready(function () {
    $('.header__burger').click(function (event) {  // при нажатии на бургер будет выполняться следующее действие
        $('.header__burger,.header__menu').toggleClass('active'); // добавляет класс active для заданных классов
        $('body').toggleClass('lock');
    });
});