<%-- 
    Document   : login
    Created on : 1 jul. 2026, 9:33:43 p. m.
    Author     : Hola
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Holly's Baking</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        body {
            background-color: #f8f5f2;
        }
        .login-card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 5px 20px rgba(0,0,0,0.1);
        }
        .btn-brown {
            background-color: #6F4E37;
            color: white;
            border: none;
        }
        .btn-brown:hover {
            background-color: #5a3f2d;
            color: white;
        }
        .btn-catalogo {
            background-color: white;
            color: #6F4E37;
            border: 2px solid #6F4E37;
            border-radius: 25px;
            transition: all 0.3s;
        }
        .btn-catalogo:hover {
            background-color: #6F4E37;
            color: white;
        }
    </style>
</head>
<body class="bg-light">

    <div class="container d-flex justify-content-center align-items-center vh-100">
        
        <div class="card login-card p-4" style="width: 25rem;">
            <div class="card-body text-center">
                
                <h2 class="mb-2" style="color: #6F4E37;">
                    <i class="bi bi-shop"></i> Holly's Baking
                </h2>
                <p class="text-muted mb-4">Ingresa a tu cuenta</p>

                <form action="AuthServlet" method="POST">
                    
                    <div class="mb-3 text-start">
                        <label for="correo" class="form-label">Correo Electrónico</label>
                        <input type="email" class="form-control" id="correo" name="correo" required>
                    </div>
                    
                    <div class="mb-3 text-start">
                        <label for="clave" class="form-label">Contraseña</label>
                        <input type="password" class="form-control" id="clave" name="clave" required>
                    </div>
                    
                    <button type="submit" class="btn btn-brown w-100 mt-3 py-2">
                        <i class="bi bi-box-arrow-in-right"></i> Iniciar Sesión
                    </button>
                    
                </form>

                <!-- Mensajes -->
                <div class="mt-3">
                    <% if(request.getParameter("error") != null) { %>
                        <div class="alert alert-danger py-2" role="alert">
                            <small><i class="bi bi-exclamation-circle"></i> Correo o contraseña incorrectos.</small>
                        </div>
                    <% } %>

                    <% if(request.getParameter("registro") != null) { %>
                        <div class="alert alert-success py-2" role="alert">
                            <small><i class="bi bi-check-circle"></i> ¡Cuenta creada con éxito! Ahora puedes iniciar sesión.</small>
                        </div>
                    <% } %>
                </div>

                <hr class="my-3">

                <!-- Botón de Catálogo (DENTRO de la tarjeta) -->
                <a href="CatalogoServlet" class="btn btn-catalogo w-100 py-2 mb-3">
                    <i class="bi bi-shop-window"></i> Ver Catálogo de Productos
                </a>

                <div class="mt-2">
                    <small>¿No tienes cuenta? <a href="registro.jsp" style="color: #6F4E37;">Regístrate aquí</a></small>
                </div>
            </div>
        </div>

    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>