var tour = new Tour({
        storage : false
    });

    tour.addSteps([
      {
        element: ".paso1",
        placement: "top",
        backdrop: true,
        title: "Rellena el nombre",
        content: "El nombre del evento es el texto que se mostrará en las pantallas con el evento. Este campo es obligatorio."
      },
      {
        element: ".paso2",
        placement: "top",
        title: "Selecciona la prioridad",
        content: "La imagen que tenga el valor númerico más bajo, será la que aparezca primero y tenga más importancia."
      },
      {
        element: ".paso3",
        placement: "top",
        backdrop: true,
        title: "Indicar la fecha ",
        content: "Debes introducir la fecha del evento para que aparezca en la agenda."
      },
      
      {
        element: ".paso4",
        placement: "top",
        backdrop: true,
        title: "Añadir imágenes",
        content: "Elige la imagen, puedes arrastrarla y soltarla directamente al espacio cuadrado o pinchando en el botón 'seleccionar imagen'.\n\
No es obligatorio, pero en caso de poner una imagen aparecerá encima de la descripción."
      },
       {
        element: ".paso5",
        placement: "top",
        backdrop: true,
        title: "Guardar evento",
        content: "Y por último solo queda hacer click en el botón guardar. Con este último paso, se mostrará el evento en la agenda."
      }

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









