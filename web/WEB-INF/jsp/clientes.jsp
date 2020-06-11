<%-- 
    Document   : clientes
    Created on : 30/08/2019, 02:25:59 PM
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
        <spring:url value="/resources/css/clientes.css" var="clientesCSS" />
        <link href="${clientesCSS}" rel="stylesheet">
        
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
                    <i class="fas fa-address-book"></i>
                    Clientes
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
                <div class="input-group mb-3 busqueda-cliente">
                    <input type="text" class="form-control" placeholder="No. de cliente" id="valor-busqueda-idcliente" />
                    <input type="text" class="form-control" placeholder="Nombre" id="valor-busqueda-cliente" />
                    <div class="input-group-append">
                        <button type="button" id="btn-buscar-cliente" class="btn btn-primary" title="Buscar"><i class="fas fa-search"></i></button>
                    </div>                    
                </div>
                <div class="curved-border-table">
                    <h4>Clientes Registrados</h4>
                    <div class="table-responsive">
                        <table class="table table-hover table-borderless">
                            <thead>
                                <tr>
                                    <th>No. de cliente</th>
                                    <th>Nombre</th>
                                    <th>Teléfono</th>
                                    <th>Correo</th>
                                    <th>Domicilio</th>
                                    <th>
                                        <button type="button" class="btn btn-success" data-toggle="modal" data-target="#modal-add-cliente" title="Agregar cliente">
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
                      <button type="button" class="btn btn-success" id="btn-registrar">Registrar</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="modal-mod-cliente" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Modificación de cliente</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="form-group">
                                <label for="id-cliente-modificar" class="col-form-label">No. de Cliente</label>
                                <input type="text" readonly="readonly" class="form-control" id="id-cliente-modificar">
                            </div> 
                            <div class="form-group">
                                <label for="nombreCliente-modificar" class="col-form-label">Nombre:</label>
                                <input type="text" class="form-control" id="nombreCliente-modificar">
                            </div>
                            <div class="form-group">
                                <label for="noTelefonico-modificar" class="col-form-label">Teléfono:</label>
                                <input type="text" class="form-control" id="noTelefonico-modificar">
                            </div>
                            <div class="form-group">
                                <label for="correo-modificar" class="col-form-label">Correo:</label>
                                <input type="text" class="form-control" id="correo-modificar">
                            </div>
                            <div class="form-group">
                                <label for="domicilio-modificar" class="col-form-label">Domicilio:</label>
                                <input type="text" class="form-control" id="domicilio-modificar">
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
        <div class="modal fade" id="modal-elim-cliente" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Eliminación de cliente</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="form-group">
                                <label class="col-form-label">
                                    ¿Esta seguro de que desea eliminar este cliente?
                                </label>
                                <input type="hidden" id="id-cliente-eliminar"/>
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
        <div class="modal fade" id="modal-compras-cliente" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Compras del cliente</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="table-responsive curved-border-table">
                            <table class="table table-hover table-borderless">
                                <thead>
                                    <tr>
                                        <th>Fecha</th>
                                        <th>Total</th>
                                        <th>Abono</th>
                                        <th>Modalidad</th>
                                        <th>Estatus</th>
                                        <th>Usuario</th>
                                    </tr>                            
                                </thead>
                                <tbody id="contenidoTablaCompras">                            
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
                        
        <span id="msjClienteAgregado">            
        </span>
        
        <div id="msjClienteModificado">
        </div>
        
        <div id="msjClienteEliminado">            
        </div>
                        
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <%-- Scripts de control de bootstrap --%>
        <spring:url value="/resources/js/clientesBootstrap.js" var="clientesBootstrap" />
        <script type="text/javascript" src="${clientesBootstrap}"></script>
        <%-- Scripts de control de Ajax --%>
        <script type="text/javascript">
            $(document).ready(function (){
                function obtenerClientes(){
                    $.ajax({
                        type: 'GET',
                        url: '${pageContext.request.contextPath}/clientes/clientesRegistrados.htm',
                        success: function (result) {
                            $('#contenidoTabla').html(result);
                        }
                    }); 
                }
                obtenerClientes();
                $('#btn-registrar').click(function(){
                    var nombre,telefono,correo,domicilio;
                    nombre = $('#nombreCliente-registrar').val();
                    telefono = $('#noTelefonico-registrar').val();
                    correo = $('#correo-registrar').val();
                    domicilio = $('#domicilio-registrar').val();
                    $.ajax({
                        type:'POST',
                        url: '${pageContext.request.contextPath}/clientes/registrarCliente/'+nombre+'/'+telefono+'/'+correo+'/'+domicilio+'.htm',
                        success: function (result){                        
                            obtenerClientes();
                            $('#modal-add-cliente').modal('hide');
                            $('#msjClienteAgregado').html(result);
                        }
                    });
                });
                $('#btn-modificar').click(function(){
                    var idcliente,nombre,telefono,correo,domicilio;
                    idcliente = $('#id-cliente-modificar').val();
                    nombre = $('#nombreCliente-modificar').val();
                    telefono = $('#noTelefonico-modificar').val();
                    correo = $('#correo-modificar').val();
                    domicilio = $('#domicilio-modificar').val();
                    $.ajax({
                        type:'POST',
                        url: '${pageContext.request.contextPath}/clientes/modificarCliente/'+idcliente+'/'+nombre+'/'+telefono+'/'+correo+'/'+domicilio+'.htm',
                        success: function(result){
                            obtenerClientes();
                            $('#modal-mod-cliente').modal('hide');
                            $('#msjClienteModificado').html(result);
                        }
                    });
                });
                $('#btn-eliminar').click(function(){
                    var idcliente = $('#id-cliente-eliminar').val();
                    $.ajax({
                        type:'POST',
                        url: '${pageContext.request.contextPath}/clientes/eliminarCliente/'+idcliente+'.htm',
                        success: function(result){
                            obtenerClientes();
                            $('#modal-elim-cliente').modal('hide');
                            $('#msjClienteEliminado').html(result);
                        }
                    }); 
                });
                $('#modal-compras-cliente').on('show.bs.modal', function (event) {
                    var button = $(event.relatedTarget);
                    var idcliente = button.data('idcliente');
                    $.ajax({
                        type:'GET',
                        url: '${pageContext.request.contextPath}/clientes/comprasCliente/'+idcliente+'.htm',
                        success: function(result){
                            $('#contenidoTablaCompras').html(result);
                        }
                    }); 
                 });
                $('#btn-buscar-cliente').click(function(){
                    var idcliente = $('#valor-busqueda-idcliente').val();
                    var cliente = $('#valor-busqueda-cliente').val();
                    if(idcliente === ''){
                        idcliente = '0';
                    }
                    if(cliente === ''){
                        cliente = 'todos';
                    }
                    $.ajax({
                        type: 'GET',
                        url: '${pageContext.request.contextPath}/clientes/filtraClientesRegistrados/'+idcliente+'/'+cliente+'.htm',
                        success: function (result) {
                            $('#contenidoTabla').html(result);
                        }
                    }); 
                });
            });
        </script>
    </body>
</html>
