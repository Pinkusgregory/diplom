$(document).ready(function () {  // инициализация jquery
    $('.slider').slick({
        arrows: true, //стрелки
        dots: true, //точки снизу
        adaptiveHeight: true, // адаптив высоты для слайдов
        slidesToShow: 3,     //сколько нужно сразу отобразить слайдов
        slidesToScroll: 2,    //колличество прокрутки слайдов за раз
        speed: 800,   // скорость
        easing: 'linear',   //тип анимации
        infinite: true,  //бесконечный слайд
        initialSlide: 0, //  с какого слайда начинается
        autoplay: true, //автоплей слайдов
        autoplaySpeed: 1500, //скорость проктуки слайдов на автоплее
        pauseOnFocus: true, //остановка при нажатии
        pauseOnHover: true,
        pauseOnDotsHover: true,
        draggable: true, //перетаскивать слайды свапом руками
        swipe: true, //для телефонов предыдущая функция
        touchThreshold: 5, //расстояния для свапа
        touchMove: true, //включает все возмоности тачскрина
        waitForAnimate: true, //следующий клик срабатывания на стрелку или кнопку пока слайд не загрузится не включится
        centerMode: true, //центрирует слайд 
        variableWidth: true,   //ширина слайда сам определяет padding между слайдами
        row: 1, //ряды в слайде
        slidesPerRow: 1, // сколько слайдов в ряду
        vertical: false, // вертикальный слайд
        vericalSwiping: false, //свайп вертикально
        fade: false, // для большого слайда оыбчно используется для двойнго сладера
        responsive: [  // адаптив для слайдера    
            {
                breakpoint: 768,
                settings: {
                    slidesToShow: 2
                }
            }
        ]
        //appendArrows:$('nameofclass') добавить стрелки в отдельный класс div 
        //appendDots:$('nameofclass') добавить точки в отдельный класс div 

    });
});

// методы слайдера
// $('.slider').slick('goTo', 3);  //перейти к слайду номер 4

// $('.link').slick(function (event) { // при нажатии на ссылку переход к слайду № 4
//     $('.slider').slick('goTo', 3);
// });

// $('.link').slick(function (event) { // при нажатии на ссылку переход к предыдущему слайду
//     $('.slider').slick('slickPrev');
// });
// $('.link').slick(function (event) { // при нажатии на ссылку переход к следущему слайду
//     $('.slider').slick('slickNext');
// });

// $('.link').slick(function (event) { // при нажатии на ссылку добавить блок в слайдер! обычно используется для ajax запросов
//     $('.slider').slick('slickAdd','<div class="newslide">123</div>');
// });

// $('.link').slick(function (event) { // при нажатии на ссылку удалить блок в слайдере! 0 1 2 3 номер слайда, обычно используется для ajax запросов
//     $('.slider').slick('slickRemove', 0);
// });

// var s=$('.slider').slick('slickGetOption','slidesToShow'); console.log(s); получаем переменую из слайдера удобно для использования  ('slickSetOption','slidesToShow',2) изменить значение переменной