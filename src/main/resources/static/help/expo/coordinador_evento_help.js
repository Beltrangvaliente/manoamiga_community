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
        backdrop: true,
        title: "Indica la fecha",
        content: "Debes introducir la fecha del evento para que aparezca en la agenda."
      },
      {
        element: ".paso3",
        placement: "top",
        backdrop: true,
        title: "Indica la hora",
        content: "Debes introducir la fecha del evento para que aparezca en la agenda."
      },
      {
        element: ".paso4",
        placement: "bottom",
        backdrop: true,
        title: "Selecciona estado",
        content: "Aquí tienes que especificar si se acepta o rechaza el evento. Por defecto queda en 'Pendiente'."
      },
      {
        element: ".paso5",
        placement: "bottom",
        backdrop: true,
        title: "Busca el área",
        content: "Elige el área donde se va a realizar el evento para que quede reflejado en la agenda. Es obligatorio."
      },
      {
        element: ".paso6",
        placement: "bottom",
        backdrop: true,
        title: "Busca el espacio",
        content: "Elige el espacio donde se va a realizar el evento para que quede reflejado en la agenda. Es obligatorio."
      },
      
      {
        element: ".paso7",
        placement: "top",
        backdrop: true,
        title: "Añade la descripción",
        content: "Rellenando este campo aparecera la descripción en la agenda."
      },
      {
        element: ".paso8",
        placement: "top",
        backdrop: true,
        title: "Añade imágenes",
        content: "Elige la imagen, puedes arrastrarla y soltarla directamente al espacio cuadrado o pinchando en el botón 'seleccionar imagen'.\n\
No es obligatorio, pero en caso de poner una imagen aparecerá encima de la descripción."
      },
      {
        element: ".paso9",
        placement: "bottom",
        backdrop: true,
        title: "Añade el autor",
        content: "Cuando vayas a editar un evento que un trabajador haya creado, en este campo aparecera el nombre del trabajador."
      },
       {
        element: ".paso10",
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









