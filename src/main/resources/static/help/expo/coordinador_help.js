
    var tour = new Tour({
        storage : false
    });

    tour.addSteps([
      {
        element: ".paso1",
        placement: "top",
        backdrop: true,
        title: "Rellena el nombre",
        content: "Este será el nombre de la plantilla que vayas a crear."
      },
      {
        element: ".paso2",
        placement: "top",
        backdrop: true,
        title: "Selecciona  el número de imágenes",
        content: "Selecciona del uno al tres las imágenes que quieres que haya en la platilla."
      },
      {
        element: ".paso3",
        placement: "bottom",
        backdrop: true,
        title: "Selecciona  el número de textos",
        content: "Selecciona del uno al tres las imágenes que quieres que haya en la platilla."
      },
      {
        element: ".paso4",
        placement: "top",
        backdrop: true,
        title: "Posiciona los elementos",
        content: "Ahora, para posicionar las imagenes y los textos, hay que hacer un primer click, arrastrar hasta la posición deseada y\n\
        hacer un segundo click para soltar."
      },
      {
        element: ".paso5",
        placement: "top",
        backdrop: true,
        title: "Elige un color de fondo",
        content: "Este es opcional."
      },

    ]);

$(document).ready(function(){
    $(".verTutorial").html("<i class='fas fa-question-circle fa-2x'></i>")
    var primera=0;
    $(".verTutorial").click(function(){
        if (primera==0){
            tour.init();                
            tour.start();
            primera=1;
        }else{
            tour.restart();
        }
    })
});





