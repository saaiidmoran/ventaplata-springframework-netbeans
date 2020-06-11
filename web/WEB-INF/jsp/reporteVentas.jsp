<%-- 
    Document   : reporteVentas
    Created on : 14/09/2019, 02:22:51 AM
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
        <spring:url value="/resources/css/reporteVentas.css" var="reporteVentasCSS" />
        <link href="${reporteVentasCSS}" rel="stylesheet">
        <spring:url value="/resources/imagenes/reporteventas.png" var="ImgReporteVenta" />
        
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
                    <i><img src="${ImgReporteVenta}" width="23px" height="23px"></i>
                    Reporte
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
                        <div class="input-group mb-3">
                            <input type="number" class="form-control" placeholder="No. de venta" id="valor-busqueda-iddetalleventa" />
                            <input type="text" list="lista-clientes" class="form-control" placeholder="Cliente" id="valor-busqueda-cliente" />
                            <datalist id="lista-clientes">
                            </datalist>
                            <input type="text" list="lista-modalidades" class="form-control" placeholder="Modalidad" id="valor-modalidadVenta-buscar" />
                            <datalist id="lista-modalidades">
                            </datalist>
                            <input type="text" list="lista-estatus" class="form-control" placeholder="Estatus" id="valor-estatusVenta-buscar" />
                            <datalist id="lista-estatus">
                            </datalist>
                            <div class="input-group-append">
                                <button type="button" id="btn-buscar-venta" class="btn btn-primary" title="Buscar"><i class="fas fa-search"></i></button>
                            </div>                    
                        </div>
                        <div class="input-group mb-3">
                            <input type="date" class="form-control" id="valor-busqueda-fechadetalleventa" />
                            <input type="date" class="form-control" id="valor-busqueda-fechafindetalleventa" />
                        </div>
                        <div class="curved-border-table">
                            <h4>Ventas Registradas</h4>
                            <div class="table-responsive">
                                <table class="table table-hover table-borderless">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Fecha</th>
                                            <th>Total</th>
                                            <th>Abono</th>
                                            <th>Cliente</th>
                                            <th>Modalidad</th>
                                            <th>Estatus</th>
                                            <th>Usuario</th>
                                            <th colspan="4">Acciones</th>
                                        </tr>
                                    </thead>
                                    <tbody id="contenidoTabla">                            
                                    </tbody>
                                </table>
                            </div>                    
                        </div>
                    </div>
                </div>
                <div class="row align-items-end">
                    <div class="container mt-4">
                        <div class="curved-border-footer">
                            <table class="table table-hover table-borderless">
                                <thead>
                                    <tr>
                                        <th>Total de inversión</th>
                                        <th>Total en ventas</th>
                                        <th>Ganancias</th>
                                    </tr>
                                </thead>
                                <tbody id="totalesTabla">
                                </tbody>
                            </table>                            
                        </div>
                    </div>
                </div>
            </div>            
        </div>
        <div class="modal fade" id="modal-productos-venta" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Productos</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="table-responsive curved-border-table">
                            <table class="table table-hover table-borderless">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Producto</th>
                                        <th>Precio</th>
                                        <th></th>
                                    </tr>                            
                                </thead>
                                <tbody id="contenidoTablaProductosVenta">                            
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-info" data-dismiss="modal">Aceptar</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="modal-abonar-venta" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Abonar venta</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form>
                            <input type="hidden" id="id-detalleventa-abonar"/>
                            <div class="form-group">
                                <label for="id-cliente-modificar" class="col-form-label">Total de Venta:</label>
                                <input type="number" readonly="readonly" class="form-control" id="valor-total-venta">
                            </div> 
                            <div class="form-group">
                                <label for="nombreCliente-modificar" class="col-form-label">Abonado:</label>
                                <input type="numbe" readonly="readonly" class="form-control" id="valor-abonado-venta">
                            </div>
                            <div class="form-group">
                                <label for="noTelefonico-modificar" class="col-form-label">Abonar:</label>
                                <input type="number" class="form-control" id="valor-abonar-venta">
                            </div>                            
                        </form>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                      <button type="button" class="btn btn-success" id="btn-abonar">Abonar</button>
                    </div>
                </div>
            </div>            
        </div>                 
        
        <div class="modal fade" id="modal-eliminar-producto-venta" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Eliminación de producto</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-form-label">
                                ¿Está seguro de que desea eliminar este producto de la venta?
                            </label>
                            <input type="hidden" id="valor-id-venta-producto-eliminar"/>
                            <input type="hidden" id="valor-id-dv-producto-eliminar"/>
                        </div>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                      <button type="button" class="btn btn-danger" id="btn-eliminar-producto-venta">Eliminar</button>
                    </div>
                </div>
            </div>
        </div>
                        
        <div class="modal fade" id="modal-modificar-venta" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Modificación de venta</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="form-group">
                                <label for="id-venta-modificar" class="col-form-label">No. Venta:</label>
                                <input type="text" readonly="readonly" class="form-control" id="id-venta-modificar">
                            </div>
                            <div class="form-group">
                                <label for="fecha-venta-modificar" class="col-form-label">Fecha:</label>
                                <input type="date" class="form-control" id="fecha-venta-modificar">
                            </div>
                            <div class="form-group">
                                <label for="total-venta-modificar" class="col-form-label">Total:</label>
                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <div class="input-group-text ">$</div>
                                    </div>
                                    <input type="number" class="form-control" id="total-venta-modificar">
                                </div>                                
                            </div>
                            <div class="form-group">
                                <label for="abono-venta-modificar" class="col-form-label">Abono:</label>
                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <div class="input-group-text ">$</div>
                                    </div>
                                    <input type="number" class="form-control" id="abono-venta-modificar">
                                </div>                                
                            </div>
                            <div class="form-group">
                                <label for="cliente-venta-modificar" class="col-form-label">Cliente:</label>
                                <input type="text" list="lista-clientes" class="form-control" id="cliente-venta-modificar">                                
                            </div>
                            <div class="form-group">
                                <label for="modalidad-venta-modificar" class="col-form-label">Modalidad:</label>
                                <input type="text" list="lista-modalidades" class="form-control" id="modalidad-venta-modificar">
                            </div>
                            <div class="form-group">
                                <label for="estatus-venta-modificar" class="col-form-label">Estatus:</label>
                                <input type="text" list="lista-estatus" class="form-control" id="estatus-venta-modificar">
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                      <button type="button" class="btn btn-warning" id="btn-modificar-venta">Modificar</button>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="modal fade" id="modal-eliminar-venta" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Eliminación de venta</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="form-group">
                                <label class="col-form-label">
                                    ¿Está seguro de que desea eliminar esta venta?
                                </label>
                                <input type="hidden" id="valor-idventa-eliminar" />
                            </div>                            
                        </form>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                      <button type="button" class="btn btn-danger" id="btn-eliminar-venta">Eliminar</button>
                    </div>
                </div>
            </div>
        </div>
                        
        <div id="msjAbonadoAVenta">          
        </div>
        
        <div id="msjProductoEliminado">
        </div>
                        
        <div id="msjVentaModificada">            
        </div>
            
        <div id="msjVentaEliminada">                            
        </div>
        
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        
        <spring:url value="/resources/js/reporteVentasBootstrap.js" var="reporteVentasBootstrap" />
        <script type="text/javascript" src="${reporteVentasBootstrap}"></script>
        
        <script type="text/javascript">
            $(document).ready(function (){
                function obtenerModalidadesDeVenta(){
                     $.ajax({
                        type: 'GET',
                        url: '${pageContext.request.contextPath}/realizarVenta/obtenerModalidadesDeVenta.htm',
                        success: function (result) {
                            $('#lista-modalidades').html(result);
                        }
                    }); 
                 }
                 function obtenerListaClientes(){
                    $.ajax({
                        type: 'GET',
                        url: '${pageContext.request.contextPath}/realizarVenta/obtenerListaClientes.htm',
                        success: function (result) {
                            $('#lista-clientes').html(result);
                        }
                    }); 
                 }
                 function obtenerEstatusVenta(){
                    $.ajax({
                        type: 'GET',
                        url: '${pageContext.request.contextPath}/reporteVentas/obtenerEstatusVenta.htm',
                        success: function (result) {
                            $('#lista-estatus').html(result);
                        }
                    }); 
                 }
                 function obtenerVentasRegistradas(){
                    $.ajax({
                        type: 'GET',
                        url: '${pageContext.request.contextPath}/reporteVentas/obtenerVentasRegistradas.htm',
                        success: function (result) {
                            $('#contenidoTabla').html(result);
                        }
                    }); 
                 }
                 function obtenerTotales(){
                    var fecha1 = $('#valor-busqueda-fechadetalleventa').val();
                    var fecha2 = $('#valor-busqueda-fechafindetalleventa').val();
                    if(fecha1 === ''){
                        fecha1 = 'todos';
                    }
                    if(fecha2 === ''){
                        fecha2 = 'todos';
                    }
                    $.ajax({
                        type: 'GET',
                        url: '${pageContext.request.contextPath}/reporteVentas/obtenerTotales/'+fecha1+'/'+fecha2+'.htm',
                        success: function (result) {
                            $('#totalesTabla').html(result);
                        }
                    }); 
                 }
                 obtenerModalidadesDeVenta();
                 obtenerListaClientes();
                 obtenerEstatusVenta();
                 obtenerVentasRegistradas();
                 obtenerTotales();
                 $('#modal-productos-venta').on('show.bs.modal', function (event) {
                    var button = $(event.relatedTarget);
                    var iddetalleventa = button.data('iddetalleventa');
                    $.ajax({
                        type:'GET',
                        url: '${pageContext.request.contextPath}/reporteVentas/obtenerProductosVenta/'+iddetalleventa+'.htm',
                        success: function(result){
                            $('#contenidoTablaProductosVenta').html(result);
                        }
                    }); 
                 });
                 $('#btn-buscar-venta').click(function(){
                    var iddv = $('#valor-busqueda-iddetalleventa').val();
                    var fecha = $('#valor-busqueda-fechadetalleventa').val();
                    var fechafin = $('#valor-busqueda-fechafindetalleventa').val();
                    var cliente = $('#valor-busqueda-cliente').val();
                    var modalidad = $('#valor-modalidadVenta-buscar').val();
                    var estatus = $('#valor-estatusVenta-buscar').val();   
                    
                    if(iddv === ''){
                        iddv = '0';
                    }
                    if(fecha === ''){
                        fecha = 'todos';
                    }
                    if(fechafin === ''){
                        fechafin = 'todos';
                    }
                    if(cliente === ''){
                        cliente = 'todos';
                    }
                    if(modalidad === ''){
                        modalidad = 'todos';
                    }
                    if(estatus === ''){
                        estatus = 'todos';
                    }
                    $.ajax({
                        type: 'GET',
                        url: '${pageContext.request.contextPath}/reporteVentas/obtenerVentasRegistradasFiltradas/'+iddv+'/'+fecha+'/'+fechafin+'/'+cliente+'/'+modalidad+'/'+estatus+'.htm',
                        success: function (result) {
                            $('#contenidoTabla').html(result);
                            obtenerTotales();
                        }
                    }); 
                 }); 
                 
                 $('#btn-abonar').click(function(){
                    var iddvabonar,abono;
                    iddvabonar = $('#id-detalleventa-abonar').val();
                    abono = $('#valor-abonar-venta').val();
                    $.ajax({
                        type: 'POST',
                        url: '${pageContext.request.contextPath}/reporteVentas/abonarAVenta/'+iddvabonar+'/'+abono+'.htm',
                        success: function (result) {
                            $('#msjAbonadoAVenta').html(result);
                            $('#modal-abonar-venta').modal('hide');
                            obtenerVentasRegistradas();
                            obtenerTotales();
                        }
                    }); 
                 });
                 
                 $('#btn-eliminar-producto-venta').click(function(){
                    iddetalleventa = $('#valor-id-dv-producto-eliminar').val();
                    idventa = $('#valor-id-venta-producto-eliminar').val();
                    $.ajax({
                        type: 'POST',
                        url: '${pageContext.request.contextPath}/reporteVentas/eliminarProductoDeVenta/'+idventa+'/'+iddetalleventa+'.htm',
                        success: function (result) {
                            $('#msjProductoEliminado').html(result);
                            $('#modal-eliminar-producto-venta').modal('hide');
                            $('#modal-productos-venta').modal('hide');
                            obtenerVentasRegistradas();
                            obtenerTotales();
                        }
                    }); 
                 });
                 $('#btn-modificar-venta').click(function(){
                    var v1,v2,v3,v4,v5,v6,v7;
                    v1 = $('#id-venta-modificar').val();
                    v2 = $('#fecha-venta-modificar').val();
                    v3 = $('#total-venta-modificar').val();
                    v4 = $('#abono-venta-modificar').val();
                    v5 = $('#cliente-venta-modificar').val();
                    v6 = $('#modalidad-venta-modificar').val();
                    v7 = $('#estatus-venta-modificar').val();
                    $.ajax({
                        type: 'POST',
                        url: '${pageContext.request.contextPath}/reporteVentas/modificarVenta/'+v1+'/'+v2+'/'+v3+'/'+v4+'/'+v5+'/'+v6+'/'+v7+'.htm',
                        success: function (result) {
                            $('#msjVentaModificada').html(result);
                            $('#modal-modificar-venta').modal('hide');                            
                            obtenerVentasRegistradas();
                            obtenerTotales();
                        }
                    }); 
                 });
                 
                 $('#btn-eliminar-venta').click(function(){
                 var v1;
                 v1 = $('#valor-idventa-eliminar').val();
                    $.ajax({
                        type: 'POST',
                        url: '${pageContext.request.contextPath}/reporteVentas/eliminarVenta/'+v1+'.htm',
                        success: function (result) {
                            $('#msjVentaEliminada').html(result);
                            $('#modal-eliminar-venta').modal('hide');                            
                            obtenerVentasRegistradas();
                            obtenerTotales();
                        }
                    }); 
                 });
            });
        </script>
    </body>
</html>
