<%-- 
    Document   : inicio
    Created on : 23/07/2019, 12:56:56 PM
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
        <spring:url value="/resources/css/inicio.css" var="inicioCSS" />
        <link href="${inicioCSS}" rel="stylesheet">
        
        <title>Venta Plata</title>
    </head>
    <body>
        <%
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
                    <i class="fas fa-home"></i>
                    Bienvenido(a)
                </h1>
                <div class="form-inline">
                    <form action="logout.htm" method="post">
                        ${sessionScope.login.getUsuario()}
                        <button class="btn btn-white botones" title="Salir">
                            <span><i class="fas fa-sign-out-alt"></i></span>
                        </button>                 
                    </form>                      
                </div>               
            </nav>            
            <div class="menu">                               
                <ul class="nav justify-content-center contenedor-cajas">
                    <a href="ventas.htm">
                        <li class="nav-item cajas">                        
                            <span class=""><i class="fas fa-cash-register"></i></span><br>
                            Ventas
                        </li>
                    </a>
                    
                    <a href="productos.htm">
                        <li class="nav-item cajas">
                        
                            <span class=""><i class="fas fa-cart-arrow-down"></i></span><br>
                            Productos                        
                        </li>
                    </a>
                    
                    <a href="clientes.htm">
                        <li class="nav-item cajas">                        
                            <span class=""><i class="fas fa-address-book"></i></span><br>
                            Clientes                      
                        </li>
                    </a>
                    
                    <a href="usuarios.htm">
                        <li class="nav-item cajas">                        
                            <span class=""><i class="fas fa-users"></i></span><br>
                            Usuarios
                        </li>  
                    </a>
                                      
                </ul>
            </div>  
        </div>   
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </body>
</html>
