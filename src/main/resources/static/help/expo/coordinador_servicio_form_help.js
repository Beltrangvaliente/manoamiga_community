var tour = new Tour({
    storage: false
});

tour.addSteps([
    {
        element: ".paso1",
        placement: "top",
        backdrop: true,
        title: "Nombre del servicio",
        content: "Pondremos el nombre del servicio con el cual será identificado."
    },
    {
        element: ".paso2",
        placement: "top",
         backdrop: true,
        title: "Seleccionar pantalla",
        content: "Selecionaremos las pantallas en las que deseemos visualizar el servico.Las opciones son:'1'(una pantalla),'2'(en la segunda pantalla) o 'todas'(en ambas pantallas)."
    },
    {
        element: ".paso3",
        placement: "top",
        backdrop: true,
        title: "Seleccionar Activo ",
        content: "tenemos la opción 'si', en caso que desee mostrar el servico una vez le demos clck al botón de 'Guardar', o la opción 'No' si es que no desea mostrar el servicio."
    },
    {
        element: ".paso4",
        placement: "top",
        backdrop: true,
        title: "Opciones para subir una imagen",
        content: "En este apartado encontraremos dos formas de subir una imagen.Una de ellas es 'Arrastra y suelta imágenes' y la segunda 'Selecciona una imagen'."
    },

    {
        element: ".paso5",
        placement: "top",
        backdrop: true,
        title: "Arrastrar y solatar imagen",
        content: "Elige la imagen, puedes arrastrarla y soltarla directamente al espacio cuadrado o pinchando en el botón 'seleccionar imagen'.\n\
No es obligatorio, pero en caso de poner una imagen aparecerá encima de la descripción con un tamaño máximo de 20 Mb . Además, la resolución recomendada es de 1920x1080 para vista horizontal y 1080x1920 para vista vertical."
    },
    {
        element: ".paso6",
        placement: "top",
        backdrop: true,
        title: "Seleccionar imagen",
        content: "Esta opción nos permite buscar una imagen que se encuentre almacenada en nuestro ordenador."
    },
    {
        element: ".paso7",
        placement: "top",
        backdrop: true,
        title: "Guardar servicio",
        content: "Y por último solo queda hacer click en el botón guardar. Con este último paso, se guardará el servicio."
    }
    

]);

$(document).ready(function () {
    $(".verTutorial").html("<i class='fas fa-question-circle fa-2x'></i>")
    var primera = 0;
    $(".verTutorial").click(function () {
        if (primera == 0) {
            tour.init();
            tour.start();
            primera = 1;
        } else {
            tour.restart();
        }
    })
});


