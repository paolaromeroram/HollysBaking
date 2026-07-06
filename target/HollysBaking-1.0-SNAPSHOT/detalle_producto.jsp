<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="modelo.entidades.Producto" %>

<%
    Producto producto = (Producto) request.getAttribute("producto");
    if (producto == null) {
        response.sendRedirect("CatalogoServlet");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title><%= producto.getNombreProducto() %> - Holly's Baking</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        body { background-color: #f8f5f2; }
        .navbar-custom { background-color: #6F4E37; }
        .navbar-custom .navbar-brand, .navbar-custom .nav-link { color: white; }
        .product-detail {
            background: white;
            border-radius: 20px;
            padding: 40px;
            margin-top: 30px;
            box-shadow: 0 5px 20px rgba(0,0,0,0.1);
        }
        .product-img-large {
            height: 400px;
            background-color: #e9ecef;
            border-radius: 15px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 8rem;
            color: #6F4E37;
        }
        .price-large {
            font-size: 2.5rem;
            color: #6F4E37;
            font-weight: bold;
        }
        .btn-add-cart {
            background-color: #6F4E37;
            color: white;
            border: none;
            border-radius: 30px;
            padding: 15px 40px;
            font-size: 1.2rem;
            transition: all 0.3s;
        }
        .btn-add-cart:hover {
            background-color: #5a3f2d;
            color: white;
            transform: scale(1.05);
        }
        .btn-back {
            border-radius: 20px;
        }
    </style>
</head>
<body>

    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-custom">
        <div class="container">
            <a class="navbar-brand" href="CatalogoServlet">
                <i class="bi bi-shop"></i> Holly's Baking
            </a>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="CarritoServlet">
                            <i class="bi bi-cart3"></i> Carrito
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container">
        <div class="product-detail">
            <div class="row">
                <div class="col-md-6">
                    <div class="product-img-large">
                        <i class="bi bi-cake2"></i>
                    </div>
                </div>
                <div class="col-md-6 d-flex flex-column justify-content-center">
                    <span class="badge bg-secondary mb-3" style="width: fit-content;">
                        <%= producto.getCategoria() != null ? producto.getCategoria() : "Sin categoría" %>
                    </span>
                    <h1><%= producto.getNombreProducto() %></h1>
                    <p class="text-muted">Delicioso producto hecho con los mejores ingredientes seleccionados especialmente para ti.</p>
                    
                    <div class="price-large mb-4">S/ <%= producto.getPrecioVenta() %></div>
                    
                    <form action="CarritoServlet" method="POST">
                        <input type="hidden" name="accion" value="agregar">
                        <input type="hidden" name="id" value="<%= producto.getIdProducto() %>">
                        <button type="submit" class="btn btn-add-cart mb-3">
                            <i class="bi bi-cart-plus"></i> Agregar al Carrito
                        </button>
                    </form>
                    
                    <a href="CatalogoServlet" class="btn btn-outline-secondary btn-back">
                        <i class="bi bi-arrow-left"></i> Volver al Catálogo
                    </a>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>