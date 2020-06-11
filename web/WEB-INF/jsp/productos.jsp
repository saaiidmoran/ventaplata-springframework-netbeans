<%-- 
    Document   : productos
    Created on : 27/08/2019, 12:51:49 AM
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
        <spring:url value="/resources/css/productos.css" var="productosCSS" />
        <link href="${productosCSS}" rel="stylesheet">
        
        <title>Venta Plata</title>
    </head>
    <body>
        <%
            // Impide que se guarde el cache en el navegador y verifica si ya se inició sesión
            response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");
            if(session.getAttribute("login")==null){
                response.sendRedirect("index.htm");
            }
        %>
        
        <div class="contenedor-principal">
            <nav class="navbar navbar-light bg-light">
                <h1 class="navbar-brand mb-0 h1">
                    <i class="fas fa-cart-arrow-down"></i>
                    Productos
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
            <div class="container mt-4">
                <div class="input-group mb-3 busqueda-producto">
                    <input type="text" class="form-control" placeholder="No. de producto" id="valor-busqueda-idproducto" />
                    <input type="text" class="form-control" placeholder="Producto" id="valor-busqueda-producto" />
                    <select class="form-control" id="estatusProducto-buscar"></select>
                    <div class="input-group-append">
                        <button type="button" id="btn-buscar-producto" class="btn btn-primary" title="Buscar"><i class="fas fa-search"></i></button>
                    </div>                    
                </div>
                <div class="curved-border-table">
                    <h4>Productos Registrados</h4>
                    <div class="table-responsive">
                        <table class="table table-hover table-borderless">
                            <thead>
                                <tr>
                                    <th>No. Producto</th>
                                    <th>Producto</th>
                                    <th>Inversión</th>
                                    <th>Precio</th>
                                    <th>Fecha de Registro</th>
                                    <th>Estatus</th>
                                    <th>
                                        <button type="button" class="btn btn-success" data-toggle="modal" data-target="#modal-add-producto" title="Agregar producto">
                                            <i class="fas fa-plus"></i>
                                        </button>
                                    </th>
                                </tr>                            
                            </thead>
                            <tbody id="contenidoTabla">                            
                            </tbody>
                        </table>
                    </div>      
                </div>
            </div>
        </div>
                        
        <div class="modal fade" id="modal-add-producto" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Registro de producto</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="form-group">
                                <label for="producto-registrar" class="col-form-label">Producto:</label>
                                <input type="text" class="form-control" id="producto-registrar">
                            </div>
                            <div class="form-group">
                                <label for="inversion-registrar" class="col-form-label">Inversión:</label>
                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <div class="input-group-text ">$</div>
                                    </div>
                                    <input type="text" class="form-control" id="inversion-registrar" value="0.0">
                                </div>
                                
                            </div>
                            <div class="form-group">
                                <label for="precio-registrar" class="col-form-label">Precio:</label>
                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <div class="input-group-text ">$</div>
                                    </div>
                                    <input type="text" class="form-control" id="precio-registrar" value="0.0">
                                </div>                                
                            </div>
                            <div class="form-group">
                                <label for="fechaRegistro-registrar" class="col-form-label">Fecha:</label>
                                <input type="date" class="form-control" id="fechaRegistro-registrar">
                            </div>
                            <div class="form-group">
                                <label for="estatusProducto-registrar" class="col-form-label">Estatus:</label>
                                <select class="form-control" id="estatusProducto-registrar"></select>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                      <button type="button" class="btn btn-success" id="btn-registrar">Registrar</button>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="modal fade" id="modal-mod-producto" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Modificación de producto</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="form-group">
                                <label for="idproducto-modificar" class="col-form-label">No. Producto:</label>
                                <input type="text" readonly="readonly" class="form-control" id="id-producto-modificar">
                            </div>
                            <div class="form-group">
                                <label for="producto-modificar" class="col-form-label">Producto:</label>
                                <input type="text" class="form-control" id="producto-modificar">
                            </div>
                            <div class="form-group">
                                <label for="inversion-modificar" class="col-form-label">Inversión:</label>
                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <div class="input-group-text ">$</div>
                                    </div>
                                    <input type="text" class="form-control" id="inversion-modificar">
                                </div>                                
                            </div>
                            <div class="form-group">
                                <label for="precio-modificar" class="col-form-label">Precio:</label>
                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <div class="input-group-text ">$</div>
                                    </div>
                                    <input type="text" class="form-control" id="precio-modificar">
                                </div>                                
                            </div>
                            <div class="form-group">
                                <label for="fechaRegistro-modificar" class="col-form-label">Fecha:</label>
                                <input type="date" class="form-control" id="fechaRegistro-modificar">
                            </div>
                            <div class="form-group">
                                <label for="estatusProducto-modificar" class="col-form-label">Estatus:</label>
                                <select class="form-control" id="estatusProducto-modificar"></select>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                      <button type="button" class="btn btn-success" id="btn-modificar">Modificar</button>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="modal fade" id="modal-elim-producto" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Eliminación de producto</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="form-group">
                                <label class="col-form-label">
                                    ¿Esta seguro de que desea eliminar este producto?
                                </label>
                                <input type="hidden" id="id-producto-eliminar"/>
                            </div>                 
                        </form>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                      <button type="button" class="btn btn-danger" id="btn-eliminar">Eliminar</button>
                    </div>
                </div>
            </div>
        </div>
                        
        <span id="msjProductoAgregado">            
        </span>
        
        <div id="msjProductoModificado">
        </div>
        
        <div id="msjProductoEliminado">            
        </div>
        
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        
        <%-- 
            Scripts de control de bootstrap
        --%>
        
        <spring:url value="/resources/js/productosBootstrap.js" var="productosBootstrap" />
        <script type="text/javascript" src="${productosBootstrap}"></script>
        
        <%-- 
            Scripts de control de ajax
        --%>
        
        <script type="text/javascript">
             $(document).ready(function (){
                 
                 function obtenerProductos(){
                     $.ajax({
                        type: 'GET',
                        url: '${pageContext.request.contextPath}/productos/productosRegistrados.htm',
                        success: function (result) {
                            $('#contenidoTabla').html(result);
                        }
                    }); 
                 }
                 
                 function obtenerFechaActual(){
                     $.ajax({
                        type: 'GET',
                        url: '${pageContext.request.contextPath}/productos/obtenerFechaActual.htm',
                        success: function (result) {
                            $('#fechaRegistro-registrar').val(result);
                        }
                    }); 
                 }
                 
                 function obtenerEstatusProducto(){
                     $.ajax({
                        type: 'GET',
                        url: '${pageContext.request.contextPath}/estatusProducto/estatusProdictoRegistrados.htm',
                        success: function (result) {
                            $('#estatusProducto-registrar').html(result);
                            $('#estatusProducto-modificar').html(result);
                            $('#estatusProducto-buscar').html('<option></option>'+result);
                            $('#estatusProducto-buscar').val('');
                        }
                     });
                 }
                 obtenerProductos();
                 obtenerEstatusProducto();  
                 obtenerFechaActual();
                 
                 $('#btn-registrar').click(function(){
                    var producto,precioInv,precioVent,estatus,fechaProducto;
                    producto = $('#producto-registrar').val();
                    precioInv = $('#inversion-registrar').val();
                    precioVent = $('#precio-registrar').val();
                    estatus = $('#estatusProducto-registrar').val();
                    fechaProducto = $('#fechaRegistro-registrar').val();
                    $.ajax({
                        type:'POST',
                        url: '${pageContext.request.contextPath}/productos/registrarProducto/'+producto+'/'+precioInv+'/'+precioVent+'/'+fechaProducto+'/'+estatus+'.htm',
                        success: function (result){                        
                            obtenerProductos();
                            $('#modal-add-producto').modal('hide');
                            $('#msjProductoAgregado').html(result);
                        }
                    });
                });
                
                $('#btn-modificar').click(function(){
                    var idproducto,producto,precioInv,precioVent,estatus,fechaRegistro;
                    idproducto = $('#id-producto-modificar').val();
                    producto = $('#producto-modificar').val();
                    precioInv = $('#inversion-modificar').val();
                    precioVent = $('#precio-modificar').val();
                    estatus = $('#estatusProducto-modificar').val();
                    fechaRegistro = $('#fechaRegistro-modificar').val();
                    $.ajax({
                        type:'POST',
                        url: '${pageContext.request.contextPath}/productos/modificarProducto/'+idproducto+'/'+producto+'/'+precioInv+'/'+precioVent+'/'+fechaRegistro+'/'+estatus+'.htm',
                        success: function(result){
                            obtenerProductos();
                            $('#modal-mod-producto').modal('hide');
                            $('#msjProductoModificado').html(result);
                        }
                    });
                });
                
                $('#btn-eliminar').click(function(){
                    var idproducto = $('#id-producto-eliminar').val();
                    $.ajax({
                        type:'POST',
                        url: '${pageContext.request.contextPath}/productos/eliminarProducto/'+idproducto+'.htm',
                        success: function(result){
                            obtenerProductos();
                            $('#modal-elim-producto').modal('hide');
                            $('#msjProductoEliminado').html(result);
                        }
                    }); 
                });
                
                $('#btn-buscar-producto').click(function (){
                    var idproducto = $('#valor-busqueda-idproducto').val();
                    var producto = $('#valor-busqueda-producto').val();
                    var estatusproducto = $('#estatusProducto-buscar').val();
                    if(idproducto === ''){
                        idproducto = '0';
                    }
                    if(producto === ''){
                        producto = 'todos';
                    }
                    if(estatusproducto === ''){
                        estatusproducto = 'todos';
                    }
                    $.ajax({
                        type: 'GET',
                        url: '${pageContext.request.contextPath}/productos/filtraProductosRegistrados/'+idproducto+'/'+producto+'/'+estatusproducto+'.htm',
                        success: function (result) {
                            $('#contenidoTabla').html(result);
                        }
                    });
                    
                });
                
             });
        </script>
    </body>
</html>
