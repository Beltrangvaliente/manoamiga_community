var tecnara = {

    addControllerParent: function (obj, url) {
        var controller = $(obj).parent().attr("data-controller");
        if (controller != null) {
            url = controller + "/" + url;
        }
        controller = $(obj).parent().parent().attr("data-controller");
        if (controller != null) {
            url = controller + "/" + url;
        }
        controller = $(obj).parent().parent().parent().attr("data-controller");
        if (controller != null) {
            url = controller + "/" + url;
        }
        controller = $(obj).parent().parent().parent().parent().attr("data-controller");
        if (controller != null) {
            url = controller + "/" + url;
        }
        controller = $(obj).parent().parent().parent().parent().parent().attr("data-controller");
        if (controller != null) {
            url = controller + "/" + url;
        }
        return url;
    },

    abrirFormularioNuevo: function (obj) {
        var url = $(obj).attr("data-url");
        if (url == null) {
            url = "form";
        }
        document.location = url;
    },
    guardarAjax: function (url) {
        if ($("#bbVentana form input[type='file']").length == 0) {
            $.ajax({
                url: url,
                type: "post",
                data: $("#bbVentana form").serialize(),
                success: function (res) {
                    var destino = $("#bbVentana form").attr("destino");
                    bootbox.hideAll();
                    tecnara.refrescarTablas();
                    if (destino != null) {
                        $("#" + destino).val(res);
                        tecnara.buscadorIdCargar($("#" + destino));
                    }
                },
                error: function (res) {
                    console.log(res);
                    bootbox.hideAll();
                    tecnara.refrescarTablas();
                }
            });
        } else {
            var fd = new FormData();
            $("#bbVentana form *[name]").each(function (index, obj) {
                if ($(obj).attr("type") != "file") {
                    fd.append($(obj).attr("name"), $(obj).val());
                } else {
                    var files = $(obj)[0].files;
                    fd.append($(obj).attr("name"), files[0]);
                }
            });
            var files = $("#bbVentana form input[type='file']")[0].files;
            $.ajax({
                url: url,
                type: "post",
                data: fd,
                contentType: false,
                processData: false,
                success: function (res) {
                    bootbox.hideAll();
                    tecnara.refrescarTablas();
                },
                error: function (res) {
                    console.log(res);
                    bootbox.hideAll();
                    tecnara.refrescarTablas();
                }
            })
        }
    },
    ejecutarAjax: function (obj) {
        var params = "";
        if ($(obj).attr("data-params") != null && $(obj).attr("data-params") != "") {
            params = "?" + $(obj).attr("data-params") + "=" + $("*[name='" + $(obj).attr("data-params") + "']").val();
        }
        var textoConfirmar = $(obj).attr("data-confirmar");
        if (textoConfirmar == null || textoConfirmar.length == 0) {
            $.ajax({
                url: $(obj).attr("data-url") + params,
                success: function (res) {
                    $("*[data-toggle='table']").bootstrapTable('refresh');
                },
                error: function (res) {
                    alert("No se ha podido hacer la operacion");
                    $("*[data-toggle='table']").bootstrapTable('refresh');
                }
            })
        } else {
            bootbox.confirm({
                message: textoConfirmar,
                callback: function (res) {
                    if (res) {
                        $.ajax({
                            url: $(obj).attr("data-url") + params,
                            success: function (res) {
                                $("*[data-toggle='table']").bootstrapTable('refresh');
                            },
                            error: function (res) {
                                alert("No se ha podido hacer la operacion");
                                $("*[data-toggle='table']").bootstrapTable('refresh');
                            }
                        })
                    }
                }
            });
        }
    },
    ejecutarAjaxConfirmar: function (obj, pregunta) {
        bootbox.confirm({
            message: pregunta,
            callback: function (res) {
                if (res) {
                    tecnara.ejecutarAjax(obj);
                }
            }
        });
    },
    confirmar: function (pregunta, mifunction) {
        bootbox.confirm({
            message: pregunta,
            callback: function (res) {
                if (res) {
                    mifunction();
                }
            }
        });
    },

    abrirFormularioNuevoSinMenu: function (obj) {
        var url = $(obj).attr("data-url");
        if (url == null) {
            url = "form";
        }
        url = tecnara.addControllerParent(obj, url);

        var idTablaPrincipal = $("input[name='id']").val();
        var params = "?idPrincipal=" + idTablaPrincipal;
        var dp = $(obj).attr("data-params");
        if (dp != null) {
            params += "&" + dp + "=" + $("#" + dp).val();
        }

        $.ajax({
            url: url + params,
            success: function (resultado) {

                resultado = resultado.split("<body>")[1].split("</body>")[0];

                bootbox.dialog({
                    message: "<div id='bbVentana'>" + resultado + "</div>",
                    size: "xl",
                });
                var base = tecnara.findBase();
                if ($("#bbVentana #id" + base + ":not([type='hidden'])").parent().attr("data-toggle") != "obligatorio") {
                    $("#bbVentana #id" + base + ":not([type='hidden'])").parent().addClass("d-none");
                } else {
                    $("#bbVentana #id" + base + ":not([type='hidden'])").parent().parent().addClass("d-none");
                }
                $("#bbVentana #id" + base).val(idTablaPrincipal);
                if ($(obj).attr("data-return") != null) {
                    $("#bbVentana form").attr("action", "guardar_ajax");
                    $("#bbVentana form").attr("destino", $(obj).attr("data-return"));
                }

                tecnara.bbPrepararFormulario($("#bbVentana"), url);
                tecnara.detectarDataToggle($("#bbVentana"));
            }
        });
    },
    findBase: function () {
        var data = window.location.pathname.split("/")[3];
        var sep = data.split("_");
        var res = "";
        for (var n = 0; n < sep.length; n++) {
            res += sep[n].substring(0, 1).toUpperCase() + sep[n].substring(1);
        }
        return res;
    },
    bbPrepararFormulario: function (obj, url) {
        $(obj).find("nav").hide();
        $(obj).find(".nav-tabs").hide();
        $(obj).find(".tab-content").hide();
        $(obj).find("*[data-toggle='indice']").hide();
        $(obj).find("*[data-toggle='volver']").hide();
        var urlGuardar = url;
        if (url.endsWith("/form")) {
            urlGuardar = url.substring(0, url.length - 5) + "/guardar";
            if ($("#bbVentana form").attr("destino") != null) {
                urlGuardar += "_ajax";
            }
        }

        $("#bbVentana form button[type='submit']").attr("type", "button").attr("onclick", "tecnara.guardarAjax('" + urlGuardar + "')");

    },
    abrirFormularioMostrarSinMenu: function (obj, id) {
        var url = $(obj).attr("data-url");
        if (url == null) {
            url = "show";
        }
        url = tecnara.addControllerParent(obj, url);
        var idTablaPrincipal = $("#id").val();
        $.ajax({
            url: url + "?id=" + id + "&idPrincipal=" + idTablaPrincipal,
            success: function (resultado) {
                resultado = resultado.split("<body>")[1].split("</body>")[0];

                bootbox.dialog({
                    message: "<div id='bbVentana'>" + resultado + "</div>",
                    size: "xl",
                });
                var base = tecnara.findBase();
                if ($("#bbVentana #id" + base + ":not([type='hidden'])").parent().attr("data-toggle") != "obligatorio") {
                    $("#bbVentana #id" + base + ":not([type='hidden'])").parent().addClass("d-none");
                } else {
                    $("#bbVentana #id" + base + ":not([type='hidden'])").parent().parent().addClass("d-none");
                }
                $("#bbVentana #id" + base).val(idTablaPrincipal);

                tecnara.bbPrepararFormulario($("#bbVentana"), url);
                $("#bbVentana nav").hide();
                $("#bbVentana head").html("");
                tecnara.detectarDataToggle($("#bbVentana"));
            }
        });
    },
    abrirFormularioEditar: function (obj, id) {
        var url = $(obj).attr("data-url");
        if (url == null) {
            url = "form";
        }

        url = tecnara.addControllerParent(obj, url);

        document.location = url + "?id=" + id;
    },
    abrirFormularioMostrar: function (obj, id) {
        var url = $(obj).attr("data-url");
        if (url == null) {
            url = "show";
        }

        url = tecnara.addControllerParent(obj, url);

        document.location = url + "?id=" + id;
    },

    clickSelect: function () {
        $("select[value]").each(function (index, obj) {
            $(obj).find("option[value = " + $(obj).attr("value") + "]").attr("selected", "");
        });
    },
    abrirFormularioEditarSinMenu: function (obj, id) {
        var url = $(obj).attr("data-url");
        if (url == null) {
            url = "form";
        }
        var controller = $(obj).parent().parent().parent().parent().attr("data-controller");
        if (controller != null) {
            url = controller + "/" + url;
        }
        var idTablaPrincipal = $("#id").val();
        $.ajax({
            url: url + "?id=" + id + "&idPrincipal=" + idTablaPrincipal,
            success: function (resultado) {
                resultado = resultado.split("<body>")[1].split("</body>")[0];
                bootbox.dialog({
                    message: "<div id='bbVentana'>" + resultado + "</div>",
                    size: "xl",
                });
                var base = tecnara.findBase();
                if ($("#bbVentana #id" + base + ":not([type='hidden'])").parent().attr("data-toggle") != "obligatorio") {
                    $("#bbVentana #id" + base + ":not([type='hidden'])").parent().addClass("d-none");
                } else {
                    $("#bbVentana #id" + base + ":not([type='hidden'])").parent().parent().addClass("d-none");
                }
                $("#bbVentana #id" + base).val(idTablaPrincipal);

                tecnara.bbPrepararFormulario($("#bbVentana"), url);
                tecnara.detectarDataToggle($("#bbVentana"));
            }
        });
    },
    refrescarTablas: function () {
        $("table[data-toggle='table']").bootstrapTable('refresh');
        event.preventDefault();
    },
    introRefrescarTablas: function () {
        if (event.keyCode === 13) {
            $("table[data-toggle='table']").bootstrapTable('refresh');
        }
    },
    borrarRegistro: function (id) {
        bootbox.confirm({
            message: "Estos datos no se podrán recuperar. ¿Desea borrar?",
            callback: function (res) {
                if (res) {
                    document.location = "borrar?id=" + id;
                }
            }
        });
    },

    logTecnico: function (mensaje) {
        alert("FALLO TECNICO: " + mensaje);
    },
    borrarRegistroSinMenu: function (obj, id) {
        var controller = $(obj).parent().parent().parent().parent().attr("data-controller");
        var url = "borrar?id=" + id;
        if (controller != null) {
            url = controller + "/" + url;
        } else {
            tecnara.logTecnico("En necesario rellenar data-controller en la etiqueta table");
        }
        bootbox.confirm({
            message: "Estos datos no se podrán recuperar. ¿Desea borrar?",
            callback: function (res) {
                if (res) {
                    $.ajax({
                        url: url,
                        success: function (res) {
                            tecnara.refrescarTablas();
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            tecnara.refrescarTablas();
                        }
                    });
                }
            }
        });
    },
    tablaBotones: function (id, row, index) {
        return "<div class='text-nowarp'>" +
                "<button class='btn btn-success btn-sm' onclick='tecnara.abrirFormularioEditar(this, " + row["id"] + ")' > Editar </button> " +
                "<button class='btn btn-danger btn-sm' onclick='tecnara.borrarRegistro(" + row["id"] + ")'> Borrar </button> " +
                "</div>";
    },
    tablaBotonMostrar: function (id, row, index) {
        return "<div<button class='btn btn-success btn-sm' onclick='tecnara.abrirFormularioMostrarSinMenu(this, " + row["id"] + ")' > Consultar </button> ";
    },
    tablaBotonConsulta: function (id, row, index) {
        return "<button class='btn btn-success btn-sm' onclick='tecnara.abrirFormularioMostrar(this, " + row["id"] + ")' > Consultar </button> ";
    },
    tablaBotonConsultaForm: function (id, row, index) {
        return "<button class='btn btn-success btn-sm' onclick='tecnara.abrirFormularioEditar(this, " + row["id"] + ")' > Consultar </button> ";
    },
    tablaBotonesSinMenu: function (id, row, index) {
        return "<button type='button' class='btn btn-success btn-sm' onclick='tecnara.abrirFormularioEditarSinMenu(this, " + row["id"] + ")' > Editar </button> " +
                "<button type='button' class='btn btn-danger btn-sm' onclick='tecnara.borrarRegistroSinMenu(this, " + row["id"] + ")'> Borrar </button> ";
    },
    tablaBotonesBorrar: function (id, row, index) {
        return  "<button type='button' class='btn btn-danger btn-sm' onclick='tecnara.borrarRegistroSinMenu(this, " + row["id"] + ")'> Borrar </button> ";
    },
    tablaBotonesEditarSinMenu: function (id, row, index) {
        return "<button type='button' class='btn btn-success btn-sm' onclick='tecnara.abrirFormularioEditarSinMenu(this, " + row["id"] + ")' > Editar </button> ";
    },

    tablaBotonesVerificadoSiOcultar: function (id, row, index) {
        var botones = "";
        botones += "<button type='button' class='btn btn-danger btn-sm' onclick  ='tecnara.borrarRegistroSinMenu(this, " + row["id"] + ")'> Borrar </button> ";
        if (row['verificado'] != 'si') {
            botones += "<button type='button' class='btn btn-success btn-sm' onclick ='tecnara.abrirFormularioEditarSinMenu(this, " + row["id"] + ")' >Editar</button> ";
        }


        return botones;
    },

    tablaBotonesAceptarRechazar: function (id, row, index) {
        return "<button type='button' class='btn btn-success btn-sm' onclick='tecnara.bootboxEnConstruccion()' >Aceptar</button> " +
                "<button type='button' class='btn btn-danger btn-sm' onclick='tecnara.bootboxEnConstruccion()'>Rechazar</button> ";
    },
    tablaBotonesDescargarBorrarSinMenu: function (id, row, index) {
        return "<button type='button' class='btn btn-success btn-sm' onclick='tecnara.bootboxEnConstruccion()' > Descargar </button> " +
                "<button type='button' class='btn btn-danger btn-sm' onclick='tecnara.borrarRegistroSinMenu(this, " + row["id"] + ")'> Borrar </button> ";
    },

    tablaImagen: function (id, row, index) {
        if (id == null) {
            return "Sin imagen";
        }
        return "<img onclick='tecnara.zoomImg(this)' src='/expo/coordinador/imagen/descargar?id=" + id + "' style='width:150px; height: 100px;object-fit:contain'>";
    },
    tablaFormatColor: function (value, row, index) {


        return"<div style='width:30px;height:10px;background-color:" + value + "'></div>";
    },
    ocultar: function (id) {
        $("#" + id).toggleClass("d-none");
    },

    buscadorIdCargar: function (obj) {
        var valor = $(obj).val();
        if (valor > 0) {
            var myId = $(obj).attr("id");
            $.ajax({
                url: $(obj).attr("data-url") + "_id?id=" + valor,
                success: function (res) {
                    $("#" + myId + "_filtro").val(res);
                }
            })
        }

    },
    buscadorClickSelect: function (obj, destino) {
        $("#" + destino).val($(obj).val()).change();
        //$("#" + destino + "_filtro").val($(obj).find("option[selected]").val());
        $("#" + destino + "_filtro").val($(obj)[0].options[$(obj)[0].selectedIndex].text);
        $("#" + destino + "_select").addClass("d-none");
    },
    buscadorCargarSelect: function (obj, event, destino, url) {
        if (event.keyCode == 38 || event.keyCode == 40 || event.keyCode == 13) {
            return;
        }
        $.ajax({
            url: url,
            typeData: "json",
            data: {
                filtro: $(obj).val()
            },
            success: function (res) {
                $("#" + destino + "_select").removeClass("d-none");
                $("#" + destino + "_select").html("");
                res.forEach(function (registro) {
                    var option = $("<option>").attr("value", registro.clave).html(registro.valor);
                    $("#" + destino + "_select").append(option);
                })
            }
        })
    },
    buscadorObtenerFoco: function (obj) {
        $(obj).attr("save-id", $("#" + $(obj).attr("idOrigen")).val());
        $(obj).attr("save-filtro", $(obj).val());
        $(obj).select();
    },
    buscadorPerderFoco: function (obj, id) {
        setTimeout(function () {
            $("#" + id + "_select").addClass("d-none")
            if ($(obj).attr("save-id") == $("#" + $(obj).attr("idOrigen")).val()) {
                $(obj).val($(obj).attr("save-filtro"));
            }
        }, 1000);

    },
    buscadorPulsaTecla: function (obj, event, idDestino) {
        if (event.keyCode == 38) {
            var indice = $("#" + idDestino + "_select")[0].selectedIndex;
            if (indice == -1) {
                $("#" + idDestino + "_select")[0].selectedIndex = 0;
            } else {
                $("#" + idDestino + "_select")[0].selectedIndex--;
            }
        } else if (event.keyCode == 40) {
            var indice = $("#" + idDestino + "_select")[0].selectedIndex;
            if (indice == -1) {
                $("#" + idDestino + "_select")[0].selectedIndex = 0;
            } else {
                $("#" + idDestino + "_select")[0].selectedIndex++;
            }
        } else if (event.keyCode == 13) {
            tecnara.buscadorClickSelect($("#" + idDestino + "_select")[0], idDestino);
            event.preventDefault();
            event.stopPropagation();
        }
    },
    buscadorGenerar: function (obj) {
        if ($(obj).attr("id") == null) {
            $(obj).attr("id", $(obj).attr("name"));
        }
        //$(obj).attr("type", "hidden");
        $(obj).addClass("d-none");
        var campo = $(obj).attr("data-params");
        var campoF = "#" + campo;
        var valorParam = $("#" + campo).val();
        var inp = $("<input>").attr("class", "form-control").attr("autocomplete", "off")
                .attr("id", $(obj).attr("id") + "_filtro")
                .attr("idOrigen", $(obj).attr("id"));
        if ($(obj).attr("readonly") != null) {
            inp = $(inp).attr("readonly", "readonly");
        } else {
            inp = $(inp).attr("onkeyup", "tecnara.buscadorCargarSelect(this, event,  '" + $(obj).attr("id") + "', '" + $(obj).attr("data-url") + "?" + $(obj).attr("data-params") + "=" + valorParam + "')")
                    .attr("onfocus", "tecnara.buscadorObtenerFoco(this)")
                    .attr("onfocusout", "tecnara.buscadorPerderFoco(this, '" + $(obj).attr("id") + "')")
                    .attr("onkeydown", "tecnara.buscadorPulsaTecla(this, event, '" + $(obj).attr("id") + "')");

        }
        var sel = $("<select>").attr("class", "form-control d-none")
                .attr("multiple", "")
                .attr("id", $(obj).attr("id") + "_select")
                .attr("onclick", "tecnara.buscadorClickSelect(this, '" + $(obj).attr("id") + "')")
                .attr("style", "position:absolute;z-index:1")
        $(obj).parent().append(inp);
        if ($(obj).parent().attr("data-toggle") == "obligatorio") {
            $(obj).parent().parent().append(sel);
        } else {
            $(obj).parent().append(sel);
        }
        tecnara.buscadorIdCargar(obj);
    },
    asteriscoGenerar: function (obj) {
        $(obj).attr("class", "input-group");
        var igp = $("<div>").attr("class", "input-group-prepend");
        var spa = $("<span>").attr("class", "input-group-text").attr("id", "inputGroupPrepend");
        var i = $("<i>").attr("class", "fas fa-asterisk");
        if ($(obj).attr("data-icon") != null) {
            i.attr("class", $(obj).attr("data-icon"));
        }

        var invalid = $(obj).attr("data-invalid-text");
        if (invalid != null) {
            var div = $("<div>").attr("class", "invalid-feedback").html(invalid);
            $(obj).append(div);
        }

        $(obj).prepend(igp);
        igp.append(spa);
        spa.append(i);
    },
    volverGenerar: function (obj) {
        $(obj).attr("class", "w-100 text-right ");
        var a = $("<a>").attr("href", "list").attr("class", "mb-5");
        var i = $("<i>").attr("class", "fas fa-hand-point-left");
        $(obj).append(a);
        a.append(i).append(" Volver");
    },
    editorHTML: function (obj) {
        if ($(obj).attr("id") == null) {
            $(obj).attr("id", $(obj).attr("name"));
        }
        tinymce.init({
            selector: '#' + $(obj).attr("id"),
            height: 400,
            menubar: false,
            plugins: [
                'advlist autolink lists link image charmap print preview anchor',
                'searchreplace visualblocks code fullscreen',
                'insertdatetime media table paste code help wordcount'
            ],
            toolbar: 'undo redo | formatselect | ' +
                    'bold italic forecolor backcolor | alignleft aligncenter ' +
                    'alignright alignjustify | bullist numlist outdent indent | ' +
                    'removeformat | help',
            content_style: 'body { font-family:Helvetica,Arial,sans-serif; font-size:14px }'
        });
    },
    editorHTMLPlus: function (obj) {
        var useDarkMode = window.matchMedia('(prefers-color-scheme: dark)').matches;
        var isSmallScreen = window.matchMedia('(max-width: 1023.5px)').matches;

        if ($(obj).attr("id") == null) {
            $(obj).attr("id", $(obj).attr("name"));
        }
        tinymce.init({
            selector: '#' + $(obj).attr("id"),
            plugins: 'preview importcss searchreplace autolink autosave save directionality code visualblocks visualchars fullscreen image link media template codesample table charmap pagebreak nonbreaking anchor insertdatetime advlist lists wordcount help charmap quickbars emoticons',
            editimage_cors_hosts: ['picsum.photos'],
            menubar: 'file edit view insert format tools table help',
            toolbar: 'undo redo | bold italic underline strikethrough | fontfamily fontsize blocks | alignleft aligncenter alignright alignjustify | outdent indent |  numlist bullist | forecolor backcolor removeformat | pagebreak | charmap emoticons | fullscreen  preview save print | insertfile image media template link anchor codesample | ltr rtl',
            toolbar_sticky: true,
            toolbar_sticky_offset: isSmallScreen ? 102 : 108,
            autosave_ask_before_unload: true,
            autosave_interval: '30s',
            autosave_prefix: '{path}{query}-{id}-',
            autosave_restore_when_empty: false,
            autosave_retention: '2m',
            image_advtab: true,
            importcss_append: true,
            file_picker_callback: (callback, value, meta) => {
                /* Provide file and text for the link dialog */
                if (meta.filetype === 'file') {
                    callback('https://www.google.com/logos/google.jpg', {text: 'My text'});
                }

                /* Provide image and alt text for the image dialog */
                if (meta.filetype === 'image') {
                    callback('https://www.google.com/logos/google.jpg', {alt: 'My alt text'});
                }

                /* Provide alternative source and posted for the media dialog */
                if (meta.filetype === 'media') {
                    callback('movie.mp4', {source2: 'alt.ogg', poster: 'https://www.google.com/logos/google.jpg'});
                }
            },
            templates: [
                {title: 'Nueva tabla', description: 'Crear una nueva tabla', content: '<div class="mceTmpl"><table width="98%%"  border="0" cellspacing="0" cellpadding="0"><tr><th scope="col"> </th><th scope="col"> </th></tr><tr><td> </td><td> </td></tr></table></div>'},
                {title: 'Starting my story', description: 'A cure for writers block', content: 'Once upon a time...'},
                {title: 'New list with dates', description: 'New List with dates', content: '<div class="mceTmpl"><span class="cdate">cdate</span><br><span class="mdate">mdate</span><h2>My List</h2><ul><li></li><li></li></ul></div>'}
            ],
            template_cdate_format: '[Date Created (CDATE): %m/%d/%Y : %H:%M:%S]',
            template_mdate_format: '[Date Modified (MDATE): %m/%d/%Y : %H:%M:%S]',
            height: 600,
            image_caption: true,
            quickbars_selection_toolbar: 'bold italic | quicklink h2 h3 blockquote quickimage quicktable',
            noneditable_class: 'mceNonEditable',
            toolbar_mode: 'sliding',
            contextmenu: 'link image table',
            skin: useDarkMode ? 'oxide-dark' : 'oxide',
            content_css: useDarkMode ? 'dark' : 'default',
            content_style: 'body { font-family:Helvetica,Arial,sans-serif; font-size:16px }'
        });
    },
    irAIndice: function (obj) {
        $(obj).attr("class", "w-100 text-right ");
        var a = $("<a>").attr("href", "../index").attr("class", "mb-5");
        var i = $("<i>").attr("class", "fas fa-hand-point-left");
        $(obj).append(a);
        a.append(i).append(" Volver al indice");
    },
    construccionEmpty: function () {
        $("body").html("");
        var div = $("<div>").attr("class", "container");
        var h1 = $("<h3>").attr("class", "text-center").html("En construccion");
        var p = $("<p>").html("Esta página se encuentra en construcción. Disculpe las molestias. <a href='#' onclick='history.back()'>Volver</a>");
        $("body").append(div);
        $(div).append(h1);
        $(div).append(p);
    },
    detectarDataToggle: function (obj) {
        $(obj).find("[data-toggle='table'").bootstrapTable();
        $(obj).find("button[data-toggle='formbox']").attr("type", "button").attr("onclick", "tecnara.abrirFormularioNuevoSinMenu(this)");
        $(obj).find("a[data-toggle='formbox']").attr("onclick", "tecnara.abrirFormularioNuevo(this)");
        $(obj).find("button[data-toggle='formboxsinmenu']").attr("type", "button").attr("onclick", "tecnara.abrirFormularioNuevoSinMenu(this)");
        $(obj).find("a[data-toggle='formboxsinmenu']").attr("onclick", "tecnara.abrirFormularioNuevoSinMenu(this)");
        $(obj).find("input[data-toggle='buscador']").each(function () {
            tecnara.buscadorGenerar(this);
        });
        $(obj).find("div[data-toggle='obligatorio']").each(function () {
            tecnara.asteriscoGenerar(this);
        });
        $(obj).find("div[data-toggle='volver']").each(function () {
            tecnara.volverGenerar(this);
        });
        $(obj).find("div[data-toggle='indice']").each(function () {
            tecnara.irAIndice(this);
        });
        $(obj).find("div[data-toggle='caras']").each(function () {
            tecnara.carasGenerar(this);
        });
        $(obj).find("*[data-toggle='caras_input']").each(function () {
            tecnara.carasGenerarInput(this);
        });
        $(obj).find("textarea[data-toggle='html']").each(function () {
            tecnara.editorHTML(this);
        });
        $(obj).find("*[data-default]").each(function (obj) {
            if ($(this).attr('data-default') == "hoy") {
                if ($(this).val() == "") {
                    var today = new Date();
                    var date = today.getFullYear() + '-' + tecnara.cerosIzquierda((today.getMonth() + 1), 2) + '-' + tecnara.cerosIzquierda(today.getDate(), 2);
                    $(this).val(date);
                }
            } else if ($(this).attr('data-default') == "ahora") {
                if ($(this).val() == "") {
                    var today = new Date();
                    var date = tecnara.cerosIzquierda(today.getHours(), 2) + ':' + tecnara.cerosIzquierda(today.getMinutes(), 2);
                    $(this).val(date);
                }
            } else {
                if ($(this).attr("type") == "color" && $(this).attr("data-real-value") == null) {
                    $(this).val($(this).attr('data-default'));
                } else if ($(this).val() == "") {
                    $(this).val($(this).attr('data-default'));
                }
            }
        });
        $("*[data-toggle='enConstruccion'").each(function () {
            tecnara.construccionEmpty();
        });
    },
    bootboxEnConstruccion: function () {
        bootbox.alert("En construcción");
    },
    carasVotar: function (url, idActividad, nota) {
        $.ajax({
            url: url,
            data: {
                idActividad: idActividad,
                nota: nota
            },
            success: function (res) {
                alert("Has votado");
            },
            error: function () {
                alert("Error");
            }
        })

    },
    mostrarAnimacion: function () {
        var d = $("<div>").attr("style", "background-color:#000A;z-index:3;position:absolute;top:0px;left:0px;width:100%;height:1900px");
        var dFlex = $("<div>").attr("class", "p-5 m-5 d-flex justify-content-center");
        var div = $("<div>").attr("class", "spinner-border text-light").attr("role", "status").attr("style", "width: 10rem; height: 10rem;");
        var span = $("<span>").attr("class", "sr-only");
        var div2 = $("<div>").attr("class", "text-light text-center").html("Espere...");

        $(div).append(span);
        $(dFlex).append(div);
        $(d).append(dFlex);
        $(d).append(div2);
        $("body").append(d);
    },

    carasVotar2: function () {


    },
    cambiarColor: function (element, clr) {
        element.style.color = clr;
    },
    formatFecha: function (id, row, index) {
        if (id != null && id.length >= 10) {
            return id.substring(8, 10) + "/" + id.substring(5, 7) + "/" + id.substring(0, 4);
        } else {
            return id;
        }

    },
    carasGenerar: function (obj) {
        var url = $(obj).attr("data-url");
        var id = $(obj).attr("data-idActividad");
        $(obj).html("");

        var d = $("<div>").attr("class", "mt-3");
        var enlace1 = $("<a>").attr("onclick", "tecnara.carasVotar('" + url + "'," + id + ",2)");
        var angry = $("<i>", {"class": "fas fa-angry fa-3x"}).attr("style", "color: #888").attr("onclick", "tecnara.cambiarColor(this, 'red')");
        var enlace2 = $("<a>").attr("onclick", "tecnara.carasVotar('" + url + "'," + id + ",4)");
        var frown = $("<i>", {"class": "fas fa-frown fa-3x"}).attr("style", "color: #888").attr("onclick", "tecnara.cambiarColor(this, 'orange')");
        var enlace3 = $("<a>").attr("onclick", "tecnara.carasVotar('" + url + "'," + id + ",6)");
        var meh = $("<i>", {"class": "fas fa-meh fa-3x"}).attr("style", "color: #888").attr("onclick", "tecnara.cambiarColor(this, 'yellow')");
        var enlace4 = $("<a>").attr("onclick", "tecnara.carasVotar('" + url + "'," + id + ",8)");
        var smile = $("<i>", {"class": "fas fa-smile-beam fa-3x"}).attr("style", "color: #888").attr("onclick", "tecnara.cambiarColor(this, 'lime')");
        var enlace5 = $("<a>").attr("onclick", "tecnara.carasVotar('" + url + "'," + id + ",10)");
        var laugh = $("<i>", {"class": "fas fa-laugh-beam fa-3x"}).attr("style", "color: #888").attr("onclick", "tecnara.cambiarColor(this, 'green')");

        $(obj).append(d);

        d.append(enlace1);
        enlace1.append(angry);
        d.append(enlace2);
        enlace2.append(frown);
        d.append(enlace3);
        enlace3.append(meh);
        d.append(enlace4);
        enlace4.append(smile);
        d.append(enlace5);
        enlace5.append(laugh);

    },
    refrescarCaras: function (obj, id) {
        var valor = $("#" + id).val();
        //$("")
    },
    carasGenerarInput: function (obj) {
        $(obj).attr("type", "hidden");
        var url = $(obj).attr("data-url");
        var input = $(obj).attr("puntuacion");
        $(obj).html("");

        var d = $("<div>").attr("class", "mt-3");
        var enlace1 = $("<a>").attr("onclick", "tecnara.carasVotar2('" + url + "'," + id + ",2)");
        var angry = $("<i>", {"class": "fas fa-angry fa-3x"}).attr("style", "color: #888").attr("onclick", "tecnara.cambiarColor(this, 'red')");
        var enlace2 = $("<a>").attr("onclick", "tecnara.carasVotar2('" + url + "'," + id + ",4)");
        var frown = $("<i>", {"class": "fas fa-frown fa-3x"}).attr("style", "color: #888").attr("onclick", "tecnara.cambiarColor(this, 'orange')");
        var enlace3 = $("<a>").attr("onclick", "tecnara.carasVotar2('" + url + "'," + id + ",6)");
        var meh = $("<i>", {"class": "fas fa-meh fa-3x"}).attr("style", "color: #888").attr("onclick", "tecnara.cambiarColor(this, 'yellow')");
        var enlace4 = $("<a>").attr("onclick", "tecnara.carasVotar2('" + url + "'," + id + ",8)");
        var smile = $("<i>", {"class": "fas fa-smile-beam fa-3x"}).attr("style", "color: #888").attr("onclick", "tecnara.cambiarColor(this, 'lime')");
        var enlace5 = $("<a>").attr("onclick", "tecnara.carasVotar2('" + url + "'," + id + ",10)");
        var laugh = $("<i>", {"class": "fas fa-laugh-beam fa-3x"}).attr("style", "color: #888").attr("onclick", "tecnara.cambiarColor(this, 'green')");

        $(obj).append(d);

        d.append(enlace1);
        enlace1.append(angry);
        d.append(enlace2);
        enlace2.append(frown);
        d.append(enlace3);
        enlace3.append(meh);
        d.append(enlace4);
        enlace4.append(smile);
        d.append(enlace5);
        enlace5.append(laugh);



    },
    formatHorario: function (value) {
        if (value != null) {
            var res = "";
            var lista = value.split(";");
            for (var n = 0; n < lista.length; n++) {
                res += "<div class='text-nowarp'>" + lista[n] + "</div>"
            }
            return res;
        } else {
            return "Horario no disponible";
        }

    },

    selectFont: function (id) {
        $("#" + id).attr("style", "font-family:" + $("#" + id).val());
    },

    cerosIzquierda: function (valor, size) {
        var texto = "" + valor;
        while (texto.length < size) {
            texto = "0" + texto;
        }
        return texto;
    },
    zoomImg: function (obj) {
        bootbox.dialog({
            message: "<img src='" + $(obj).attr("src") + "' style='width:100%;height:800px;object-fit:contain'>",
            size: "xl",
        });
    },
    inputPonerHoy: function (id) {
        if ($("#" + id).val() == "") {
            var today = new Date();
            var date = today.getFullYear() + '-' + tecnara.cerosIzquierda((today.getMonth() + 1), 2) + '-' + tecnara.cerosIzquierda(today.getDate(), 2);
            $("#" + id).val(date);
        }
    },
    setCookie: function (cname, cvalue, minutes) {
        var now = new Date();
        now.setTime(now.getTime() + (minutes * 60 * 1000));
        const d = new Date();
        document.cookie = cname + "=" + cvalue + ";path=/;expires=" + now.toUTCString() + ";";
    },
    getCookie: function (cname) {
        let name = cname + "=";
        let ca = document.cookie.split(';');
        for (let i = 0; i < ca.length; i++) {
            let c = ca[i];
            while (c.charAt(0) == ' ') {
                c = c.substring(1);
            }
            if (c.indexOf(name) == 0) {
                return c.substring(name.length, c.length);
            }
        }
        return "";
    },
    almacenarDataMemory: function () {
        $("input[data-memory],select[data-memory]").each(function (idx, item) {
            var nombre = location.pathname + "###" + $(item).attr("name");
            tecnara.setCookie(nombre, $(item).val(), $(item).attr("data-memory"));
        })
        $("a[data-memory],button[data-memory]").each(function (idx, item) {
            $(item).attr("data-click", "1");
            var nombre = location.pathname + "###botonFiltro";
            tecnara.setCookie(nombre, $(item).attr("data-click"), $(item).attr("data-memory"));
        })
    },
    recuperarDataMemory: function () {
        $("input[data-memory],select[data-memory]").each(function (idx, item) {
            var nombre = location.pathname + "###" + $(item).attr("name");
            var info = tecnara.getCookie(nombre);
            if (info != "") {
                $(item).val(info);
            }
        })
        $("a[data-memory],button[data-memory]").each(function (idx, item) {
            var nombre = location.pathname + "###botonFiltro";
            var info = tecnara.getCookie(nombre);
            if (info != "") {
                $(item).attr("data-click", info);
            }
            if ($(item).attr("data-click") == "1") {
                $(item).trigger("click");
            }
        })

    },
    configurarDataMemory: function () {
        tecnara.recuperarDataMemory();
        if ($("*[data-memory]").length > 0) {
            $("form").on("submit", function () {
                tecnara.almacenarDataMemory();
            });
            $("button").on("click", function () {
                tecnara.almacenarDataMemory();
            });
            $("a").on("click", function () {
                tecnara.almacenarDataMemory();
            });

        }
    },
    formatTiempo: function (valor, fila, idx) {
        var horas = Math.trunc(valor / 60);
        var minutos = (valor % 60);

        if (horas != 0 && minutos != 0) {
            return  horas + "h " + minutos + "m ";
        }
        if (horas == 0 && minutos != 0) {
            return  minutos + "m ";
        }
        if (horas != 0 && minutos == 0) {
            return  horas + "h ";
        }
        return "0h"
    },
    formatDiaSemana: function (value, row, index) {
        var dia = value;
        if (dia == 1) {
            return 'Lunes';
        } else if (dia == 2) {
            return 'Martes';
        } else if (dia == 3) {
            return 'Miercoles';
        } else if (dia == 4) {
            return 'Jueves';
        } else if (dia == 5) {
            return 'Viernes';
        } else if (dia == 6) {
            return 'Sabado';
        } else if (dia == 0) {
            return 'Domingo';
        }
    }
}

$(document).ready(function () {
    tecnara.detectarDataToggle($("body"));
    if ($("#id").val() == "") {
        $(".tab-content button[data-toggle='formbox']").attr("onclick", "alert('Debes guardar la cabecera para añadir líneas. (En construcción)')");
        //$(".tab-content button[data-toggle='formbox']").addClass("d-none");
    }
    tecnara.configurarDataMemory();

})


