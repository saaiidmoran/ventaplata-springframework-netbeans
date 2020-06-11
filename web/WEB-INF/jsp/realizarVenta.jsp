<%-- 
    Document   : realizarVenta
    Created on : 14/09/2019, 02:22:28 AM
    Author     : saaii
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width,user-scalable=no,
              initial-scale=1, maximum-scale=1, minimum-scale=1"/>
        
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" integrity="sha384-gfdkjb5BdAXd+lj+gudLWI+BXq4IuLW5IT+brZEZsLFm++aCMlF1V92rMkPaX4PP" crossorigin="anonymous">
        <spring:url value="/resources/css/realizarVenta.css" var="realizarVentaCSS" />
        <link href="${realizarVentaCSS}" rel="stylesheet">
        <spring:url value="/resources/imagenes/venta.png" var="ImgVenta" />
        
        <title>Venta Plata</title>
    </head>
    <body>
        
        <%
            response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0"); // Proxies.
            if(session.getAttribute("login")==null){
                response.sendRedirect("index.htm");
            }
        %>
        
        
        <div class="contenedor-principal">
            <nav class="navbar navbar-light bg-light">
                <h1 class="navbar-brand mb-0 h1">
                    <i><img src="${ImgVenta}" width="23px" height="23px"></i>
                    Realizar Venta
                </h1> 
                <div class="form-inline">
                    <form action="logout.htm" method="post">
                        ${sessionScope.login.getUsuario()}
                        <button class="btn btn-white botones" title="Salir">
                            <span><i class="fas fa-sign-out-alt"></i></span>
                        </button>                 
                    </form>
                        <a href="inicio.htm" title="Inicio">
                            <span><i class="fas fa-home"></i></span>
                        </a>                        
                </div>
            </nav>
            <div class="container">
                
                <div class="row align-items-start">
                    <div class="container mt-4">
                        <div class="input-group mb-3 busqueda-producto">
                            <input type="text" class="form-control" placeholder="No. de producto" id="valor-busqueda-idproducto" />
                            <input type="text" class="form-control" placeholder="Producto" id="valor-busqueda-producto" />
                            <div class="input-group-append">
                                <button type="button" id="btn-buscar-producto" class="btn btn-primary" title="Buscar"><i class="fas fa-search"></i></button>
                            </div> 
                        </div>
                        <div class="curved-border-table">
                            <h4>Productos disponibles</h4>
                            <div class="table-responsive">
                                <table class="table table-hover table-borderless">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Producto</th>
                                            <th>Precio</th>
                                            <th></th>
                                        </tr>                            
                                    </thead>
                                    <tbody id="contenidoTablaProductos">                            
                                    </tbody>
                                </table>
                            </div>                            
                        </div>
                    </div>      
                </div> 
                
                <div class="row align-items-center">
                    <div class="container mt-4">
                        <div class="curved-border-table">
                            <h4>Pila de venta</h4>
                            <div class="table-responsive">
                                <table class="table table-hover table-borderless">
                                    <thead>                                    
                                        <tr>
                                            <th>#</th>
                                            <th>Producto</th>
                                            <th>Precio</th>
                                            <th>Total</th>
                                            <th></th>
                                        </tr>                            
                                    </thead>
                                    <tbody id="contenidoTablaProductosVenta">                            
                                    </tbody>
                                </table>
                            </div>                            
                        </div>
                    </div>
                </div>
                
                <div class="row align-items-end">
                    <div class="container mt-4 curved-footer-buttons">
                        <div class="">
                            <h4>Concretar Venta</h4>
                                <div class="form-group row">
                                    <label for="modalidadVenta-registrar" class="col-sm-2 col-form-label">Modalidad de Venta:</label>
                                    <div class="col-sm-10">
                                        <input type="text" list="listaModalidadVenta" class="form-control" id="modalidadVenta-registrar" placeholder="Seleccionar...">
                                        <datalist id="listaModalidadVenta">                                            
                                        </datalist>
                                    </div>                                    
                                </div>
                                <div class="form-group row">
                                    <label for="cliente-registrar" class="col-sm-2 col-form-label">Cliente:</label>
                                    <div class="col-sm-10 input-group">
                                        <input type="text" list="listaClientes" class="form-control" id="cliente-registrar" placeholder="Seleccionar...">
                                        <datalist id="listaClientes">                                            
                                        </datalist>
                                        <div class="input-group-append">
                                            <button type="button" class="btn btn-success" data-toggle="modal" data-target="#modal-add-cliente" title="Agregar cliente">
                                                <i class="fas fa-plus"></i>
                                            </button>
                                        </div> 
                                        
                                    </div>                                    
                                </div>
                                <div class="form-group row">
                                    <label for="abonoVenta-registrar" class="col-sm-2 col-form-label">Abono:</label>
                                    <div class="col-sm-10 input-group">
                                        <div class="input-group-prepend">
                                            <div class="input-group-text ">$</div>
                                        </div>
                                        <input type="text" class="form-control" id="abonoVenta-registrar">                               
                                        
                                    </div>  
                                </div>
                                <div class="form-group row">
                                    <label for="fecha-venta-registrar" class="col-sm-2 col-form-label">Fecha de Venta:</label>
                                    <div class="col-sm-10">
                                        <input type="date" class="form-control" id="fecha-venta-registrar">
                                    </div>                                    
                                </div>
                                <button type="button" class="btn btn-secondary" id="btn-cancelar-venta">Cancelar</button>
                                <button type="button" class="btn btn-primary" id="btn-registrar-venta">Realizar Venta</button>                 
                        </div>
                    </div>
                </div>
                
            </div>
        </div>
        
        <div class="modal fade" id="modal-add-producto-pilav" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Mensaje</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="form-group">
                                <label class="col-form-label">
                                    ¿Desea agregar este producto a la Pila de Venta?
                                </label>
                                <input type="hidden" id="id-producto-add-pila-venta"/>
                            </div>                 
                        </form>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
                      <button type="button" class="btn btn-success" id="btn-add-producto-pila-venta">Si</button>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="modal fade" id="modal-elim-producto-pilav" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Mensaje</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="form-group">
                                <label class="col-form-label">
                                    ¿Desea quitar este producto de la Pila de Venta?
                                </label>
                                <input type="hidden" id="id-producto-elim-pila-venta"/>
                            </div>                 
                        </form>
                    </div>
                    <div class="modal-footer">                      
                      <button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
                      <button type="button" class="btn btn-danger" id="btn-elim-producto-pila-venta">Si</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="modal-add-cliente" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Registro de cliente</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="form-group">
                                <label for="nombreCliente-registrar" class="col-form-label">Nombre:</label>
                                <input type="text" class="form-control" id="nombreCliente-registrar">
                            </div>
                            <div class="form-group">
                                <label for="noTelefonico-registrar" class="col-form-label">Teléfono:</label>
                                <input type="text" class="form-control" id="noTelefonico-registrar">
                            </div>
                            <div class="form-group">
                                <label for="correo-registrar" class="col-form-label">Correo:</label>
                                <input type="text" class="form-control" id="correo-registrar">
                            </div>
                            <div class="form-group">
                                <label for="domicilio-registrar" class="col-form-label">Domicilio:</label>
                                <input type="text" class="form-control" id="domicilio-registrar">
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                      <button type="button" class="btn btn-success" id="btn-registrar-cliente">Registrar</button>
                    </div>
                </div>
            </div>
        </div>
        
        <span id="ventaAgregada-msj">
        </span>
                        
        <span id="msjClienteAgregado">
        </span>
        
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        
        <script type="text/javascript">
            $(document).ready(function (){
                
                function obtenerProductosEnVenta(){
                     $.ajax({
                        type: 'GET',
                        url: '${pageContext.request.contextPath}/realizarVenta/productosEnVenta.htm',
                        success: function (result) {
                            $('#contenidoTablaProductos').html(result);
                        }
                    }); 
                 }
                
                function obtenerPilaDeVenta(){
                     $.ajax({
                        type: 'GET',
                        url: '${pageContext.request.contextPath}/realizarVenta/obtenerPilaDeVenta.htm',
                        success: function (result) {
                            $('#contenidoTablaProductosVenta').html(result);
                        }
                    }); 
                 }
                 
                 function obtenerModalidadesDeVenta(){
                     $.ajax({
                        type: 'GET',
                        url: '${pageContext.request.contextPath}/realizarVenta/obtenerModalidadesDeVenta.htm',
                        success: function (result) {
                            $('#listaModalidadVenta').html(result);
                        }
                    }); 
                 }
                 
                 function obtenerListaClientes(){
                    $.ajax({
                        type: 'GET',
                        url: '${pageContext.request.contextPath}/realizarVenta/obtenerListaClientes.htm',
                        success: function (result) {
                            $('#listaClientes').html(result);
                        }
                    }); 
                 }
                 
                 function obtenerTotalVenta(){
                    if($('#modalidadVenta-registrar').val() === 'De contado'){
                        $.ajax({
                            type: 'GET',
                            url: '${pageContext.request.contextPath}/realizarVenta/obtenerTotalVenta.htm',
                            success: function (result) {
                                $('#abonoVenta-registrar').val(result);
                            }
                        }); 
                    }
                    
                 }
                 
                 function obtenerFechaActual(){
                     $.ajax({
                        type: 'GET',
                        url: '${pageContext.request.contextPath}/productos/obtenerFechaActual.htm',
                        success: function (result) {
                            $('#fecha-venta-registrar').val(result);
                        }
                    }); 
                 }
                 
                 obtenerProductosEnVenta();
                 obtenerPilaDeVenta();
                 obtenerModalidadesDeVenta();
                 obtenerListaClientes();
                 obtenerFechaActual();
                 $('#abonoVenta-registrar').val('0.0');
                 
                 $('#btn-registrar-cliente').click(function(){
                    var nombre,telefono,correo,domicilio;
                    nombre = $('#nombreCliente-registrar').val();
                    telefono = $('#noTelefonico-registrar').val();
                    correo = $('#correo-registrar').val();
                    domicilio = $('#domicilio-registrar').val();
                    $.ajax({
                        type:'POST',
                        url: '${pageContext.request.contextPath}/clientes/registrarCliente/'+nombre+'/'+telefono+'/'+correo+'/'+domicilio+'.htm',
                        success: function (result){                        
                            obtenerListaClientes();
                            $('#modal-add-cliente').modal('hide');
                            $('#msjClienteAgregado').html(result);
                        }
                    });
                });
                 
                 $('#modal-add-producto-pilav').on('show.bs.modal', function (event) {
                    var button = $(event.relatedTarget);
                    var idproducto = button.data('idproducto');
                    $('#id-producto-add-pila-venta').val(idproducto);
                 });
                 
                 $('#btn-add-producto-pila-venta').click(function(){
                     var idproducto = $('#id-producto-add-pila-venta').val();
                     $.ajax({
                        type: 'POST',
                        url: '${pageContext.request.contextPath}/realizarVenta/agregarAPilaDeVenta/'+idproducto+'.htm',
                        success: function (result) {
                            obtenerProductosEnVenta();
                            obtenerPilaDeVenta();
                            obtenerTotalVenta();
                            $('#modal-add-producto-pilav').modal('hide');
                        }
                    }); 
                     
                 });
                 
                 $('#modalidadVenta-registrar').change(function(){
                    if($('#modalidadVenta-registrar').val() === 'De contado'){
                        $.ajax({
                            type: 'GET',
                            url: '${pageContext.request.contextPath}/realizarVenta/obtenerTotalVenta.htm',
                            success: function (result) {
                                $('#abonoVenta-registrar').val(result);
                            }
                        }); 
                    }else{
                        $('#abonoVenta-registrar').val('0.0');
                    }
                 });
                 
                 $('#modal-elim-producto-pilav').on('show.bs.modal', function (event) {
                    var button = $(event.relatedTarget);
                    var idproducto = button.data('idproducto');
                    $('#id-producto-elim-pila-venta').val(idproducto);
                 });
                 
                 $('#btn-elim-producto-pila-venta').click(function(){
                     var idproducto = $('#id-producto-elim-pila-venta').val();
                     $.ajax({
                        type: 'POST',
                        url: '${pageContext.request.contextPath}/realizarVenta/quitarDePilaDeVenta/'+idproducto+'.htm',
                        success: function (result) {
                            obtenerProductosEnVenta();
                            obtenerPilaDeVenta();
                            obtenerTotalVenta();
                            $('#modal-elim-producto-pilav').modal('hide');
                        }
                    }); 
                     
                 });
                 
                 $('#btn-cancelar-venta').click(function(){
                    $('#modalidadVenta-registrar').val('');
                    $('#cliente-registrar').val('');
                    $('#abonoVenta-registrar').val('0.0');
                    $.ajax({
                        type: 'POST',
                        url: '${pageContext.request.contextPath}/realizarVenta/cancelarVenta.htm',
                        success: function (result) {
                            obtenerProductosEnVenta();
                            obtenerPilaDeVenta();
                        }
                    });
                 });
                 
                 $('#btn-registrar-venta').click(function(){
                 var modalidadVenta = $('#modalidadVenta-registrar').val();
                 var abono = $('#abonoVenta-registrar').val();
                 var nombreCliente = $('#cliente-registrar').val(); 
                 var fecha = $('#fecha-venta-registrar').val();
                    if(modalidadVenta !== '' && abono !== '' && nombreCliente !== ''){
                        $.ajax({
                            type: 'POST',
                            url: '${pageContext.request.contextPath}/realizarVenta/registrarVenta/'+nombreCliente+'/'+abono+'/'+modalidadVenta+'/'+fecha+'.htm',
                            success: function (result) {
                                obtenerProductosEnVenta();
                                obtenerPilaDeVenta();  
                                obtenerTotalVenta();
                                $('#modalidadVenta-registrar').val('');
                                $('#cliente-registrar').val('');
                                $('#abonoVenta-registrar').val('');
                                $('#ventaAgregada-msj').html(result);
                            }
                        });
                    }else{
                        alert('Faltan campos por llenar');
                    }                    
                 });
                $('#btn-buscar-producto').click(function(){
                    var idproducto = $('#valor-busqueda-idproducto').val();
                    var producto = $('#valor-busqueda-producto').val();
                    
                    if(idproducto === ''){
                        idproducto = '0';
                    }
                    if(producto === ''){
                        producto = 'todos';
                    }
                    
                    $.ajax({
                        type: 'GET',
                        url: '${pageContext.request.contextPath}/realizarVenta/filtraProductosEnVenta/'+idproducto+'/'+producto+'.htm',
                        success: function (result) {
                            $('#contenidoTablaProductos').html(result);
                        }
                    });                
                    
                    
                });
            });
        </script>        
        
    </body>
</html>
